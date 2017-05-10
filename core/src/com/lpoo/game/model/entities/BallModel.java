package com.lpoo.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by andre on 05/05/2017.
 */

public class BallModel extends EntityModel {
    private static int radius = 40;  // the ball's radius in world coordinates
    private static float density = 1f;
    private static float friction = 1f;
    private static float restitution = 1f;

    public BallModel(World world, Vector2 pos) {
        super(world, pos, ModelType.BALL);

        // Create Fixture's Shape
        Shape circle = new CircleShape();
        circle.setRadius(radius);

        createFixture(circle, density, friction, restitution, BALL_BODY, BALL_BODY );

    }

}
