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

    public enum Request {LOAD, START, NONE}

    /**
     * A Stage used to represent the HUD, containing the score and the pause Button.
     */
    private Stage hud;

    /**
     * A Class used to represent the Menu that appears when the game is paused / over / won.
     */
    private OptionsMenu optionsMenu;

    /**
     * Flag responsible for indicating whether the HUD or the Options Menu should be drawn.
     */
    private boolean options_flag = false;

    /**
     * Represents the current session's score.
     */
    private float score;

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
    private static final float HUD_VIEWPORT_HEIGHT = HUD_VIEWPORT_WIDTH *
            ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());

    //Layout Macros
    /**
     * The Font rescaling factor, for the score representation.
     */
    private static final float FONT_SCALE = HUD_VIEWPORT_WIDTH / 400;
    /**
     * The Side Size of the HUD elements.
     */
    private static final float HUD_ELEMENTS_SIZE = HUD_VIEWPORT_WIDTH /15;
    /**
     * The HUD elements pad, in relation to the vertical limits.
     */
    private static final float HORIZONTAL_PAD = HUD_VIEWPORT_WIDTH / 20;
    /**
     * The HUD elements pad, in relation to the horizontal limits.
     */
    private static final float VERTICAL_PAD = HUD_VIEWPORT_HEIGHT / 25;

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

        skin = game.getSkinOne();

        initHUD();
    }

    /**
     * Function used to initialize all the elements of the HUD and add their Listeners.
     */
    private void initHUD () {
        Table hudTable = new Table();
        hudTable.setFillParent(true);

        Button pauseButton = new Button(new TextureRegionDrawable(
                new TextureRegion(game.getAssetManager().get("pause.png", Texture.class))));

        scoreText = new Label ("0:00", skin);
        scoreText.setFontScale(FONT_SCALE,FONT_SCALE);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                model.togglePause();
            }
        });

        hudTable.top();
        hudTable.add(scoreText).size(HUD_ELEMENTS_SIZE, HUD_ELEMENTS_SIZE).expandX()
                .left().fill().padLeft(HORIZONTAL_PAD).padTop(VERTICAL_PAD);
        hudTable.add(pauseButton).size(HUD_ELEMENTS_SIZE, HUD_ELEMENTS_SIZE).fill()
                .padRight(HORIZONTAL_PAD).padTop(VERTICAL_PAD);

        hud.addActor(hudTable);
    }

    /**
     * Function responsible for updating the current score
     */
    private void updateScore() {
        score = model.getCurrentRunTime();

        scoreText.setText(getScoreString());
    }

    /**
     * Function that returns the current score, in a String format.
     *
     * @return Score's string representation.
     */
    String getScoreString() {
        return (Integer.toString((int) score / 60) + ":" +
                (((int) score % 60) > 9 ? "" : "0") + Integer.toString((int) score % 60));
    }

    HudMenu.Request update (GameModel.ModelState state, int level) {

        if (state != lastState) {
            lastState = state;

            switch (state) {
                case LOST:
                    optionsMenu = new LostMenu(viewport, game, this);
                    break;
                case WON:
                    game.getGameServices().submitScore(level, (int) (score * 1000));
                    optionsMenu = new WonMenu(viewport, game, this);
                    break;
                case PAUSED:
                    optionsMenu = new PauseMenu(viewport, game, this);
                    break;
            }
        }

        if (state == GameModel.ModelState.LIVE) {
            options_flag = false;
            updateScore();
        } else
            options_flag = true;

        return currentRequest;
    }

    void draw () {
        if (options_flag) {
            optionsMenu.draw();
        } else {
            hud.act(Gdx.graphics.getDeltaTime());
            hud.draw();
            Gdx.input.setInputProcessor(hud);
        }
    }

    void togglePause() {
        currentRequest = Request.NONE;
        model.togglePause();
    }

    void loadNextLevel() {
        currentRequest = Request.LOAD;
    }

    void startLevel() {
        currentRequest = Request.START;
    }

    void resetRequest() {
        currentRequest = Request.NONE;
    }
}
