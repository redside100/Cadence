package me.andrewpeng.cadence.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Renderer {
    int width, height;
    public static int scoreX1, scoreX2, scoreY1, scoreY2;
    public Renderer(Context context, int width, int height){
        this.width = width;
        this.height = height;
        scoreX1 = (int) (width * 0.005);
        scoreX2 = (int) (width * 0.995);
        scoreY1 = (int) (height * 0.69);
        scoreY2 = (int) (height * 0.81);
    }

    public void render(Canvas graphics, Paint paint){

        // Background
        paint.setColor(Color.WHITE);
        graphics.drawRect(new Rect(0, 0, width, height), paint);

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
    }
}
