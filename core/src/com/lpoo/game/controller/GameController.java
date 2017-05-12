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
        pollKeys(delta);

        if (accelerometerAvailable)
            pollAccelerometer();
    }

    private void pollTouch(float delta) {
        if (Gdx.input.justTouched()) {
            System.out.println("Just touched: " + Gdx.input.getX() + ", " + Gdx.input.getY());

            Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(vec);
            System.out.println("World coords: " + vec.x + ", " + vec.y);
        }
    }

    private void pollKeys(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            System.out.println("LEFT KEY");
            model.getBall().rotate(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            System.out.println("RIGHT KEY");
            model.getBall().rotate(delta * -1);
        }
    }

    private void pollAccelerometer() {

    }

}
