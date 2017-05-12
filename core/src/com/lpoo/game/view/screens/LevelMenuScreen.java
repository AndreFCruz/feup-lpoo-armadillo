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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
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
     * Constant representing the distance between the first line of Level Buttons and the screen Top.
     */
    private static final int TOP_EDGE = 100;
    /**
     * Constant representing the distance between the stage elements and the screen side limits.
     */
    private static final int SIDE_DISTANCE = 40;

    /**
     * Variable representing the Main Menu's background image.
     */
    private Image backgroundImg;
    /**
     * Variable representing the Title's image.
     */
    private Image titleImg;

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

        backgroundImg = new Image(new Texture("background.png"));
        titleImg = new Image(new Texture("spheral.png"));
    }

    @Override
    public void show() {

        Table levels = new Table();
        //levels.setFillParent(true);

        Table fixElements = new Table();
        fixElements.setFillParent(true);

        //Create buttons - levels Table
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
        TextButton lvlThirteen = new TextButton("13", skin);

        //Create standing Elements
        TextButton back = new TextButton("Back", skin);
        ScrollPane scroller = new ScrollPane(levels, skin);
        scroller.getStyle().background = null;  //Setting the scroll background invisible
        /*//INVISIBLE SCROLLER
        scroller.getStyle().vScroll = null;
        scroller.getStyle().vScrollKnob = null;*/

        //Add listeners to buttons
        lvlOne.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        //Add buttons to table
        //levels.setDebug(true); //Testing Purposes
        levels.top();

        //First Line of levels
        levels.padTop(TOP_EDGE);
        levels.add(lvlOne).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        levels.add(lvlTwo).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        levels.add(lvlThree).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        levels.add(lvlFour).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        levels.row();

        //Second Line of levels
        levels.add(lvlFive).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        levels.add(lvlSix).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        levels.add(lvlSeven).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        levels.add(lvlEight).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        levels.row();

        //Third Line of levels - supposed to be only half visible
        levels.add(lvlNine).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        levels.add(lvlTen).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        levels.add(lvlEleven).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        levels.add(lvlTwelve).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);
        levels.row();

        //Forth Line of levels - initially invisible
        levels.add(lvlThirteen).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);

        //Standing Elements
        //fixElements.setDebug(true); //Testing Purposes
        fixElements.add(back).top().padLeft(SIDE_DISTANCE).padTop(TOP_EDGE / 3);
        fixElements.add(scroller).fill().expand().padRight(SIDE_DISTANCE);

        // Add table to stage
        //stage.addActor(backgroundImg);
        //stage.addActor(titleImg);
        stage.addActor(fixElements);

        Gdx.input.setInputProcessor(stage); //TODO: averiguar o pq de ter de ser aqu (se não não dá)
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.begin();
        //batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
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
