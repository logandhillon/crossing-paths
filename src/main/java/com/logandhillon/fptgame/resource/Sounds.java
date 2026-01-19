package com.logandhillon.fptgame.resource;

import com.logandhillon.fptgame.GameHandler;
import com.logandhillon.logangamelib.resource.AudioResource;
import com.logandhillon.logangamelib.resource.ResourceLoader;
import javafx.scene.media.AudioClip;

/**
 * @author Logan Dhillon
 */
public class Sounds {
    public static final AudioClip MENU_CLICK = ResourceLoader.loadSafe(AudioResource.class, "menu_click.wav");

    /**
     * no-op to force the class to load
     */
    public static void init() {}

    /**
     * Plays a specified {@link AudioClip} with the volume in the user's config.
     *
     * @param sound sound clip to play
     */
    public static void playSfx(AudioClip sound) {
        var c = GameHandler.getUserConfig();
        System.out.println("playing sound with vol: " + c.getMasterVolume() * c.getSfxVolume());
        sound.play(c.getMasterVolume() * c.getSfxVolume());
    }

    /**
     * Plays a specified {@link AudioClip} with the volume in the user's config.
     *
     * @param sound music clip to play
     */
    public static void playMusic(AudioClip sound) {
        var c = GameHandler.getUserConfig();
        sound.play(c.getMasterVolume() * c.getMusicVolume());
    }
}
