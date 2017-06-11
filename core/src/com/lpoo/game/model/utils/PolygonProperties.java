package com.lpoo.game.model.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Class responsible for handling Polygon Properties.
 */
public class PolygonProperties {

    /**
     * Polygon's centroid.
     */
    private Vector2 centroid;

    /**
     * Polygon's area.
     */
    private float area;

    /**
     * PolygonProperties' constructor.
     * It sets the polygon's area and centroid.
     *
     * @param centroid The Polygon's centroid.
     * @param area     The Polygon's area.
     */
    public PolygonProperties(Vector2 centroid, float area) {
        this.centroid = centroid;
        this.area = area;
    }

    /**
     * @return the centroid of the Polygon.
     */
    public Vector2 getCentroid() {
        return centroid;
    }

    /**
     * @return the area of the Polygon.
     */
    public float getArea() {
        return area;
    }
}
