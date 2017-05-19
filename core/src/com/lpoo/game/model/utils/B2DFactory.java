package com.lpoo.game.model.utils;


import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.game.model.controllers.BuoyancyController;

import static com.lpoo.game.model.entities.EntityModel.FLUID_BIT;
import static com.lpoo.game.model.entities.EntityModel.GROUND_BIT;
import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * Created by andre on 19/05/2017.
 */

public class B2DFactory {

    public static Body makePlatform(World world, RectangleMapObject object) {
        Rectangle rect = object.getRectangle();

        // Body and Fixture variables
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(PIXEL_TO_METER * (rect.getX() + rect.getWidth() / 2), PIXEL_TO_METER * (rect.getY() + rect.getHeight() / 2));

        Body body = world.createBody(bdef);

        shape.setAsBox((rect.getWidth() / 2) * PIXEL_TO_METER, (rect.getHeight() / 2) * PIXEL_TO_METER);
        fdef.shape = shape;
        fdef.filter.categoryBits = GROUND_BIT;
        fdef.isSensor = false;
        fdef.density = 0.5f;
        fdef.friction = 0.1f;
        fdef.restitution = 0f;
        body.createFixture(fdef);

        return body;
    }

    public static Body makeWater(World world, RectangleMapObject object) {
        Rectangle rect = object.getRectangle();

        // Body and Fixture variables
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(PIXEL_TO_METER * (rect.getX() + rect.getWidth() / 2), PIXEL_TO_METER * (rect.getY() + rect.getHeight() / 2));

        Body body = world.createBody(bdef);

        shape.setAsBox((rect.getWidth() / 2) * PIXEL_TO_METER, (rect.getHeight() / 2) * PIXEL_TO_METER);
        fdef.shape = shape;
        fdef.filter.categoryBits = FLUID_BIT;
        fdef.isSensor = true;
        fdef.density = 1f;
        fdef.friction = 0.1f;
        fdef.restitution = 0f;
        body.createFixture(fdef);

        body.setUserData(new BuoyancyController(world, body.getFixtureList().first()));

        return body;
    }

    public static Body makePolygonGround(World world, PolygonMapObject object) {
        Polygon polygon = object.getPolygon();

        // Body and Fixture variables
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(PIXEL_TO_METER * polygon.getX(), PIXEL_TO_METER * polygon.getY());

        Body body = world.createBody(bdef);

        float[] new_vertices = polygon.getVertices().clone();
        for (int i = 0; i < new_vertices.length; i++)
            new_vertices[i] *= PIXEL_TO_METER;

        shape.set(new_vertices);
        fdef.shape = shape;
        fdef.filter.categoryBits = GROUND_BIT;
        body.createFixture(fdef);

        return body;
    }

    public static Body makeRectGround(World world, RectangleMapObject object) {
        Rectangle rect = object.getRectangle();

        // Body and Fixture variables
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(PIXEL_TO_METER * (rect.getX() + rect.getWidth() / 2), PIXEL_TO_METER * (rect.getY() + rect.getHeight() / 2));

        Body body = world.createBody(bdef);

        shape.setAsBox((rect.getWidth() / 2) * PIXEL_TO_METER, (rect.getHeight() / 2) * PIXEL_TO_METER);
        fdef.shape = shape;
        fdef.filter.categoryBits = GROUND_BIT;
        body.createFixture(fdef);

        return body;
    }
}
