package me.andrewpeng.cadence.objects;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Arrays;

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
    // Difficulty of the song (0 = Novice, 1 = Easy, 2 = Medium, 3 = Hard)
    public int difficulty;
    // AFD of wav/mp3 file
    public AssetFileDescriptor afd, afdPreview;

    public Bitmap album, background;

    /**
     * Create a new beatmap
     * @param beatLocation Location where the png file is located
     * @param infoLocation Location where the ini file is located
     * @param songLocation Location where the wav file is located
     * @param previewLocation Location where the preview wav file is located
     * @param album The album that the song will be put to
     */
    public Beatmap(String beatLocation, String infoLocation, String songLocation, String previewLocation, Bitmap album, Bitmap background){

        this.album = album;
        this.background = background;

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
                case "difficulty":
                    difficulty = Integer.parseInt(value);
                    break;

            }
        }

        this.beats = Reader.getBeatConfiguration(beatLocation);
        this.songLocation = songLocation;
        this.afd = Reader.getSoundFile(songLocation);
        this.afdPreview = Reader.getSoundFile(previewLocation);

    }

    public boolean equals(Beatmap beatmap){
        return this.bpm == beatmap.bpm && this.afd == beatmap.afd && this.afdPreview == beatmap.afdPreview
                && Arrays.equals(this.beats, beatmap.beats) && this.songLocation.equals(beatmap.songLocation)
                && this.startOffset == beatmap.startOffset && this.subBeats == beatmap.subBeats
                && this.noteSpeed == beatmap.noteSpeed && this.name.equals(beatmap.name)
                && this.artist == beatmap.artist && this.difficulty == beatmap.difficulty
                && this.album == beatmap.album && this.background == beatmap.background;
    }

    public AssetFileDescriptor getSongAFD(){
        return afd;
    }
    public AssetFileDescriptor getPreviewAFD() {return afdPreview; }
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
    public int getDifficulty(){ return difficulty; }
    public Bitmap getAlbumBitmap(){ return album; }
    public Bitmap getBackgroundBitmap(){ return background; }

}
