package com.lpoo.game.model.entities;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * Created by andre on 31/05/2017.
 */

public class JumpPowerUp extends PowerUp {

    JumpPowerUp(World world, RectangleMapObject object) {
        super(world, object.getRectangle().getCenter(new Vector2()).scl(PIXEL_TO_METER), ModelType.POWERUP_JUMP);

        // TODO FINISH
    }

    @Override
    public void onHit(BallModel model) {
        model.decreaseDensity();
    }
}
