package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.game.Spheral;

/**
 * Created by Edgar on 11/05/2017.
 */

public class CustomizeMenuScreen extends ScreenAdapter {

    private final Spheral game;

    private Stage stage;
    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;
    private Skin skin;
    private TextureAtlas atlas;

    /**
     * Index on the array of Skins of the currently active skin.
     */
    private int current_skin = 0;

    //Layout Macros
    /**
     * Constant representing the extra space around the edges of all Images.
     */
    private static final int IMAGE_EDGE = 30;
    /**
     * Constant representing the extra space around the bottom edge of the bottom Button.
     */
    private static final int TOP_EDGE = 100;
    /**
     * Constant representing the distance between the stage elements and the screen limits.
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

    CustomizeMenuScreen(final Spheral game) {
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

        Table skins = new Table();
        skins.setFillParent(true);

        Table fixElements = new Table();
        fixElements.setFillParent(true);


        Label labelOne = new Label ("Current", skin);
        Image imageOne = new Image ( new Texture ("ball.png"));
        //imageOne.setScale(1.4f,1.4f);

        Label labelTwo = new Label ("Current", skin);
        Image imageTwo = new Image ( new Texture ("ball.png"));

        Label labelThree = new Label ("Current", skin);
        Image imageThree = new Image ( new Texture ("ball.png"));

        Label labelFour = new Label ("Current", skin);
        Image imageFour = new Image ( new Texture ("ball.png"));

        final Label labelFive = new Label ("Current", skin);
        Image imageFive = new Image ( new Texture ("ball.png"));

        final Label labelSix = new Label ("Current", skin);
        Image imageSix = new Image ( new Texture ("ball.png"));

        //Adding all the Images/Buttons to the same line
        skins.add(imageOne).pad(IMAGE_EDGE);
        skins.add(imageTwo).pad(IMAGE_EDGE);
        skins.add(imageThree).pad(IMAGE_EDGE);
        skins.add(imageFour).pad(IMAGE_EDGE);
        skins.add(imageFive).pad(IMAGE_EDGE);
        skins.add(imageSix).pad(IMAGE_EDGE);

        skins.row();

        //Adding the label below the skins -> TODO: need to do the same for time stamps on levelMenu
        skins.add(labelOne);
        skins.add(labelTwo);
        skins.add(labelThree);
        skins.add(labelFour);
        skins.add(labelFive);
        skins.add(labelSix);
        labelSix.setVisible(false);

        //Create standing Elements
        TextButton back = new TextButton("Back", skin);
        //ScrollPane scroller = new ScrollPane(skins, skin);
        //scroller.getStyle().background = null;  //Setting the scroll background invisible

        //Add listeners to buttons
        imageOne.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        //Add listeners to buttons
        imageFive.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                labelFive.setVisible(true);
                labelSix.setVisible(false);
            }
        });
        //Add listeners to buttons
        imageSix.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                labelFive.setVisible(false);
                labelSix.setVisible(true);
            }
        });

        //Add buttons to table
        skins.setDebug(true); //Testing Purposes
        skins.center();


        //Standing Elements
        //fixElements.setDebug(true); //Testing Purposes
        //fixElements.add(back).top().padLeft(SIDE_DISTANCE).padTop(TOP_EDGE / 3);
        //fixElements.add(scroller).fill().expand().padRight(SIDE_DISTANCE);


        // Add table to stage
        //stage.addActor(backgroundImg);
        //stage.addActor(titleImg);
        stage.addActor(skins);
        //stage.addActor(fixElements);

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
