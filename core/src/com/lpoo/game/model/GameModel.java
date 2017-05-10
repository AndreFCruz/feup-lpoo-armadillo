package com.lpoo.game.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.game.model.entities.BallModel;
import com.lpoo.game.model.entities.EntityModel;
import com.lpoo.game.model.tools.B2DWorldCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 04/05/2017.
 */

public class GameModel {
    private static final GameModel ourInstance = new GameModel();

    public static GameModel getInstance() {
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

        player = new BallModel(world, new Vector2(50, 200));
    }

    public void update(float delta) {
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
}
