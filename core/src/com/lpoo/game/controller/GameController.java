package com.lpoo.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lpoo.game.model.GameModel;

/**
 * The game's input handler.
 */

public class GameController implements InputHandler {

    private GameModel model;
    private Boolean accelerometerAvailable;

    public GameController(GameModel model) {
        this.model = model;

        if (! (accelerometerAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)))
            System.err.println("Accelerometer unavailable");
    }

    @Override
    public void handleInput(float delta) {
        pollTouch(delta);

        if (accelerometerAvailable)
            pollAccelerometer(delta);
        else
            pollKeys(delta);
    }

    private void pollTouch(float delta) {
        if (Gdx.input.justTouched()) {
            model.getBallModel().jump();
        }
    }

    private void pollKeys(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            model.getBallModel().rotate(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            model.getBallModel().rotate(delta * -1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            model.getBallModel().jump();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            model.togglePause();
        }
    }

    private void pollAccelerometer(float delta) {
        float y = Gdx.input.getAccelerometerY();
        float z = Gdx.input.getAccelerometerZ();

        float roll = (float) Math.atan2(y, z) * 180 / ((float) Math.PI);

        if (roll > 45)
            roll = 45;
        else if (roll < -45)
            roll = -45;

        model.getBallModel().rotate(delta * (roll / -45));
    }

}
