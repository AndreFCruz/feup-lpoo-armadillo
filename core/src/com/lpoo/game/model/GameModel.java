package com.lpoo.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.game.Spheral;
import com.lpoo.game.model.entities.BallModel;
import com.lpoo.game.model.entities.EntityModel;
import com.lpoo.game.model.entities.ShapeModel;

import java.util.HashMap;
import java.util.Map;

import static com.lpoo.game.model.GameModel.ModelState.LIVE;
import static com.lpoo.game.model.GameModel.ModelState.PAUSED;
import static com.lpoo.game.model.GameModel.ModelState.START;


/**
 * Created by andre on 04/05/2017.
 */

public class GameModel implements Disposable {
    private static GameModel ourInstance = null;

    public static GameModel getInstance() {
        if (GameModel.ourInstance == null)
            GameModel.ourInstance = new GameModel();

        return ourInstance;
    }

    /**
     * Possible Game States the Model may be in.
     */
    public enum ModelState {PAUSED, LIVE, START, END};

    /**
     * Current Model State the game is in.
     */
    private ModelState currentState;

    private Level level;

    private GameModel() {
        currentState = START;
    }

    public void loadMap(TiledMap map) {
        level = new Level(map);
    }

    public void update(float delta) {
        if (currentState == PAUSED)
            return;

        level.update(delta);
    }

    public World getWorld() {
        return level.getWorld();
    }

    public BallModel getBall() {
        return level.getBall();
    }

    public Array<ShapeModel> getShapeModels() {
        return level.getShapeModels();
    }

    public Array<EntityModel> getEntityModels() {
        return level.getEntityModels();
    }

    public TiledMap getMap() {
        return level.getMap();
    }

    public ModelState getCurrentState() {
        return currentState;
    }

    // TODO Pause mechanism needs improvement
    public void togglePause() {
        if (currentState == PAUSED)
            currentState = LIVE;
        else
            currentState = PAUSED;
    }

    @Override
    public void dispose() {
        level.dispose();

        GameModel.ourInstance = null;
    }

}
