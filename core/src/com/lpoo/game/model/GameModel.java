package com.lpoo.game.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.game.model.entities.BallModel;
import com.lpoo.game.model.entities.EntityModel;
import com.lpoo.game.model.entities.ShapeModel;
import com.lpoo.game.model.entities.WaterModel;
import com.lpoo.game.model.utils.B2DWorldCreator;
import com.lpoo.game.model.controllers.BuoyancyController;

import java.util.ArrayList;
import java.util.List;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

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

    // Tiled Map Variables
    private TiledMap map;

    // Box2d variables
    private World world;
    private B2DWorldCreator worldCreator;
    private Array<WaterModel> fluids;

    private Game game;

    private final float GRAVITY_CONSTANT = 9.81f;
    private Vector2 gravity;

    private Array<EntityModel> entityModels;
    private Array<ShapeModel> shapeModels;

    private BallModel player;

    private GameModel() {
        game = ((Game) Gdx.app.getApplicationListener());

        gravity = new Vector2(0, - GRAVITY_CONSTANT);

        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("SampleMap.tmx");

        entityModels = new Array<EntityModel>();
        shapeModels = new Array<ShapeModel>();

        initModel();
    }

    public boolean update(float delta) {
        // Step the simulation with a fixed time step of 1/60 of a second
        world.step(1/60f, 6, 2);

        for (WaterModel model : fluids)
            model.step();

        return ballInBounds();
    }

    private boolean ballInBounds() {
        // TODO abstract class map
        return player.getY() > 150 * PIXEL_TO_METER;
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
        return player;
    }

    public World getWorld() {
        return world;
    }

    public void initModel() {
        world = new World(gravity, true);

        worldCreator = new B2DWorldCreator(world, map);

        worldCreator.generateWorld();
        fluids = worldCreator.getFluids();
        entityModels.clear();
        shapeModels.clear();

        world.setContactListener(new WorldContactListener());

        player = worldCreator.getBall();

        entityModels.add(player);
        shapeModels.addAll(worldCreator.getPlatforms());
    }

    @Override
    public void dispose() {
        map.dispose();
        world.dispose();

        GameModel.ourInstance = null;
    }

}
