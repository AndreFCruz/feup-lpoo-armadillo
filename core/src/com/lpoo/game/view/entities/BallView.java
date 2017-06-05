package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lpoo.game.Spheral;

/**
 * Created by andre on 10/05/2017.
 */
public class BallView extends EntityView {

    private static int currentSkinID = 0;

    BallView(Spheral game) {
        super(game);
    }

    @Override
    protected Sprite createSprite(Spheral game) {
        Texture tex = game.getAssetManager().get("skins/skin0" + currentSkinID + ".png");
        return new Sprite(new TextureRegion(tex, tex.getWidth(), tex.getHeight()));
    }

    public static void setCurrentSkin(int skinID) {
        currentSkinID = skinID;
    }
}
