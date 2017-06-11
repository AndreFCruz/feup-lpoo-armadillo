package com.lpoo.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.game.GameServices;
import com.lpoo.game.Armadillo;

/**
 * A model representing a speed PowerUp.
 */
public class SpeedPowerUp extends PowerUp {

    /**
     * Speed Power Up's constructor.
     * Creates a power up from the given object, into the given world.
     *
     * @param world  The world the power up will be in.
     * @param object The object to create the power up with.
     */
    public SpeedPowerUp(World world, RectangleMapObject object) {
        super(world, object, ModelType.POWERUP_VELOCITY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onHit(BallModel model) {
        flagForRemoval();
        model.increaseVelocity();

        try {
            GameServices gameServices = ((Armadillo) (Gdx.app.getApplicationListener())).getGameServices();
            gameServices.unlockAchievement(gameServices.getSpeedAchievementID());
        } catch (java.lang.ClassCastException e) {
            System.err.println("Activity class not of type Spheral. Tests running."); }
    }
}
