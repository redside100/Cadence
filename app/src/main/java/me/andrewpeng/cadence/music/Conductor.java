package me.andrewpeng.cadence.music;


import android.content.res.AssetFileDescriptor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import java.io.IOException;
import java.util.ArrayList;

import me.andrewpeng.cadence.core.MainView;
import me.andrewpeng.cadence.core.PlayerData;
import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.core.ScreenState;
import me.andrewpeng.cadence.managers.AnimatedTextManager;
import me.andrewpeng.cadence.managers.FadingImageManager;
import me.andrewpeng.cadence.objects.AnimatedText;
import me.andrewpeng.cadence.objects.Beatmap;
import me.andrewpeng.cadence.objects.FadingImage;
import me.andrewpeng.cadence.objects.FadingText;
import me.andrewpeng.cadence.objects.Note;
import me.andrewpeng.cadence.managers.ParticleManager;
import me.andrewpeng.cadence.objects.Pulse;
import me.andrewpeng.cadence.util.AssetLoader;
import me.andrewpeng.cadence.util.ImageAsset;

public class Conductor {
    int width, height;
    public static ArrayList<Note> activeNotes = new ArrayList<>();
    public static MediaPlayer mp = new MediaPlayer();
    public static int currentGeneralBeat = 0;
    public static Beatmap currentBeatmap;
    public double songLength;
    public double beatLength;
    public int noteTravelTicks;
    public static boolean playing = false;
    public static boolean preview = false;
    public static int volume = 100;
    public static int fxVolume = 100;

    public static int currentCombo = 0;
    public static int maxCombo = 0;
    public static int nextScore = 0;
    public static int currentScore = 0;

    public static int lastScore = 0;

    public static int perfCount = 0;
    public static int greatCount = 0;
    public static int goodCount = 0;
    public static int missCount = 0;

    private Metronome metronome;
    public static boolean paused = false;
    public static String[] names = {"popcornfunk", "shelter", "layitdown", "onetwo"};
    public static ArrayList<Beatmap> beatmapList = new ArrayList<>();

    public static String judgeDifficulty = "Normal";

    public Conductor(int width, int height){
        this.width = width;
        this.height = height;
    }

    // MUST INITIALIZE AFTER READER IS INITIALIZED
    public void initBeatmaps(){
        // Add all beatmaps to according to the names list
        for (String name : names){
            beatmapList.add(new Beatmap("beatmaps/" + name + "/" + name + ".png", "beatmaps/" + name + "/info.ini",
                    "beatmaps/" + name + "/" + name + ".wav", "beatmaps/" + name + "/preview.wav",
                    AssetLoader.getImageAsset("beatmaps/" + name + "/album.png"), AssetLoader.getImageAsset("beatmaps/" + name + "/background.png")));
        }
        // Sort beatmap list by difficulty (easy to hard)
    }

    public static ArrayList<Beatmap> getBeatmapList(){
        return beatmapList;
    }
    public double getSongLength(){
        return songLength;
    }
    public double getBeatLength(){
        return beatLength;
    }
    public int getBeat(){
        return currentGeneralBeat;
    }
    public Beatmap getBeatmap(){
        return currentBeatmap;
    }
    public MediaPlayer getMediaPlayer(){
        return mp;
    }
    public int getNoteTravelTicks(){
        return noteTravelTicks;
    }


    public void tick() {
        if (playing && !paused) {
            // Update metronome, and look for any notes that are going out of bounds
            metronome.update();
            ArrayList<Note> temp = new ArrayList<>(activeNotes);
            for (Note note : temp) {
                note.tick();
                if (note.getY1() > height) {
                    activeNotes.remove(note);
                } else if (note.getY1() > Renderer.scoreY2 && note.isValid()) {
                    // Miss
                    currentCombo = 0;
                    FadingImageManager.fadingImages.clear();
                    new FadingImage(AssetLoader.getImageAssetFromMemory(ImageAsset.SCORE0), Renderer.width / 2, (int) (Renderer.height * 0.6),
                            0, 15, 10).automate();
                    missCount++;
                    note.setValid(false);
                }
            }
            // Animate score
            if (currentScore < nextScore) {
                currentScore += 50;
            }
        }
    }

    public void nextNote(){

        // When called by the metronome, spawn the notes of the corresponding row of the beatmap,
        // increment the main beat count
        int beats[][] = currentBeatmap.getBeats();
        if (currentGeneralBeat < beats.length){
            for (int i = 0; i < beats[currentGeneralBeat].length; i++){
                if (beats[currentGeneralBeat][i] == 1){
                    registerNote(i);
                }
            }
            currentGeneralBeat++;
        }
        // Spawn pulse
            if (currentGeneralBeat % currentBeatmap.getSubBeats() == currentBeatmap.getPulse()){
                new Pulse(Renderer.scoreY1, MainView.speed(Renderer.height, 380), 10, Color.WHITE);
            }
    }

    public static int getMaxScore(){
        // Null check
        if (currentBeatmap != null){
            int count = 0;
            // Loop through each row
            for (int[] row : currentBeatmap.getBeats()){
                // Add 1 to count if a note in a row is 1
                for (int note : row){
                    if (note == 1){
                        count++;
                    }
                }
            }
            // Multiply by 300 (amount from perfect rating)
            return count * 300;
        }
        return -1;
    }

    public static void setVolume(int newVol){
        // Range from 10 to 100
        if (newVol >= 10 && newVol <= 100){
            volume = newVol;
        }
    }

    public static void setFxVolume(int newVol){
        // Range from 0 to 100
        if (newVol >= 0 && newVol <= 100){
            fxVolume = newVol;
        }
    }

    public static int getVolume(){
        return volume;
    }

    public static int getFxVolume(){
        return fxVolume;
    }

    public static void pause(){
        // Pause media player and flag
        mp.pause();
        paused = true;
    }

    public static void resume(){
        // Resume media player and flag
        mp.start();
        paused = false;
    }

    public void stop(){
        // Stop the media player, and reset
        mp.stop();
        mp.reset();

        // Remove completion listener
        mp.setOnCompletionListener(null);

        // Reset metronome, and flags
        metronome = null;
        playing = false;
        preview = false;
        paused = false;

        // Clear any notes that are still active
        activeNotes.clear();

        lastScore = nextScore;

        // Reset all variables (except for last score, and note ranking counts)
        currentGeneralBeat = 0;
        currentScore = 0;
        nextScore = 0;
        currentCombo = 0;
    }

    public void playPreview(Beatmap beatmap){
        this.currentBeatmap = beatmap;
        // Load beatmap preview into media player
        AssetFileDescriptor afd = beatmap.getPreviewAFD();
        try{
            if (mp.isPlaying()){
                mp.stop();
                mp.reset();
            }
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
        }catch(IOException e){}

        mp.setLooping(true);

        int max = 100;
        // Scale volume logarithmically, and set to the volume vaalue
        float scaledVolume = (float) (1 - (Math.log(max - volume) / Math.log(max)));
        mp.setVolume(scaledVolume, scaledVolume);

        mp.start();

        // Make sure to flag it as the preview
        preview = true;
    }

    public void loadMap(Beatmap beatmap){

        // Reset counts
        missCount = 0;
        goodCount = 0;
        greatCount = 0;
        perfCount = 0;
        maxCombo = 0;

        this.currentBeatmap = beatmap;
        // Load beatmap song into media player
        AssetFileDescriptor afd = beatmap.getSongAFD();
        try{
            if (!mp.isPlaying()){
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mp.prepare();
            }
        }catch(IOException e){}

        this.songLength = mp.getDuration() - beatmap.getStartOffset() - beatmap.getEndOffset();
        this.beatLength = 60000 / beatmap.getBPM() / beatmap.getSubBeats();
        this.noteTravelTicks = (int) Math.round((90 / beatmap.getNoteSpeed()));

        // Set up metronome to know when to spawn the next note
        metronome = new Metronome(this);
        mp.setLooping(false);

        // Scale volume logarithmically, and set to the volume value
        int max = 100;
        final float scaledVolume = (float) (1 - (Math.log(max - volume) / Math.log(max)));
        mp.setVolume(scaledVolume, scaledVolume);

        mp.start();

        // Set the completion listener to auto clean up and proceed to results after a song is finished
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                cleanUp();
            }
        });

        //For skipping music (debug purposes, leave commented)
//        mp.seekTo(170000);


        // Flag playing
        playing = true;

    }

    public void cleanUp(){
        stop();
        Renderer.changeState(ScreenState.RESULTS);
    }

    // Note drawing is now handled in renderer
    public void render(Canvas graphics, Paint paint){}


    // Spawns a note within a lane
    public void registerNote(int lane){

        // Do nothing if the lane isn't 0 1 2 3
        if (lane > 3) return;
        int laneWidth = width / 4;

        int x1 = (int) ((laneWidth * 0.07) + (laneWidth * lane));
        int x2 = (int) ((laneWidth * 0.93) + (laneWidth * lane));

        // - 1/11th of height to compensate for spawning the note slightly out of vision
        int y1 = -(height / 11);
        int y2 = 0;

        // Y padding ratio (so the user can hit the note without actually hitting the note)
        double yPad = 0.4;

        // Get the current subbeat to determine downbeats / offbeats
        int beat = currentGeneralBeat % (int) currentBeatmap.getSubBeats();
        int color;
        switch(beat){
            // Downbeat, red
            case 0:
                color = Color.rgb(255, 70, 70);
                break;
            // Offbeat, blue
            case 2:
                color = Color.rgb(70, 70, 255);
                break;
            // Neither, so yellow
            default:
                color = Color.YELLOW;
        }

        Note note = new Note(x1, y1, x2, y2, yPad, MainView.speed((int) (height * 0.75 - height / 11), noteTravelTicks), color);
        activeNotes.add(note);
    }


    public void touch(MotionEvent e, int pointerIndex){
        if (playing && !paused){
            // Check if touch in bounds of note
            ArrayList<Note> temp = new ArrayList<>(activeNotes);
            for (Note note : temp){
                // Touch within note
                if (MainView.inBounds((int) e.getX(pointerIndex), (int) e.getX(pointerIndex), (int) e.getY(pointerIndex), (int) e.getY(pointerIndex),
                        note.getX1(), note.getX2(), note.getPadY1(), note.getPadY2())){

                    int pad0 = (int) (Math.abs(Renderer.scoreY2 - Renderer.scoreY1) * 0.4);

                    // Note within score area (0.4 padding timing window)
                    if (scoreArea(note,pad0)) {

                        // Check if fading (repetition check due to random multi touch bug)
                        if (!note.fading){

                            // Calculate overlap percentage
                            double overlap = scorePercentage(note,pad0);

                            note.fadeOut(15);

                            // Add combo
                            currentCombo++;

                            //Save the user's max combo
                            if(currentCombo >= maxCombo) {
                                maxCombo = currentCombo;
                            }

                            // Display combo if above 6
                            if (currentCombo > 6){
                                // Clear previous combo text
                                for (AnimatedText text : AnimatedTextManager.texts){
                                    if (text instanceof FadingText){
                                        text.destroy();
                                    }
                                }

                                // Make it green if they have a high combo (100+)
                                int color = Color.WHITE;
                                if (currentCombo >= 100){
                                    color = Color.GREEN;
                                }

                                new FadingText("Combo: " + currentCombo, Renderer.width / 2, Renderer.height / 2, 18, color, 12, 15);
                            }

                            // Clear any previous fading images (perfect, great, good, miss)
                            FadingImageManager.fadingImages.clear();

                            // Add score and show rating for each note
                            // 0 - 80% overlap, 200 points

                            double pad = 0;
                            switch(judgeDifficulty){
                                case "Easy":
                                    pad = 0.04;
                                    break;
                                case "Hard":
                                    pad = -0.04;
                                    break;
                            }

                            // Easy difficulty shifts all overlap percentages down by 4%
                            // Hard difficulty shifts all overlap percentages up by 4%
                            // In the case of normal difficulty, the overlap percentages are not changed

                            if (overlap > 0 && overlap <= (0.8 - pad)){
                                nextScore += 200;
                                new FadingImage(AssetLoader.getImageAssetFromMemory(ImageAsset.SCORE100), Renderer.width / 2, (int) (Renderer.height * 0.6),
                                        0, 15, 10).automate();
                                goodCount++;

                            }else if (overlap > (0.8 - pad) && overlap <= (0.92 - pad)){ // 80 - 92% overlap, 250 points
                                nextScore += 250;
                                new FadingImage(AssetLoader.getImageAssetFromMemory(ImageAsset.SCORE200), Renderer.width / 2, (int) (Renderer.height * 0.6),
                                        0, 15, 10).automate();
                                greatCount++;

                            }else if (overlap > (0.92 - pad) && overlap <= 100){ // 92% - 100% overlap, 300 points
                                nextScore += 300;
                                new FadingImage(AssetLoader.getImageAssetFromMemory(ImageAsset.SCORE300), Renderer.width / 2, (int) (Renderer.height * 0.6),
                                        0, 15, 10).automate();
                                perfCount++;
                            }
                        }
                    }
                }
            }
        }
    }


    // Checks if a note is within a score area
    public static boolean scoreArea(Note note, int pad) {
        return MainView.inBounds(note.getX1(), note.getX2(), note.getY1(), note.getY2(), Renderer.scoreX1, Renderer.scoreX2, Renderer.scoreY1 - pad, Renderer.scoreY2 + pad);
    }
    // Gets the overlap percentage of a note and the score area
    public static double scorePercentage(Note note, int pad){
        return MainView.overlapPercent(note.getX1(), note.getX2(), note.getY1(), note.getY2(), Renderer.scoreX1, Renderer.scoreX2, Renderer.scoreY1, Renderer.scoreY2);

    }
}
