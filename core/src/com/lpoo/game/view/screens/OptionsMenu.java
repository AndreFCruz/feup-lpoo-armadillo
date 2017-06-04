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
import com.lpoo.game.Spheral;
import com.lpoo.game.model.GameModel;

/**
 * Created by Edgar on 01/06/2017.
 */

/**
 * Abstract class used to represent all the Pop Up Menu's classes, containing several option actions.
 */
public abstract class OptionsMenu {

    protected Spheral game;

    protected HudMenu hud;

    /**
     * A Stage used to represent the Menu that appears when the game is paused / over / won.
     */
    protected Stage menu;

    protected Table table;

    protected Label message;

    protected Skin skin;

    /**
     * The width of the HUD viewport in pixels. The height is
     * automatically calculated using the screen ratio.
     */
    protected static final float HUD_VIEWPORT_WIDTH = 1000;

    /**
     * The height of the viewport in pixels. The height is
     * automatically calculated using the screen ratio.
     */
    protected static final float HUD_VIEWPORT_HEIGHT = HUD_VIEWPORT_WIDTH * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());;

    /**
     * Options Menu Constructor
     * TODO.
     *
     * @param viewport
     * @param game
     * @param hud
     */
    public OptionsMenu (Viewport viewport, Spheral game, HudMenu hud) {
        this.game = game;
        this.hud = hud;

        menu = new Stage (viewport, game.getBatch());
        table = new Table();
        this.skin = game.getSkin();

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

        table.add(exitBtn).size(HUD_VIEWPORT_WIDTH / 3, HUD_VIEWPORT_HEIGHT / 8);
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

        table.add(restartBtn).size(HUD_VIEWPORT_WIDTH / 3, HUD_VIEWPORT_HEIGHT / 8).padBottom(HUD_VIEWPORT_HEIGHT / 14).row();
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

        table.add(resumeBtn).size(HUD_VIEWPORT_WIDTH / 3, HUD_VIEWPORT_HEIGHT / 8).padBottom(HUD_VIEWPORT_HEIGHT / 14).row();
    }

    /**
     * Function that adds a Next Level to the Stage.
     */
    protected void addNextLvlBtn() {
        TextButton nextLvlBtn = new TextButton("Next Level", skin);

        nextLvlBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadLevel();
            }
        });

        table.add(nextLvlBtn).size(HUD_VIEWPORT_WIDTH / 3, HUD_VIEWPORT_HEIGHT / 8).padBottom(HUD_VIEWPORT_HEIGHT / 14).row();
    }

    /**
     * Function used to pause or unpause the current Game level.
     */
    private void togglePause() { hud.togglePause(); }

    /**
     * Function used to start a new Game level.
     */
    private void startLevel() { hud.startLevel(); }

    /**
     * Function used to load the next available Game level.
     */
    private void loadLevel() { hud.loadNextLevel(); }

    /**
     * Function used by all the Pop Up Menus for starting the initial procedures of the stage configuration.
     */
    protected void initStage() {
        table.setFillParent(true);
        table.debugAll();

        table.add(message).padBottom(HUD_VIEWPORT_HEIGHT/ 18).row();
        message.setFontScale(HUD_VIEWPORT_WIDTH / 250,HUD_VIEWPORT_WIDTH / 250);

        Label score = new Label(hud.getScoreString(), skin);
        table.add(score).padBottom(HUD_VIEWPORT_HEIGHT/ 14).row();
        score.setFontScale(HUD_VIEWPORT_WIDTH / 400,HUD_VIEWPORT_WIDTH / 400);
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
