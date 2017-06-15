package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.lpoo.game.Armadillo;
import com.lpoo.game.controller.GameController;
import com.lpoo.game.controller.GameInputHandler;
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
    private static final boolean DEBUG_PHYSICS = false;
    /**
     * Used for Camera looseness.
     */
    private static final float CAMERA_TOLERANCE = 50;

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
    private static final float VIEWPORT_HEIGHT = VIEWPORT_WIDTH * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());

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

    /**
     * Map's Renderer of the current level's map.
     */
    private OrthogonalTiledMapRenderer mapRenderer;

    /**
     * And Handler used to listen to the User's input.
     */
    private InputHandler controller;

    /**
     * The game Model of the current Game being played by the User.
     */
    private GameModel model;

    /**
     * The current game session.
     */
    private Armadillo game;

    /**
     * Shape's renderer of the current level's shapes.
     */
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    /**
     * The Game Screen Associated HUD.
     */
    private HudMenu hud;

    /**
     * The Game's background Image.
     */
    private Texture gameBackground;

    /**
     * Game Screen's constructor.
     * The Game Screen's is responsible for drawing the current game.
     *
     * @param game         The current game session.
     * @param currentLevel The game's level that the game screen will draw.
     */
    public GameScreen(Armadillo game, int currentLevel) {
        this.game = game;

        model = new GameModel();
        this.currentLevel = currentLevel;
        loadNextMap();

        camera = createCamera();
        controller = new GameController(model);
        mapRenderer = new OrthogonalTiledMapRenderer(model.getMap(), game.getBatch());

        hud = new HudMenu(game, model);
        hud.getInputMultiplexer().addProcessor(new GameInputHandler(model));
        gameBackground = game.getAssetManager().get("background.png", Texture.class);
    }

    /**
     * Function responsible for loading the next available Map.
     *
     * @return True if no errors occurred and a map was loaded, false otherwise.
     */
    private Boolean loadNextMap() {
        if (currentLevel == game.getNumMaps())
            return false;

        TiledMap map = game.getAssetManager().get(game.getMap(currentLevel++), TiledMap.class);
        model.loadMap(map);

        if (mapRenderer != null)
            mapRenderer.setMap(map);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(float delta) {
//        if (model.getState() == GameModel.ModelState.LIVE)
//            controller.handleInput(delta);

        updateCamera();
        drawBackground();

        renderMap();

        drawEntityViews();
        drawShapeViews();
        drawDebugLines();

        GameModel.ModelState state = model.update(delta);
        updateHUD(state);
    }

    /**
     * Function responsible for rendering the current level's map.
     */
    private void renderMap() {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    /**
     * Function used to draw Debug Lines in the screen, making it easier for the developer to see if the code is working correctly.
     */
    private void drawDebugLines() {
        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(model.getWorld(), debugCamera);
        }
    }

    /**
     * Function responsible for updating the Hud associated to the game Screen
     *
     * @param state current Game Model state, used in decision making in HUD update.
     */
    private void updateHUD(GameModel.ModelState state) {
        switch (hud.update(state, currentLevel)) {
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

    /**
     * Function responsible for drawing the the views corresponding to the shapes.
     */
    private void drawShapeViews() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawShapes();
        shapeRenderer.end();
    }

    /**
     * Function responsible for drawing the the views corresponding to the entities.
     */
    private void drawEntityViews() {
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        drawEntities();
        game.getBatch().end();
    }

    /**
     * Function responsible for setting the hud.request to NONE.
     */
    private void resetRequest() {
        hud.resetRequest();
    }

    /**
     * Function responsible for drawing the the views corresponding to the entities.
     * This function is called inside function drawEntityViews().
     */
    private void drawEntities() {
        Array<EntityModel> models = model.getEntityModels();
        for (EntityModel model : models) {
            EntityView view = ViewFactory.makeView(game, model);
            view.update(model);
            view.draw(game.getBatch());
        }
    }

    /**
     * Function responsible for drawing the the views corresponding to the shapes.
     * This function is called inside function drawEntityViews().
     */
    private void drawShapes() {
        Array<ShapeModel> models = model.getShapeModels();
        for (ShapeModel model : models) {
            ShapeView view = ViewFactory.makeView(model);
            view.update(model);
            view.draw(shapeRenderer);
        }
    }

    /**
     * Function responsible for drawing the Game's background.
     */
    private void drawBackground() {
        game.getBatch().begin();
        game.getBatch().draw(gameBackground, 0, 0);
        game.getBatch().end();
    }

    /**
     * Updates camera's position according to the player's position (follows the player).
     */
    private void updateCamera() {
        // Follow player
        Vector2 player_pos = new Vector2(model.getBallModel().getX() / PIXEL_TO_METER,
                model.getBallModel().getY() / PIXEL_TO_METER);
        Vector2 delta = new Vector2(camera.position.x - player_pos.x, camera.position.y - player_pos.y);
        Vector3 new_pos = camera.position.cpy();

        if (Math.abs(delta.x) > CAMERA_TOLERANCE)
            new_pos.x -= delta.x - CAMERA_TOLERANCE * (delta.x < 0 ? -1 : 1);

        if (Math.abs(delta.y) > CAMERA_TOLERANCE)
            new_pos.y -= delta.y - CAMERA_TOLERANCE * (delta.y < 0 ? -1 : 1);

        camera.position.set(new_pos);
        camera.update();
    }

    /**
     * Creates a new Orthographic Camera for the Game.
     *
     * @return the created camera.
     */
    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER,
                VIEWPORT_HEIGHT / PIXEL_TO_METER);

        camera.position.set(model.getBallModel().getX() / PIXEL_TO_METER,
                model.getBallModel().getY() / PIXEL_TO_METER, 0);
        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }

}
