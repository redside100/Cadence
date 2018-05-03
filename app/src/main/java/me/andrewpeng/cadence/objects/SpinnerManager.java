package me.andrewpeng.cadence.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

import me.andrewpeng.cadence.core.MainView;


/**
 * Created by isaacleung on 2018-04-26.
 */

public class SpinnerManager {
    public static ArrayList<Spinner> spinners = new ArrayList<>();

    public static void render(Canvas canvas, Paint paint) {
        ArrayList<Spinner> temp = new ArrayList<>(spinners);
        for (Spinner spinner : temp) {
            spinner.render();
        }
    }

    public void tick() {
        ArrayList<Spinner> temp = new ArrayList<>(spinners);
        for(Spinner spinner : temp) {
            spinner.render();
        }
    }

    public static void swipe(MotionEvent e1, MotionEvent e2, float v1, float v2){
        int x1 = (int) e1.getX();
        int y1 = (int) e1.getY();
        int x2 = (int) e2.getX();
        int y2 = (int) e2.getY();
        for (Spinner spinner : spinners){
            if (MainView.inBounds(x1, x2, y1, y2, spinner.getX1(), spinner.getX2(), spinner.getY1(), spinner.getY2())){

            }
        }
    }
}

