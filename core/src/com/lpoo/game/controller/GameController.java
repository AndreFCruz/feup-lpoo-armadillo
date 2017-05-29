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

    public GameController(OrthographicCamera camera, GameModel model) {
        this.model = model;
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

    int debugCount = 0;

    private void pollAccelerometer(float delta) {
//        float x = Gdx.input.getAccelerometerX();
        float y = Gdx.input.getAccelerometerY();
        float z = Gdx.input.getAccelerometerZ();

        float roll = (float) Math.atan2(y, z) * 180 / ((float) Math.PI);
        //float pitch = (float) Math.atan2(-x, Math.sqrt(y*y + z*z)) * 180 / ((float) Math.PI);

        if (roll > 45)
            roll = 45;
        else if (roll < -45)
            roll = -45;

        model.getBallModel().rotate(delta * (roll / -45));
    }

}
