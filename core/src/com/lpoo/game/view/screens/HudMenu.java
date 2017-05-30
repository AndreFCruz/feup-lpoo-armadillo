package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.game.Spheral;
import com.lpoo.game.model.GameModel;

/**
 * Created by Edgar on 29/05/2017.
 */

/**
 * Class responsible for the HUD during the Game.
 */
public class HudMenu {
    /**
     * A Stage used to represent the HUD, containing the score and the pause Button.
     */
    private Stage hud;

    /**
     * A Stage used to represent the Menu that appears when the game is paused / over / won.
     */
    private Stage optionsMenu;

    /**
     * Represents the User's score in the current Level.
     */
    private float score = 0;

    /**
     * Flag responsible for indicating whether the HUD or the Options Menu should be drawn.
     */
    private boolean options_flag = false;

    /**
     * Represents the last displayed score's value. Used for performance efficiency reasons.
     */
    private int lastScore;

    /**
     * The width of the HUD viewport in pixels. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float HUD_VIEWPORT_WIDTH = 1000;

    /**
     * The height of the viewport in pixels. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float HUD_VIEWPORT_HEIGHT = HUD_VIEWPORT_WIDTH * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());;

    private Viewport viewport;

    protected Skin skin;
    protected TextureAtlas atlas;

    private Label scoreText;

    private GameModel model;

    private Spheral game;

    HudMenu (Spheral game, GameModel model) {

        this.game = game;
        this.model = model;

        viewport = new FitViewport(HUD_VIEWPORT_WIDTH, HUD_VIEWPORT_HEIGHT);

        hud = new Stage(viewport, game.getBatch());

        optionsMenu = new Stage (viewport, game.getBatch());

        atlas = new TextureAtlas("uiskin.atlas");
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);

        initTables();
    }

    /**
     * Function responsible for initializing all the Elements from both the HUD and the Options Menu.
     */
    public void initTables() {
        //----------HUD-------------
        Table hudTable = new Table();
        hudTable.setFillParent(true);
        hudTable.debugAll();    //TODO: Delete

        initHUD(hudTable);
        hud.addActor(hudTable);

        //-------OPTIONS MENU-------
        Table optionsTable = new Table();
        optionsTable.setFillParent(true);
        optionsTable.debugAll();  //TODO: Delete

        initOptionsMenu(optionsTable);
        optionsMenu.addActor(optionsTable);
    }

    /**
     * Function used to initialize all the elements of the HUD and add their Listeners.
     *
     * @param table
     *          Table contatining the HUD elements.
     */
    private void initHUD (Table table) {

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
     * Function used to initialize all the elements of the Options' Menu  and their Listeners.
     *
     * @param table
     *          Table that contains the Options Menu elements.
     */
    private void initOptionsMenu(Table table) {
        TextButton resumeButton = new TextButton("Resume", skin);
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
                resetScore();
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

    /**
     * Function responsible for updating the current score
     *
     * @param delta
     *          Time elapsed since last update.
     */
    private void updateScore(float delta) {
        score += delta;

        int new_score = (int) score;

        //String formation for Label
        if (new_score > lastScore) {
            scoreText.setText(Integer.toString(new_score / 60) + ":" + ((new_score % 60) > 9 ? "" : "0") + Integer.toString(new_score % 60));
            lastScore = new_score;
        }
    }

    public void update (float delta, GameModel.ModelState state) {
        switch (state) {
            case LOST:
                //set Label to "GAME OVER!" ->Show Score ALWAYS -> Restart and Exit Only
                //Change second table ->
                resetScore();
                options_flag = true;
                break;
            case WON:
                //set Label to "YOU WON!" -> Next Level, Restart and Exit
                //Change second table
                options_flag = true;
                resetScore();
                break;
            case PAUSED:
                //Show Score, e botões normais -> Esta cena em baixo terá de passar para LOST E WON TB
                options_flag = true;
                break;
            default:
                options_flag = false;
                break;
        }

        if (!options_flag)
            updateScore(delta);
    }

    public void draw () {

        if (options_flag) {
            optionsMenu.act(Gdx.graphics.getDeltaTime());
            optionsMenu.draw();
            Gdx.input.setInputProcessor(optionsMenu);
        } else {
            hud.act(Gdx.graphics.getDeltaTime());
            hud.draw();
            Gdx.input.setInputProcessor(hud);
        }
    }

    public void resetScore() {
        score = 0;
        lastScore = 0;
        scoreText.setText("0:00");
    }
}
