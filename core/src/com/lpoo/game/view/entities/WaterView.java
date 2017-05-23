package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by andre on 20/05/2017.
 */

public class WaterView extends ShapeView {

    private static Color waterColor = new Color(0.2f, 0.2f, 0.8f, 0.2f);

    WaterView() {
        setColor(waterColor);
    }

}
