package me.andrewpeng.cadence.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import me.andrewpeng.cadence.managers.ButtonManager;
import me.andrewpeng.cadence.managers.GradientManager;
import me.andrewpeng.cadence.music.Conductor;
import me.andrewpeng.cadence.objects.Gradient;
import me.andrewpeng.cadence.managers.SpinnerManager;
import me.andrewpeng.cadence.util.AssetLoader;
import me.andrewpeng.cadence.util.Reader;

public class MainView extends View implements GestureDetector.OnGestureListener {
    int height, width;
    private Renderer renderer;
    private Conductor conductor;
    private Gradient gradient;
    private Loop loop;
    public Typeface font;
    public static boolean canTouch = true;

    public GestureDetector gestureDetector;
    public MainView(Context context, int width, int height){

        super(context);

        // Init height / width
        this.height = height;
        this.width = width;

        // Init game loop, conductor, and renderer
        loop = new Loop(this);
        conductor = new Conductor(width, height);
        renderer = new Renderer(getContext(), width, height, ScreenState.HOME, conductor);
        Renderer.next(ScreenState.HOME);

        // Set first state to HOME without triggering screen transition animation
        renderer.next(ScreenState.HOME);

        // Init reader and asset loader (only one time)
        new Reader(getContext());
        new AssetLoader(getContext(), width, height);

        // Init font
        font = Typeface.createFromAsset(context.getAssets(), "fonts/Asgalt-Regular.ttf");

        // Init gesture detector
        gestureDetector = new GestureDetector(this);

        // Load beatmaps (add twice for now, to get a full list)
        conductor.initBeatmaps();
        conductor.initBeatmaps();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Where everything is drawn
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTypeface(font);
        canvas.drawPaint(paint);

        renderer.render(canvas, paint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){

        // Check if touch is enabled
        if (canTouch){

            // Take a look at the action (and masked action if multiple touches)
            switch(e.getAction() & e.getActionMasked()){

                // For conductor purposes (when the user first taps on the screen)
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    for (int i = 0; i < e.getPointerCount(); i++){
                        conductor.touch(e, i);
                        GradientManager.touch(e, i);
                    }
                    // For button animations (alpha change)
                    ButtonManager.preTouch(e);
                    break;

                // For renderer purposes (when the user lets go of the screen), usually for buttons
                case MotionEvent.ACTION_UP:
                    Renderer.touch(e);
                    break;
            }

            // Pass off the motion event to detect for gestures (swipes, flings, etc.)
            gestureDetector.onTouchEvent(e);
        }
        return true;
    }

    public static void disableTouch(){
        canTouch = false;
    }
    public static void enableTouch(){
        canTouch = true;
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
        renderer.tick();
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
    public static double overlapPercent(int x1, int x2, int y1, int y2, int ax1, int ax2, int ay1, int ay2){

        // Calculate overlap percentage using areas
        double areaOverlap = (Math.max(x1, ax1) - Math.min(x2, ax2)) * (Math.max(y1,ay1) - Math.min(y2, ay2));
        double selfArea = (x2 - x1) * (y2 - y1);
        if (selfArea > 0){
            return areaOverlap / selfArea;
        }
        return -1;
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        // Pass off info to spinners
        SpinnerManager.swipe(motionEvent, motionEvent1, v, v1);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
