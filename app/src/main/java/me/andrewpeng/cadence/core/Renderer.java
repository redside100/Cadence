package me.andrewpeng.cadence.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import me.andrewpeng.cadence.objects.AnimatedTextManager;
import me.andrewpeng.cadence.objects.FloatingText;
import me.andrewpeng.cadence.util.AssetLoader;
import me.andrewpeng.cadence.util.ImageAsset;

public class Renderer {
    public static int width, height;
    public static int scoreX1, scoreX2, scoreY1, scoreY2;
    public static ScreenState state;
    public Renderer(Context context, int width, int height, ScreenState state){
        this.width = width;
        this.height = height;
        scoreX1 = (int) (width * 0.005);
        scoreX2 = (int) (width * 0.995);
        scoreY1 = (int) (height * 0.69);
        scoreY2 = (int) (height * 0.81);
        this.state = state;
    }

    public void render(Canvas graphics, Paint paint){
        // Background
        paint.setColor(Color.WHITE);
        graphics.drawRect(new Rect(0, 0, width, height), paint);
        switch(state){
            case HOME:
                Bitmap background = AssetLoader.getImageAssetFromMemory(ImageAsset.HOME_BACKGROUND);
                graphics.drawBitmap(background, 0, 0, paint);
                centerText("Cadence", graphics, width / 2, height / 4, paint, 30, Color.WHITE);
                break;
            case MENU:
                centerText("hi this is a menu", graphics, width / 2, height / 2, paint, 15, Color.BLACK);
                break;
            case SETTINGS:
                break;
            case CREDITS:
                break;
            case PLAY:

                // Lines
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(MainView.scale((float) 1.2, graphics));
                for (int i = 0; i < 3; i++){
                    int quarter = width / 4;
                    graphics.drawLine(quarter + (quarter * i), 0, quarter + (quarter * i), height, paint);
                }

                // Score area
                paint.setStyle(Paint.Style.STROKE);
                graphics.drawRect(new Rect(scoreX1, scoreY1, scoreX2, scoreY2), paint);
                break;
        }

        AnimatedTextManager.render(graphics, paint);
    }

    public void tick(){
        AnimatedTextManager.tick();
    }

    public static void changeState(ScreenState newState){
        state = newState;
        AnimatedTextManager.texts.clear();
        switch(newState){
            case HOME:
                new FloatingText("Tap to Start", width / 2, (int) (height * 0.8), 15, Color.WHITE, 240, (int) (height * 0.01));
                break;
        }
    }

    public static void centerText(String text, Canvas graphics, int x, int y, Paint paint, int textSize, int color){

        // Modify text size and color
        float old = paint.getTextSize();
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
        paint.setColor(Color.WHITE);
    }
    public static void fadeToNextScreen(ScreenState state){


    }
}
