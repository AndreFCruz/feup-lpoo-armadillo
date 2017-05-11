package com.lpoo.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * Created by andre on 05/05/2017.
 */

public class BallModel extends EntityModel {
    private static float radius = 64 * PIXEL_TO_METER;
    private static float density = 1f;
    private static float friction = 1f;
    private static float restitution = 0.8f;

    public BallModel(World world, Vector2 pos) {
        super(world, pos, ModelType.BALL);

        // Create Fixture's Shape
        Shape circle = new CircleShape();
        circle.setRadius(radius);

        createFixture(circle, density, friction, restitution, BALL_BIT, (short) (BALL_BIT | GROUND_BIT));

    }

}
