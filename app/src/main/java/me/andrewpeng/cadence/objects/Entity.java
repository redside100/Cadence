package me.andrewpeng.cadence.objects;

public class Entity {
    public int x, y, alpha;
    public int maxAlpha = 255;
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
    public void setMaxAlpha(int alpha){ this.maxAlpha = maxAlpha; }
    public void tick(){

        if (fadeIn){
            int amount = maxAlpha / fadeTicks;
            if (alpha + amount < maxAlpha){
                alpha += amount;
            }else{
                alpha = maxAlpha;
                fadeIn = false;
            }
        }else if (fadeOut){
            int amount = maxAlpha / fadeTicks;
            if (alpha - amount > 0){
                alpha -= amount;
            }else{
                alpha = 0;
                fadeOut = false;
            }
        }

    }
    public void fadeIn(int ticks){
        if (alpha < maxAlpha){
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
