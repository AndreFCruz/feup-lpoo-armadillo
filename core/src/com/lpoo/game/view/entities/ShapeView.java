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

    //private Polygon poly;

    private Rectangle rect;

    private Vector2 center;

    private float rotation;


    public void draw(ShapeRenderer renderer) {
        if (rect == null) return;

        renderer.setColor(color);
        renderer.rect(center.x - rect.getWidth() / 2, center.y - rect.getHeight() / 2,
                rect.getWidth() / 2, rect.getHeight() / 2, rect.getWidth(), rect.getHeight(), 1, 1, rotation);
    }

    public void update(ShapeModel model) {
        rect = (Rectangle) model.getShape();
        rotation = (float) Math.toDegrees(model.getRotation());
        center = new Vector2(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
        //rect.getCenter(center);
/*        rect.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);

        poly = new Polygon(new float[] {
                rect.x, rect.y,
                rect.x, rect.y + rect.height,
                rect.x + rect.width, rect.y + rect.height,
                rect.x + rect.width, rect.y
        });
        poly.setOrigin(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
        poly.rotate((float) Math.toDegrees(model.getRotation()));*/
    }

    protected void setColor(Color color) {
        this.color = color;
    }
}
