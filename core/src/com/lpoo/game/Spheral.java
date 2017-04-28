package com.lpoo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Spheral extends Game {
    SpriteBatch batch;
    AssetManager assetManager;

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
