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

    public static int perfect = 300;
    public static int great = 200;
    public static int good = 100;
    public static int miss = 0;

    public static int getPerfect() {
        return perfect;
    }

    public static int getGreat() {
        return great;
    }

    public static int getGood() {
        return good;
    }

    public static int getMiss() {
        return miss;
    }
}
