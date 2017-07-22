package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lpoo.game.Armadillo;

/**
 * A class used to represent a Box's entity view.
 */
class BoxView extends EntityView {

    /**
     * BoxView's Constructor.
     * It calls its superclass constructor. EntityView(Armadillo game).
     *
     * @param game The current game session.
     */
    BoxView(Armadillo game) {
        super(game);
    }

    /**
     * Template method implementation to create this view's sprite.
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     * @return This view's sprite.
     */
    @Override
    protected Sprite createSprite(Armadillo game) {
        Texture tex = game.getAssetManager().get("box.png");
        return new Sprite(new TextureRegion(tex, tex.getWidth(), tex.getHeight()));
    }
}
