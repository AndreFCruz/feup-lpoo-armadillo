package com.lpoo.game.model.entities;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * A model representing a random PowerUp.
 */
public class RandomPowerUp extends PowerUp {

    /**
     * Interface for Ball actors (power ups)
     */
    interface BallActor {
        /**
         * Changes the given ball.
         *
         * @param model The ball that will be updated.
         */
        void actOnBall(BallModel model);
    }

    /**
     * Array containing the ball actors. This will associate each to a random generated power up.
     */
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

    /**
     * Random Power Up's constructor.
     * Creates a power up from the given object, into the given world.
     *
     * @param world  The world the power up will be in.
     * @param object The object to create the power up with.
     */
    public RandomPowerUp(World world, RectangleMapObject object) {
        super(world, object, ModelType.POWERUP_RANDOM);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void onHit(BallModel model) {
        flagForRemoval();

        int n = new Random().nextInt(actors.size);

        actors.get(n).actOnBall(model);
    }
}
