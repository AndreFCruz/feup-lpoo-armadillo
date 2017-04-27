package com.lpoo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by andre on 27/04/2017.
 */

public class Spheral extends Game {

    // Lazy, thread-safe Singleton implementation
    private static class HelperHolder {
        private static final Spheral INSTANCE = new Spheral();
    }

    public static Spheral getInstance() {
        return HelperHolder.INSTANCE;
    }

    private SpriteBatch batch;
    private AssetManager assetManager;

    private Spheral() {

    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();

        startGame();
    }

    private void startGame() {
        // TODO
    }

    /**
     * Disposes of all assets.
     */
    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
        super.dispose();
    }

    /**
     * Returns the sprite batch used to improve drawing performance.
     *
     * @return the sprite batch
     */
    public SpriteBatch getBatch() { return batch; }

    /**
     * Returns the asset manager used to load all textures and sounds.
     *
     * @return the asset manager
     */
    public AssetManager getAssetManager() { return assetManager; }

}
