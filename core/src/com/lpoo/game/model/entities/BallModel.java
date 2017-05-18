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
    public static enum State {LANDED, FLYING, DUNKING}

    private static final float ANGULAR_DAMP = 2f;
    private static final float LINEAR_DAMP = 0.1f;

    private static float radius = 32 * PIXEL_TO_METER;
    private static float density = .5f;
    private static float friction = 40f;
    private static float restitution = 0.5f;

    private State state = State.LANDED;
    private float jump_force = density * 60f;

    public BallModel(World world, Vector2 pos) {
        super(world, pos, ModelType.BALL, ANGULAR_DAMP, LINEAR_DAMP);

        this.body.setUserData(this);    // Necessary ? or downcast works with EntityModel's pointer?

        // Create Fixture's Shape
        Shape circle = new CircleShape();
        circle.setRadius(radius);

        createFixture(circle, density, friction, restitution, BALL_BIT, (short) (BALL_BIT | GROUND_BIT | FLUID_BIT));

//        PolygonShape polygon = new PolygonShape();
//        float ang_increment = (float) Math.PI * 2 / 8;
//        List<Vector2> vertices = new ArrayList<Vector2>(8);
//
//        for (int i = 0; i < 8; i++) {
//            float ang = i * ang_increment;
//            vertices.add(new Vector2((float) (radius * Math.cos(ang)), (float) (radius * Math.sin(ang))));
//        }
//        Vector2[] vertArray = new Vector2[vertices.size()];
//        polygon.set(vertices.toArray(vertArray));
//
//        createFixture(polygon, density, friction, restitution, BALL_BIT, (short) (BALL_BIT | GROUND_BIT | FLUID_BIT) );
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
