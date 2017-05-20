package com.lpoo.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by andre on 20/05/2017.
 */

public abstract class ShapeModel {
    public enum ModelType {WATER, PLATFORM}

    final private ModelType type;

    protected Body body;

    public ShapeModel(ModelType type) {
        this.type = type;
    }

    public ShapeModel.ModelType getType() {
        return type;
    }

    public Shape getShape() {
        return body.getFixtureList().first().getShape();
    }

}
