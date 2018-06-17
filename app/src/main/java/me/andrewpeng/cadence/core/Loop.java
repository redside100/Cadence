package me.andrewpeng.cadence.core;

import android.os.Handler;

public class Loop {

    public static int FPS = 60;
    private Handler handler;
    private Runnable runnable;
    private final MainView mainView;
    private long beginTime;
    private long timeDiff;
    private int sleepTime;
    private int framesSkipped;
    private double framePeriod = (int) (1000 / FPS);
    private int maxFrameSkips = 5;
    public boolean paused = false;

    public Loop(MainView mainView){
        this.mainView = mainView;
        init();
    }
    private void init(){
        sleepTime = 0;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                // Get the time this iteration starts at for reference
                beginTime = System.currentTimeMillis();
                framesSkipped = 0;
                // Only tick and render if the game isn't paused
                if (!paused){
                    mainView.tick();
                    mainView.render();
                }
                // Get the time it took to tick and render once
                timeDiff = System.currentTimeMillis() - beginTime;
                // Get the amount of time missed (if needed to catch up)
                sleepTime = (int) (framePeriod - timeDiff);

                // If the time missed is negative, and it is possible to skip frames, then do it
                while (sleepTime < 0 && framesSkipped < maxFrameSkips && !paused) {
                    // Tick additional times to catch up
                    mainView.tick();
                    sleepTime += framePeriod;
                    framesSkipped++;
                }
                if (framesSkipped > 0){
                    System.out.println("Can't keep up! Skipped " + framesSkipped + " frames");
                }
                // Recall this runnable for the next iteration of ticks and renders
                handler.postDelayed(runnable, sleepTime);
            }
        };
        // Run for the first time
        handler.post(runnable);
    }
}