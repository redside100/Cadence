package me.andrewpeng.cadence.core;

public class GameValues {
    public static int musicNotes = 100;

    public static int getNextMusicNoteGoal() {
        return nextMusicNoteGoal;
    }

    public static void setNextMusicNoteGoal(int nextMusicNoteGoal) {
        GameValues.nextMusicNoteGoal = nextMusicNoteGoal;
    }

    public static int nextMusicNoteGoal = 1000;
    public static int getMusicNotes() {
        return musicNotes;
    }
    public static void setMusicNotes(int musicNotes) {
        GameValues.musicNotes = musicNotes;
    }
}
