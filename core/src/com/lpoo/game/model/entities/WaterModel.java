package com.lpoo.game.model.entities;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.game.model.controllers.BuoyancyController;

import static com.lpoo.game.model.entities.EntityModel.FLUID_BIT;
import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * Created by andre on 20/05/2017.
 */

public class WaterModel {

    private Body body;

    public WaterModel(World world, Rectangle rect) {
        // Body and Fixture variables
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(PIXEL_TO_METER * (rect.getX() + rect.getWidth() / 2), PIXEL_TO_METER * (rect.getY() + rect.getHeight() / 2));

        this.body = world.createBody(bdef);

        shape.setAsBox((rect.getWidth() / 2) * PIXEL_TO_METER, (rect.getHeight() / 2) * PIXEL_TO_METER);
        fdef.shape = shape;
        fdef.filter.categoryBits = FLUID_BIT;
        fdef.isSensor = true;
        fdef.density = 1f;
        fdef.friction = 0.1f;
        fdef.restitution = 0f;
        body.createFixture(fdef);

        body.setUserData(new BuoyancyController(world, body.getFixtureList().first()));

    }

    public void step() {
        ((BuoyancyController) this.body.getUserData()).step();
    }
}
