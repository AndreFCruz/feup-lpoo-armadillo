package com.lpoo.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.game.GameServices;
import com.lpoo.game.Spheral;

/**
 * Created by andre on 01/06/2017.
 */

public class SpeedPowerUp extends PowerUp {

    public SpeedPowerUp(World world, RectangleMapObject object) {
        super(world, object, ModelType.POWERUP_VELOCITY);
    }

    @Override
    public void onHit(BallModel model) {
        flagForRemoval();
        model.increaseVelocity();

        GameServices gameServices = ((Spheral) (Gdx.app.getApplicationListener())).getGameServices();
        gameServices.unlockAchievement(gameServices.getSpeedAchievementID());
    }
}
