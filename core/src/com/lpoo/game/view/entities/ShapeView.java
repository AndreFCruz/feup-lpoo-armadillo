package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.game.model.entities.ShapeModel;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;


/**
 * Abstract Class used to represent the View of a Shape.
 */
public abstract class ShapeView {

    /**
     * The color of the Shape's view.
     */
    protected Color color;

    /**
     * The Shape's surrounding rectangle.
     */
    private Rectangle rect;

    /**
     * The position indicating the Shape's center.
     */
    private Vector2 center;

    /**
     * The rotation of the Shape, relatively to the original position.
     */
    private float rotation;

    /**
     * Draws the Shape in the screen, using the given renderer.
     *
     * @param renderer The Shape renderer.
     */
    public void draw(ShapeRenderer renderer) {
        if (rect == null) return;

        renderer.setColor(color);
        renderer.rect(center.x - rect.getWidth() / 2, center.y - rect.getHeight() / 2,
                rect.getWidth() / 2, rect.getHeight() / 2, rect.getWidth(), rect.getHeight(), 1, 1, rotation);
    }

    /**
     * Updates the Shape, by updating its surrounding rectangle, center and rotation.
     *
     * @param model The Shape Model to be updated.
     */
    public void update(ShapeModel model) {
        rect = (Rectangle) model.getShape();
        rotation = (float) Math.toDegrees(model.getAngle());
        center = new Vector2(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
    }

    /**
     * Change the Shape's color to the given color.
     *
     * @param color Color to draw Shape with.
     */
    protected void setColor(Color color) {
        this.color = color;
    }
}
