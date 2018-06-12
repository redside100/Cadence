package me.andrewpeng.cadence.core;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import me.andrewpeng.cadence.music.Conductor;

public class MainActivity extends Activity {

    private MainView mainView;

    public static boolean itdoes;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        mainView = new MainView(this, width, height);
        setContentView(mainView);
        refocus();
    }

    @Override
    public void onResume(){
        super.onResume();
        refocus();
        mainView.resume();
        if (Conductor.mp != null){
            if (Conductor.playing || Conductor.preview){
                Conductor.mp.start();
            }
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        mainView.pause();
        if (Conductor.mp != null){
            if (Conductor.playing || Conductor.preview){
                Conductor.mp.pause();
            }
        }
    }

    // Handle all back button presses for each screen state
    @Override
    public void onBackPressed(){
        if (!Renderer.transition){
            switch(Renderer.state){
                case SONG_SELECTION:
                case SETTINGS:
                case CREDITS:
                    Renderer.changeState(ScreenState.MENU);
                    break;
                case MENU:
                    Renderer.changeState(ScreenState.HOME);
                    break;
            }
        }
    }

    // Recall refocus() every time the focus is changed, so that the app maintains the navigation bar settings
    @Override
    public void onWindowFocusChanged(boolean changed){
        super.onWindowFocusChanged(changed);
        refocus();
    }

    private void refocus(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

}
