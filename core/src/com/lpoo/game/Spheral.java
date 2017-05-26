package com.lpoo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.lpoo.game.view.screens.MainMenuScreen;

import java.util.HashMap;
import java.util.Map;

public class Spheral extends Game {
	private SpriteBatch batch;
    private AssetManager assetManager;

    public BitmapFont font;

    private static final Map<Integer, String> gameMaps = new HashMap<Integer, String>();

    static {
        gameMaps.put(0, "maps/map1.tmx");
        gameMaps.put(1, "maps/map2.tmx");
    }

    @Override
	public void create () {
		batch = new SpriteBatch();
        assetManager = new AssetManager();

        font = new BitmapFont();

        loadAssets();
        startGame();
    }

    private void startGame() {
        setScreen(new MainMenuScreen(this));
    }

    /**
     * Loads the assets needed by this all screens.
     */
    private void loadAssets() {

        // Load ball skins
        for (int i = 0; i < 6; i++)
            assetManager.load( "skins/skin0" + i + ".png" , Texture.class);

        // Load levels
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        for (String entry : gameMaps.values())
            assetManager.load(entry, TiledMap.class);

        assetManager.finishLoading();
    }

    public String getMap(int i) {
        return gameMaps.get(i);
    }

    public int getNumMaps() {
        return gameMaps.size();
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
