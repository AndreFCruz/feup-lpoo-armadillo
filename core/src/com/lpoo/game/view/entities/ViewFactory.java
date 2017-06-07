package com.lpoo.game.view.entities;

import com.lpoo.game.Armadillo;
import com.lpoo.game.model.entities.EntityModel;
import com.lpoo.game.model.entities.ShapeModel;

import java.util.HashMap;
import java.util.Map;

import static com.lpoo.game.model.entities.EntityModel.ModelType.BALL;
import static com.lpoo.game.model.entities.ShapeModel.ModelType.PLATFORM;

/**
 * A factory for EntityView objects with cache
 */
public class ViewFactory {

    /**
     * A HashMap serving as the entities' cache.
     */
    private static Map<EntityModel.ModelType, EntityView> entitiesCache = new HashMap<>();

    /**
     * A HashMap serving as the shapes' cache.
     */
    private static Map<ShapeModel.ModelType, ShapeView> shapesCache = new HashMap<>();

    /**
     * Gets from the entities cache the entity being managed.
     *
     * @param game  The current game session.
     * @param model The current Entity being managed.
     * @return An Entity View referring to the entity being managed.
     */
    public static EntityView makeView(Armadillo game, EntityModel model) {
        if (!entitiesCache.containsKey(model.getType())) {
            switch (model.getType()) {
                case BALL:
                    entitiesCache.put(model.getType(), new BallView(game));
                    break;

                case BOX:
                    entitiesCache.put(model.getType(), new BoxView(game));
                    break;

                case POWERUP_GRAVITY:
                case POWERUP_JUMP:
                case POWERUP_VELOCITY:
                case POWERUP_RANDOM:
                    entitiesCache.put(model.getType(), new PowerUpView(game, model.getType()));
                    break;

            }
        }

        return entitiesCache.get(model.getType());
    }

    /**
     * Gets from the Shapes cache the shape being managed.
     *
     * @param model The shape model that is being managed.
     * @return The Shape view corresponding to the shape model given.
     */
    public static ShapeView makeView(ShapeModel model) {
        if (!shapesCache.containsKey(model.getType())) {
            if (model.getType() == PLATFORM) shapesCache.put(model.getType(), new PlatformView());
        }

        return shapesCache.get(model.getType());
    }

    /**
     * Removes the Ball Entity from the entities Cache.
     */
    public static void resetBallView() {
        entitiesCache.remove(BALL);
    }
}
