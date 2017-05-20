package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.lpoo.game.model.entities.ShapeModel;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * Created by andre on 20/05/2017.
 */

public class WaterView extends ShapeView {

    private static Color waterColor = new Color(0.2f, 0.2f, 0.8f, 0.5f);

    WaterView() {
        setColor(waterColor);
    }

}
