package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.lpoo.game.model.entities.ShapeModel;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;


/**
 * Created by andre on 20/05/2017.
 */

public abstract class ShapeView {

    protected Color color;

    private Polygon poly = new Polygon();

    public void draw(ShapeRenderer renderer) {
        if (poly == null) return;

        renderer.setColor(color);
        renderer.polygon(poly.getTransformedVertices());
    }

    public void update(ShapeModel model) {
        Rectangle rect = (Rectangle) model.getShape();
        rect.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
        poly.setVertices(new float[] {
                rect.x, rect.y,
                rect.x, rect.y + rect.height,
                rect.x + rect.width, rect.y + rect.height,
                rect.x + rect.width, rect.y
        });
        poly.setRotation((float) Math.toDegrees(model.getRotation()));
    }

    protected void setColor(Color color) {
        this.color = color;
    }
}
