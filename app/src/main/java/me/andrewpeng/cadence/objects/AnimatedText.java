package me.andrewpeng.cadence.objects;

import android.graphics.Canvas;
import android.graphics.Paint;

import me.andrewpeng.cadence.core.Renderer;

public class AnimatedText {
    public int x, y;
    public String text;
    public int textSize, color, alpha;
    public AnimatedText(String text, int x, int y, int textSize, int color, int startingAlpha){
        this.x = x;
        this.y = y;
        this.text = text;
        this.textSize = textSize;
        this.color = color;
        alpha = startingAlpha;
        AnimatedTextManager.texts.add(this);
    }
    // To be overridden
    public void tick(){

    }
    public void render(Canvas graphics, Paint paint){
        int oldAlpha = paint.getAlpha();
        paint.setAlpha(alpha);
        Renderer.centerText(text, graphics, x, y, paint, textSize, color);
        paint.setAlpha(oldAlpha);
    }
    public void destroy(){
        AnimatedTextManager.texts.remove(this);
    }
}
