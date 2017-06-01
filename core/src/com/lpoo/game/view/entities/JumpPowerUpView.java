package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lpoo.game.Spheral;

/**
 * Created by andre on 01/06/2017.
 */

public class JumpPowerUpView extends EntityView {

    /**
     * Creates a view belonging to a game.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     */
    JumpPowerUpView(Spheral game) {
        super(game);
    }

    @Override
    protected Sprite createSprite(Spheral game) { // TODO FINISH
        Texture tex = game.getAssetManager().get(".png");
        return new Sprite(new TextureRegion(tex, tex.getWidth(), tex.getHeight()));
    }
}
