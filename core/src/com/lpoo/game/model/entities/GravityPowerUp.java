package com.lpoo.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.game.GameServices;
import com.lpoo.game.Spheral;

/**
 * Created by andre on 01/06/2017.
 */

public class GravityPowerUp extends PowerUp {

    public GravityPowerUp(World world, RectangleMapObject object) {
        super(world, object, ModelType.POWERUP_GRAVITY);
    }


    @Override
    public void onHit(BallModel model) {
        flagForRemoval();
        model.flipGravity();

        try {
            GameServices gameServices = ((Spheral) (Gdx.app.getApplicationListener())).getGameServices();
            gameServices.unlockAchievement(gameServices.getGravityAchievementID());
        }
        catch (java.lang.ClassCastException e) {
            System.err.println("Activity class not of type Spheral. Tests running.");
        }
    }
}
