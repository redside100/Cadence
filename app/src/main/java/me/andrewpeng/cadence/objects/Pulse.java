package me.andrewpeng.cadence.objects;

import android.graphics.Canvas;
import android.graphics.Paint;

import me.andrewpeng.cadence.core.MainView;
import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.managers.PulseManager;

public class Pulse extends Entity{

    public int speed, duration, color;
    public int counter = 0;
    public Pulse(int y, int speed, int duration, int color){

        super(0, y, 220);
        super.setMaxAlpha(220);
        this.speed = speed;
        this.duration = duration;
        this.color = color;
        PulseManager.pulses.add(this);

        // Start fading out immediately
        super.fadeOut(duration);
    }
    @Override
    public void tick(){
        super.tick();

        // Decrease y value according to speed
        y -= speed;

        // Remove if hit its duration limit
        if (counter == duration){
            PulseManager.pulses.remove(this);
        }
        counter++;
    }
    public void render(Canvas graphics, Paint paint){

        int oldAlpha = paint.getAlpha();
        int oldColor = paint.getColor();

        paint.setStrokeWidth(MainView.scale((float) 1.05, graphics));

        paint.setColor(color);
        paint.setAlpha(super.alpha);

        graphics.drawLine(0, y, Renderer.width, y, paint);

        paint.setAlpha(oldAlpha);
        paint.setColor(oldColor);
    }
}
