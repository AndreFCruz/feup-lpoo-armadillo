package com.lpoo.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.game.GameServices;
import com.lpoo.game.Armadillo;

/**
 * A model representing a gravity PowerUp.
 */
public class GravityPowerUp extends PowerUp {

    /**
     * Gravity Power Up's constructor.
     * Creates a power up from the given object, into the given world.
     *
     * @param world  The world the power up will be in.
     * @param object The object to create the power up with.
     */
    public GravityPowerUp(World world, RectangleMapObject object) {
        super(world, object, ModelType.POWERUP_GRAVITY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onHit(BallModel model) {
        flagForRemoval();
        model.flipGravity();

        try {
            GameServices gameServices = ((Armadillo) (Gdx.app.getApplicationListener())).getGameServices();
            gameServices.unlockAchievement(gameServices.getGravityAchievementID());
        } catch (java.lang.ClassCastException e) {
            System.err.println("Activity class not a Game. Tests running."); }
    }
}
