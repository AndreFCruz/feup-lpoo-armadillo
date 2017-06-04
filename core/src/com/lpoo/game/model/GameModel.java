package com.lpoo.game.model;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.game.model.entities.BallModel;
import com.lpoo.game.model.entities.EntityModel;
import com.lpoo.game.model.entities.ShapeModel;
import com.lpoo.game.model.entities.WaterModel;
import com.lpoo.game.model.utils.B2DWorldCreator;

import static com.lpoo.game.model.GameModel.ModelState.LIVE;
import static com.lpoo.game.model.GameModel.ModelState.LOST;
import static com.lpoo.game.model.GameModel.ModelState.PAUSED;
import static com.lpoo.game.model.GameModel.ModelState.WON;


/**
 * Created by andre on 04/05/2017.
 */

public class GameModel implements Disposable {

    public static final int MAX_HEIGHT = 400;
    public static final int MIN_HEIGHT = 1;

    /**
     * Possible Game States the Model may be in.
     */
    public enum ModelState {PAUSED, LIVE, WON, LOST};

    /**
     * Current Model State the game is in.
     */
    private ModelState currentState;

    // Tiled Map Variables
    private TiledMap map;

    // Box2d variables
    private World world;
    private B2DWorldCreator worldCreator;
    private Array<WaterModel> fluids;

    private final float GRAVITY_CONSTANT = 9.81f;
    private Vector2 gravity;

    private Array<EntityModel> entityModels;
    private Array<ShapeModel> shapeModels;

    private BallModel ballModel;

    private Vector2 endPos;

    private float currentRunTime;

    public GameModel() {}

    public GameModel(TiledMap map) {
        loadMap(map);
    }

    public void loadMap(TiledMap map) {
        this.map = map;
        startLevel();
    }

    public void startLevel() {
        if (map == null)
            return;

        currentRunTime = 0;
        currentState = LIVE;

        gravity = new Vector2(0, - GRAVITY_CONSTANT);
        world = new World(gravity, true);

        entityModels = new Array<>();
        shapeModels = new Array<>();

        createWorld();
        addModels();
    }

    private void createWorld() {
        worldCreator = new B2DWorldCreator(world, map);

        worldCreator.generateWorld();
        endPos = worldCreator.getEndPos();
        fluids = worldCreator.getFluids();

        world.setContactListener(new WorldContactListener(this));
    }

    private void addModels() {
        ballModel = worldCreator.getBall();

        entityModels.add(ballModel);
        entityModels.addAll(worldCreator.getEntityModels());
        shapeModels.addAll(worldCreator.getShapeModels());
    }

    public ModelState update(float delta) {

        if (currentState != LIVE)
            return currentState;

        currentRunTime += delta;

        // Step the simulation with a fixed time step of 1/60 of a second
        world.step(1/60f, 6, 2);

        for (WaterModel model : fluids)
            model.step();

        removeFlagged();

        if (! ballInBounds())
            return (currentState = LOST);

        if (ballReachedEnd())
            return (currentState = WON);

        return LIVE;
    }

    // TODO eventually world.destroy(body) of flagged?
    private void removeFlagged() {
        for (int i = 0; i < entityModels.size; i++) {
            if (entityModels.get(i).isFlaggedForRemoval()) {
                entityModels.removeIndex(i);
            }
        }
    }

    private boolean ballInBounds() {
        return ballModel.getY() > MIN_HEIGHT && ballModel.getY() < MAX_HEIGHT;
    }

    private boolean ballReachedEnd() {
        Vector2 diff = new Vector2(endPos);
        diff.sub(ballModel.getX(), ballModel.getY());

        return diff.len() < BallModel.RADIUS;
    }

    public World getWorld() {
        return world;
    }

    public BallModel getBallModel() {
        return ballModel;
    }

    public Array<ShapeModel> getShapeModels() {
        return shapeModels;
    }

    public Array<EntityModel> getEntityModels() {
        return entityModels;
    }

    public TiledMap getMap() {
        return map;
    }

    public ModelState getState() {
        return currentState;
    }

    public void setState(ModelState state) {
        currentState = state;
    }

    public float getCurrentRunTime() {
        return currentRunTime;
    }

    // TODO Pause mechanism needs improvement ?
    public void togglePause() {
        if (currentState == PAUSED)
            currentState = LIVE;
        else
            currentState = PAUSED;
    }

    @Override
    public void dispose() {
        map.dispose();
        world.dispose();
    }

}
