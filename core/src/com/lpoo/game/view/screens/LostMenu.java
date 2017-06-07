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
     * See OptionsMenu(Viewport viewport, Armadillo game, HudMenu hud).
     *
     * @param viewport The viewport that will be associated to the stage.
     * @param game     The current game session.
     * @param hud      The current Game's hud.
     */
    LostMenu(Viewport viewport, Armadillo game, HudMenu hud) {
        super(viewport, game, hud);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    protected void confStage() {
        initStage();
        addRestartBtn();
        finishStage();
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    protected void setMessage() {
        message.setText("GAME OVER");
    }
}
