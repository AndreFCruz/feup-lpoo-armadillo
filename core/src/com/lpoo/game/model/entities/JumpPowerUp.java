package com.lpoo.game.model.entities;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by andre on 31/05/2017.
 */

public class JumpPowerUp extends PowerUp {

    public JumpPowerUp(World world, RectangleMapObject object) {
        super(world, object, ModelType.POWERUP_JUMP);
    }

    @Override
    public void onHit(BallModel model) {
        flagForRemoval();
        model.decreaseDensity();
    }
}
