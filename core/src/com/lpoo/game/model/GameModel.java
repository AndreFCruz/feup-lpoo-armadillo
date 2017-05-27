package com.lpoo.game.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.game.model.entities.BallModel;
import com.lpoo.game.model.entities.EntityModel;
import com.lpoo.game.model.entities.ShapeModel;

import static com.lpoo.game.model.GameModel.ModelState.LIVE;
import static com.lpoo.game.model.GameModel.ModelState.LOST;
import static com.lpoo.game.model.GameModel.ModelState.PAUSED;
import static com.lpoo.game.model.GameModel.ModelState.WON;


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
    public enum ModelState {PAUSED, LIVE, WON, LOST};

    /**
     * Current Model State the game is in.
     */
    private ModelState currentState;

    private Level level;

    private GameModel() {}

    public void loadMap(TiledMap map) {
        level = new Level(map);
    }

    public ModelState update(float delta) {
        if (currentState == PAUSED)
            return PAUSED;

        // TODO
        // GameScreen must be able to load next level and restart the level.
        return level.update(delta);
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

    /**
     * Wraps the getMap method from the Level class.
     */
    public TiledMap getMap() {
        return level.getMap();
    }

    /**
     * Wraps the startLevel method from the Level class.
     */
    public void startLevel() {
        level.startLevel();
    }

    // TODO Pause mechanism needs improvement
    public void togglePause() {
        if (currentState == PAUSED)
            currentState = LIVE;
        else
            currentState = PAUSED;

        System.out.print("Im being paused.\n");
    }

    @Override
    public void dispose() {
        level.dispose();

        GameModel.ourInstance = null;
    }

}
