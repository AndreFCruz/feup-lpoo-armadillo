package com.lpoo.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lpoo.game.Armadillo;
import com.lpoo.game.model.entities.EntityModel;

import java.util.HashMap;
import java.util.Map;

/**
 * A class used to represent the Power Up's entity view.
 */
public class PowerUpView extends EntityView {

    /**
     * The number of Frames of the animation.
     */
    private static final int NUM_FRAMES = 8;

    /**
     * HashMap containing the Power Ups paired with their views.
     */
    private static Map<EntityModel.ModelType, String> texFiles = new HashMap<>();

    static {
        texFiles.put(EntityModel.ModelType.POWERUP_GRAVITY, "animations/crystal-32-blue.png");
        texFiles.put(EntityModel.ModelType.POWERUP_JUMP, "animations/crystal-32-yellow.png");
        texFiles.put(EntityModel.ModelType.POWERUP_RANDOM, "animations/crystal-32-pink.png");
        texFiles.put(EntityModel.ModelType.POWERUP_VELOCITY, "animations/crystal-32-orange.png");
    }

    /**
     * The time between the animation frames
     */
    private static final float FRAME_TIME = 0.1f;

    /**
     * The power-up's animation
     */
    private Animation<TextureRegion> textureRegion;

    /**
     * Time since the model started the game. Used
     * to calculate the current animation frame.
     */
    private float stateTime = 0;

    /**
     * Creates a PowerUpModel's View.
     * It calls its superclass constructor. EntityView(Armadillo game, EntityModel.ModelType type).
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     * @param type The type of PowerUP being created.
     */
    PowerUpView(Armadillo game, EntityModel.ModelType type) {
        super(game, type);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    protected Sprite createSprite(Armadillo game) {
        textureRegion = createAnimation(game, texFiles.get(getType()));
        // Template method is called before this constructor, this.type is not yet initialized!!
        return new Sprite(textureRegion.getKeyFrame(stateTime, true));
    }

    /**
     * Creates the animation used for this PowerUp Type
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     * @return the animation used for this PowerUp
     */
    private Animation<TextureRegion> createAnimation(Armadillo game, String fileName) {
        Texture texture = game.getAssetManager().get(fileName);
        TextureRegion[][] regions = TextureRegion.split(texture, texture.getWidth() / NUM_FRAMES, texture.getHeight());

        TextureRegion[] frames = new TextureRegion[NUM_FRAMES];
        System.arraycopy(regions[0], 0, frames, 0, NUM_FRAMES);

        return new Animation<>(FRAME_TIME, frames);
    }

    /**
     * Draws the sprite from this view using a sprite batch.
     * Chooses the correct texture or animation to be used
     * depending on the accelerating flag.
     *
     * @param batch The sprite batch to be used for drawing.
     */
    @Override
    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        sprite.setRegion(textureRegion.getKeyFrame(stateTime, true));

        sprite.draw(batch);
    }
}
