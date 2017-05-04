package com.lpoo.game.model.tools;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by andre on 27/04/2017.
 */

public class B2DWorldCreator implements WorldCreator {

    @Override
    public void generateWorld(World world, Map map) {

        // Body and Fixture variables
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        Body body;

        // Create ground Bodies/Fixtures
        for (MapObject object : map.getLayers().get(0).getObjects().getByType(PolygonMapObject.class)) {
            Polygon polygon = ((PolygonMapObject) object).getPolygon();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(polygon.getX(), polygon.getY()); // will need adjusting

            body = world.createBody(bdef);

            shape.set(polygon.getVertices()); // check
            fdef.shape = shape;
            body.createFixture(fdef);
        }

    }

    // TODO check this whole class
}
