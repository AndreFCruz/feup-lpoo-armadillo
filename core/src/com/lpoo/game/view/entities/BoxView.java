package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lpoo.game.Spheral;

/**
 * Created by andre on 26/05/2017.
 */

public class BoxView extends EntityView {

    BoxView(Spheral game) {
        super(game);
    }

    @Override
    protected Sprite createSprite(Spheral game) {
        Texture tex = game.getAssetManager().get("box.png");
        return new Sprite(new TextureRegion(tex, tex.getWidth(), tex.getHeight()));
    }

}
