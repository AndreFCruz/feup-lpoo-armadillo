package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.lpoo.game.Armadillo;
import com.lpoo.game.model.GameModel;


/**
 * Class responsible for the HUD during the Game.
 */
class HudMenu extends Stage {

    /**
     * Possible Requests to the Game Screen.
     */
    public enum Request {
        LOAD, START, NONE
    }

    /**
     * A Class used to represent the Menu that appears when the game is paused / over / won.
     */
    private OptionsMenu optionsMenu;

    /**
     * Flag responsible for indicating whether the HUD or the Options Menu should be drawn.
     */
    private boolean options_flag = false;

    /**
     * Represents the current level's score.
     */
    private float score;

    /**
     * Represents the last game's state. Used for performance/efficiency reasons.
     */
    private GameModel.ModelState lastState;

    /**
     * Represents the current Hud Menu's Request to the Game Screen.
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
            ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());

    /**
     * The Font rescaling factor, for the score representation.
     */
    private static final float FONT_SCALE = HUD_VIEWPORT_WIDTH / 400;
    /**
     * The Side Size of the HUD elements.
     */
    private static final float HUD_ELEMENTS_SIZE = HUD_VIEWPORT_WIDTH / 15;
    /**
     * The HUD elements distance to the horizontal screen limits.
     */
    private static final float HORIZONTAL_PAD = HUD_VIEWPORT_WIDTH / 20;
    /**
     * The HUD elements distance to the vertical screen limits.
     */
    private static final float VERTICAL_PAD = HUD_VIEWPORT_HEIGHT / 25;

    /**
     * Current Level Number (Level1, Level2, etc)
     */
    private int currentLevel;

    /**
     * The ViewPort used for the HUD Stage.
     */
    private Viewport viewport;

    /**
     * Skin used in the HUD elements.
     */
    private Skin skin;

    /**
     * Label containing the string referencing the current score.
     */
    private Label scoreText;

    /**
     * The current Game Model, in display on the screen.
     */
    private GameModel model;

    /**
     * The current game session.
     */
    private Armadillo game;

    private InputMultiplexer inputMultiplexer;

    /**
     * HudMenu's Constructor.
     * It takes the current game and the current game model to initialize.
     * It initializes all the HUD elements, in the right positions.
     *
     * @param game  The current game session.
     * @param model The current game Model.
     */
    HudMenu(Armadillo game, GameModel model) {
        super(new FitViewport(HUD_VIEWPORT_WIDTH, HUD_VIEWPORT_HEIGHT), game.getBatch());

        inputMultiplexer = new InputMultiplexer(this);
        Gdx.input.setInputProcessor(inputMultiplexer);

        this.game = game;
        this.model = model;

        viewport = this.getViewport();

        skin = game.getSkinOne();

        initHUD();
    }

    /**
     * Function used to initialize all the elements of the HUD and add the respective Listeners.
     */
    private void initHUD() {
        Table hudTable = new Table();
        hudTable.setFillParent(true);

        Button pauseButton = new Button(new TextureRegionDrawable(
                new TextureRegion(game.getAssetManager().get("pause.png", Texture.class))));

        scoreText = new Label("0:00", skin);
        scoreText.setFontScale(FONT_SCALE, FONT_SCALE);

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

        this.addActor(hudTable);
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

    /**
     * The function responsible for updating the current HudMenu.
     * Depending of the HudMenu update, the HudMenu might have a request.
     *
     * @param state The current Game Model state.
     * @param level The current level being played.
     * @return The HudMenu's Request.
     */
    HudMenu.Request update(GameModel.ModelState state, int level) {
        this.act();

        currentLevel = level;

        if (state != lastState) {
            lastState = state;

            switch (state) {
                case LOST:
                    optionsMenu = new LostMenu(viewport, game, this);
                    break;
                case WON:
                    game.getGameServices().submitScore(level - 1, (int) (score * 1000));
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
            Gdx.input.setInputProcessor(inputMultiplexer);
        } else if (! options_flag) {
            options_flag = true;
        }

        return currentRequest;
    }

    /**
     * The function that draws the current HudMenu in the screen.
     */
    @Override
    public void draw() {
        if (options_flag && optionsMenu != null) {
            optionsMenu.draw();
        } else {
            super.draw();
        }
    }

    /**
     * Function responsible for pausing the current game model.
     */
    void togglePause() {
        currentRequest = Request.NONE;
        model.togglePause();
    }

    /**
     * @return current Level being played by the User.
     */
    int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Function used to load the next Level.
     */
    void loadNextLevel() {
        currentRequest = Request.LOAD;
    }

    /**
     * Function used to start the next Level.
     */
    void startLevel() {
        currentRequest = Request.START;
    }

    /**
     * Function used to set the current HudMenu's request to NONE.
     */
    void resetRequest() {
        currentRequest = Request.NONE;
    }

    /**
     * Getter for the HUD's InputMultiplexer.
     * @return The HUD's InputMultiplexer.
     */
    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }
}
