package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.lpoo.game.Spheral;

import java.util.Random;

/**
 * Created by andre on 10/05/2017.
 */
class BallView extends EntityView {

    BallView(Spheral game) {
        super(game);
    }

    @Override
    protected Sprite createSprite(Spheral game) {
        Random rand = new Random();
        return createSprite(game, rand.nextInt(6));
    }

    protected Sprite createSprite(Spheral game, int skinID) {
        Texture tex = game.getAssetManager().get("skins/skin0" + skinID + ".png");
        return new Sprite(new TextureRegion(tex, tex.getWidth(), tex.getHeight()));
    }

}
