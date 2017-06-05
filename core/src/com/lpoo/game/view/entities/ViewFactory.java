package com.lpoo.game.view.entities;

import com.lpoo.game.Spheral;
import com.lpoo.game.model.entities.EntityModel;
import com.lpoo.game.model.entities.ShapeModel;

import java.util.HashMap;
import java.util.Map;

import static com.lpoo.game.model.entities.EntityModel.ModelType.BALL;
import static com.lpoo.game.model.entities.EntityModel.ModelType.BOX;
import static com.lpoo.game.model.entities.EntityModel.ModelType.POWERUP_GRAVITY;
import static com.lpoo.game.model.entities.ShapeModel.ModelType.PLATFORM;

/**
 * A factory for EntityView objects with cache
 */

public class ViewFactory {
    private static Map<EntityModel.ModelType, EntityView> entitiesCache =
            new HashMap<EntityModel.ModelType, EntityView>();

    private static Map<ShapeModel.ModelType, ShapeView> shapesCache =
            new HashMap<ShapeModel.ModelType, ShapeView>();

    public static EntityView makeView(Spheral game, EntityModel model) {
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

    public static ShapeView makeView(ShapeModel model) {
        if (!shapesCache.containsKey(model.getType())) {
            if (model.getType() == PLATFORM) shapesCache.put(model.getType(), new PlatformView());
        }

        return shapesCache.get(model.getType());
    }

    public static void resetBallView() {
        entitiesCache.remove(BALL);
    }
}
