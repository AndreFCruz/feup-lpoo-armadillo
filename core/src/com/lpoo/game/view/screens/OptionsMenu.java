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

public abstract class OptionsMenu {

    protected Spheral game;

    protected GameModel model;

    protected HudMenu hud;

    /**
     * A Stage used to represent the Menu that appears when the game is paused / over / won.
     */
    protected Stage menu;

    protected Label message;

    protected int score;

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
     * Default Constructor.
     */
    public OptionsMenu () {}

    public OptionsMenu (Viewport viewport, Spheral game, GameModel model, HudMenu hud) {
        this.game = game;
        this.model = model;
        this.hud = hud;

        menu = new Stage (viewport, game.getBatch());
        this.skin = game.getSkin();

        message = new Label("", this.skin);

    }

    public void draw() {
        menu.act(Gdx.graphics.getDeltaTime());
        menu.draw();
        Gdx.input.setInputProcessor(menu);
    }

    protected void addHeader(Table table) {
        table.add(message).padBottom(HUD_VIEWPORT_HEIGHT/ 18).row();
        message.setFontScale(HUD_VIEWPORT_WIDTH / 250,HUD_VIEWPORT_WIDTH / 250);

        Label score = new Label(hud.getScoreString(), skin);
        table.add(score).padBottom(HUD_VIEWPORT_HEIGHT/ 14).row();
        score.setFontScale(HUD_VIEWPORT_WIDTH / 400,HUD_VIEWPORT_WIDTH / 400);
    }

    protected void addExitBtn(Table table) {
        TextButton exitBtn = new TextButton("Exit", this.skin);

        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelMenuScreen(game));
            }
        });

        table.add(exitBtn).size(HUD_VIEWPORT_WIDTH / 3, HUD_VIEWPORT_HEIGHT / 8);
    }

    protected void addRestartBtn(Table table) {
        TextButton restartBtn = new TextButton("Restart", skin);

        restartBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                togglePause();
                startLevel();
            }
        });

        table.add(restartBtn).size(HUD_VIEWPORT_WIDTH / 3, HUD_VIEWPORT_HEIGHT / 8).padBottom(HUD_VIEWPORT_HEIGHT / 14).row();
    }

    protected void addResumeBtn(Table table) {
        TextButton resumeBtn = new TextButton("Resume", skin);

        resumeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                togglePause();
            }
        });

        table.add(resumeBtn).size(HUD_VIEWPORT_WIDTH / 3, HUD_VIEWPORT_HEIGHT / 8).padBottom(HUD_VIEWPORT_HEIGHT / 14).row();
    }

    protected void addNextLvlBtn(Table table) {
        TextButton nextLvlBtn = new TextButton("Next Level", skin);

        nextLvlBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                togglePause();
            }
        });

        table.add(nextLvlBtn).size(HUD_VIEWPORT_WIDTH / 3, HUD_VIEWPORT_HEIGHT / 8).padBottom(HUD_VIEWPORT_HEIGHT / 14).row();
    }

    protected void togglePause() { model.togglePause(); }

    protected void startLevel() { model.startLevel(); }

    protected abstract void confStage();

    protected abstract void setMessage();
}
