package com.lpoo.game.model.utils;


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
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.lpoo.game.model.entities.BallModel;
import com.lpoo.game.model.entities.BoxModel;
import com.lpoo.game.model.entities.GravityPowerUp;
import com.lpoo.game.model.entities.JumpPowerUp;
import com.lpoo.game.model.entities.PlatformModel;
import com.lpoo.game.model.entities.PowerUp;
import com.lpoo.game.model.entities.RandomPowerUp;
import com.lpoo.game.model.entities.SpeedPowerUp;
import com.lpoo.game.model.entities.WaterModel;

import static com.lpoo.game.model.entities.EntityModel.GROUND_BIT;
import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A factory for Box2D World objects.
 */
class B2DFactory {

    static PlatformModel makePlatform(World world, RectangleMapObject object) {
        PlatformModel platform = new PlatformModel(world, object);

        Boolean pivoted = object.getProperties().get("pivoted", boolean.class);
        if (pivoted != null && pivoted == true) {
            Rectangle rect = object.getRectangle();
            RectangleMapObject anchorRect = new RectangleMapObject(
                    rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2, 1, 1
            );
            Body anchor = makeRectGround(world, anchorRect);

            RevoluteJointDef jointDef = new RevoluteJointDef();
            jointDef.initialize(anchor, platform.getBody(), platform.getBody().getWorldCenter());
            world.createJoint(jointDef);
        }

        return platform;
    }

    static WaterModel makeWater(World world, RectangleMapObject object) {
        return new WaterModel(world, object.getRectangle());
    }

    static Body makePolygonGround(World world, PolygonMapObject object) {
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

    static Body makeRectGround(World world, RectangleMapObject object) {
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

    static BoxModel makeBox(World world, RectangleMapObject object) {
        return new BoxModel(world, object);
    }

    static BallModel makeBall(World world, RectangleMapObject rectObj) {
        Rectangle rect = rectObj.getRectangle();
        return new BallModel(world, new Vector2(rect.getX() * PIXEL_TO_METER, rect.getY() * PIXEL_TO_METER));
    }

    static PowerUp makePowerUp(World world, RectangleMapObject object) {
        String classProperty = object.getProperties().get("class", String.class);
        if (classProperty == null) {
            System.err.println("PowerUp has no class set!");
            return null;
        }

        switch (classProperty) {
            case "gravity":
                return new GravityPowerUp(world, object);
            case "velocity":
                return new SpeedPowerUp(world, object);
            case "jump":
                return new JumpPowerUp(world, object);
            default:
                return new RandomPowerUp(world, object);
        }
    }
}
