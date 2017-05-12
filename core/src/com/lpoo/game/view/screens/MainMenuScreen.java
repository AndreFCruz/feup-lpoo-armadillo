package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.game.Spheral;

/**
 * Created by andre on 04/05/2017.
 */

public class MainMenuScreen extends ScreenAdapter {
    private final Spheral game;

    private Stage stage;
    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;
    private Skin skin;
    private TextureAtlas atlas;

    /**
     * Image representing the Main Menu's background image.
     */
    private Image backgroundImg;
    /**
     * Image representing the Title's image.
     */
    private Image titleImg;

    //Layout Macros
    /**
     * Constant representing all the Buttons' Width.
     */
    private static final int BUTTON_WIDTH = 400;
    /**
     * Constant representing the extra space around the edges of all Buttons.
     */
    private static final int BUTTON_EDGE = 15;
    /**
     * Constant representing the extra space around the bottom edge of the bottom Button.
     */
    private static final int BOTTOM_EDGE = 50;

    public MainMenuScreen(final Spheral game) {
        this.game = game;
        this.batch = game.getBatch();

        atlas = new TextureAtlas("uiskin.atlas");
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);

        camera = new OrthographicCamera();
        viewport = new ScreenViewport();
        viewport.apply();
        stage = new Stage(viewport, batch);

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        backgroundImg = new Image(new Texture("background.png"));
        titleImg = new Image(new Texture("spheral.png"));
    }

    @Override
    public void show() {

        Table table = new Table();
        table.setFillParent(true);

        //Create buttons
        TextButton playButton = new TextButton("Play", skin);
        //playButton.getLabel().setFontScale(2, 2);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelMenuScreen(game));
            }
        });
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CustomizeMenuScreen(game));
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Add buttons to table
        //table.setDebug(true); //Testing Purposes
        table.bottom();
        table.add(playButton).width(BUTTON_WIDTH).pad(BUTTON_EDGE);
        table.row();
        table.add(optionsButton).width(BUTTON_WIDTH).pad(BUTTON_EDGE);
        table.row();
        table.add(exitButton).width(BUTTON_WIDTH).padBottom(BOTTOM_EDGE).padTop(BUTTON_EDGE);

        // Add table to stage
        //stage.addActor(backgroundImg);
        //stage.addActor(titleImg);
        stage.addActor(table);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.begin();
        //batch.end();

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
