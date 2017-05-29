package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private Stage hud;

    /**
     * A Stage used to represent the Pause Menu.
     */
    private Stage pauseMenu;

    /**
     * Represents the User's score in the current Level.
     */
    private float score;

    /**
     * Represents the last displayed score's value. Used for performance efficiency reasons.
     */
    private int lastScore;

    /**
     * Integer that saves the value of the current Level (Level 0, Level 1, ...).
     */
    private int currentLevel;

    /**
     * The width of the HUD viewport in pixels. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float HUD_VIEWPORT_WIDTH = VIEWPORT_WIDTH / PIXEL_TO_METER;

    /**
     * The height of the viewport in meters. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float HUD_VIEWPORT_HEIGHT = VIEWPORT_HEIGHT / PIXEL_TO_METER;

    private Viewport viewport;  //TODO: Verify if this is needed really

    protected Skin skin;
    protected TextureAtlas atlas;

    private Label scoreText;

    public GameScreen(Spheral game) {
        this.game = game;

        model = new GameModel();
        loadNextMap();

        camera = createCamera();

        controller = new GameController(camera, model);

        mapRenderer = new OrthogonalTiledMapRenderer(model.getMap(), game.getBatch());

        viewport = new FitViewport(HUD_VIEWPORT_WIDTH, HUD_VIEWPORT_HEIGHT);

        hud = new Stage(viewport, game.getBatch());

        pauseMenu = new Stage (viewport, game.getBatch());

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
     * Function used to initialize all the elements of the HUD and add their Listeners.
     *
     * @param table
     *          Table contatining the HUD elements.
     */
    private void initHUD ( Table table) {

        Button pauseButton = new Button (new TextureRegionDrawable(new TextureRegion(new Texture("pause.png"))));

        scoreText = new Label ("0:00", skin);
        scoreText.setFontScale(HUD_VIEWPORT_WIDTH / 250,HUD_VIEWPORT_WIDTH / 250); //TODO: Hardcoded, need to change

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                model.togglePause();
            }
        });
            //TODO: Hardcoded values, need to create macros for this. Hardcoded till perfection
        table.top();
        table.add(scoreText).size(HUD_VIEWPORT_WIDTH /15, HUD_VIEWPORT_WIDTH / 15).expandX().left().fill().padLeft(HUD_VIEWPORT_WIDTH / 20).padTop(HUD_VIEWPORT_HEIGHT/ 25);
        table.add(pauseButton).size(HUD_VIEWPORT_WIDTH / 15, HUD_VIEWPORT_WIDTH /15).fill().padRight(HUD_VIEWPORT_WIDTH / 20).padTop(HUD_VIEWPORT_HEIGHT/ 25);
    }

    /**
     * Function used to initialize all the elements of the pause Menu  and their Listeners.
     *
     * @param table
     *          Table that contains the Pause Menu elements.
     */
    private void initPauseMenu(Table table) {
        TextButton resumeButton = new TextButton("Resume", skin);
        //resumeButton.setSize(HUD_VIEWPORT_WIDTH /4, HUD_VIEWPORT_WIDTH / 4);
        TextButton restartButton = new TextButton("Restart", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        //Installing Listeners
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                model.togglePause();
            }
        });
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                model.togglePause();
                model.startLevel();
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                model.togglePause();
                game.setScreen(new LevelMenuScreen(game));
            }
        });

        table.add(resumeButton).size(HUD_VIEWPORT_WIDTH / 3, HUD_VIEWPORT_HEIGHT / 8).padBottom(HUD_VIEWPORT_HEIGHT/ 14).row();
        table.add(restartButton).size(HUD_VIEWPORT_WIDTH / 3, HUD_VIEWPORT_HEIGHT / 8).padBottom(HUD_VIEWPORT_HEIGHT/ 14).row();
        table.add(exitButton).size(HUD_VIEWPORT_WIDTH / 3, HUD_VIEWPORT_HEIGHT / 8);
    }

    @Override
    public void show () {

        //----------HUD-------------
        Table hudTable = new Table();
        hudTable.setFillParent(true);
        hudTable.debugAll();    //TODO: delete

        initHUD(hudTable);
        hud.addActor(hudTable);

        //-------PAUSE MENU---------
        Table pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.debugAll();

        initPauseMenu(pauseTable);
        pauseMenu.addActor(pauseTable);
    }

    /**
     * Function responsible for updating the current score
     *
     * @param delta
     *          Time elapsed since last update.
     */
    private void updateScore(float delta) {
        score += delta;

        int new_score = (int) score / 1;

        //String formation for Label
        if (new_score > lastScore)
            scoreText.setText(Integer.toString(new_score / 60) + ":" + ((new_score % 60) > 9 ? "" : "0") + Integer.toString(new_score % 60));
    }

    @Override
    public void render(float delta) {
        controller.handleInput(delta);

        updateScore(delta);

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

        // TODO pause/death pop-up menu
        switch (model.update(delta)) {
            case LOST:
                //Game Over Animation or sth
                score = 0;
                model.startLevel();
                break;
            case WON:
                score = 0;
                loadNextMap();
                break;
            case PAUSED:
                pauseMenu.act(Gdx.graphics.getDeltaTime());
                pauseMenu.draw();
                Gdx.input.setInputProcessor(pauseMenu);
                break;
            default:
                hud.act(Gdx.graphics.getDeltaTime());
                hud.draw();
                Gdx.input.setInputProcessor(hud);
                break;
        }
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
