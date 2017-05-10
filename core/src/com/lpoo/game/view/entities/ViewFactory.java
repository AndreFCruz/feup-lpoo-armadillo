package com.lpoo.game.view.entities;

import com.lpoo.game.Spheral;
import com.lpoo.game.model.entities.EntityModel;

import java.util.HashMap;
import java.util.Map;

import static com.lpoo.game.model.entities.EntityModel.ModelType.BALL;

/**
 * A factory for EntityView objects with cache
 */

public class ViewFactory {
    private static Map<EntityModel.ModelType, EntityView> cache =
            new HashMap<EntityModel.ModelType, EntityView>();

    public static EntityView makeView(Spheral game, EntityModel model) {
        if (!cache.containsKey(model.getType())) {
            if (model.getType() == BALL) cache.put(model.getType(), new BallView(game));

        }
        return cache.get(model.getType());
    }
}
