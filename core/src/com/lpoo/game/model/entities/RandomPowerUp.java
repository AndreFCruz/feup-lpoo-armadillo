package com.lpoo.game.model.entities;

import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

/**
 * Created by andre on 01/06/2017.
 */

public class RandomPowerUp extends PowerUp {

    public RandomPowerUp(World world, RectangleMapObject object) {
        super(world, object, ModelType.POWERUP_RANDOM);
    }

    @Override
    public void onHit(BallModel model) {
        flagForRemoval();

        Random rand = new Random();

        switch (rand.nextInt(6)) {
            case 0:
                model.increaseDensity();
                break;
            case 1:
                model.decreaseDensity();
                break;
            case 2:
                model.increaseVelocity();
                break;
            case 3:
                model.decreaseVelocity();
                break;
            case 4:
                model.increaseJumpForce();
                break;
            case 5:
                model.decreaseJumpForce();
                break;
        }
    }
}
