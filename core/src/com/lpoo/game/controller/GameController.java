package com.lpoo.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by andre on 04/05/2017.
 */

public class GameController extends InputAdapter {

    public GameController() {
        Gdx.input.setInputProcessor(this);
    }

}
