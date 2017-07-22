package com.lpoo.game.model.entities;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A model representing an abstract PowerUp entity.
 */
public abstract class PowerUp extends EntityModel implements Hittable {

    /**
     * Generic Power Up's constructor.
     * Creates a power up from the given object, with the given type, into the given world.
     *
     * @param world  The world the power up will be in.
     * @param object The object to create the power up with.
     * @param type   Type of Power Up.
     */
    public PowerUp(World world, RectangleMapObject object, ModelType type) {
        super(world, object.getRectangle().getCenter(new Vector2()).scl(PIXEL_TO_METER), type);

        getBody().setType(BodyDef.BodyType.StaticBody);

        Shape shape = createPolygonShape(new float[]{
                14f, 1f, 18f, 1f, 25f, 7f, 25f, 23f, 18f, 31f, 14f, 31f, 7f, 23f, 7f, 7f
        }, new Vector2(32, 32));

        FixtureProperties fixtureProperties = new FixtureProperties(shape, HITTABLE_BIT, BALL_BIT);
        fixtureProperties.setSensor();
        createFixture(fixtureProperties);
    }

}
