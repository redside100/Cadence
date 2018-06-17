package me.andrewpeng.cadence.objects;


import me.andrewpeng.cadence.music.Conductor;

public class Note {
    private int x1, y1, x2, y2, speed;
    private int boundY1, boundY2;
    private int alpha;
    public boolean fading = false;
    public boolean valid = true;
    private int fadeTick = 0, maxFadeTicks = 0;
    public int color;
    public Note(int x1, int y1, int x2, int y2, double yPad, int speed, int color){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        // Set bounds relative to the pad
        this.boundY1 = (int) (y1 - (Math.abs(y2 - y1) * yPad));
        this.boundY2 = (int) (y2 + (Math.abs(y2 - y1) * yPad));

        this.speed = speed;
        this.alpha = 255;
        this.color = color;

    }
    public void tick(){
        // If it's fading, then increase fade tick until it reaches its max
        if (fading){
            fadeTick++;
            if (fadeTick < maxFadeTicks){
                // Decrease alpha by portion of max fade ticks
                alpha -= (255 / maxFadeTicks);
            }else{
                destroy();
            }
        }else{
            if (!Conductor.paused){
                // Increase y position and y bounds
                y1 += speed;
                y2 += speed;
                boundY1 += speed;
                boundY2 += speed;
            }
        }
    }

    public boolean isValid(){
        return valid;
    }
    public void setValid(boolean valid){
        this.valid = valid;
    }
    public void destroy(){
        Conductor.activeNotes.remove(this);
    }
    public int getY1(){
        return y1;
    }
    public int getY2(){
        return y2;
    }
    public int getX1(){
        return x1;
    }
    public int getX2(){
        return x2;
    }
    public int getPadY1(){
        return boundY1;
    }
    public int getPadY2(){
        return boundY2;
    }
    public int getSpeed(){
        return speed;
    }
    public int getAlpha(){
        return alpha;
    }
    public int getColor(){
        return color;
    }
    public void fadeOut(int ticks){
        fading = true;
        maxFadeTicks = ticks;
    }
    public boolean equals(Note note){
        return this.x1 == note.getX1() && this.x2 == note.getX2()
                && this.y1 == note.getY1() && this.y2 == note.getY2()
                && this.speed == note.getSpeed();
    }

}
