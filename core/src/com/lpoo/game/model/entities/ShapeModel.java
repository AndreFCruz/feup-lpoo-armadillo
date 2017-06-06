package com.lpoo.game.model.entities;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * A model representing an abstract shape entity.
 */

public abstract class ShapeModel {

    /**
     * Enum representing the model's type.
     */
    public enum ModelType {PLATFORM}

    /**
     * This model's type.
     */
    final private ModelType type;

    /**
     * This model's body.
     */
    protected Body body;

    /**
     * This model's shape.
     */
    final private Shape2D shape;

    /**
     * Constructor for a ShapeModel.
     * @param type The model's type.
     * @param shape The model's shape.
     */
    ShapeModel(ModelType type, Shape2D shape) {
        this.type = type;
        this.shape = shape;
    }

    /**
     * Getter for the model's body.
     * @return This model's body.
     */
    public Body getBody() {
        return body;
    }

    /**
     * Getter for the model's type.
     * @return This model's type
     */
    public ShapeModel.ModelType getType() {
        return type;
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
     * Wraps the getAndle method from the Box2D body class.
     * @return the body's rotation in degrees.
     */
    public float getAngle() {
        return body.getAngle();
    }

    /**
     * Getter for the model's shape.
     * @return this model's shape.
     */
    public Shape2D getShape() {
        return shape;
    }

}
