package com.lpoo.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A model representing a single ball.
 */
public class BallModel extends EntityModel {

    /**
     * Constant Power Up Ratio used for density increasing / decreasing.
     */
    private static final float POWERUP_RATIO = 2f;
    /**
     * Constant Power Up Ratio used for velocity increasing / decreasing.
     */
    private static final double VEL_POWERUP_RATIO = 1.6;


    /**
     * Possible Ball States.
     */
    public enum State {
        LANDED, FLYING, DUNKING
    }

    /**
     * Ball's Angular Damping.
     */
    private static final float ANGULAR_DAMP = 5f;
    /**
     * Ball's Linear Damping.
     */
    private static final float LINEAR_DAMP = 0.1f;

    /**
     * Ball's Radius.
     */
    public static final float RADIUS = 32 * PIXEL_TO_METER;

    /**
     * Ball's Density.
     */
    private float density = .5f;
    /**
     * Ball's Friction.
     */
    private float friction = 10f;
    /**
     * Ball's Restitution.
     */
    private float restitution = 0.5f;

    /**
     * Ball's Current State.
     */
    private State state = State.LANDED;

    /**
     * Ball's angular acceleration.
     */
    private float angular_accel = density * 22000f;
    /**
     * Ball's Jump Force.
     */
    private float jump_force = density * 60f;

    /**
     * Ball Model's Constructor.
     * Creates a ball in a given starting position, in a given world.
     *
     * @param world Physics world where the ball will be used in.
     * @param pos   Ball's initial Position.
     */
    public BallModel(World world, Vector2 pos) {
        super(world, pos, ModelType.BALL, ANGULAR_DAMP, LINEAR_DAMP);

        // Create Fixture's Shape
        Shape circle = new CircleShape();
        circle.setRadius(RADIUS);

        createFixture(new FixtureProperties(circle, density, friction, restitution, BALL_BIT,
                (short) (BALL_BIT | GROUND_BIT | FLUID_BIT | HITTABLE_BIT)));
    }

    /**
     * Rotates Ball with the given value.
     *
     * @param delta value to rotate ball with.
     */
    public void rotate(float delta) {
        getBody().applyTorque(delta * angular_accel, true);
    }

    /**
     * Logic associated to the Ball.
     * Controls the Ball's current state, making it jump if on the ground, making it dunk if flying.
     */
    public void jump() {
        Body body = getBody();
        switch (this.state) {
            case LANDED:
                body.applyLinearImpulse(new Vector2(0, jump_force), body.getWorldCenter(), true);
                this.state = State.FLYING;
                break;
            case FLYING:
                body.applyLinearImpulse(new Vector2(0, -1.5f * jump_force), body.getWorldCenter(), true);
                this.state = State.DUNKING;
                break;
            case DUNKING:
                // Do nothing
                break;
            default:
                System.err.println("Unhandled state in BallEntity");
        }
    }

    /**
     * Set the Ball's current state to the given state.
     *
     * @param st state to update the Ball with.
     */
    public void setState(State st) {
        this.state = st;
    }

    /**
     * Increase Ball's density.
     * Uses constant PowerUp Ratio.
     */
    void increaseDensity() {
        density *= POWERUP_RATIO;
        getBody().getFixtureList().first().setDensity(density);
    }

    /**
     * Decrease Ball's density.
     * Uses constant PowerUp Ratio.
     */
    void decreaseDensity() {
        density *= 1 / POWERUP_RATIO;
        getBody().getFixtureList().first().setDensity(density);
    }

    /**
     * Increase Ball's velocity.
     * Uses constant Velocity PowerUp Ratio.
     */
    void increaseVelocity() {
        angular_accel *= VEL_POWERUP_RATIO;
        friction *= VEL_POWERUP_RATIO;
        getBody().getFixtureList().first().setFriction(friction);
    }

    /**
     * Decrease Ball's velocity.
     * Uses constant Velocity PowerUp Ratio.
     */
    void decreaseVelocity() {
        angular_accel *= 1 / VEL_POWERUP_RATIO;
        friction *= 1 / VEL_POWERUP_RATIO;
        getBody().getFixtureList().first().setFriction(friction);
    }

    /**
     * Increase Ball's Jump Force.
     */
    void increaseJumpForce() {
        jump_force *= 1.2f;
    }

    /**
     * Decrease Ball's Jump Force.
     */
    void decreaseJumpForce() {
        jump_force /= 1.2f;
    }

    /**
     * Change the Ball's Gravity.
     */
    void flipGravity() {
        if (getBody().getGravityScale() == 1)
            getBody().setGravityScale(-1);
        else
            getBody().setGravityScale(1);

        jump_force *= -1;
    }

    //Getters for Tests

    /**
     * @return The Ball's Jump Force
     */
    public float getJumpForce() {
        return jump_force;
    }

    /**
     * @return The Ball's Acceleration
     */
    public float getAcceleration() {
        return angular_accel;
    }

    /**
     * @return The Ball's Density
     */
    public float getDensity() {
        return density;
    }

    /**
     * @return The Ball's Power Up Ratio.
     */
    public float getPowerUpRatio() { return POWERUP_RATIO; }
}
