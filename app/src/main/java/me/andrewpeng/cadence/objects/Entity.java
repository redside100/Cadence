package me.andrewpeng.cadence.objects;

import android.util.Log;

public class Entity {
    public int x, y, alpha;
    private boolean fadeIn = false;
    private boolean fadeOut = false;
    private int fadeTicks = 0;
    public Entity(int x, int y, int alpha){
        this.x = x;
        this.y = y;
        this.alpha = alpha;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getAlpha(){
        return alpha;
    }
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setAlpha(int alpha){
        this.alpha = alpha;
    }
    public void tick(){
        int amount = 255 / fadeTicks;
        if (fadeIn){
            if (alpha + amount < 255){
                alpha += amount;
            }else{
                alpha = 255;
                fadeIn = false;
            }
        }else if (fadeOut){
            if (alpha - amount > 0){
                alpha -= amount;
            }else{
                alpha = 0;
                fadeOut = false;
            }
        }

    }
    public void fadeIn(int ticks){
        if (alpha < 255){
            fadeTicks = ticks;
            fadeIn = true;
        }

    }
    public void fadeOut(int ticks){
        if (alpha > 0){
            fadeTicks = ticks;
            fadeOut = true;
        }
    }
}
