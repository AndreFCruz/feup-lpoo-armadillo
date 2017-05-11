package com.lpoo.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * Created by andre on 05/05/2017.
 */

public abstract class EntityModel {
    public enum ModelType {BALL};

    // Constants for collision mask
    public final static short GROUND_BIT = 0x01;
    final static short BALL_BIT = 0x02;

    final private ModelType type;

    final private Body body;

    private float angular_accel = 10000f;

    EntityModel(World world, Vector2 pos, ModelType type) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(pos);

        body = world.createBody(bdef);
        body.setActive(true);
        // setUserData ?

        this.type = type;
    }

    /**
     * Creates a polygon shape from a pixel based list of vertices
     * @param vertices  Vertices defining an image in pixels
     * @param width     Width of the bitmap the vertices were extracted from
     * @param height    Height of the bitmap the vertices were extracted from
     * @return      A PolygonShape with the correct vertices
     */
    final static PolygonShape createPolygonShape(float[] vertices, int width, int height) {
        // Transform pixels into meters, center and invert the y-coordinate
        for (int i = 0; i < vertices.length; i++) {
            if (i % 2 == 0) vertices[i] -= width / 2;   // center the vertex x-coordinate
            if (i % 2 != 0) vertices[i] -= height / 2;  // center the vertex y-coordinate

            if (i % 2 != 0) vertices[i] *= -1;          // invert the y-coordinate

            vertices[i] *= PIXEL_TO_METER;              // scale from pixel to meter
        }

        PolygonShape polygon = new PolygonShape();
        polygon.set(vertices);

        return polygon;
    }

    final void createFixture(Shape shape, float density, float friction, float restitution, short category, short mask) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.maskBits = mask;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    /**
     * Wraps the getX method from the Box2D body class.
     *
     * @return the x-coordinate of this body.
     */
    public float getX() {
        return body.getPosition().x;
    }

    /**
     * Wraps the getY method from the Box2D body class.
     *
     * @return the y-coordinate of this body.
     */
    public float getY() {
        return body.getPosition().y;
    }

    /**
     * Wraps the getAngle method from the Box2D body class.
     *
     * @return the angle of rotation of this body.
     */
    public float getAngle() {
        return body.getAngle();
    }

    /**
     * Wraps the setTransform method from the Box2D body class.
     *
     * @param x the new x-coordinate for this body
     * @param y the new y-coordinate for this body
     * @param angle the new rotation angle for this body
     */
    public void setTransform(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    /**
     * Sets the angular velocity of this object in the direction it is rotated.
     *
     * @param velocity the new linear velocity angle for this body
     */
    public void setLinearVelocity(float velocity) {
        body.setLinearVelocity((float)(velocity * -Math.sin(getAngle())), (float) (velocity * Math.cos(getAngle())));
    }

    /**
     * Wraps the setAngularVelocity method from the Box2D body class.
     *
     * @param omega the new angular velocity angle for this body
     */
    public void setAngularVelocity(float omega) {
        body.setAngularVelocity(omega);
    }

    /**
     * Wraps the applyForceToCenter method from the Box2D body class.
     *delta * angular_vel
     * @param forceX the x-component of the force to be applied
     * @param forceY the y-component of the force to be applied
     * @param awake should the body be awaken
     */
    public void applyForceToCenter(float forceX, float forceY, boolean awake) {
        body.applyForceToCenter(forceX, forceY, awake);
    }

    public void rotate(float delta) {
        body.applyTorque(delta * angular_accel, true);
    }

    public float getRotation() {
        return body.getAngle();
    }

    public ModelType getType() {
        return type;
    }

}
