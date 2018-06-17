package me.andrewpeng.cadence.music;

public class Metronome {

    private Conductor conductor;
    private double songPosition = 0;
    private double markers[];
    private double noteDelayMs;

    public Metronome(Conductor conductor){
        this.conductor = conductor;
        // Create enough space for each subbeat of the song
        markers = new double[(int) (conductor.getSongLength() / conductor.getBeatLength() + 0.5)];

        // Calculate the time for a note to fall to the score area in milliseconds
        noteDelayMs = (double) conductor.getNoteTravelTicks() / 60 * 1000;

        // Add all time stamps of where the note should be, but take the falling time into consideration
        for (int i = 0; i < markers.length; i++){
            // This waits 8 beats before the first notes arrive
            int pad = (int) (7 * conductor.getBeatmap().getSubBeats());
            markers[i] = (conductor.getBeatLength() * (i + pad)) - noteDelayMs;
        }
    }

    public void update(){

        // Gets the current song position relative to the start offset
        songPosition = conductor.getMediaPlayer().getCurrentPosition() - conductor.getBeatmap().getStartOffset();


        for (int i = 0; i < markers.length; i++) {
            // Check if the song's position is greater than or equal to the marker (only updates 60 times a second)
            if (songPosition >= markers[i]){
                // Set the marker to a really big value so it won't be true anymore the next time it updates
                markers[i] = Double.MAX_VALUE;
                // Tell the conductor to spawn the next note
                conductor.nextNote();
                break;
            }
        }
    }

}
