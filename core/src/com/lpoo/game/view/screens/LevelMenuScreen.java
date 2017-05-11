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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.game.Spheral;

/**
 * Created by Edgar on 10/05/2017.
 */

public class LevelMenuScreen extends ScreenAdapter {

    private final Spheral game;

    private Stage stage;
    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;
    private Skin skin;
    private TextureAtlas atlas;

    //Layout Macros
    /**
     * Since the Level Buttons are square, this Constant represents both their Width and Height.
     */
    private static final int BUTTON_SIDE = 80;
    /**
     * Constant representing the extra space around the edges of all Buttons.
     */
    private static final int BUTTON_EDGE = 30;
    /**
     * Constant representing the extra space around the bottom edge of the bottom Button.
     */
    private static final int TOP_EDGE = 100;

    /**
     * Variable representing the Main Menu's background image.
     */
    private Texture background;
    /**
     * Variable representing the Title's image.
     */
    private Texture title;

    LevelMenuScreen(final Spheral game) {
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

        background = new Texture("background.png");
        title = new Texture("spheral.png");

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

        Table table = new Table();
        table.setFillParent(true);

        table.center();

        Label title = new Label("Spheral", skin);

        //Create buttons
        TextButton lvlOne = new TextButton("1", skin);
        TextButton lvlTwo = new TextButton("2", skin);
        TextButton lvlThree = new TextButton("3", skin);
        TextButton lvlFour = new TextButton("4", skin);
        TextButton lvlFive = new TextButton("5", skin);
        TextButton lvlSix = new TextButton("6", skin);
        TextButton lvlSeven = new TextButton("7", skin);
        TextButton lvlEight = new TextButton("8", skin);
        TextButton lvlNine = new TextButton("9", skin);
        TextButton lvlTen = new TextButton("10", skin);
        TextButton lvlEleven = new TextButton("11", skin);
        TextButton lvlTwelve = new TextButton("12", skin);

        //Add listeners to buttons
        lvlOne.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        //Add buttons to table
        //table.setDebug(true); //Testing Purposes
        table.top();

        //First Line of levels
        table.padTop(TOP_EDGE);
        table.add(lvlOne).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        table.add(lvlTwo).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        table.add(lvlThree).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        table.add(lvlFour).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        table.row();

        //Second Line of levels
        table.add(lvlFive).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        table.add(lvlSix).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        table.add(lvlSeven).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        table.add(lvlEight).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        table.row();

        //Third Line of levels - supposed to be only half visible
        table.add(lvlNine).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        table.add(lvlTen).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        table.add(lvlEleven).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        table.add(lvlTwelve).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        table.row();



        // Add table to stage
        stage.addActor(table);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        /*batch.begin();
        batch.draw(background, 0, 0);//, camera.viewportWidth, camera.viewportHeight);
        batch.draw(title, 0, 0, (camera.viewportWidth / 2) - (title.getWidth() / 2), (camera.viewportHeight /3) - (title.getHeight() / 2));
        batch.end();*/

        stage.act();
        stage.draw();
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
