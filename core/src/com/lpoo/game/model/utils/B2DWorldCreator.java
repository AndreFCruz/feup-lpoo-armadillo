package com.lpoo.game.model.utils;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
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

    /**
     * Abstract class responsible for loading the Map Layers.
     *
     * @param <T> a MapObject
     */
    private abstract class LayerLoader<T extends MapObject> {
        /**
         * The layer's name.
         */
        private String name;

        /**
         * The Layer's associated world.
         */
        private World world;

        /**
         * The layer's type.
         */
        private Class<T> type;

        /**
         * LayerLoader's constructor.
         *
         * @param name  The Layer's name.
         * @param world The Layer's associated World.
         * @param type  The Layer's type.
         */
        LayerLoader(String name, World world, Class<T> type) {
            this.name = name;
            this.world = world;
            this.type = type;
        }

        /**
         * Loads the layers of a map.
         *
         * @return true if the map layers were correctly loaded, false otherwise.
         */
        Boolean load() {
            MapLayer layer = map.getLayers().get(name);
            if (layer == null)
                return false;

            for (T object : layer.getObjects().getByType(type)) {
                loadObject(world, object);
            }

            return true;
        }

        /**
         * Abstract function that loads an Object.
         *
         * @param world  The world where the object will be.
         * @param object The object to be loaded.
         */
        protected abstract void loadObject(World world, MapObject object);
    }

    /**
     * The associated World.
     */
    private World world;
    /**
     * The map were objects are loaded from.
     */
    private Map map;
    /**
     * Array containing all the loaded fluids.
     */
    private Array<WaterModel> fluids = new Array<>();
    /**
     * Array containing all the loaded shape models.
     */
    private Array<ShapeModel> shapeModels = new Array<>();
    /**
     * Array containing all the loaded entity models.
     */
    private Array<EntityModel> entityModels = new Array<>();
    /**
     * Loaded ball.
     */
    private BallModel ball;
    /**
     * Vector containing the loaded ending position.
     */
    private Vector2 endPos;

    /**
     * Hash Set containing the layer loaders.
     */
    private Set<LayerLoader> layerLoaders = new HashSet<>();

    /**
     * B2DWorldCreator's constructor.
     * In loads the given map to the given world.
     *
     * @param world World to were the map elements will be loaded.
     * @param map   Map to load the world elements from.
     */
    public B2DWorldCreator(World world, Map map) {
        this.world = world;
        this.map = map;

        addLayerLoaders();
    }

    /**
     * Adds the layer loaders to the Layer loaders' HashSet.
     */
    private void addLayerLoaders() {
        addRectGroundLayerLoader();
        addPolyGroundLayerLoader();
        addWaterLayerLoader();
        addObjectsLayerLoader();
        addPositionsLayerLoader();
    }

    /**
     * Initializes layer loader responsible for loading the rectangular ground present in the 'ground' layer of the map.
     */
    private void addRectGroundLayerLoader() {
        layerLoaders.add(new LayerLoader<RectangleMapObject>("ground", world, RectangleMapObject.class) {
            @Override
            protected void loadObject(World world, MapObject object) {
                B2DFactory.makeRectGround(world, (RectangleMapObject) object);

            }
        });
    }

    /**
     * Initializes layer loader responsible for loading the polygonal ground present in the 'ground' layer of the map.
     */
    private void addPolyGroundLayerLoader() {
        layerLoaders.add(new LayerLoader<PolygonMapObject>("ground", world, PolygonMapObject.class) {
            @Override
            protected void loadObject(World world, MapObject object) {
                B2DFactory.makePolygonGround(world, (PolygonMapObject) object);
            }
        });
    }

    /**
     * Initializes layer loader responsible for loading the water present in the 'water' layer of the map.
     */
    private void addWaterLayerLoader() {
        layerLoaders.add(new LayerLoader<RectangleMapObject>("water", world, RectangleMapObject.class) {
            @Override
            protected void loadObject(World world, MapObject object) {
                fluids.add(B2DFactory.makeWater(world, (RectangleMapObject) object));
            }
        });
    }

    /**
     * Initializes layer loader responsible for loading all the objects present in the 'objects' layer of the map.
     */
    private void addObjectsLayerLoader() {
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
                    case "powerup":
                        entityModels.add(B2DFactory.makePowerUp(world, (RectangleMapObject) object));
                        break;
                    default:
                        System.err.println("Invalid MapObject type in objects' layer");
                }
            }
        });
    }

    /**
     * Initializes layer loader responsible for loading the starting and ending position present int 'positions' layer of the map.
     */
    private void addPositionsLayerLoader() {
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

    /**
     * Creates the world by loading all the map layers.
     */
    public void generateWorld() {
        for (LayerLoader loader : layerLoaders)
            loader.load();
    }

    /**
     * @return The map's ending position.
     */
    public Vector2 getEndPos() {
        return endPos;
    }

    /**
     * @return The map's ball.
     */
    public BallModel getBall() {
        return ball;
    }

    /**
     * @return Array containing the map's fluids.
     */
    public Array<WaterModel> getFluids() {
        return fluids;
    }

    /**
     * @return Array containing all the map's generated shapes.
     */
    public Array<ShapeModel> getShapeModels() {
        return shapeModels;
    }

    /**
     * @return Array containing the map's generated entities.
     */
    public Array<EntityModel> getEntityModels() {
        return entityModels;
    }
}
