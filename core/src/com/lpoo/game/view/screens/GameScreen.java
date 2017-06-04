package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.lpoo.game.Spheral;
import com.lpoo.game.controller.GameController;
import com.lpoo.game.controller.InputHandler;
import com.lpoo.game.model.GameModel;
import com.lpoo.game.model.entities.EntityModel;
import com.lpoo.game.model.entities.ShapeModel;
import com.lpoo.game.view.entities.EntityView;
import com.lpoo.game.view.entities.ShapeView;
import com.lpoo.game.view.entities.ViewFactory;

/**
 * A view representing the game screen. Draws all the other views and
 * controls the camera.
 */
public class GameScreen extends ScreenAdapter {
    /**
     * Used to debug the position of the physics fixtures
     */
    private static final boolean DEBUG_PHYSICS = true;

    /**
     * Integer that saves the value of the current Level (Level 0, Level 1, ...).
     */
    private int currentLevel;

    /**
     * How much meters does a pixel represent.
     */
    public final static float PIXEL_TO_METER = 0.04f;

    /**
     * The width of the viewport in meters. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float VIEWPORT_WIDTH = 40;

    /**
     * The height of the viewport in meters. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float VIEWPORT_HEIGHT = VIEWPORT_WIDTH * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());

    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    /**
     * A renderer used to debug the physical fixtures.
     */
    private Box2DDebugRenderer debugRenderer;

    /**
     * The transformation matrix used to transform meters into
     * pixels in order to show fixtures in their correct places.
     */
    private Matrix4 debugCamera;

    private OrthogonalTiledMapRenderer mapRenderer;

    private InputHandler controller;

    private GameModel model;

    private Spheral game;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    /**
     * The Game Screen Associated HUD.
     */
    private HudMenu hud;

    /**
     * The Game's background Image.
     */
    private Texture gameBackground;

    public GameScreen (Spheral game, int i) {
        this.game = game;

        model = new GameModel();
        currentLevel = i;
        loadNextMap();

        camera = createCamera();

        controller = new GameController(camera, model);

        mapRenderer = new OrthogonalTiledMapRenderer(model.getMap(), game.getBatch());

        hud = new HudMenu(game, model);
        gameBackground = game.getAssetManager().get("gamebackground.png", Texture.class);
    }

    private Boolean loadNextMap() {
        if (currentLevel == game.getNumMaps())
            return false;

        TiledMap map = game.getAssetManager().get(game.getMap(currentLevel++), TiledMap.class);
        model.loadMap(map);

        if (mapRenderer != null)
            mapRenderer.setMap(map);

        return true;
    }

    @Override
    public void render(float delta) {
        if (model.getState() == GameModel.ModelState.LIVE)
            controller.handleInput(delta);

        updateCamera();
        drawBackground();

        renderMap();

        drawEntityViews();
        drawShapeViews();
        drawDebugLines();

        GameModel.ModelState state = model.update(delta);
        updateHUD(state);
    }

    private void renderMap() {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    private void drawDebugLines() {
        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(model.getWorld(), debugCamera);
        }
    }

    private void updateHUD(GameModel.ModelState state) {
        switch(hud.update(state)) {
            case LOAD:
                loadNextMap();
                resetRequest();
                break;
            case START:
                model.startLevel();
                resetRequest();
                break;
        }
        hud.draw();
    }

    private void drawShapeViews() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawShapes();
        shapeRenderer.end();
    }

    private void drawEntityViews() {
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        drawEntities();
        game.getBatch().end();
    }

    private void resetRequest() {
        hud.resetRequest();
    }

    private void drawEntities() {
        Array<EntityModel> models = model.getEntityModels();
        for (EntityModel model : models) {
            EntityView view = ViewFactory.makeView(game, model);
            view.update(model);
            view.draw(game.getBatch());
        }
    }

    private void drawShapes() {
        Array<ShapeModel> models = model.getShapeModels();
        for (ShapeModel model : models) {
            ShapeView view = ViewFactory.makeView(model);
            view.update(model);
            view.draw(shapeRenderer);
        }
    }

    private void drawBackground() {
        game.getBatch().begin();
        game.getBatch().draw(gameBackground, 0, 0);
        game.getBatch().end();
    }

    // TODO add looseness to camera's movement
    private void updateCamera() {
        // Follow player
        camera.position.set(model.getBallModel().getX() / PIXEL_TO_METER, model.getBallModel().getY() / PIXEL_TO_METER, 0);
        camera.update();
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_HEIGHT / PIXEL_TO_METER );

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }

}
