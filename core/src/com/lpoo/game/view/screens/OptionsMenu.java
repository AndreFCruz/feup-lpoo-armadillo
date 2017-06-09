package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.game.Armadillo;

/**
 * Abstract class used to represent all the Pop Up Menu's classes, containing several option actions.
 */
abstract class OptionsMenu {

    /**
     * The current game session.
     */
    protected Armadillo game;

    /**
     * The Hud associated to the current game being played by the User.
     */
    private HudMenu hud;

    /**
     * A Stage used to represent the Menu that appears when the game is paused / over / won.
     */
    protected Stage menu;

    /**
     * The table containing the elements, that will be added to the stage.
     */
    private Table table;

    /**
     * Message displayed in the Screen, when the pop up Menu appears.
     */
    protected Label message;

    /**
     * Skin used in the table elements.
     */
    private Skin skin;

    /**
     * The width of the HUD viewport in pixels. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float HUD_VIEWPORT_WIDTH = 1000;

    /**
     * The height of the viewport in pixels. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float HUD_VIEWPORT_HEIGHT = HUD_VIEWPORT_WIDTH * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());

    /**
     * The Scale that should be applied to the Message Label's Font.
     */
    private static final float MESSAGE_FONT_SCALE = HUD_VIEWPORT_WIDTH / 400;
    /**
     * The Scale that should be applied to the Score Label's Font.
     */
    private static final float SCORE_FONT_SCALE = HUD_VIEWPORT_WIDTH / 600;
    /**
     * Distance between the Labels and the other stage elements
     */
    private static final float LABEL_DISTANCE = HUD_VIEWPORT_HEIGHT / 40;
    /**
     * Width of the Options Menu's Buttons.
     */
    private static final float BUTTON_WIDTH = HUD_VIEWPORT_WIDTH / 3;
    /**
     * Height of the Options Menu's Buttons.
     */
    private static final float BUTTON_HEIGHT = HUD_VIEWPORT_HEIGHT / 8;
    /**
     * Distance between Buttons and other stage elements.
     */
    private static final float BUTTON_DISTANCE = HUD_VIEWPORT_HEIGHT / 18;

    /**
     * Options Menu Constructor.
     * It initializes all the needed elements.
     *
     * @param viewport The viewport that will be associated to the stage.
     * @param game     The current game session.
     * @param hud      The current Hud, associated to the current game being played.
     */
    protected OptionsMenu(Viewport viewport, Armadillo game, HudMenu hud) {
        this.game = game;
        this.hud = hud;

        menu = new Stage(viewport, game.getBatch());
        table = new Table();
        skin = game.getSkinOne();

        message = new Label("", this.skin);

        confStage();
    }

    /**
     * Function used to draw the Pop Up Menu in the screen.
     */
    public void draw() {
        menu.act(Gdx.graphics.getDeltaTime());
        menu.draw();
        Gdx.input.setInputProcessor(menu);
    }

    /**
     * Function that adds an Exit Button to the Stage.
     */
    protected void addExitBtn() {
        TextButton exitBtn = new TextButton("Exit", this.skin);

        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelMenuScreen(game));
            }
        });

        table.add(exitBtn).size(BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    /**
     * Function that adds a Restart Button to the Stage.
     */
    protected void addRestartBtn() {
        TextButton restartBtn = new TextButton("Restart", skin);

        restartBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startLevel();
            }
        });

        table.add(restartBtn).size(BUTTON_WIDTH, BUTTON_HEIGHT).padBottom(BUTTON_DISTANCE).row();
    }

    /**
     * Function that adds a Button responsible for showing the Leaderboard of the current Level.
     */
    protected void addLeaderboardBtn() {
        TextButton leaderboardBtn = new TextButton("Leaderboard", skin);

        leaderboardBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getGameServices().showScores(hud.getCurrentLevel());
            }
        });

        table.add(leaderboardBtn).size(BUTTON_WIDTH, BUTTON_HEIGHT).padBottom(BUTTON_DISTANCE).row();
    }

    /**
     * Function that adds a Resume Button to the Stage.
     */
    protected void addResumeBtn() {
        TextButton resumeBtn = new TextButton("Resume", skin);

        resumeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                togglePause();
            }
        });

        table.add(resumeBtn).size(BUTTON_WIDTH, BUTTON_HEIGHT).padBottom(BUTTON_DISTANCE).row();
    }

    /**
     * Function that adds a Next Level Button to the Stage.
     */
    protected void addNextLvlBtn() {
        TextButton nextLvlBtn = new TextButton("Next Level", skin);

        nextLvlBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadLevel();
            }
        });

        table.add(nextLvlBtn).size(BUTTON_WIDTH, BUTTON_HEIGHT).padBottom(BUTTON_DISTANCE).row();
    }

    /**
     * Function used to pause or unpause the current Game level.
     */
    private void togglePause() {
        hud.togglePause();
    }

    /**
     * Function used to start a new Game level.
     */
    private void startLevel() {
        hud.startLevel();
    }

    /**
     * Function used to load the next available Game level.
     */
    private void loadLevel() {
        hud.loadNextLevel();
    }

    /**
     * Function used by all the Pop Up Menus for starting the initial procedures of the stage configuration.
     */
    protected void initStage() {
        table.setFillParent(true);

        table.add(message).padBottom(LABEL_DISTANCE).row();
        message.setFontScale(MESSAGE_FONT_SCALE, MESSAGE_FONT_SCALE);

        Label score = new Label(hud.getScoreString(), skin);
        table.add(score).padBottom(LABEL_DISTANCE).row();
        score.setFontScale(SCORE_FONT_SCALE, SCORE_FONT_SCALE);
    }

    /**
     * Function used by all the Pop Up Menus for ending the final procedures of the stage configuration.
     */
    protected void finishStage() {
        addExitBtn();

        setMessage();
        menu.addActor(table);
    }

    /**
     * Function responsible for configure the Stage that contains the Pop Up Menu's elements.
     */
    protected abstract void confStage();

    /**
     * Function used to set the Pop Up Menu's message accordingly.
     */
    protected abstract void setMessage();
}
