package me.andrewpeng.cadence.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

import me.andrewpeng.cadence.buttons.PauseButton;
import me.andrewpeng.cadence.managers.FadingImageManager;
import me.andrewpeng.cadence.managers.PulseManager;
import me.andrewpeng.cadence.music.Conductor;
import me.andrewpeng.cadence.managers.AnimatedTextManager;
import me.andrewpeng.cadence.objects.Beatmap;
import me.andrewpeng.cadence.managers.ButtonManager;
import me.andrewpeng.cadence.objects.FadingImage;
import me.andrewpeng.cadence.objects.FloatingText;
import me.andrewpeng.cadence.objects.Gradient;
import me.andrewpeng.cadence.managers.GradientManager;
import me.andrewpeng.cadence.objects.Note;
import me.andrewpeng.cadence.buttons.StateChangeButton;
import me.andrewpeng.cadence.buttons.VolumeControlButton;
import me.andrewpeng.cadence.objects.Particle;
import me.andrewpeng.cadence.managers.ParticleManager;
import me.andrewpeng.cadence.objects.Spinner;
import me.andrewpeng.cadence.managers.SpinnerManager;
import me.andrewpeng.cadence.util.AssetLoader;
import me.andrewpeng.cadence.util.ImageAsset;

public class Renderer {
    public static int width, height;
    public static boolean songStarting = false;
    public static int scoreX1, scoreX2, scoreY1, scoreY2;
    private static int lineAnimationProgress = 0;
    private static int scoreRectAnimationProgress = 0;
    public static ScreenState state = ScreenState.HOME;

    public static boolean transition = false;
    public static boolean fadeIn = false;
    public static boolean fadeOut = false;

    // Enable to show positioning lines
    public static boolean debug = false;

    public static int transitionAlpha = 0;
    public static ScreenState nextState;
    public static Conductor conductor;
    public static Spinner songSelectionSpinner = null;
    public static int songSelectionPosition = 0;

    public Renderer(Context context, int width, int height, ScreenState state, Conductor conductor){
        Renderer.width = width;
        Renderer.height = height;
        scoreX1 = (int) (width * 0.005);
        scoreX2 = (int) (width * 0.995);
        scoreY1 = (int) (height * 0.69);
        scoreY2 = (int) (height * 0.81);
        scoreRectAnimationProgress = scoreX1;
        Renderer.state = state;
        Renderer.conductor = conductor;
    }

    public void render(Canvas graphics, Paint paint){

        // Base background
        paint.setColor(Color.WHITE);
        graphics.drawRect(new Rect(0, 0, width, height), paint);
        switch(state){
            case HOME:

                // Outer space background + title
                graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.HOME_BACKGROUND), 0, 0, paint);
                centerText("CADENCE", graphics, width / 2, height / 4, paint, 45, Color.WHITE, 255);
                break;

            case MENU:

                // Outer space background, title, and footers
                graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.HOME_BACKGROUND), 0, 0, paint);
                centerText("CADENCE", graphics, width / 2, height / 4, paint, 45, Color.WHITE, 255);
                centerText("v1.0 Alpha", graphics, (int) (width * 0.155), (int) (height * 0.99), paint, 17, Color.WHITE, 255);
                centerText("ICS4U", graphics, (int) (width * 0.9), (int) (height * 0.99), paint, 17, Color.WHITE, 255);
                break;

            case SETTINGS:

                // Outer space background, title, and options
                graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.HOME_BACKGROUND), 0, 0, paint);
                centerText("SETTINGS", graphics, width / 2, height / 4, paint, 45, Color.WHITE, 255);
                writeText("Music Volume", graphics, (int) (width * 0.15), (int) (height * 0.3), paint, 20, Color.WHITE);
                writeText("FX Volume", graphics, (int) (width * 0.15), (int) (height * 0.4), paint, 20, Color.WHITE);
                writeText("Judge Difficulty", graphics, (int) (width * 0.15), (int) (height * 0.5), paint, 20, Color.WHITE);
                writeText("Erase Data (!)", graphics, (int) (width * 0.15), (int) (height * 0.6), paint, 20, Color.rgb(255, 100, 100));
                writeText("Misc", graphics, (int) (width * 0.15), (int) (height * 0.7), paint, 20, Color.WHITE);

                // Volume display
                centerText(Conductor.getVolume() + "", graphics, (int) (width * 0.75), (int) (height * 0.315), paint, 20, Color.WHITE, 255);

                // FX vol display
                centerText(Conductor.getFxVolume() + "", graphics, (int) (width * 0.75), (int) (height * 0.415), paint, 20, Color.WHITE, 255);
                break;

            case CREDITS:

                // Outer space background, title, and credits
                graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.HOME_BACKGROUND), 0, 0, paint);
                centerText("CREDITS", graphics, width / 2, height / 4, paint, 45, Color.WHITE, 255);
                centerText("PROGRAMMING", graphics, width / 2, (int) (height * 0.35), paint, 20, Color.WHITE, 255);
                centerText("Andrew Peng, Isaac Leung", graphics, width / 2, (int) (height * 0.4), paint, 15, Color.WHITE, 255);
                centerText("STORY", graphics, width / 2, (int) (height * 0.5), paint, 20, Color.WHITE, 255);
                centerText("Zelia Fang", graphics, width / 2, (int) (height * 0.55), paint, 15, Color.WHITE, 255);
                centerText("MANAGER", graphics, width / 2, (int) (height * 0.65), paint, 20, Color.WHITE, 255);
                centerText("Gordon Roller", graphics, width / 2, (int) (height * 0.7), paint, 15, Color.WHITE, 255);
                break;


            case SONG_SELECTION:

                graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.HOME_BACKGROUND), 0, 0, paint);
                graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.BLACK_BACKDROP), 0, height / 2, paint);

                // Make sure to render spinners BEFORE the border, so that the text will be rendered behind if going out of bounds
                SpinnerManager.render(graphics, paint);

                graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.SPINNER_BORDER), 0, height / 2, paint);

                // Note count
                centerBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.MUSIC_NOTE_ICON), graphics, (int) (width * 0.08), (int) (height * 0.05), paint);
                writeText(GameValues.getMusicNotes() + "/" + GameValues.getNextMusicNoteGoal(), graphics, (int) (width * 0.15), (int) (height * 0.065), paint, 20, Color.WHITE);

                // Song name and artist
                if (songSelectionSpinner != null){

                    Beatmap currentBeatmap = Conductor.getBeatmapList().get(songSelectionSpinner.getPosition());
                    writeText(currentBeatmap.getName(), graphics, (int) (width * 0.025), (int) (height * 0.45), paint, 16, Color.WHITE);
                    writeText(currentBeatmap.getArtist(), graphics, (int) (width * 0.025), (int) (height * 0.49), paint, 16, Color.WHITE);

                    paint.setTextAlign(Paint.Align.RIGHT);
                    String difficulty = "";
                    int color = 0;
                    switch(currentBeatmap.getDifficulty()){
                        case 0:
                            difficulty = "Novice";
                            color = Color.MAGENTA;
                            break;
                        case 1:
                            difficulty = "Easy";
                            color = Color.GREEN;
                            break;
                        case 2:
                            difficulty = "Medium";
                            color = Color.YELLOW;
                            break;
                        case 3:
                            difficulty = "Hard";
                            color = Color.RED;
                            break;
                    }
                    writeText(difficulty, graphics, (int) (width * 0.975), (int) (height * 0.45), paint, 16, color);
                    writeText("Grade: N/A", graphics, (int) (width * 0.975), (int) (height * 0.49), paint, 16, Color.WHITE);
                    paint.setTextAlign(Paint.Align.LEFT);
                }
                break;
            case PLAY:

                // Probably gonna change this later TODO
                // Draw beatmap background
                if (conductor.currentBeatmap != null){
                    Bitmap background = conductor.currentBeatmap.getBackgroundBitmap();
                    graphics.drawBitmap(background, 0, 0, paint);
                }else{
                    graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.PLAY_BACKGROUND), 0, 0, paint);
                }

                // Lines
                paint.setColor(Color.WHITE);
                paint.setStrokeWidth(MainView.scale((float) 1.18, graphics));
                for (int i = 0; i < 3; i++){
                    int quarter = width / 4;
                    int lineHeight = height;
                    if (songStarting){
                        lineHeight = (int) (lineAnimationProgress - ((height * 0.1) * i));
                    }
                    graphics.drawLine(quarter + (quarter * i), 0, quarter + (quarter * i), lineHeight, paint);
                }

                // Score area
                paint.setStyle(Paint.Style.STROKE);
                if (songStarting){
                    graphics.drawRect(new Rect(scoreX1, scoreY1, scoreRectAnimationProgress, scoreY2), paint);
                }else{
                    graphics.drawRect(new Rect(scoreX1, scoreY1, scoreX2, scoreY2), paint);
                }

                for (Note note : Conductor.activeNotes){
                    paint.setStyle(Paint.Style.FILL);

                    // Set to note color
                    paint.setColor(note.getColor());
                    paint.setAlpha(note.getAlpha());

                    // Draw a rounded rectangle for the note
                    float round = 0.1F;
                    graphics.drawRoundRect(new RectF(note.getX1(), note.getY1(), note.getX2(), note.getY2()),
                            (note.getX2() - note.getX1()) * round, (note.getY2() - note.getY1()) * round, paint);

                    // Draw black outline for the note
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.BLACK);

                    // Set alpha again (this must be done for some reason)
                    paint.setAlpha(note.getAlpha());
                    graphics.drawRoundRect(new RectF(note.getX1(), note.getY1(), note.getX2(), note.getY2()),
                            (note.getX2() - note.getX1()) * round, (note.getY2() - note.getY1()) * round, paint);

                    // Reset paint color to white and alpha to max
                    paint.setColor(Color.WHITE);
                    paint.setAlpha(255);
                }
                // Reset paint style to fill
                paint.setStyle(Paint.Style.FILL);

                // Score Value
                centerText(Conductor.currentScore + "", graphics,width/2,height/16,paint,18, Color.WHITE, 255);
                break;

            case RESULTS:
                graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.HOME_BACKGROUND),0,0,paint);
                centerText(Conductor.currentBeatmap.getName(),graphics, width/2,height/16,paint,15,Color.WHITE,255);
                centerText(Conductor.currentBeatmap.getArtist(),graphics, width/2,height/15,paint,15,Color.WHITE,255);

                double percentage = (double) Conductor.lastScore / Conductor.getMaxScore();

                if(Conductor.currentScore >= 10000) {
                    new FadingImage(AssetLoader.getImageAssetFromMemory(ImageAsset.RANKINGS),width/2,height/4,0).fadeIn(8);
                }
                else if(Conductor.currentScore < 10000 && Conductor.currentScore >= 9000) {
                    new FadingImage(AssetLoader.getImageAssetFromMemory(ImageAsset.RANKINGA),width/2,height/4,0).fadeIn(8);
                }
                else if(Conductor.currentScore < 9000 && Conductor.currentScore >= 8000) {
                    new FadingImage(AssetLoader.getImageAssetFromMemory(ImageAsset.RANKINGB),width/2,height/4,0).fadeIn(8);
                }
                else if(Conductor.currentScore < 8000 && Conductor.currentScore >= 7000) {
                    new FadingImage(AssetLoader.getImageAssetFromMemory(ImageAsset.RANKINGC),width/2,height/4,0).fadeIn(8);
                }
                else {
                    new FadingImage(AssetLoader.getImageAssetFromMemory(ImageAsset.RANKINGD),width/2,height/4,0).fadeIn(8);
                }

                //TODO fix positions
                writeText(percentage + "", graphics, width/2,height/2,paint,15,Color.WHITE);
                writeText("Perfect: " + Conductor.perfCount, graphics,width/2,height/2,paint,15,Color.WHITE);
                writeText("Great: " + Conductor.greatCount, graphics,width/2,height/3,paint,15,Color.WHITE);
                writeText("Good: " + Conductor.goodCount + "", graphics,width/2,height/4,paint,15,Color.WHITE);
                writeText("Miss: " + Conductor.missCount + "", graphics,width/2,height/5,paint,15,Color.WHITE);
                writeText("Max Combo: " + Conductor.maxCombo + "", graphics,width/2,height/6,paint,15,Color.WHITE);

        }

        // Render animated text objects and buttons
        FadingImageManager.render(graphics, paint);
        AnimatedTextManager.render(graphics, paint);
        ButtonManager.render(graphics, paint);
        GradientManager.render(graphics, paint);
        ParticleManager.render(graphics, paint);
        PulseManager.render(graphics, paint);

        // Check for transitioning process (always last, since the white rectangle should draw over everything)
        if (transition){
            // White screen fade
            paint.setColor(Color.WHITE);
            Paint.Style old = paint.getStyle();
            paint.setStyle(Paint.Style.FILL);
            int oldAlpha = paint.getAlpha();
            paint.setAlpha(transitionAlpha);
            graphics.drawRect(new Rect(0, 0, width, height), paint);
            paint.setAlpha(oldAlpha);
            paint.setStyle(old);
        }

        // Debug lines (for width and height scale)
        if (debug){
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(MainView.scale((float) 0.5, graphics));
            for (int i = 0; i <= 100; i += 5){
                int position = (int) (width * (double) i / 100);
                int hPosition = (int) (height * (double) i / 100);
                graphics.drawLine(position, 0, position, height, paint);
                centerText((double) i / 100 + "", graphics, position, (int) (height * 0.04), paint, 5, Color.BLACK, 255);
                graphics.drawLine(0, hPosition, width, hPosition, paint);
                centerText((double) i / 100 + "", graphics, (int) (width * 0.02), hPosition, paint, 5, Color.BLACK, 255);
            }
        }

    }

    public void tick(){

        // Handle screen transitioning
        int transitionInc = 15;
        if (transition){
            // Check fading in
            if (fadeIn){
                // Increase alpha of transition overlay
                if (transitionAlpha + transitionInc < 255){
                    transitionAlpha += transitionInc;
                }else{
                    // Once alpha is max, switch to the next screen state and start fading out
                    transitionAlpha = 255;
                    fadeIn = false;
                    fadeOut = true;
                    next(nextState);
                }

                // Check fading out
            }else if (fadeOut){

                // Decrease alpha of transition overlay
                if (transitionAlpha - transitionInc > 0){
                    transitionAlpha -= transitionInc;
                }else{
                    // Once alpha is 0, end the transition process, and re enable touch
                    transitionAlpha = 0;
                    fadeOut = false;
                    transition = false;
                    MainView.enableTouch();
                }
            }
        }

        if (songStarting){
            if (lineAnimationProgress < height * 1.2){
                lineAnimationProgress += MainView.speed(height, 90);
                if (scoreRectAnimationProgress < scoreX2){
                    scoreRectAnimationProgress += MainView.speed(scoreX2 - scoreX1, 90);
                }
            }else{
                songStarting = false;
                lineAnimationProgress = 0;
                scoreRectAnimationProgress = 0;
            }
        }

        // Don't forget to tick managers
        AnimatedTextManager.tick();
        ButtonManager.tick();
        GradientManager.tick();
        ParticleManager.tick();
        SpinnerManager.tick();
        FadingImageManager.tick();
        PulseManager.tick();

    }

    // This touch event is for action down
    public static void touch(MotionEvent e){
        ButtonManager.touch(e);

        switch(state){
            case HOME:
                changeState(ScreenState.MENU);
                break;
            case CUTSCENE:
                // TODO
                break;
        }
    }
    public static void changeState(ScreenState newState){
        nextState = newState;
        transition = true;
        fadeIn = true;
        MainView.disableTouch();
    }


    // Handle on state change events (create buttons, etc.)
    public static void next(ScreenState newState){
        AnimatedTextManager.texts.clear();
        ButtonManager.buttons.clear();
        GradientManager.gradients.clear();
        ParticleManager.particles.clear();
//        ScoreMessageManager.scoreMessages.clear();
        SpinnerManager.spinners.clear();
        FadingImageManager.fadingImages.clear();
        PulseManager.pulses.clear();


        if (conductor.playing || conductor.preview){
            conductor.stop();
        }
        state = newState;
        switch(newState){

            case HOME:

                // Floating text
                new FloatingText("< Tap To Start >", width / 2, (int) (height * 0.8), 20, Color.WHITE, 240, (int) (height * 0.01), 255);

                break;

            case MENU:

                // Menu buttons
                new StateChangeButton(AssetLoader.getImageAssetFromMemory(ImageAsset.SONG_SELECTION_BUTTON),width / 2, (int) (height * 0.4), 255, ScreenState.SONG_SELECTION);
                new StateChangeButton(AssetLoader.getImageAssetFromMemory(ImageAsset.SETTINGS_BUTTON), (int) (width * 0.26), (int) (height * 0.68), 255, ScreenState.SETTINGS);
                new StateChangeButton(AssetLoader.getImageAssetFromMemory(ImageAsset.CREDITS_BUTTON), (int) (width * 0.74), (int) (height * 0.68), 255, ScreenState.CREDITS);
                break;

            case CREDITS:

                // Back to menu button
                new StateChangeButton(AssetLoader.getImageAssetFromMemory(ImageAsset.OK_BUTTON), width / 2, (int) (height * 0.85), 255, ScreenState.MENU);
                break;

            case SETTINGS:

                // Back to menu button
                new StateChangeButton(AssetLoader.getImageAssetFromMemory(ImageAsset.OK_BUTTON), width / 2, (int) (height * 0.85), 255, ScreenState.MENU);

                // Music volume control buttons
                new VolumeControlButton(AssetLoader.getImageAssetFromMemory(ImageAsset.LEFT_ARROW_BUTTON), (int) (width * 0.62), (int) (height * 0.285), 255, false, false);
                new VolumeControlButton(AssetLoader.getImageAssetFromMemory(ImageAsset.RIGHT_ARROW_BUTTON), (int) (width * 0.88), (int) (height * 0.285), 255, true, false);

                // FX volume control buttons
                new VolumeControlButton(AssetLoader.getImageAssetFromMemory(ImageAsset.LEFT_ARROW_BUTTON), (int) (width * 0.62), (int) (height * 0.385), 255, false, true);
                new VolumeControlButton(AssetLoader.getImageAssetFromMemory(ImageAsset.RIGHT_ARROW_BUTTON), (int) (width * 0.88), (int) (height * 0.385), 255, true, true);
                break;

            case SONG_SELECTION:

                // For now, just have a button that plays the only beatmap available (popcorn funk)
                //new StateChangeButton(AssetLoader.getImageAssetFromMemory(ImageAsset.OK_BUTTON), width / 2, height / 2, 255, ScreenState.PLAY);

                // Get all the available beatmaps, and compile their names into a list
                ArrayList<Beatmap> maps = Conductor.getBeatmapList();

                //Sort by difficulty
                for(int i = 0;i < maps.size();i ++) {
                    int smalldiff = i;
                    for(int j = i + 1;j < maps.size(); j++) {
                        if(maps.get(j).getDifficulty() < maps.get(smalldiff).getDifficulty()) {
                            smalldiff = j;
                        }
                    }
                    if(smalldiff != i) {
                        Beatmap temp = maps.get(i);
                        maps.set(i,maps.get(smalldiff));
                        maps.set(smalldiff, temp);
                    }
                }

                String[] list = new String[maps.size()];

                for (int i = 0; i < Conductor.getBeatmapList().size(); i++){
                    list[i] = maps.get(i).getName();
                }

                // Create a new spinner with the names
                songSelectionSpinner = new Spinner(0, (int) (height * 0.56), width, height, list);

                // Be sure the update the spinner to make the conductor play the preview
                updateSpinner();

                // Back button
                new StateChangeButton(AssetLoader.getImageAssetFromMemory(ImageAsset.RIGHT_ARROW_BUTTON), (int) (width * 0.92), (int) (height * 0.05), 255, ScreenState.PLAY);

                break;

            case PLAY:

                //Creates a set of 16 particles to be used for animation
                /*for(int i = 0; i <= 16; i++) {
                    new Particle(AssetLoader.getImageAssetFromMemory(ImageAsset.LEFT_ARROW_BUTTON),width*0,(int)(height*0.45),0);
                }*/
                // TODO fix everything ;-;
//                String name = "popcornfunk";
//                Beatmap beatmap = new Beatmap("beatmaps/" + name + "/" + name + ".png", "beatmaps/" + name + "/info.ini", "beatmaps/" + name + "/" + name + ".wav");
//                conductor.loadMap(beatmap);

                Beatmap beatmap = Conductor.getBeatmapList().get(songSelectionPosition);
                conductor.loadMap(beatmap);

                //Creates the gradients that will appear if a finger has touched the score area
                new Gradient(AssetLoader.getImageAssetFromMemory(ImageAsset.GRADIENT), width*0,(int)(height * 0.497),9,0, false);
                new Gradient(AssetLoader.getImageAssetFromMemory(ImageAsset.GRADIENT), width/4,(int)(height * 0.497),9,0, false);
                new Gradient(AssetLoader.getImageAssetFromMemory(ImageAsset.GRADIENT), width/2,(int)(height * 0.497),9,0, false);
                new Gradient(AssetLoader.getImageAssetFromMemory(ImageAsset.GRADIENT), 3*width/4,(int)(height * 0.497),9,0, false);

                //new StateChangeButton(AssetLoader.getImageAssetFromMemory(ImageAsset.LEFT_ARROW_BUTTON), (int) (width * 0.08), (int) (height * 0.05), 255, ScreenState.HOME);
                new PauseButton(AssetLoader.getImageAssetFromMemory(ImageAsset.PAUSE_BUTTON),(int)(width*0.08),(int)(height*0.05),255);

                songStarting = true;
                break;

            case RESULTS:
                //Returns user to the song selection after the results
                new StateChangeButton(AssetLoader.getImageAssetFromMemory(ImageAsset.OK_BUTTON),width / 2, (int) (height * 0.85),255, ScreenState.SONG_SELECTION);
        }
    }

    public static void updateSpinner(){
        if (songSelectionSpinner != null){
            int position = songSelectionSpinner.getPosition();
            songSelectionPosition = position;
            Beatmap currentBeatmap = Conductor.getBeatmapList().get(position);
            conductor.playPreview(currentBeatmap);
            for (FadingImage image : FadingImageManager.fadingImages){
                image.fadeOut(20);
            }
            new FadingImage(currentBeatmap.getAlbumBitmap(), width / 2, (int) (height * 0.25), 0).fadeIn(20);
        }
    }

    public static void centerText(String text, Canvas graphics, int x, int y, Paint paint, int textSize, int color, int alpha){

        Paint.Style oldStyle = paint.getStyle();
        paint.setStyle(Paint.Style.FILL);
        // Modify text size and color
        float old = paint.getTextSize();
        int oldColor = paint.getColor();
        paint.setColor(color);
        float scaledTextSize = MainView.scale(textSize, graphics);
        paint.setTextSize(scaledTextSize);
        paint.setShadowLayer(20, 0, 0, Color.BLACK);
        // Get bounds
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Centered x and y coords according to bounds
        x -= bounds.width() / 2;
        y -= bounds.height() / 2;

        // Draw text (now centered)
        int oldAlpha = paint.getAlpha();
        paint.setAlpha(alpha);
        graphics.drawText(text, x, y, paint);

        paint.setAlpha(oldAlpha);

        // Reset paint
        paint.setTextSize(old);
        paint.setColor(oldColor);
        paint.setStyle(oldStyle);
    }

    public static void writeText(String text, Canvas graphics, int x, int y, Paint paint, int textSize, int color){
        // Modify text size and color
        float old = paint.getTextSize();
        int oldColor = paint.getColor();
        paint.setColor(color);
        float scaledTextSize = MainView.scale(textSize, graphics);
        paint.setTextSize(scaledTextSize);
        paint.setShadowLayer(20, 0, 0, Color.BLACK);

        // Draw text (now centered)
        graphics.drawText(text, x, y, paint);

        // Reset paint
        paint.setTextSize(old);
        paint.setColor(oldColor);
    }

    // TODO
    public static void centerBitmap(Bitmap bitmap, Canvas graphics, int x, int y, Paint paint){
        int newX = x - (bitmap.getWidth() / 2);
        int newY = y - (bitmap.getHeight() / 2);
        graphics.drawBitmap(bitmap, newX, newY, paint);
    }
}