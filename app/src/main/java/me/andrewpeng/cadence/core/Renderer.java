package me.andrewpeng.cadence.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import me.andrewpeng.cadence.music.Conductor;
import me.andrewpeng.cadence.objects.AnimatedText;
import me.andrewpeng.cadence.objects.AnimatedTextManager;
import me.andrewpeng.cadence.objects.Beatmap;
import me.andrewpeng.cadence.objects.Button;
import me.andrewpeng.cadence.objects.ButtonManager;
import me.andrewpeng.cadence.objects.FloatingText;
import me.andrewpeng.cadence.objects.Note;
import me.andrewpeng.cadence.objects.StateChangeButton;
import me.andrewpeng.cadence.objects.VolumeControlButton;
import me.andrewpeng.cadence.util.AssetLoader;
import me.andrewpeng.cadence.util.ImageAsset;

public class Renderer {
    public static int width, height;
    public static int scoreX1, scoreX2, scoreY1, scoreY2;
    public static ScreenState state = ScreenState.HOME;

    public static boolean transition = false;
    public static boolean fadeIn = false;
    public static boolean fadeOut = false;

    // Enable to show positioning lines
    public static boolean debug = false;

    public static int transitionAlpha = 0;
    public static ScreenState nextState;
    public static Conductor conductor;

    public Renderer(Context context, int width, int height, ScreenState state, Conductor conductor){
        this.width = width;
        this.height = height;
        scoreX1 = (int) (width * 0.005);
        scoreX2 = (int) (width * 0.995);
        scoreY1 = (int) (height * 0.69);
        scoreY2 = (int) (height * 0.81);
        this.state = state;
        this.conductor = conductor;
    }

    public void render(Canvas graphics, Paint paint){

        // Background
        paint.setColor(Color.WHITE);
        graphics.drawRect(new Rect(0, 0, width, height), paint);
        switch(state){
            case HOME:

                // Outer space background + title
                graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.HOME_BACKGROUND), 0, 0, paint);
                centerText("Cadence", graphics, width / 2, height / 4, paint, 30, Color.WHITE);
                break;

            case MENU:

                // Outer space background, title, and footers
                graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.HOME_BACKGROUND), 0, 0, paint);
                centerText("Cadence", graphics, width / 2, height / 4, paint, 30, Color.WHITE);
                centerText("v 1.0 Alpha", graphics, (int) (width * 0.13), (int) (height * 0.99), paint, 10, Color.WHITE);
                centerText("ICS4U", graphics, (int) (width * 0.92), (int) (height * 0.99), paint, 10, Color.WHITE);
                break;

            case SETTINGS:

                // Outer space background, title, and options
                graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.HOME_BACKGROUND), 0, 0, paint);
                centerText("Settings", graphics, width / 2, height / 4, paint, 30, Color.WHITE);
                writeText("Music Volume", graphics, (int) (width * 0.15), (int) (height * 0.3), paint, 12, Color.WHITE);
                writeText("FX Volume", graphics, (int) (width * 0.15), (int) (height * 0.4), paint, 12, Color.WHITE);
                writeText("Judge Difficulty", graphics, (int) (width * 0.15), (int) (height * 0.5), paint, 12, Color.WHITE);
                writeText("Erase Data (!)", graphics, (int) (width * 0.15), (int) (height * 0.6), paint, 12, Color.rgb(255, 100, 100));
                writeText("Misc", graphics, (int) (width * 0.15), (int) (height * 0.7), paint, 12, Color.WHITE);

                // Volume display
                centerText(Conductor.getVolume() + "", graphics, (int) (width * 0.75), (int) (height * 0.315), paint, 12, Color.WHITE);

                // FX vol display
                centerText(Conductor.getFxVolume() + "", graphics, (int) (width * 0.75), (int) (height * 0.415), paint, 12, Color.WHITE);
                break;

            case CREDITS:

                // Outer space background, title, and credits
                graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.HOME_BACKGROUND), 0, 0, paint);
                centerText("Credits", graphics, width / 2, height / 4, paint, 30, Color.WHITE);
                centerText("Programming", graphics, width / 2, (int) (height * 0.35), paint, 14, Color.WHITE);
                centerText("Andrew Peng, Isaac Leung", graphics, width / 2, (int) (height * 0.4), paint, 10, Color.WHITE);
                centerText("Story", graphics, width / 2, (int) (height * 0.5), paint, 14, Color.WHITE);
                centerText("Zelia Fang", graphics, width / 2, (int) (height * 0.55), paint, 10, Color.WHITE);
                centerText("Manager", graphics, width / 2, (int) (height * 0.65), paint, 14, Color.WHITE);
                centerText("Gordon Roller", graphics, width / 2, (int) (height * 0.7), paint, 10, Color.WHITE);
                break;


            case SONG_SELECTION:

                graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.HOME_BACKGROUND), 0, 0, paint);
                centerBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.MUSIC_NOTE_ICON), graphics, (int) (width * 0.08), (int) (height * 0.05), paint);
               // writeText(GameValues.getMusicNotes() + "/" + GameValues.getNextMusicNoteGoal(), graphics, (int) (width * 0.15), (int) (height * 0.065), paint, 12, Color.WHITE);
                writeText(GameValues.getMusicNotes() + "", graphics, (int) (width * 0.15), (int) (height * 0.065), paint, 12, Color.WHITE);

                break;
            case PLAY:

                // Probably gonna change this later TODO

                graphics.drawBitmap(AssetLoader.getImageAssetFromMemory(ImageAsset.HOME_BACKGROUND), 0, 0, paint);

                // Lines
                paint.setColor(Color.WHITE);
                paint.setStrokeWidth(MainView.scale((float) 1.2, graphics));
                for (int i = 0; i < 3; i++){
                    int quarter = width / 4;
                    graphics.drawLine(quarter + (quarter * i), 0, quarter + (quarter * i), height, paint);
                }

                // Score area
                paint.setStyle(Paint.Style.STROKE);
                graphics.drawRect(new Rect(scoreX1, scoreY1, scoreX2, scoreY2), paint);

                if (conductor.playing){
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.WHITE);
                    for (Note note : conductor.activeNotes){
                        paint.setAlpha(note.getAlpha());
                        graphics.drawRect(new Rect(note.getX1(), note.getY1(), note.getX2(), note.getY2()), paint);
                        paint.setAlpha(255);
                    }
                    paint.setStyle(Paint.Style.STROKE);
                }
                break;
        }

        // Render animated text objects and buttons
        AnimatedTextManager.render(graphics, paint);
        ButtonManager.render(graphics, paint);

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
                centerText((double) i / 100 + "", graphics, position, (int) (height * 0.04), paint, 5, Color.BLACK);
                graphics.drawLine(0, hPosition, width, hPosition, paint);
                centerText((double) i / 100 + "", graphics, (int) (width * 0.02), hPosition, paint, 5, Color.BLACK);
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

        // Don't forget to tick managers
        AnimatedTextManager.tick();
        ButtonManager.tick();
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
        if (conductor.playing){
            conductor.stop();
        }
        state = newState;
        switch(newState){

            case HOME:

                // Floating text
                new FloatingText("Tap to Start", width / 2, (int) (height * 0.8), 15, Color.WHITE, 240, (int) (height * 0.01), 255);
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
                new StateChangeButton(AssetLoader.getImageAssetFromMemory(ImageAsset.OK_BUTTON), width / 2, height / 2, 255, ScreenState.PLAY);
                break;

            case PLAY:

                // TODO fix everything ;-;
                String name = "popcornfunk";
                Beatmap beatmap = new Beatmap("beatmaps/" + name + "/" + name + ".png", "beatmaps/" + name + "/info.ini", "beatmaps/" + name + "/" + name + ".wav");
                conductor.loadMap(beatmap);

                new StateChangeButton(AssetLoader.getImageAssetFromMemory(ImageAsset.LEFT_ARROW_BUTTON), (int) (width * 0.08), (int) (height * 0.05), 255, ScreenState.HOME);
                break;
        }
    }

    public static void centerText(String text, Canvas graphics, int x, int y, Paint paint, int textSize, int color){

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
        graphics.drawText(text, x, y, paint);

        // Reset paint
        paint.setTextSize(old);
        paint.setColor(oldColor);
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
