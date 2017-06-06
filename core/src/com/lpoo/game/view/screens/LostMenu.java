package com.lpoo.game.view.screens;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.game.Armadillo;


/**
 * Class used to represent the pop up Menu that shows up when the User loses the game.
 */
public class LostMenu extends OptionsMenu {

    /**
     * Lost Menu Constructor.
     * Takes as parameters a viewport, a game and a HUD Menu to call its superclass constructor.
     *
     * @param viewport
     * @param game
     * @param hud
     */
    LostMenu (Viewport viewport, Armadillo game, HudMenu hud) { super(viewport, game, hud); }

    @Override
    protected void confStage() {
        initStage();
        addRestartBtn();
        finishStage();
    }

    @Override
    protected void setMessage() { message.setText("GAME OVER"); }
}
