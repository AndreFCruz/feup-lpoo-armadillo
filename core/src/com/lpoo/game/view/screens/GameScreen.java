package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.game.Spheral;
import com.lpoo.game.controller.GameController;
import com.lpoo.game.controller.InputHandler;
import com.lpoo.game.model.GameModel;
import com.lpoo.game.model.GameModel.ModelState;
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
     * A Stage used to represent the HUD, containing the score and the pause Button.
     */
    private Stage stage;

    /**
     * Represents the User's score in the current Level.
     */
    private int score;

    private int currentLevel;

    private Viewport viewport;  //TODO: Verify if this is really necessary
    protected Skin skin;
    protected TextureAtlas atlas;

    public GameScreen(Spheral game) {
        this.game = game;

        model = GameModel.getInstance();
        loadNextMap();

        camera = createCamera();

        controller = new GameController(camera);

        mapRenderer = new OrthogonalTiledMapRenderer(model.getMap(), game.getBatch());

        viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        stage = new Stage(viewport, game.getBatch());

        atlas = new TextureAtlas("uiskin.atlas");
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);

        score = 0;
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

    /**
     * Function used to initialize all thhe elements of the HUD and add their Listeners.
     *
     * @param table
     */
    private void initHUD ( Table table) {

        Button pauseButton = new Button (skin);   //TODO: Change to button with image

        Label points = new Label (Integer.toString(score), skin);
        points.setSize(10,10);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO: Do sth;
            }
        });

        //table.right();//.top();
        //table.add(points);
        table.add(pauseButton).size(VIEWPORT_WIDTH / 10, VIEWPORT_HEIGHT / 15);//.left().top();
    }

    @Override
    public void show () {

        Table hud = new Table();
        hud.debugAll();

        initHUD(hud);
        stage.addActor(hud);

    }

    @Override
    public void render(float delta) {
        controller.handleInput(delta);

        // TODO pause/death pop-up menu
        switch (model.update(delta)) {
            case LOST:
                model.startLevel();
                break;
            case WON:
                loadNextMap();
                break;
            default:
                break;
        }

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        updateCamera();
        drawBackground();

        mapRenderer.setView(camera);
        mapRenderer.render();

        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        drawEntities();
        game.getBatch().end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawShapes();
        shapeRenderer.end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(model.getWorld(), debugCamera);
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        Gdx.input.setInputProcessor(stage);

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

    }

    // TODO add looseness to camera's movement
    private void updateCamera() {
        // Follow player
        camera.position.set(GameModel.getInstance().getBall().getX() / PIXEL_TO_METER, GameModel.getInstance().getBall().getY() / PIXEL_TO_METER, 0);
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
