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

    private int currentLevel;

    private AssetManager assetManager;

    private static final Map<Integer, String> levelNames = new HashMap<Integer, String>();

    static {
        levelNames.put(0, "SampleMap.tmx");
        /*
        levelNames.put(1, "level01.tmx");
        levelNames.put(2, "level02.tmx");
        */
    }

    private GameModel() {
        assetManager = ((Spheral) Gdx.app.getApplicationListener()).getAssetManager();

        currentLevel = 0;
        currentState = START;

        loadNextLevel();
    }

    private void loadNextLevel() {
        if (currentLevel == levelNames.size())
            currentLevel = 0;

        TiledMap map = assetManager.get(levelNames.get(currentLevel++));
        level = new Level(map);
    }

    public void update(float delta) {
        if (currentState == PAUSED)
            return;

        // TODO change this to react to losses
        if (! level.update(delta)) {
            loadNextLevel();
        }
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
