package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.game.Armadillo;
import com.lpoo.game.model.entities.EntityModel;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A abstract view capable of holding a sprite with a certain
 * position and rotation.
 * <p>
 * This view is able to update its data based on a entity model.
 */
public abstract class EntityView {

    /**
     * The type of entity of the entity view.
     */
    private final EntityModel.ModelType type;

    /**
     * The sprite representing this entity view.
     */
    protected Sprite sprite;

    /**
     * Creates a view belonging to a game.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     * @param type the type of entity.
     */
    EntityView(Armadillo game, EntityModel.ModelType type) {
        this.type = type;
        sprite = createSprite(game);
    }

    /**
     * Creates a view belonging to a game.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     */
    EntityView(Armadillo game) {
        this(game, null);
    }

    /**
     * Draws the sprite from this view using a sprite batch.
     *
     * @param batch The sprite batch to be used for drawing.
     */
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    /**
     * Abstract method that creates the view sprite. Concrete
     * implementation should extend this method to create their
     * own sprites.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     * @return the sprite representing this view.
     */
    protected abstract Sprite createSprite(Armadillo game);

    /**
     * Updates this view based on a certain model.
     *
     * @param model the entity model used to update this view
     */
    public void update(EntityModel model) {
        sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
        sprite.setRotation((float) Math.toDegrees(model.getAngle()));
    }

    protected EntityModel.ModelType getType() {
        return type;
    }
}
