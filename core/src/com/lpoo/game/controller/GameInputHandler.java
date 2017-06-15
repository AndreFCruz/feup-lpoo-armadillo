package com.lpoo.game.controller;

import com.badlogic.gdx.InputAdapter;
import com.lpoo.game.model.GameModel;

/**
 * Input handler for in-game events.
 */

public class GameInputHandler extends InputAdapter {

    private GameModel model;

    public GameInputHandler(GameModel model) {
        this.model = model;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        model.getBallModel().jump();
        return true;
    }
}
