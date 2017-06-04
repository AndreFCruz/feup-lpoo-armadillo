package com.lpoo.game.view.screens;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.game.Spheral;

/**
 * Class used to represent the pop up Menu that shows up when the User wins the game.
 */
public class WonMenu extends OptionsMenu {

    /**
     * Won Menu Constructor.
     * Takes as parameters a viewport, a game and a HUD Menu to call its superclass constructor.
     *
     * @param viewport
     * @param game
     * @param hud
     */
    WonMenu (Viewport viewport, Spheral game, HudMenu hud) { super(viewport, game, hud); }

    @Override
    protected void confStage() {
        initStage();
        addNextLvlBtn();
        addRestartBtn();
        finishStage();
    }

    @Override
    protected void setMessage() {
        message.setText("LEVEL FINISHED");
    }
}
