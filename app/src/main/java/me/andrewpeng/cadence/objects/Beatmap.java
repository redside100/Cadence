package me.andrewpeng.cadence.objects;

import android.content.res.AssetFileDescriptor;

import java.util.ArrayList;

import me.andrewpeng.cadence.util.Reader;


// Usually, a beatmap should have a few empty bars in the beginning to allow the metronome to sort things out

public class Beatmap {

    // BPM (Beats per minute)
    // SubBeats (Beats within a quarter note, should only be used if there are eighth notes or 16th notes)
    public double bpm, subBeats, noteSpeed;
    // Start and end offset (do determine when the first beat and last beat is)
    public long startOffset, endOffset;
    // Name and artist
    public String name, artist;
    // Beat array (0 for empty, 1 for note)
    public int[][] beats;
    // URL of the wav/mp3 file
    public String songLocation;
    // AFD of wav/mp3 file
    public AssetFileDescriptor afd;
    public Beatmap(String beatLocation, String infoLocation, String songLocation){

        ArrayList<String> info = Reader.getTextContents(infoLocation);
        for (String line : info){
            String key = line.split(": ")[0];
            String value = line.split(": ")[1];
            switch(key){
                case "bpm":
                    bpm = Double.parseDouble(value);
                    break;
                case "subbeats":
                    subBeats = Double.parseDouble(value);
                    break;
                case "name":
                    name = value;
                    break;
                case "artist":
                    artist = value;
                    break;
                case "startoffset":
                    startOffset = Long.parseLong(value);
                    break;
                case "endoffset":
                    endOffset = Long.parseLong(value);
                    break;
                case "notespeed":
                    noteSpeed = Double.parseDouble(value);
                    break;

            }
        }

        this.beats = Reader.getBeatConfiguration(beatLocation);
        this.songLocation = songLocation;
        this.afd = Reader.getSoundFile(songLocation);

    }
    public AssetFileDescriptor getSongAFD(){
        return afd;
    }
    public String getSongLocation(){
        return songLocation;
    }
    public int[][] getBeats(){
        return beats;
    }
    public double getBPM(){
        return bpm;
    }
    public long getStartOffset(){
        return startOffset;
    }
    public long getEndOffset(){
        return endOffset;
    }
    public double getSubBeats(){
        return subBeats;
    }
    public double getNoteSpeed(){
        return noteSpeed;
    }
    public String getName(){
        return name;
    }
    public String getArtist(){
        return artist;
    }

}
