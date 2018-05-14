package me.andrewpeng.cadence.objects;

import android.graphics.Canvas;
import android.graphics.Paint;

import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.managers.AnimatedTextManager;

public class AnimatedText extends Entity{
    public String text;
    public int textSize, color;

    /**
     * Text that will be animated
     * @param text The text you want to have animated
     * @param x x location
     * @param y y location
     * @param textSize Text Size
     * @param color Color of the text
     * @param startingAlpha The transparency of the text at the start of the animation
     */
    public AnimatedText(String text, int x, int y, int textSize, int color, int startingAlpha){
        super(x, y, startingAlpha);
        this.text = text;
        this.textSize = textSize;
        this.color = color;
        AnimatedTextManager.texts.add(this);
    }
    // To be overridden
    public void tick(){}

    /**
     * Renders the set of text into the canvas so that it can be seen
     * @param graphics Canvas
     * @param paint Paint
     */
    public void render(Canvas graphics, Paint paint){
        int oldAlpha = paint.getAlpha();
        paint.setAlpha(super.alpha);
        Renderer.centerText(text, graphics, x, y, paint, textSize, color);
        paint.setAlpha(oldAlpha);
    }
    public void destroy(){
        AnimatedTextManager.texts.remove(this);
    }
}
