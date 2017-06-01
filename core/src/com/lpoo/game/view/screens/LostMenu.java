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

public class LostMenu extends OptionsMenu {

    LostMenu (Viewport viewport, Spheral game, GameModel model, HudMenu hud) {
        super(viewport, game, model, hud);
    }

    @Override
    protected void confStage() {
        Table table = new Table();

        addHeader(table);
        addRestartBtn(table);
        addExitBtn(table);

        setMessage();
    }

    @Override
    protected void setMessage() { message.setText("GAME OVER"); }
}
