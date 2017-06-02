package com.lpoo.game.test;

/**
 * Created by Edgar on 01/06/2017.
 */

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.lpoo.game.model.GameModel;

/**
 * Class containing useful functions for interaction with the game, making easier to test it.
 */
public class TestClass {

    GameModel model;
    AssetManager assetManager;

    public TestClass (TiledMap map) {
        model = new GameModel(map);
    }

    public TestClass(String map_name) {
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(map_name, TiledMap.class);
        assetManager.finishLoading();   

        model = new GameModel(assetManager.get(map_name, TiledMap.class));
    }

    // @param time - Time the update will last, in seconds.
    public GameModel.ModelState updateDuringTime (float time) {
        GameModel.ModelState state = GameModel.ModelState.LIVE;
        float timer = 0;

        //Because World has update ratio of 60 frames per second.
        while (timer++ <= (time * 60)) {
            state = model.update(1);
        }
        return state;
    }
}
