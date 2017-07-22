package com.lpoo.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Class responsible for handling all the game sounds and the game music.
 */
public class Sounds {

    /**
     * Asset Manager where the sounds are saved.
     */
    AssetManager assetManager;

    //enum sound tipo;

    /**
     * Sound used for when a power up is picked up.
     */
    Sound powerup;
    /**
     * Sound used for when the game armadillo jumps.
     */
    Sound jump;
    /**
     * Sound used for when the game is lost.
     */
    Sound lost;
    /**
     * Sound used for when the game is won.
     */
    Sound won;
    /**
     * Sound used for when the Armadillo collides with water.
     */
    Sound water;

    /**
     * Sounds constructor.
     * Plays the background music and initializes the game dependent sounds.
     *
     * @param assetManager Place where the sounds and musics are gotten from.
     */
    public Sounds(AssetManager assetManager) {
        this.assetManager = assetManager;
        loadSounds();

        Music music = assetManager.get("music/Artofescapism_-_Three_Star_Sky.mp3");
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();

        //Initializing the game different sounds
        powerup = assetManager.get("music/item.mp3");
        jump = assetManager.get("music/jump.wav");
        lost = assetManager.get("music/lost.wav");
        won = assetManager.get("music/won.wav");
        water = assetManager.get("music/water.wav");
    }

    /*
    public produceSound( type tipo) {
        switch typo (do) {

        }
    }*/

    /**
     * Load the sounds and musics used in the game.
     */
    private void loadSounds() {
        assetManager.load("music/Artofescapism_-_Three_Star_Sky.mp3", Music.class);
        assetManager.load("music/item.mp3", Sound.class);
        assetManager.load("music/jump.wav", Sound.class);
        assetManager.load("music/lost.wav", Sound.class);
        assetManager.load("music/won.wav", Sound.class);
        assetManager.load("music/water.wav", Sound.class);
    }
}

