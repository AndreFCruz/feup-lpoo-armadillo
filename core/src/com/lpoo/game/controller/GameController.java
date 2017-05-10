package com.lpoo.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.lpoo.game.model.GameModel;

/**
 * Created by andre on 04/05/2017.
 */

public class GameController extends InputAdapter {

    GameModel model;

    public GameController() {
        Gdx.input.setInputProcessor(this);

        model = GameModel.getInstance();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                model.getBall().rotate(10);
                break;
            case Input.Keys.RIGHT:
                model.getBall().rotate(-10);
                break;
        }

        return true;
    }

}
