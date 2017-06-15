package com.lpoo.game.controller;

import com.badlogic.gdx.InputAdapter;
import com.lpoo.game.model.GameModel;

/**
 * Created by andre on 15/06/2017.
 */

public class GameInputHandler extends InputAdapter {

    private GameModel model;

    GameInputHandler(GameModel model) {
        this.model = model;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        model.getBallModel().jump();
        return true;
    }
}
