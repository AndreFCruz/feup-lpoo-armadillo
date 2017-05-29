package com.lpoo.game.model.utils;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lpoo.game.model.entities.BallModel;
import com.lpoo.game.model.entities.EntityModel;
import com.lpoo.game.model.entities.ShapeModel;
import com.lpoo.game.model.entities.WaterModel;

import java.util.HashSet;
import java.util.Set;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A class to load TiledMap layers and correctly create the associated physics world.
 */
public class B2DWorldCreator {

    private abstract class LayerLoader <T extends MapObject> {
        private String name;

        private World world;

        private Class<T> type;

        LayerLoader(String name, World world, Class<T> type) {
            this.name = name;
            this.world = world;
            this.type = type;
        }

        Boolean load() {
            MapLayer layer = map.getLayers().get(name);
            if (layer == null)
                return false;

            for (T object : layer.getObjects().getByType(type)) {
                loadObject(world, object);
            }

            return true;
        }

        protected abstract void loadObject(World world, MapObject object);
    }

    private World world;
    private Map map;
    private Array<WaterModel> fluids = new Array<WaterModel>();
    private Array<ShapeModel> shapeModels = new Array<ShapeModel>();
    private Array<EntityModel> entityModels = new Array<EntityModel>();
    private BallModel ball;
    private Vector2 endPos;

    private Set<LayerLoader> layerLoaders = new HashSet<LayerLoader>();

    public B2DWorldCreator(World world, Map map) {
        this.world = world;
        this.map = map;

        addLayerLoaders();
    }

    private void addLayerLoaders() {
        layerLoaders.add(new LayerLoader<RectangleMapObject>("ground", world, RectangleMapObject.class) {
            @Override
            protected void loadObject(World world, MapObject object) {
                B2DFactory.makeRectGround(world, (RectangleMapObject) object);

            }
        });
        layerLoaders.add(new LayerLoader<PolygonMapObject>("ground", world, PolygonMapObject.class) {
            @Override
            protected void loadObject(World world, MapObject object) {
                B2DFactory.makePolygonGround(world, (PolygonMapObject) object);
            }
        });
        layerLoaders.add(new LayerLoader<RectangleMapObject>("water", world, RectangleMapObject.class) {
            @Override
            protected void loadObject(World world, MapObject object) {
                fluids.add(B2DFactory.makeWater(world, (RectangleMapObject) object));
            }
        });
        layerLoaders.add(new LayerLoader<RectangleMapObject>("objects", world, RectangleMapObject.class) {
            @Override
            protected void loadObject(World world, MapObject object) {
                String property = object.getProperties().get("type", String.class);
                if (property == null) {
                    System.err.println("MapObject in objects' layer has no type property");
                    return;
                }

                switch (property) {
                    case "box":
                        entityModels.add(B2DFactory.makeBox(world, (RectangleMapObject) object));
                        break;
                    case "platform":
                        shapeModels.add(B2DFactory.makePlatform(world, (RectangleMapObject) object));
                        break;
                    default:
                        System.err.println("Invalid MapObject type in objects' layer");
                }
            }
        });
        layerLoaders.add(new LayerLoader<RectangleMapObject>("positions", world, RectangleMapObject.class) {
            @Override
            protected void loadObject(World world, MapObject object) {
                RectangleMapObject rectObj = (RectangleMapObject) object;

                switch (object.getName()) {
                    case "start":
                        ball = B2DFactory.makeBall(world, rectObj);
                        break;
                    case "end":
                        endPos = rectObj.getRectangle().getCenter(new Vector2()).scl(PIXEL_TO_METER);
                        break;
                    default:
                        System.err.println("Invalid object name in layer 'positions'");
                }
            }
        });
    }

    public void generateWorld() {
        for (LayerLoader loader : layerLoaders)
            loader.load();
    }

    public Vector2 getEndPos() {
        return endPos;
    }

    public BallModel getBall() {
        return ball;
    }

    public Array<WaterModel> getFluids() {
        return fluids;
    }

    public Array<ShapeModel> getShapeModels() {
        return shapeModels;
    }

    public Array<EntityModel> getEntityModels() {
        return entityModels;
    }
}
