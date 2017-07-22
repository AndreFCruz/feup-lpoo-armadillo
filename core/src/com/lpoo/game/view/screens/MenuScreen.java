package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
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
import com.lpoo.game.Armadillo;

/**
 * Abstract Class used to represent all Views that are Menus.
 */
public abstract class MenuScreen extends ScreenAdapter {

    /**
     * The current game session.
     */
    protected final Armadillo game;

    /**
     * The Stage where the menu elements will be organized.
     */
    protected Stage stage;

    /**
     * The viewport associated to the Menu's stage.
     */
    private Viewport viewport;

    /**
     * The SpriteBatch used in the Menu's screen.
     */
    private SpriteBatch batch;

    /**
     * One of the skins associated to the Menu.
     */
    protected Skin skin1;
    /**
     * One of the skins associated to the Menu. This skin is majorly used for square-shaped buttons.
     */
    protected Skin skin2;

    /**
     * Game Services used for Networking.
     */
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
    private static final float VIEWPORT_HEIGHT = VIEWPORT_WIDTH * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());

    /**
     * Image representing the Menu's background image.
     */
    private Image backgroundImg;

    /**
     * Image representing the Menu's Title image.
     */
    private Image titleImg;

    /**
     * Default's Button Side Size
     */
    protected static final float DEFAULT_BUTTON_SIZE = VIEWPORT_WIDTH / 15;

    /**
     * Menu Screen's constructor.
     * It initializes all the Menu elements.
     *
     * @param game The current game session.
     */
    protected MenuScreen(final Armadillo game) {
        this.game = game;
        batch = game.getBatch();
        gameServices = game.getGameServices();
        skin1 = game.getPrimarySkin();
        skin2 = game.getSecondarySkin();

        viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        viewport.apply();

        stage = new Stage(viewport, batch);

        backgroundImg = new Image(game.getAssetManager().get("background.png", Texture.class));
        backgroundImg.setScale(VIEWPORT_WIDTH / backgroundImg.getWidth(), VIEWPORT_HEIGHT / backgroundImg.getHeight());

        titleImg = new Image(game.getAssetManager().get("armadillo_title.png", Texture.class));
        titleImg.setSize(0.8f * titleImg.getWidth(), 0.8f * titleImg.getHeight());
        titleImg.setPosition(VIEWPORT_WIDTH / 2 - titleImg.getWidth() / 2, VIEWPORT_HEIGHT * 0.98f - titleImg.getHeight());
    }

    /**
     * Creates a back Button, that will return the User to the previous Screen
     *
     * @param style Style of the button.
     * @return Newly created button.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        //Displaying the background and the Image
        stage.addActor(backgroundImg);
        stage.addActor(titleImg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}