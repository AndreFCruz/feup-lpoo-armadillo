package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.lpoo.game.model.entities.ShapeModel;

/**
 * Created by andre on 20/05/2017.
 */

public class PlatformView extends ShapeView {

    private static Color platformColor = Color.BLACK;

    PlatformView() {
        setColor(platformColor);
    }

}
