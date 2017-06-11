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

    /**
     * Static Class used to represent Fixture Properties
     */
    static class FixtureProperties {
        /**
         * The Fixture Shape.
         */
        private final Shape shape;
        /**
         * The Fixture Density.
         */
        private final float density;
        /**
         * The Fixture Friction.
         */
        private final float friction;
        /**
         * The Fixture Restitution.
         */
        private final float restitution;
        /**
         * The Fixture Category.
         */
        private final short category;
        /**
         * The Fixture Mask.
         */
        private final short mask;
        /**
         * Flag informing if Fixture is a sensor or not.
         */
        private boolean isSensor;

        /**
         * Fixture Properties' constructor.
         *
         * @param shape    The Fixture's shape
         * @param category The Fixture's category
         * @param mask     The Fixture's Mask
         */
        FixtureProperties(Shape shape, short category, short mask) {
            this(shape, 1, 1, 1, category, mask);
        }

        /**
         * Fixture Properties' constructor.
         *
         * @param shape       The Fixture's shape.
         * @param density     The Fixture's density
         * @param friction    The Fixture's friction.
         * @param restitution The Fixture's restitution.
         * @param category    The Fixture's category.
         * @param mask        The Fixture's mask.
         */
        FixtureProperties(Shape shape, float density, float friction, float restitution, short category, short mask) {
            this.shape = shape;
            this.density = density;
            this.friction = friction;
            this.restitution = restitution;
            this.category = category;
            this.mask = mask;
            this.isSensor = false;
        }

        /**
         * Set isSensor falg to true.
         */
        void setSensor() {
            isSensor = true;
        }

        /**
         * @return flag value informing if the Fixture is a sensor.
         */
        boolean isSensor() {
            return isSensor;
        }

        /**
         * @return Fixture's shape.
         */
        Shape getShape() {
            return shape;
        }

        /**
         * @return Fixture's density.
         */
        float getDensity() {
            return density;
        }

        /**
         * @return Fixture's friction.
         */
        float getFriction() {
            return friction;
        }

        /**
         * @return Fixture's restitution.
         */
        float getRestitution() {
            return restitution;
        }

        /**
         * @return Fixture's category.
         */
        short getCategory() {
            return category;
        }

        /**
         * @return Fixture's mask.
         */
        short getMask() {
            return mask;
        }
    }

    /**
     * The possible type of entity models.
     */
    public enum ModelType {
        BALL, BOX, POWERUP_JUMP, POWERUP_VELOCITY, POWERUP_RANDOM, POWERUP_GRAVITY
    }

    /**
     * Constant for collision mask, for ground.
     */
    public final static short GROUND_BIT = 0x01;
    /**
     * Constant for collision mask, for ball.
     */
    public final static short BALL_BIT = 0x02;
    /**
     * Constant for collision mask, for fluid.
     */
    public final static short FLUID_BIT = 0x04;
    /**
     * Constant for collision mask, for hittable.
     */
    public final static short HITTABLE_BIT = 0x08;

    /**
     * The Entity Model's type.
     */
    final private ModelType type;

    /**
     * The Entity Model's body.
     */
    final private Body body;

    /**
     * Flag informing if the Entity Model should be removed.
     */
    private boolean flaggedForRemoval = false;

    /**
     * Entity Model's Constructor.
     *
     * @param world world the entity model will be in.
     * @param pos   entity model initial position.
     * @param type  entity model type.
     */
    EntityModel(World world, Vector2 pos, ModelType type) {
        this(world, pos, type, 0f, 0f);
    }

    /**
     * Entity Model's Constructor.
     *
     * @param world   world the entity model will be in.
     * @param pos     entity model initial position.
     * @param type    entity model type.
     * @param angDamp entity model angular damping.
     * @param linDamp entity model linear damping.
     */
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
     *
     * @param vertices Vertices defining an image in pixels
     * @param size     Width/Height of the bitmap the vertices were extracted from
     * @return A PolygonShape with the correct vertices
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

    /**
     * Creates a Fixture with the given Fixture Properties.
     *
     * @param fixtureProperties Fixture properties used to generate the fixture
     */
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

    /**
     * @return The Entity Model's body.
     */
    public Body getBody() {
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

    /**
     * @return The entity model's type.
     */
    public ModelType getType() {
        return type;
    }

    /**
     * @return flag 'flaggedForRemoval' current value.
     */
    public boolean isFlaggedForRemoval() {
        return flaggedForRemoval;
    }

    /**
     * Set the entity model to be removed.
     */
    void flagForRemoval() {
        flaggedForRemoval = true;
    }

}
