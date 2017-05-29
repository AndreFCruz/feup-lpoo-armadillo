package com.lpoo.game.model.entities;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by andre on 20/05/2017.
 */

public abstract class ShapeModel {

    public enum ModelType {PLATFORM}

    final private ModelType type;

    protected Body body;

    final private Shape2D shape;

    ShapeModel(ModelType type, Shape2D shape) {
        this.type = type;
        this.shape = shape;
    }

    public Body getBody() {
        return body;
    }

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

    public float getRotation() {
        return body.getAngle();
    }

    public Shape2D getShape() {
        return shape;
    }

}
