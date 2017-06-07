package com.lpoo.game.view.entities;

import com.badlogic.gdx.graphics.Color;

/**
 * A class used to represent the Platform's shape view.
 */
class PlatformView extends ShapeView {

    /**
     * Platform's draw Color.
     */
    private static Color platformColor = Color.DARK_GRAY;

    /**
     * Platform's View Constructor.
     * It defines in which color the platform shape will be drawn.
     */
    PlatformView() {
        setColor(platformColor);
    }
}
