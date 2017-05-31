package com.lpoo.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * Created by andre on 31/05/2017.
 */

public abstract class PowerUp extends EntityModel {

    private static float RADIUS = 32 * PIXEL_TO_METER;

    PowerUp(World world, Vector2 pos, ModelType type) {
        super(world, pos, type);

        getBody().setType(BodyDef.BodyType.StaticBody);

        Shape shape = createPolygonShape(new float[]{
                14f, 1f, 18f, 1f, 25f, 7f, 25f, 23f, 18f, 31f, 14f, 31f, 7f, 23f, 7f, 7f
        }, new Vector2(32, 32));

        createSensorFixture(shape, BALL_BIT);
    }

    private void createSensorFixture(Shape shape, short mask) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        getBody().createFixture(fixtureDef);

        shape.dispose();
    }

    public abstract void onHit(BallModel model);

}
