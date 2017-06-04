package com.lpoo.game.model.entities;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by andre on 01/06/2017.
 */

public class RandomPowerUp extends PowerUp {

    interface BallActor {
        void actOnBall(BallModel model);
    }

    private static Array<BallActor> actors = new Array<>();

    static {
        actors.add(new BallActor() {
            @Override
            public void actOnBall(BallModel model) {
                model.increaseDensity();
            }
        });
        actors.add(new BallActor() {
            @Override
            public void actOnBall(BallModel model) {
                model.decreaseDensity();
            }
        });
        actors.add(new BallActor() {
            @Override
            public void actOnBall(BallModel model) {
                model.increaseVelocity();
            }
        });
        actors.add(new BallActor() {
            @Override
            public void actOnBall(BallModel model) {
                model.decreaseVelocity();
            }
        });
        actors.add(new BallActor() {
            @Override
            public void actOnBall(BallModel model) {
                model.increaseJumpForce();
            }
        });
        actors.add(new BallActor() {
            @Override
            public void actOnBall(BallModel model) {
                model.decreaseJumpForce();
            }
        });
    }

    public RandomPowerUp(World world, RectangleMapObject object) {
        super(world, object, ModelType.POWERUP_RANDOM);
    }

    @Override
    public void onHit(BallModel model) {
        flagForRemoval();

        int n = new Random().nextInt(actors.size);

        actors.get(n).actOnBall(model);
    }
}
