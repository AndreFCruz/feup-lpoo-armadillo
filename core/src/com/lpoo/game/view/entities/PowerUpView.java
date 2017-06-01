package com.lpoo.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lpoo.game.Spheral;
import com.lpoo.game.model.entities.EntityModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andre on 01/06/2017.
 */

public class PowerUpView extends EntityView {

    private static final int NUM_FRAMES = 8;

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
    private static final float FRAME_TIME = 0.05f;

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
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     */
    PowerUpView(Spheral game, EntityModel.ModelType type) {
        super(game, type);
    }

    @Override
    protected Sprite createSprite(Spheral game) {
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
    private Animation<TextureRegion> createAnimation(Spheral game, String fileName) {
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
