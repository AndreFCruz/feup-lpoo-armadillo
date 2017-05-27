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
        layerLoaders.add(new LayerLoader<RectangleMapObject>("platforms", world, RectangleMapObject.class) {
            @Override
            protected void loadObject(World world, MapObject object) {
                shapeModels.add(B2DFactory.makePlatform(world, (RectangleMapObject) object));
            }
        });
        layerLoaders.add(new LayerLoader<RectangleMapObject>("boxes", world, RectangleMapObject.class) {
            @Override
            protected void loadObject(World world, MapObject object) {
                entityModels.add(B2DFactory.makeBox(world, (RectangleMapObject) object));
            }
        });
    }

    public void generateWorld() {
        for (LayerLoader loader : layerLoaders)
            loader.load();

        // Get Ball start pos
        MapObject startPosObj = map.getLayers().get("start_pos").getObjects().get(0);
        Rectangle startPosRect = ((RectangleMapObject) startPosObj).getRectangle();
        this.ball = new BallModel(world, new Vector2(startPosRect.getX() * PIXEL_TO_METER, startPosRect.getY() * PIXEL_TO_METER));
        // TODO: change Ball creation to factory makeBall

        // Get Ball end pos
        MapObject endPosObj = map.getLayers().get("end_pos").getObjects().get(0);
        Rectangle endPosRect = ((RectangleMapObject) endPosObj).getRectangle();
        this.endPos = new Vector2(
                (endPosRect.getX() + endPosRect.getWidth() / 2) * PIXEL_TO_METER,
                (endPosRect.getY() + endPosRect.getHeight() / 2) * PIXEL_TO_METER);

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
