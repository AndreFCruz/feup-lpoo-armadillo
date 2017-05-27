package com.lpoo.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.lpoo.game.model.GameModel;

/**
 * Created by andre on 04/05/2017.
 */

public class GameController implements InputHandler {

    private GameModel model;
    private OrthographicCamera camera;
    private Boolean accelerometerAvailable;

    public GameController(OrthographicCamera camera) {
        model = GameModel.getInstance();
        this.camera = camera;

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
            model.getBall().jump();
        }
    }

    private void pollKeys(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            model.getBall().rotate(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            model.getBall().rotate(delta * -1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            model.getBall().jump();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            model.togglePause();
        }
    }

    int debugCount = 0;

    private void pollAccelerometer(float delta) {
        float x = Gdx.input.getAccelerometerX();
        float y = Gdx.input.getAccelerometerY();
        float z = Gdx.input.getAccelerometerZ();

        float roll = (float) Math.atan2(y, z) * 180 / ((float) Math.PI);
        //float pitch = (float) Math.atan2(-x, Math.sqrt(y*y + z*z)) * 180 / ((float) Math.PI);

        if (debugCount++ % 100 == 0) {
            Gdx.app.log("Accelerometer", x + ", " + y + ", " + z);
            //Gdx.app.log("Roll / Pitch", roll + " / " + pitch);
        }

        model.getBall().rotate(delta * roll / -45);
    }

}
