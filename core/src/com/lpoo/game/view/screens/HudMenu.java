package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

    public enum Request {LOAD, START, NONE};

    /**
     * A Stage used to represent the HUD, containing the score and the pause Button.
     */
    private Stage hud;

    /**
     * A Stage used to represent the Menu that appears when the game is paused / over / won. TODO: CHANGE
     */
    private OptionsMenu optionsMenu;

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
     * Represents the last game's state. Used for performance efficiency reasons.
     */
    private GameModel.ModelState lastState;

    /**
     * TODO
     */
    private HudMenu.Request currentRequest = Request.NONE;

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

    private Skin skin;

    private Label scoreText;

    private GameModel model;

    private Spheral game;

    HudMenu (Spheral game, GameModel model) {

        this.game = game;
        this.model = model;

        viewport = new FitViewport(HUD_VIEWPORT_WIDTH, HUD_VIEWPORT_HEIGHT);

        hud = new Stage(viewport, game.getBatch());

        skin = game.getSkin();

        initHUD();
    }

    /**
     * Function used to initialize all the elements of the HUD and add their Listeners.
     *
     * @param table
     *          Table contatining the HUD elements.
     */
    private void initHUD () {
        Table hudTable = new Table();
        hudTable.setFillParent(true);

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
        hudTable.top();
        hudTable.add(scoreText).size(HUD_VIEWPORT_WIDTH /15, HUD_VIEWPORT_WIDTH / 15).expandX().left().fill().padLeft(HUD_VIEWPORT_WIDTH / 20).padTop(HUD_VIEWPORT_HEIGHT/ 25);
        hudTable.add(pauseButton).size(HUD_VIEWPORT_WIDTH / 15, HUD_VIEWPORT_WIDTH /15).fill().padRight(HUD_VIEWPORT_WIDTH / 20).padTop(HUD_VIEWPORT_HEIGHT/ 25);

        hud.addActor(hudTable);
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
            scoreText.setText(getScoreString());
            lastScore = new_score;
        }
    }

    /**
     * Function that returns the current score, in a String format.
     *
     * @return Score's string representation.
     */
    public String getScoreString() {
        return (Integer.toString((int) score / 60) + ":" + (((int) score % 60) > 9 ? "" : "0") + Integer.toString((int) score % 60));
    }

    public HudMenu.Request update (float delta, GameModel.ModelState state) {

        if (state != lastState) {
            lastState = state;

            switch (state) {
                case LOST:
                    optionsMenu = new LostMenu(viewport, game, this);
                    options_flag = true;
                    break;
                case WON:
                    optionsMenu = new WonMenu(viewport, game, this);
                    options_flag = true;
                    break;
                case PAUSED:
                    optionsMenu = new PauseMenu(viewport, game, this);
                    options_flag = true;
                    break;
                default:
                    options_flag = false;
                    break;
            }
        }

        if (!options_flag)
            updateScore(delta);

        return currentRequest;
    }

    public void draw () {
        if (options_flag) {
            optionsMenu.draw();
        } else {
            hud.act(Gdx.graphics.getDeltaTime());
            hud.draw();
            Gdx.input.setInputProcessor(hud);
        }
    }

    private void resetScore() {
        score = 0;
        lastScore = 0;
        scoreText.setText("0:00");
    }

    public void togglePause() {
        currentRequest = Request.NONE;
        model.togglePause();
    }

    public void loadNextLevel() {
        resetScore();
        currentRequest = Request.LOAD;
    }

    public void startLevel() {
        resetScore();
        currentRequest = Request.START;
    }

    public void resetRequest() {
        currentRequest = Request.NONE;
    }
}
