package me.andrewpeng.cadence.objects;

import me.andrewpeng.cadence.managers.AnimatedTextManager;

public class FadingText extends AnimatedText{

    public int fadeTicks, duration;
    public int counter = 0;
    private boolean fadingIn = true;

    /**
     * Creates a line of fading text that will move up and down in a continuous cycle
     * @param text Text that will be fading in and out
     * @param x x location
     * @param y y location
     * @param textSize Text Size
     * @param color Color of the text
     * @param fadeTicks How long it takes for the fading text to fade in/out
     * @param duration How long the text will stay solid
     */
    public FadingText(String text, int x, int y, int textSize, int color, int fadeTicks, int duration){
        super(text, x, y, textSize, color, 0);
        this.fadeTicks = fadeTicks;
        this.duration = duration;
        super.fadeIn(fadeTicks);
    }

    @Override
    public void tick(){
        super.tick();
        if (fadingIn){
            if (super.alpha == super.maxAlpha){
                counter++;
                if (counter == duration){
                    super.fadeOut(fadeTicks);
                    counter = 0;
                    fadingIn = false;
                }
            }
        }else{
            counter++;
            if (counter == fadeTicks){
                AnimatedTextManager.texts.remove(this);
            }
        }

    }

}
