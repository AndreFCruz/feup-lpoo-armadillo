package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.lpoo.game.Spheral;
import com.lpoo.game.model.GameModel;
import com.lpoo.game.model.entities.EntityModel;

import java.util.List;

/**
 * Created by andre on 27/04/2017.
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
    private static final float VIEWPORT_WIDTH = 35;

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

    private GameModel model;

    private Spheral game;

    public GameScreen(Spheral game) {
        this.game = game;

        loadAssets();

        model = GameModel.getInstance();

        camera = createCamera();

        mapRenderer = new OrthogonalTiledMapRenderer(model.getMap());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        model.update(delta);

        updateCamera();
        drawBackground();
        drawEntities();

        //check
        mapRenderer.setView(camera);
        mapRenderer.render();

    }

    private void drawEntities() {
        List<EntityModel> models = model.getModels();
    }

    private void drawBackground() {

    }

    private void updateCamera() {

        // Follow player
        // TODO
        // camera.pos.set();

        camera.update();
    }

    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {

        // Load Assets
        game.getAssetManager().load( "ball.png" , Texture.class);

        game.getAssetManager().finishLoading();
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

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
