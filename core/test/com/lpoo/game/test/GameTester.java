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
public class GameTester {

    GameModel model;

    public GameTester(TiledMap map) {
        model = new GameModel(map);
    }

    public GameTester(String map_name) {
        TiledMap testmap = new TmxMapLoader().load(map_name);
        model = new GameModel(testmap);

        //For positions and other stuff initialization
        noMotionDuringTime(0);
    }

    // In all the functions, param time is in seconds
    public GameModel.ModelState stepGame (float time, int direction)
    {
        GameModel.ModelState state = GameModel.ModelState.LIVE;
        float timer = 0;

        //World has update ratio of 60 frames per second.
        while (timer++ <= (time * 60)) {
            state = model.update(1);
            model.getBallModel().rotate(direction);
        }
        return state;
    }

    public GameModel.ModelState noMotionDuringTime (float time) { return stepGame(time, 0); }

    public GameModel.ModelState rotateRightDuringTime(float time) { return stepGame(time, -1); }

    public GameModel.ModelState rotateLeftDuringTime(float time) { return stepGame(time, 1); }

    public void ballJump() { model.getBallModel().jump(); }

    //GETTERS
    public float ballXPosition() { return model.getBallModel().getX(); }

    public float ballYPosition() { return model.getBallModel().getY(); }
}
