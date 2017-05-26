package com.lpoo.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A model representing the player's ball.
 */
public class BallModel extends EntityModel {
    public enum State {LANDED, FLYING, DUNKING}

    private static final float ANGULAR_DAMP = 2f;
    private static final float LINEAR_DAMP = 0.1f;

    public static final float radius = 32 * PIXEL_TO_METER;

    private float density = .5f;
    private float friction = 40f;
    private float restitution = 0.5f;

    private State state = State.LANDED;

    private float angular_accel = density * 12000f;
    private float jump_force = density * 50f;

    public BallModel(World world, Vector2 pos) {
        super(world, pos, ModelType.BALL, ANGULAR_DAMP, LINEAR_DAMP);

        //this.body.setUserData(this);    // Necessary ? or downcast works with EntityModel's pointer?

        // Create Fixture's Shape
        Shape circle = new CircleShape();
        circle.setRadius(radius);

        createFixture(circle, density, friction, restitution, BALL_BIT, (short) (BALL_BIT | GROUND_BIT | FLUID_BIT));
    }

    public void rotate(float delta) {
        body.applyTorque(delta * angular_accel, true);
    }

    public void jump() {
        switch (this.state) {
            case LANDED:
                this.body.applyLinearImpulse(new Vector2(0, jump_force), body.getWorldCenter(), true);
                this.state = State.FLYING;
                break;
            case FLYING:
                this.body.applyLinearImpulse(new Vector2(0, -3 * jump_force), body.getWorldCenter(), true);
                this.state = State.DUNKING;
                break;
            case DUNKING:
                // Do nothing
                break;
            default:
                System.err.println("Unhandled state in BallEntity");
        }
    }

    public void setState(State st) {
        this.state = st;
    }

}
