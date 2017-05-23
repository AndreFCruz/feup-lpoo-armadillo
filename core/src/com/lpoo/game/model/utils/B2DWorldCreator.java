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
import com.lpoo.game.model.entities.PlatformModel;
import com.lpoo.game.model.entities.ShapeModel;
import com.lpoo.game.model.entities.WaterModel;

import java.util.ArrayList;
import java.util.List;

import sun.security.provider.SHA;

import static com.lpoo.game.model.entities.EntityModel.FLUID_BIT;
import static com.lpoo.game.model.entities.EntityModel.GROUND_BIT;
import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A class to load TiledMap layers and correctly create the associated physics world.
 */
public class B2DWorldCreator {
    private World world;
    private Map map;
    private Array<WaterModel> fluids = new Array<WaterModel>();
    private Array<ShapeModel> shapeModels = new Array<ShapeModel>();
    private BallModel ball;

    private Vector2 endPos;

    public B2DWorldCreator(World world, Map map) {
        this.world = world;
        this.map = map;
    }

    public void generateWorld() {

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
            fluids.add(B2DFactory.makeWater(world, (RectangleMapObject) object));
        }

        // Create Platforms
        for (MapObject object : map.getLayers().get("platforms").getObjects().getByType(RectangleMapObject.class)) {
            shapeModels.add(B2DFactory.makePlatform(world, (RectangleMapObject) object));
        }

        // Get Ball start pos
        MapObject startPosObj = map.getLayers().get("start_pos").getObjects().get(0);
        Rectangle startPosRect = ((RectangleMapObject) startPosObj).getRectangle();
        this.ball = new BallModel(world, new Vector2(startPosRect.getX() * PIXEL_TO_METER, startPosRect.getY() * PIXEL_TO_METER));

        // Get Ball end pos
        MapObject endPosObj = map.getLayers().get("end_pos").getObjects().get(0);
        Rectangle endPosRect = ((RectangleMapObject) endPosObj).getRectangle();
        this.endPos = new Vector2(
                (endPosRect.getX() + endPosRect.getWidth() / 2) * PIXEL_TO_METER,
                (endPosRect.getY() + endPosRect.getHeight() / 2) * PIXEL_TO_METER);

    }
    public Vector2 getEndPos() {
        return endPos;
    }

    public BallModel getBall() {
        return ball;
    }

    public Array<WaterModel> getFluids() {
        return fluids;
    }

    public Array<ShapeModel> getShapeModels() {
        return shapeModels;
    }
}
