package com.lpoo.game.view.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.game.Spheral;
import com.lpoo.game.model.GameModel;

/**
 * Created by Edgar on 01/06/2017.
 */

public class PauseMenu extends OptionsMenu {

    PauseMenu (Viewport viewport, Spheral game, HudMenu hud) {
        super(viewport, game, hud);

        confStage();
    }

    @Override
    protected void confStage() {
        Table table = new Table();
        table.setFillParent(true);
        table.debugAll();

        addHeader(table);
        addResumeBtn(table);
        addRestartBtn(table);
        addExitBtn(table);

        setMessage();
        menu.addActor(table);
    }

    @Override
    protected void setMessage() { message.setText("GAME PAUSED"); }
}
