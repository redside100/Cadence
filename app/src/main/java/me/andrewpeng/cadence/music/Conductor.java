package me.andrewpeng.cadence.music;


import android.content.res.AssetFileDescriptor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import java.io.IOException;
import java.util.ArrayList;

import me.andrewpeng.cadence.core.MainView;
import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.objects.Beatmap;
import me.andrewpeng.cadence.objects.Note;

public class Conductor {
    int width, height;
    public static ArrayList<Note> activeNotes = new ArrayList<>();
    public MediaPlayer mp = new MediaPlayer();
    public int currentGeneralBeat = 0;
    public Beatmap beatmap;
    public double songLength;
    public double beatLength;
    public int noteTravelTicks;

    private Metronome metronome;

    public Conductor(int width, int height){
        this.width = width;
        this.height = height;

        // Test beatmap loading
        beatmap = new Beatmap("beatmaps/popcornfunk/popcornfunk.png", "beatmaps/popcornfunk/info.ini", "beatmaps/popcornfunk/popcornfunk.wav");

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
        this.noteTravelTicks = (int) (90 / beatmap.getNoteSpeed());

        // Set up metronome to know when to spawn the next note
        metronome = new Metronome(this);
        mp.start();

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
        return beatmap;
    }
    public MediaPlayer getMediaPlayer(){
        return mp;
    }
    public int getNoteTravelTicks(){
        return noteTravelTicks;
    }


    public void tick() {

        // Update metronome, and look for any notes that are going out of bounds
        metronome.update();
        ArrayList<Note> temp = new ArrayList<>(activeNotes);
        for (Note note : temp) {
            note.tick();
            if (note.getY1() > height) {
                activeNotes.remove(note);
            }
        }

    }

    public void nextNote(){
        // When called by the metronome, spawn the notes of the corresponding row of the beatmap,
        // increment the main beat count
        int beats[][] = beatmap.getBeats();
        if (currentGeneralBeat < beats.length){
            for (int i = 0; i < beats[currentGeneralBeat].length; i++){
                if (beats[currentGeneralBeat][i] == 1){
                    registerNote(i);
                }
            }
            currentGeneralBeat++;
        }
    }

    public void pause(){
        mp.pause();
    }
    public void resume(){
        mp.start();
    }

    // Just drawing notes here
    public void render(Canvas graphics, Paint paint){
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        for (Note note : activeNotes){
            paint.setAlpha(note.getAlpha());
            graphics.drawRect(new Rect(note.getX1(), note.getY1(), note.getX2(), note.getY2()), paint);
            paint.setAlpha(255);
        }
        paint.setStyle(Paint.Style.STROKE);
    }


    // Spawns a note within a lane
    public void registerNote(int lane){
        if (lane > 3) return;
        int laneWidth = width / 4;

        int x1 = (int) ((laneWidth * 0.07) + (laneWidth * lane));
        int x2 = (int) ((laneWidth * 0.93) + (laneWidth * lane));
        int y1 = -(height / 11);
        int y2 = 0;

        double yPad = 0.4;

        // - 1/11th of height to compensate for spawning the note slightly out of vision
        Note note = new Note(x1, y1, x2, y2, yPad, MainView.speed((int) (height * 0.75 - height / 11), noteTravelTicks));
        activeNotes.add(note);
    }

    public void touch(MotionEvent e, int pointerIndex){
        // Check if touch in bounds of note
        ArrayList<Note> temp = new ArrayList<>(activeNotes);
        for (Note note : temp){
            // Touch within note
            if (MainView.inBounds((int) e.getX(pointerIndex), (int) e.getX(pointerIndex), (int) e.getY(pointerIndex), (int) e.getY(pointerIndex),
                    note.getX1(), note.getX2(), note.getPadY1(), note.getPadY2())){

                int pad = (int) (Math.abs(Renderer.scoreY2 - Renderer.scoreY1) * 0.3);
                // Note within score area (0.3 padding timing window)
                if (MainView.inBounds(note.getX1(), note.getX2(), note.getY1(), note.getY2(), Renderer.scoreX1, Renderer.scoreX2, Renderer.scoreY1 - pad, Renderer.scoreY2 + pad)){
                    note.fadeOut(15);
                }

            }
        }
    }
}
