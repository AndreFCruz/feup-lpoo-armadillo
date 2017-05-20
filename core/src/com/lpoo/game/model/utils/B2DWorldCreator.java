package com.lpoo.game.model.utils;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lpoo.game.model.controllers.BuoyancyController;
import com.lpoo.game.model.entities.BallModel;
import com.lpoo.game.model.entities.EntityModel;

import static com.lpoo.game.model.entities.EntityModel.FLUID_BIT;
import static com.lpoo.game.model.entities.EntityModel.GROUND_BIT;
import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A class to load TiledMap layers and correctly create the associated physics world.
 */
public class B2DWorldCreator {
    private World world;
    private Map map;
    private Array<Body> fluids = new Array<Body>();
    private Array<Body> platforms = new Array<Body>();
    private BallModel ball;
    private Vector2 endPos;

    public B2DWorldCreator(World world, Map map) {
        this.world = world;
        this.map = map;
    }

    public void generateWorld() {

        Body body;

        // Create (Rectangle) Ground Bodies/Fixtures
        for (MapObject object : map.getLayers().get("ground").getObjects().getByType(RectangleMapObject.class)) {
            B2DFactory.makeRectGround(world, (RectangleMapObject) object);
        }

        // Create (Polygon) Ground Bodies/Fixtures
        for (MapObject object : map.getLayers().get("ground").getObjects().getByType(PolygonMapObject.class)) {
            B2DFactory.makePolygonGround(world, (PolygonMapObject) object);
        }

        // Create Water
        for (MapObject object : map.getLayers().get("water").getObjects().getByType(RectangleMapObject.class)) {
            body = B2DFactory.makeWater(world, (RectangleMapObject) object);
            fluids.add(body);
        }

        // Create Platforms
        for (MapObject object : map.getLayers().get("platforms").getObjects().getByType(RectangleMapObject.class)) {
            body = B2DFactory.makePlatform(world, (RectangleMapObject) object);
            platforms.add(body);
        }

        // Get Ball start pos
        MapObject startPosObj = map.getLayers().get("start_pos").getObjects().get(0);
        Rectangle startPosRect = ((RectangleMapObject) startPosObj).getRectangle();
        this.ball = new BallModel(world, new Vector2(startPosRect.getX() * PIXEL_TO_METER, startPosRect.getY() * PIXEL_TO_METER));

        // Get Ball end pos
        MapObject endPosObj = map.getLayers().get("end_pos").getObjects().get(0);
        Rectangle endPosRect = ((RectangleMapObject) endPosObj).getRectangle();
        this.endPos = new Vector2(endPosRect.getX() * PIXEL_TO_METER, endPosRect.getY() * PIXEL_TO_METER);

    }

    public Array<Body> getFluids() {
        return fluids;
    }

    public BallModel getBall() {
        return ball;
    }
}
