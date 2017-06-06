package com.lpoo.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.game.GameServices;
import com.lpoo.game.Spheral;

/**
 * A model representing a speed powerup.
 */

public class SpeedPowerUp extends PowerUp {

    public SpeedPowerUp(World world, RectangleMapObject object) {
        super(world, object, ModelType.POWERUP_VELOCITY);
    }

    @Override
    public void onHit(BallModel model) {
        flagForRemoval();
        model.increaseVelocity();

        try {
            GameServices gameServices = ((Spheral) (Gdx.app.getApplicationListener())).getGameServices();
            gameServices.unlockAchievement(gameServices.getSpeedAchievementID());
        }
        catch (java.lang.ClassCastException e) {
            System.err.println("Activity class not of type Spheral. Tests running.");
        }
    }
}
