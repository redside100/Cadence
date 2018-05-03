package me.andrewpeng.cadence.objects;

import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Andrew on 2018-05-03.
 */

public class Spinner{
    public String[] list;
    int x1;
    int x2;
    int y1;

    public String[] getList() {
        return list;
    }

    public void setList(String[] list) {
        this.list = list;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    int y2;

    public Spinner(int x1, int y1, int x2, int y2, int alpha, String[] list) {

        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.list = list;

    }

    public void spin(){

    }
    public void render(){

    }

}
