package com.lpoo.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.game.GameServices;
import com.lpoo.game.Armadillo;
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
 * Main class responsible for handling the game's logic.
 */
public class GameModel implements Disposable {

    /**
     * Maximum ball height in model.
     */
    private static final int MAX_HEIGHT = 175;

    /**
     * Minimum ball height in model.
     */
    private static final int MIN_HEIGHT = 1;

    /**
     * Possible Game States the Model may be in.
     */
    public enum ModelState {
        PAUSED, LIVE, WON, LOST
    }

    /**
     * Current Model State the game is in.
     */
    private ModelState currentState;

    /**
     * The model's current map.
     */
    private TiledMap map;

    /**
     * The physics world the model operates in.
     */
    private World world;

    /**
     * The world creator. Populates the world from a TiledMap.
     */
    private B2DWorldCreator worldCreator;

    /**
     * The world's fluids.
     */
    private Array<WaterModel> fluids;

    /**
     * The world's gravity vector.
     */
    private Vector2 gravity = new Vector2(0, -9.81f);

    /**
     * The world's entities.
     */
    private Array<EntityModel> entityModels;

    /**
     * The world's shape entities.
     */
    private Array<ShapeModel> shapeModels;

    /**
     * The world's ball.
     */
    private BallModel ballModel;

    /**
     * This level's end position.
     */
    private Vector2 endPos;

    /**
     * This session's current run time.
     */
    private float currentRunTime;

    /**
     * Constructor for a GameModel.
     */
    public GameModel() {}

    /**
     * Constructor for a GameModel with a specific map.
     *
     * @param map TiledMap to be used in this model.
     */
    public GameModel(TiledMap map) {
        loadMap(map);
    }

    /**
     * Loads the given TiledMap.
     *
     * @param map Map to be loaded.
     */
    public void loadMap(TiledMap map) {
        this.map = map;
        startLevel();
    }

    /**
     * Starts (or restarts) a level from the current map.
     */
    public void startLevel() {
        if (map == null) return;

        currentRunTime = 0;
        currentState = LIVE;

        world = new World(gravity, true);

        entityModels = new Array<>();
        shapeModels = new Array<>();

        createWorld();
        addModels();
    }

    /**
     * Generates the world's objects from the current map.
     */
    private void createWorld() {
        worldCreator = new B2DWorldCreator(world, map);

        worldCreator.generateWorld();
        endPos = worldCreator.getEndPos();
        fluids = worldCreator.getFluids();

        world.setContactListener(new WorldContactListener(this));
    }

    /**
     * Adds the world's entity and shape models to this GameModel.
     */
    private void addModels() {
        ballModel = worldCreator.getBall();

        entityModels.add(ballModel);
        entityModels.addAll(worldCreator.getEntityModels());
        shapeModels.addAll(worldCreator.getShapeModels());
    }

    /**
     * Updates the model. Steps the world and updates its entities.
     *
     * @param delta Time elapsed since last update.
     * @return The model's current state.
     */
    public ModelState update(float delta) {
        if (currentState != LIVE)
            return currentState;

        currentRunTime += delta;

        // Step the simulation with a fixed time step of 1/60 of a second
        world.step(1 / 60f, 6, 2);
        for (WaterModel model : fluids)
            model.step();

        removeFlagged();

        if (!ballInBounds())
            return (currentState = LOST);
        if (ballReachedEnd())
            return (currentState = WON);

        return LIVE;
    }

    /**
     * Removes all entities flagged for removal. Must be done outside update method.
     */
    private void removeFlagged() {
        for (int i = 0; i < entityModels.size; i++) {
            if (entityModels.get(i).isFlaggedForRemoval()) {
                world.destroyBody(entityModels.get(i).getBody());
                entityModels.removeIndex(i);
            }
        }
    }

    /**
     * Checks whether the ball's model is inside the defined bounds.
     *
     * @return True if ball is in bounds, false otherwise.
     */
    private boolean ballInBounds() {
        boolean inBounds = ballModel.getY() > MIN_HEIGHT && ballModel.getY() < MAX_HEIGHT;
        if (!inBounds) {
            try {
                GameServices gameServices = ((Armadillo) (Gdx.app.getApplicationListener())).getGameServices();
                gameServices.incrementAchievement(gameServices.getFallingAchievementID());
            } catch (java.lang.ClassCastException e) {
                System.err.println("Application listener not of type game."); }
        }

        return inBounds;
    }

    /**
     * Checks whether the ball's model reached the level's end position.
     *
     * @return True if ball reached end position, false otherwise.
     */
    private boolean ballReachedEnd() {
        Vector2 diff = new Vector2(endPos);
        diff.sub(ballModel.getX(), ballModel.getY());

        return diff.len() < BallModel.RADIUS;
    }

    /**
     * Returns the world controlled by this model. Needed for debugging purposes only.
     *
     * @return The world controlled by this model.
     */
    public World getWorld() {
        return world;
    }

    /**
     * Getter for the model's ball model.
     *
     * @return The ball model.
     */
    public BallModel getBallModel() {
        return ballModel;
    }

    /**
     * Getter for the model's shape models.
     *
     * @return The shape models.
     */
    public Array<ShapeModel> getShapeModels() {
        return shapeModels;
    }

    /**
     * Getter for the model's entity models
     *
     * @return The entity models.
     */
    public Array<EntityModel> getEntityModels() {
        return entityModels;
    }

    /**
     * Getter for the model's current map.
     *
     * @return This model's current map.
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Getter for the model's current state.
     *
     * @return The model's state.
     */
    public ModelState getState() {
        return currentState;
    }

    /**
     * Setter for the model's current state.
     */
    void setLost() {
        currentState = LOST;
    }

    /**
     * Getter for this session's current run time.
     *
     * @return The session's current run time.
     */
    public float getCurrentRunTime() {
        return currentRunTime;
    }

    /**
     * Method to toggle pause (ON or OFF).
     */
    public void togglePause() {
        if (currentState == PAUSED)
            currentState = LIVE;
        else
            currentState = PAUSED;
    }

    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {
        map.dispose();
        world.dispose();
    }

}
