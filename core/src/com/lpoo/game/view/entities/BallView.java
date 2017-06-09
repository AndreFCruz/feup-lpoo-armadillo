package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lpoo.game.Armadillo;

/**
 * A class used to represent the Ball's entity view.
 */
public class BallView extends EntityView {

    /**
     * ID of the current appearance being used to draw the ball.
     */
    private static int currentSkinID = 0;

    /**
     * BallView's Constructor.
     * It calls its superclass constructor. EntityView(Armadillo game).
     *
     * @param game The current game session.
     */
    BallView(Armadillo game) {
        super(game);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    protected Sprite createSprite(Armadillo game) {
        Texture tex = game.getAssetManager().get("skins/skin0" + currentSkinID + ".png");
        return new Sprite(new TextureRegion(tex, tex.getWidth(), tex.getHeight()));
    }

    /**
     * Change the appearance of the Ball, by changing the ID of the current skin.
     *
     * @param skinID ID to update the current skin with.
     */
    public static void setCurrentSkin(int skinID) {
        currentSkinID = skinID;
    }
}
