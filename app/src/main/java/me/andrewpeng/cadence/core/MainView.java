package me.andrewpeng.cadence.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import me.andrewpeng.cadence.music.Conductor;
import me.andrewpeng.cadence.util.Reader;

public class MainView extends View {
    int height, width;
    private Renderer renderer;
    private Conductor conductor;
    private Loop loop;
    public MainView(Context context, int width, int height){
        super(context);
        this.height = height;
        this.width = width;
        loop = new Loop(this);
        renderer = new Renderer(getContext(), width, height);
        new Reader(getContext());
        conductor = new Conductor(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Where everything is drawn
        super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawPaint(paint);

        renderer.render(canvas, paint);
        conductor.render(canvas, paint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){
        switch(e.getAction() & e.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                for (int i = 0; i < e.getPointerCount(); i++){
                    conductor.touch(e, i);
                }
        }
        return true;
    }

    public void resume(){
        conductor.resume();
        loop.paused = false;
    }
    public void pause(){
        conductor.pause();
        loop.paused = true;
    }

    public void tick(){
        conductor.tick();
    }
    public void render(){
        invalidate();
    }

    public static float scale(float amount, Canvas graphics){

        double relation = Math.sqrt(graphics.getWidth() * graphics.getHeight()) / 250;
        return (float) (amount * relation);
    }
    public static int speed(int distance, int time){
        return distance / time;
    }

    public static boolean inBounds(int x1, int x2, int y1, int y2, int ax1, int ax2, int ay1, int ay2){
        return x1 >= ax1 && x1 <= ax2 && y1 >= ay1 && y1 <= ay2 && x2 > ax1 && x2 < ax2 && y2 >= ay1 && y2 <= ay2;
    }

}
