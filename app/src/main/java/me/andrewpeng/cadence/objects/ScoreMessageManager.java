package me.andrewpeng.cadence.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

import me.andrewpeng.cadence.core.MainView;
import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.music.Conductor;

/**
 * Manages the messages that will be displayed once the user has tapped a note
 * Created by isaacleung on 2018-05-07.
 */

public class ScoreMessageManager {
    public static ArrayList<ScoreMessage> scoreMessages = new ArrayList<>(10);

    public static int index = 0;
    public static void render(Canvas graphics, Paint paint) {
        ArrayList<ScoreMessage> temp = new ArrayList<>(scoreMessages);
        for(ScoreMessage scoreMessage: temp) {
            if(scoreMessage.isTouched) {
                scoreMessages.get(index).render(graphics,paint);
            }
        }
    }

    public static void tick() {
        ArrayList<ScoreMessage> temp = new ArrayList<>(scoreMessages);
        for(ScoreMessage scoreMessage: temp) {
            scoreMessage.tick();
        }

    }

    public static void touch(MotionEvent e, int pointerIndex) {
        ArrayList<Note> temp = new ArrayList<>(Conductor.activeNotes);
        for(Note note: temp) {

            //Parallels the function of the conductor
            if((MainView.inBounds((int) e.getX(pointerIndex), (int) e.getX(pointerIndex), (int) e.getY(pointerIndex), (int) e.getY(pointerIndex),
                    note.getX1(), note.getX2(), note.getPadY1(), note.getPadY2()))) {

                int pad0 = (int) (Math.abs(Renderer.scoreY2 - Renderer.scoreY1) * 0.35);
                int pad1 = (int) (Math.abs(Renderer.scoreY2 - Renderer.scoreY1) * 0.25);
                int pad2 = (int) (Math.abs(Renderer.scoreY2 - Renderer.scoreY1) * 0.15);
                int pad3 = (int) (Math.abs(Renderer.scoreY2 - Renderer.scoreY1) * 0.05);

                if(Conductor.scoreArea(note,pad0)) {
                    index = 0;
                    if(Conductor.scoreArea(note, pad1)) {
                        index =1;
                    }
                    if(Conductor.scoreArea(note, pad2)) {
                        index =2;
                    }
                    if(Conductor.scoreArea(note, pad3)) {
                        index =3;
                    }
                }
                Score.addScore(index);
                Score.setScore();
                scoreMessages.get(index).animate();

            }
        }
    }

}
