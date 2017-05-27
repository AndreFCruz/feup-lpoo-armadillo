package com.lpoo.game.model.entities;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.lpoo.game.model.entities.EntityModel.GROUND_BIT;
import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * Created by andre on 20/05/2017.
 */

public class PlatformModel extends ShapeModel {

    private static final float DEFAULT_DENSITY = 0.6f;

    public PlatformModel(World world, RectangleMapObject object) {
        super(ModelType.PLATFORM, object.getRectangle());

        Rectangle rect = object.getRectangle();

        // Body and Fixture variables
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(PIXEL_TO_METER * (rect.getX() + rect.getWidth() / 2), PIXEL_TO_METER * (rect.getY() + rect.getHeight() / 2));

        this.body = world.createBody(bdef);

        shape.setAsBox((rect.getWidth() / 2) * PIXEL_TO_METER, (rect.getHeight() / 2) * PIXEL_TO_METER);
        fdef.shape = shape;
        fdef.filter.categoryBits = GROUND_BIT;
        fdef.isSensor = false;
        fdef.friction = 0.1f;
        fdef.restitution = 0f;

        // Fetch density from properties
        Float property = object.getProperties().get("density", float.class);
        if (property != null)
            fdef.density = property;
        else
            fdef.density = DEFAULT_DENSITY;

        body.createFixture(fdef);
    }
}
