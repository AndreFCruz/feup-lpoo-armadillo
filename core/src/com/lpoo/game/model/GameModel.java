package com.lpoo.game.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.game.model.entities.BallModel;
import com.lpoo.game.model.entities.EntityModel;
import com.lpoo.game.model.tools.B2DWorldCreator;

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
    private OrthogonalTiledMapRenderer renderer;

    // Box2d variables
    private World world;

    private Game game;

    private final float GRAVITY_CONSTANT = 9.81f;
    private Vector2 gravity;

    private List<EntityModel> models;

    private BallModel player;

    private GameModel() {
        game = ((Game) Gdx.app.getApplicationListener());

        gravity = new Vector2(0, - GRAVITY_CONSTANT);
        world = new World(gravity, true);


        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("SampleMap01.tmx");

        B2DWorldCreator.generateWorld(world, map);

        world.setContactListener(new WorldContactListener());

        models = new ArrayList<EntityModel>();

        player = new BallModel(world, new Vector2(200 * PIXEL_TO_METER, 475 * PIXEL_TO_METER));
    }

    public void update(float delta) {
        // Upper bound for simulation update
        if (delta > (1/60f))
            delta = 1/60f;

        world.step(delta, 6, 2);
    }

    public TiledMap getMap() {
        return map;
    }

    public List<EntityModel> getModels() {
        models.add(player);
        return models;
    }

    public BallModel getBall() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        map.dispose();
        world.dispose();

        GameModel.ourInstance = null;
    }
}
