package com.lpoo.game.model.entities;

/**
 * Interface for hittable entities.
 */
public interface Hittable {

    /**
     * Handles the hit event.
     *
     * @param model The model this object acts upon.
     */
    void onHit(BallModel model);
}
