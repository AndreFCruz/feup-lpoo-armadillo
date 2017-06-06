package com.lpoo.game.model.entities;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A model representing a single box.
 */

public class BoxModel extends EntityModel {
    private static final float ANGULAR_DAMP = 0.2f;
    private static final float LINEAR_DAMP = 0.1f;
    private static final float FRICTION = 0.5f;
    private static final float RESTITUTION = 0.2f;

    private float density = 0.5f;

    public BoxModel(World world, RectangleMapObject object) {
        super(world, object.getRectangle().getCenter(new Vector2()).scl(PIXEL_TO_METER), ModelType.BOX, ANGULAR_DAMP, LINEAR_DAMP);

        // Fetch density from properties
        Float property = object.getProperties().get("density", float.class);
        if (property != null)
            density = property;

        // Create Fixture's Shape
        Shape shape = createPolygonShape(new float[] {
           0, 0, 64, 0, 64, 64, 0, 64
        }, new Vector2(64, 64));

        createFixture(new FixtureProperties(shape, density, FRICTION, RESTITUTION, GROUND_BIT, (short) (BALL_BIT | GROUND_BIT | FLUID_BIT)));
    }
}
