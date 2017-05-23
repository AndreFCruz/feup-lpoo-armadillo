package com.lpoo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lpoo.game.view.screens.MainMenuScreen;

public class Spheral extends Game {
	private SpriteBatch batch;
    private AssetManager assetManager;

    public BitmapFont font;

    @Override
	public void create () {
		batch = new SpriteBatch();
        assetManager = new AssetManager();

        font = new BitmapFont();

        startGame();
    }

    private void startGame() {
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
        super.dispose();
	}

	public SpriteBatch getBatch() {
        return batch;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
