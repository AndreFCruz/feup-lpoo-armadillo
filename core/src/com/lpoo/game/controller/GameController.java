package com.lpoo.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by andre on 26/04/2017.
 */

public class GameController extends InputAdapter {

    public GameController() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }
}
