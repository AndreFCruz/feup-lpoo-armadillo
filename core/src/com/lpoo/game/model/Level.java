package com.lpoo.game.model;

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

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * Created by andre on 23/05/2017.
 */

public class Level implements Disposable {

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

    private BallModel ball;

    private Vector2 endPos;

    Level(TiledMap map) {
        gravity = new Vector2(0, - GRAVITY_CONSTANT);
        this.map = map;

        startLevel();
    }

    public void startLevel() {
        world = new World(gravity, true);

        worldCreator = new B2DWorldCreator(world, map);

        worldCreator.generateWorld();
        endPos = worldCreator.getEndPos();
        fluids = worldCreator.getFluids();

        entityModels = new Array<EntityModel>();
        shapeModels = new Array<ShapeModel>();

        world.setContactListener(new WorldContactListener());

        ball = worldCreator.getBall();

        entityModels.add(ball);
        shapeModels.addAll(worldCreator.getShapeModels());
    }

    public boolean update(float delta) {
        // Step the simulation with a fixed time step of 1/60 of a second
        world.step(1/60f, 6, 2);

        for (WaterModel model : fluids)
            model.step();

        return ballInBounds() && !ballReachedEnd();
    }

    private boolean ballInBounds() {
        return ball.getY() > 150 * PIXEL_TO_METER;
    }

    private boolean ballReachedEnd() {
        Vector2 diff = new Vector2(endPos);
        diff.sub(ball.getX(), ball.getY());

        //System.err.println("Distance to end: " + diff.len());

        return diff.len() < BallModel.radius;
    }

    public TiledMap getMap() {
        return map;
    }

    public Array<EntityModel> getEntityModels() {
        return entityModels;
    }

    public Array<ShapeModel> getShapeModels() {
        return shapeModels;
    }

    public BallModel getBall() {
        return worldCreator.getBall();
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        map.dispose();
        world.dispose();
    }
}
