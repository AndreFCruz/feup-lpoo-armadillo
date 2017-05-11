package com.lpoo.game.model.tools;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by andre on 27/04/2017.
 */

public class B2DWorldCreator {

    public static void generateWorld(World world, Map map) {

        // Body and Fixture variables
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        Body body;

        // Create ground Bodies/Fixtures
        for (MapObject object : map.getLayers().get("ground").getObjects().getByType(PolygonMapObject.class)) {
            Polygon polygon = ((PolygonMapObject) object).getPolygon();
            Rectangle rect = polygon.getBoundingRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(polygon.getX() + rect.getWidth() / 2, polygon.getY() + rect.getHeight() / 2); // will need adjusting

            body = world.createBody(bdef);

            shape.set(polygon.getVertices()); // check
            fdef.shape = shape;
            body.createFixture(fdef);
        }

    }

}
