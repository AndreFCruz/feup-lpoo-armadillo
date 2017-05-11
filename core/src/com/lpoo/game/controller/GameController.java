package com.lpoo.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.lpoo.game.model.GameModel;

/**
 * Created by andre on 04/05/2017.
 */

public class GameController implements InputHandler {

    GameModel model;

    public GameController() {
        model = GameModel.getInstance();

        if (! Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer))
            System.err.println("Accelerometer unavailable");
    }

    @Override
    public void handleInput(float delta) {
        pollTouch(delta);
        pollKeys(delta);
        pollAccelerometer();
    }

    private void pollTouch(float delta) {
        if (Gdx.input.justTouched())
            System.out.println("Just touched " + Gdx.input.getX() + ", " + Gdx.input.getY());
    }

    private void pollKeys(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            System.out.println("KEY UP");
    }

    private void pollAccelerometer() {

    }

}
