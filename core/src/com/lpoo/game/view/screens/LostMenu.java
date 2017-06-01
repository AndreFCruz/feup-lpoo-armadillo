package com.lpoo.game.view.screens;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.game.Spheral;

/**
 * Created by Edgar on 01/06/2017.
 */

public class LostMenu extends OptionsMenu {

    LostMenu (Viewport viewport, Spheral game, HudMenu hud) { super(viewport, game, hud); }

    @Override
    protected void confStage() {
        initStage();
        addRestartBtn();
        finishStage();
    }

    @Override
    protected void setMessage() { message.setText("GAME OVER"); }
}
