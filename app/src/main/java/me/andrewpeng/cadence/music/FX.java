package me.andrewpeng.cadence.music;

import android.media.AudioManager;
import android.media.SoundPool;
import java.util.HashMap;

import me.andrewpeng.cadence.util.AssetLoader;

public class FX {
    private static SoundPool sp;
    private static HashMap<SoundEffect, Integer> sounds = new HashMap<>();

    /**
     * Loads all sound effects.
     */
    public static void loadSounds() {
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sounds.put(SoundEffect.SELECT, sp.load(AssetLoader.getSoundAssetFromMemory(SoundEffect.SELECT), 1));
    }

    /**
     * Plays a sound effect in a new thread
     * @param sound
     */
    public static void playSound(final SoundEffect sound) {
        new Thread(new Runnable() {
            public void run() {
                int id = sounds.get(sound);
                int max = 100;
                // Scale volume logarithmically, and set to the volume value
                float scaledVolume = (float) (1 - (Math.log(max - Conductor.fxVolume) / Math.log(max)));
                if (scaledVolume > 1){
                    scaledVolume = 1;
                }
                sp.play(id, scaledVolume, scaledVolume, 0, 0, 1);

            }
        }).start();
    }

    public enum SoundEffect {
        SELECT
    }
}