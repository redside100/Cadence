package me.andrewpeng.cadence.music;


import android.graphics.Color;

import me.andrewpeng.cadence.core.MainView;
import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.objects.Pulse;

public class Metronome {

    private Conductor conductor;
    private double songPosition = 0;
    private double markers[];
    private double noteDelayMs;

    public Metronome(Conductor conductor){
        this.conductor = conductor;
        markers = new double[(int) (conductor.getSongLength() / conductor.getBeatLength() + 0.5)];
        noteDelayMs = (double) conductor.getNoteTravelTicks() / 60 * 1000;
        System.out.println("Travel ticks: " + conductor.getNoteTravelTicks());
        System.out.println(noteDelayMs);
        for (int i = 0; i < markers.length; i++){
            // This waits 8 beats before the first notes arrive
            int pad = (int) (7 * conductor.getBeatmap().getSubBeats());
            markers[i] = (conductor.getBeatLength() * (i + pad)) - noteDelayMs;
        }
    }

    public void update(){

        songPosition = conductor.getMediaPlayer().getCurrentPosition() - conductor.getBeatmap().getStartOffset();

        for (int i = 0; i < markers.length; i++) {
            if (songPosition >= markers[i]){
                markers[i] = Double.MAX_VALUE;
                conductor.nextNote();
                break;
            }
        }
    }

}
