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

                beginTime = System.currentTimeMillis();
                framesSkipped = 0;
                if (!paused){
                    mainView.tick();
                    mainView.render();
                }
                timeDiff = System.currentTimeMillis() - beginTime;
                sleepTime = (int) (framePeriod - timeDiff);
                while (sleepTime < 0 && framesSkipped < maxFrameSkips && !paused) {
                    mainView.tick();
                    sleepTime += framePeriod;
                    framesSkipped++;
                }
                if (framesSkipped > 0){
                    System.out.println("Can't keep up! Skipped " + framesSkipped + " frames");
                }
                handler.postDelayed(runnable, sleepTime);
            }
        };
        handler.post(runnable);
    }
}