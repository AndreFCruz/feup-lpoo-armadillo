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
 * An abstract model representing an entity belonging to a game model.
 */
public abstract class EntityModel {

    static class FixtureProperties {
        private final Shape shape;
        private final float density;
        private final float friction;
        private final float restitution;
        private final short category;
        private final short mask;
        private boolean isSensor;

        FixtureProperties(Shape shape, short category, short mask) {
            this(shape, 1, 1, 1, category, mask);
        }

        FixtureProperties(Shape shape, float density, float friction, float restitution, short category, short mask) {
            this.shape = shape;
            this.density = density;
            this.friction = friction;
            this.restitution = restitution;
            this.category = category;
            this.mask = mask;
            this.isSensor = false;
        }

        void setSensor() {
            isSensor = true;
        }

        boolean isSensor() {
            return isSensor;
        }

        Shape getShape() {
            return shape;
        }

        float getDensity() {
            return density;
        }

        float getFriction() {
            return friction;
        }

        float getRestitution() {
            return restitution;
        }

        short getCategory() {
            return category;
        }

        short getMask() {
            return mask;
        }
    }

    public enum ModelType {BALL, BOX, POWERUP_JUMP, POWERUP_VELOCITY, POWERUP_RANDOM, POWERUP_GRAVITY}

    // Constants for collision mask
    public final static short GROUND_BIT = 0x01;
    public final static short BALL_BIT = 0x02;
    public final static short FLUID_BIT = 0x04;
    public final static short HITTABLE_BIT = 0x08;

    final private ModelType type;

    final private Body body;

    private boolean flaggedForRemoval = false;

    EntityModel(World world, Vector2 pos, ModelType type) {
        this(world, pos, type, 0f, 0f);
    }

    EntityModel(World world, Vector2 pos, ModelType type, float angDamp, float linDamp) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(pos);
        bdef.angularDamping = angDamp;
        bdef.linearDamping = linDamp;

        body = world.createBody(bdef);
        body.setActive(true);
        body.setUserData(this);

        this.type = type;
    }

    /**
     * Creates a polygon shape from a pixel based list of vertices
     * @param vertices  Vertices defining an image in pixels
     * @param size      Width/Height of the bitmap the vertices were extracted from
     * @return      A PolygonShape with the correct vertices
     */
    static PolygonShape createPolygonShape(float[] vertices, Vector2 size) {
        // Transform pixels into meters, center and invert the y-coordinate
        for (int i = 0; i < vertices.length; i++) {
            if (i % 2 == 0) vertices[i] -= size.x / 2;   // center the vertex x-coordinate
            if (i % 2 != 0) vertices[i] -= size.y / 2;  // center the vertex y-coordinate

            if (i % 2 != 0) vertices[i] *= -1;          // invert the y-coordinate

            vertices[i] *= PIXEL_TO_METER;              // scale from pixel to meter
        }

        PolygonShape polygon = new PolygonShape();
        polygon.set(vertices);

        return polygon;
    }

    final void createFixture(FixtureProperties fixtureProperties) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = fixtureProperties.getShape();

        fixtureDef.density = fixtureProperties.getDensity();
        fixtureDef.friction = fixtureProperties.getFriction();
        fixtureDef.restitution = fixtureProperties.getRestitution();
        fixtureDef.filter.categoryBits = fixtureProperties.getCategory();
        fixtureDef.filter.maskBits = fixtureProperties.getMask();
        fixtureDef.isSensor = fixtureProperties.isSensor();

        body.createFixture(fixtureDef);

        fixtureProperties.getShape().dispose();
    }

    protected Body getBody() {
        return body;
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

    public ModelType getType() {
        return type;
    }

    public boolean isFlaggedForRemoval() {
        return flaggedForRemoval;
    }

    void flagForRemoval() {
        flaggedForRemoval = true;
    }

}
