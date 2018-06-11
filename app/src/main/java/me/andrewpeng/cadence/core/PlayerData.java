package me.andrewpeng.cadence.core;

import java.util.ArrayList;

import me.andrewpeng.cadence.music.Conductor;
import me.andrewpeng.cadence.util.Reader;

public class PlayerData {
    public static ArrayList<String> grades = new ArrayList<>();
    public static int xp = 0;
    public static int level = 1;
    public static void addXp(int amount){
        xp += amount;
        if (xp >= 100){
            level += 1;
            xp -= 100;
        }
    }
    public static void saveAll(){
        ArrayList<String> info = new ArrayList<>();
        for(int i = 0; i < Conductor.names.length; i ++) {
            String index = Conductor.names[i];
            info.add("beatmap_" + index + ": " + grades.get(i));
        }
        info.add("xp: " + xp);
        info.add("level: " + level);
        info.add("musicVolume: " + Conductor.volume);
        info.add("fxVolume: " + Conductor.fxVolume);
        info.add("judgeDifficulty: " + Conductor.judgeDifficulty);
        Reader.save(info, "save.ini");
    }

    public static void reset(){
        Conductor.volume = 100;
        Conductor.fxVolume = 100;
        Conductor.judgeDifficulty = "Normal";

        grades.clear();

        ArrayList<String> info = new ArrayList<>();
        for(int i = 0;i < Conductor.names.length;i ++) {
            String index = Conductor.names[i];
            info.add("beatmap_" + index + ": N/A");
            grades.add("N/A");
        }
        xp = 0;
        level = 0;

        saveAll();
    }

}
