package com.lpoo.game.view.entities;

import com.lpoo.game.Spheral;
import com.lpoo.game.model.entities.EntityModel;
import com.lpoo.game.model.entities.ShapeModel;

import java.util.HashMap;
import java.util.Map;

import static com.lpoo.game.model.entities.EntityModel.ModelType.BALL;
import static com.lpoo.game.model.entities.ShapeModel.ModelType.PLATFORM;
import static com.lpoo.game.model.entities.ShapeModel.ModelType.WATER;

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
            if (model.getType() == BALL) entitiesCache.put(model.getType(), new BallView(game));

        }

        return entitiesCache.get(model.getType());
    }

    public static ShapeView makeView(ShapeModel model) {
        if (!shapesCache.containsKey(model.getType())) {
            if (model.getType() == PLATFORM) shapesCache.put(model.getType(), new PlatformView());
        }

        return shapesCache.get(model.getType());
    }
}
