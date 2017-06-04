package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.game.GameServices;
import com.lpoo.game.Spheral;

/**
 * Abstract Class used to represent all Views that are Menus.
 */
public abstract class MenuScreen extends ScreenAdapter {
    protected final Spheral game;

    protected Stage stage;
    protected Viewport viewport;
    protected Camera camera;
    protected SpriteBatch batch;
    protected Skin skin1;
    protected Skin skin2;

    GameServices gameServices;

    /**
     * The width of the viewport in pixels. The height is
     * automatically calculated using the screen ratio.
     */
    protected static final float VIEWPORT_WIDTH = 750;

    /**
     * The height of the viewport in meters. The height is
     * automatically calculated using the screen ratio.
     */
    protected static final float VIEWPORT_HEIGHT = VIEWPORT_WIDTH * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());

    //LayOut
    /**
     * Image representing the Menu's background image.
     */
    protected Image backgroundImg;
    /**
     * Image representing the Menu's Title image.
     */
    protected Image titleImg;
    /**
     * Default's Button Side Size
     */
    protected static final float DEFAULT_BUTTON_SIZE = VIEWPORT_WIDTH / 15;


    /**
     * Menu Screen's constructor.
     *
     * @param game
     *
     */
    protected MenuScreen(final Spheral game) {
        this.game = game;
        batch = game.getBatch();
        gameServices = game.getGameServices();
        skin1 = game.getSkinOne();
        skin2 = game.getSkinTwo();

        camera = new OrthographicCamera();

        viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        viewport.apply();

        stage = new Stage(viewport, batch);

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        backgroundImg = new Image(game.getAssetManager().get("background.png", Texture.class));
        backgroundImg.setScale(VIEWPORT_WIDTH / backgroundImg.getWidth(), VIEWPORT_HEIGHT / backgroundImg.getHeight());

        titleImg = new Image(game.getAssetManager().get("armadillo_title.png", Texture.class));
        titleImg.setSize(0.8f * titleImg.getWidth(), 0.8f * titleImg.getHeight());
        titleImg.setPosition(VIEWPORT_WIDTH / 2 - titleImg.getWidth() / 2, VIEWPORT_HEIGHT * 0.98f - titleImg.getHeight());
    }

    protected TextButton addBackBtn(boolean style) {
        TextButton back = new TextButton("Back", (style ? skin2 : skin1));

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        return back;
    }

    @Override
    public void show() {
        //Displaying the background and the Image
        stage.addActor(backgroundImg);
        stage.addActor(titleImg);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}