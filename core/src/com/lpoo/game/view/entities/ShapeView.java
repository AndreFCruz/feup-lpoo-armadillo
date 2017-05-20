package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.lpoo.game.model.entities.ShapeModel;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * Created by andre on 20/05/2017.
 */

public abstract class ShapeView {

    private float[] vertices;

    private Color color;

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(color);
        renderer.polygon(vertices);
    }

    public void update(ShapeModel model) {
        PolygonShape poly = (PolygonShape) model.getShape();
        this.vertices = new float[poly.getVertexCount() * 2];

        for (int i = 0; i < poly.getVertexCount(); i++) {
            Vector2 vert = new Vector2();
            poly.getVertex(i, vert);
            vertices[i*2] = vert.x / PIXEL_TO_METER;
            vertices[i*2 + 1] = vert.y / PIXEL_TO_METER;
            System.err.print("_" + vertices[i*2] + " . " + vertices[i*2 + 1] + "_");
        }
        System.err.println("---");
    }

    protected void setColor(Color color) {
        this.color = color;
    }
}
