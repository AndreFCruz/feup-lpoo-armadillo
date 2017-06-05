package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lpoo.game.Spheral;

import java.util.Random;

/**
 * Created by andre on 10/05/2017.
 */
public class BallView extends EntityView {

    private static int current_skin_ID = 0;

    BallView(Spheral game) {
        super(game);
    }

    @Override
    protected Sprite createSprite(Spheral game) {
        Texture tex = game.getAssetManager().get("skins/skin0" + current_skin_ID + ".png");
        return new Sprite(new TextureRegion(tex, tex.getWidth(), tex.getHeight()));
    }

    public static void setCurrentSkin(int skin_ID) {
        current_skin_ID = skin_ID;
    }
}
