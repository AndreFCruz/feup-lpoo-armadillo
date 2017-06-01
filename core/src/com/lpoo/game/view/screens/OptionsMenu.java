package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Edgar on 01/06/2017.
 */

public abstract class OptionsMenu {

    /**
     * A Stage used to represent the Menu that appears when the game is paused / over / won.
     */
    private Stage menu;

    private Label message;

    private TextButton exitBtn;

    private Skin skin;

    public OptionsMenu (Viewport viewport, Batch batch, Skin skin) {
        menu = new Stage (viewport, batch);

        this.skin = skin;

        message = new Label("", this.skin);

        exitBtn = new TextButton("Exit", this.skin);
    }

    public void draw() {
        menu.act(Gdx.graphics.getDeltaTime());
        menu.draw();
        Gdx.input.setInputProcessor(menu);
    }

}
