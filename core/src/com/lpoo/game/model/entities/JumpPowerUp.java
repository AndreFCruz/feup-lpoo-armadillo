package com.lpoo.game.model.entities;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A model representing a jump PowerUp.
 */
public class JumpPowerUp extends PowerUp {

    /**
     * Jump Power Up's constructor.
     * Creates a power up from the given object, into the given world.
     *
     * @param world  The world the power up will be in.
     * @param object The object to create the power up with.
     */
    public JumpPowerUp(World world, RectangleMapObject object) {
        super(world, object, ModelType.POWERUP_JUMP);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onHit(BallModel model) {
        flagForRemoval();
        model.increaseJumpForce();
    }
}
