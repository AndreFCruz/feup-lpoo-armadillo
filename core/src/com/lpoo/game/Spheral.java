package com.lpoo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lpoo.game.view.screens.MainMenuScreen;

import java.util.HashMap;
import java.util.Map;

public class Spheral extends Game {
	private SpriteBatch batch;
    private AssetManager assetManager;

    public BitmapFont font;

    private Skin skin1;
    private Skin skin2;

    private static final int NUMBER_OF_SKINS = 6;

    private static final Map<Integer, String> gameMaps = new HashMap<Integer, String>();

    private final GameServices gameServices;

    static { // Order is reversed for testing
        gameMaps.put(0, "maps/boxMap.tmx");
        gameMaps.put(6, "maps/mapGravity.tmx");
        gameMaps.put(1, "maps/map3.tmx");
        gameMaps.put(2, "maps/map2.tmx");
        gameMaps.put(3, "maps/map1.tmx");
        gameMaps.put(4, "maps/map0.tmx");
        gameMaps.put(5, "maps/map4.tmx");
    }

    public Spheral(GameServices gameServices) {
        this.gameServices = gameServices;
    }

    @Override
	public void create () {
		batch = new SpriteBatch();
        assetManager = new AssetManager();

        font = new BitmapFont();

        skin1 = new Skin(Gdx.files.internal("appearance/Armadillo.json"), new TextureAtlas("appearance/Armadillo.atlas"));
        skin2 = new Skin(Gdx.files.internal("appearance/smallBtn.json"), new TextureAtlas("appearance/smallBtn.atlas"));

        loadAssets();
        startGame();
    }

    private void startGame() {
        setScreen(new MainMenuScreen(this));
    }

    /**
     * Loads the assets needed by all screens.
     */
    private void loadAssets() {
        //Load Main Menu Background and Title
        assetManager.load( "armadillo_title.png" , Texture.class);
        assetManager.load( "background.png" , Texture.class);

        //Load Game's Virtual Components
        assetManager.load( "pause.png" , Texture.class);

        loadEntitySkins();
        loadLevels();
        loadAnimations();

        assetManager.finishLoading();
    }

    private void loadEntitySkins() {
        // Box Skin
        assetManager.load("box.png", Texture.class);

        // Ball Skins
        for (int i = 0; i < NUMBER_OF_SKINS; i++) {
            assetManager.load(("skins/skin" + (i < 10 ? "0" : "") + i + ".png"), Texture.class);
            assetManager.load(("big_skins/skin" + (i < 10 ? "0" : "") + i + ".png"), Texture.class);
        }
    }

    private void loadLevels() {
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        for (String entry : gameMaps.values())
            assetManager.load(entry, TiledMap.class);
    }

    private void loadAnimations() {
        assetManager.load("animations/crystal-32-blue.png", Texture.class );
        assetManager.load("animations/crystal-32-green.png", Texture.class );
        assetManager.load("animations/crystal-32-grey.png", Texture.class );
        assetManager.load("animations/crystal-32-orange.png", Texture.class );
        assetManager.load("animations/crystal-32-pink.png", Texture.class );
        assetManager.load("animations/crystal-32-yellow.png", Texture.class );
    }

    public String getMap(int i) {
        return gameMaps.get(i);
    }

    public int getNumMaps() {
        return gameMaps.size();
    }

    public int getNumSkins() {
        return NUMBER_OF_SKINS;
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

    public Skin getSkinOne() { return skin1; }

    public Skin getSkinTwo() { return skin2; }

    public GameServices getGameServices() {
        return gameServices;
    }
}
