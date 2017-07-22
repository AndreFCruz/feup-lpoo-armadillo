/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Leakedbits
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.lpoo.game.model.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for handling the intersection of Polygons.
 */
public class PolygonIntersector {

    /* A close to zero float epsilon value */
    private static final float EPSILON = 1.1920928955078125E-7f;

    /**
     * Informs if a given point is inside a given edge.
     *
     * @param point          The point that will be analyzed.
     * @param edgeStartPoint The Edge's starting point.
     * @param edgeEndPoint   The Edge's ending point.
     * @return True, if the point is inside the edge, false otherwise.
     */
    private static boolean isPointInsideEdge(Vector2 point,
                                             Vector2 edgeStartPoint, Vector2 edgeEndPoint) {
        return (edgeEndPoint.x - edgeStartPoint.x)
                * (point.y - edgeStartPoint.y) > (edgeEndPoint.y - edgeStartPoint.y)
                * (point.x - edgeStartPoint.x);
    }

    /**
     * Finds the point were two Edges intersect.
     *
     * @param firstEdgeStartPoint  The first edge's starting point.
     * @param firstEdgeEndPoint    The first edge's ending point.
     * @param secondEdgeStartPoint The second edge's starting point.
     * @param secondEdgeEndPoint   The second edge's ending point.
     * @return The point were the two Edges intersect.
     */
    private static Vector2 getEdgesIntersection(Vector2 firstEdgeStartPoint,
                                                Vector2 firstEdgeEndPoint, Vector2 secondEdgeStartPoint,
                                                Vector2 secondEdgeEndPoint) {

        Vector2 firstDirectionPoint = new Vector2(firstEdgeStartPoint.x
                - firstEdgeEndPoint.x, firstEdgeStartPoint.y
                - firstEdgeEndPoint.y);
        Vector2 secondDirectionPoint = new Vector2(secondEdgeStartPoint.x
                - secondEdgeEndPoint.x, secondEdgeStartPoint.y
                - secondEdgeEndPoint.y);

		/* Cross product of each edge */
        float crossFirstEdge = firstEdgeStartPoint.crs(firstEdgeEndPoint);
        float crossSecondEdge = secondEdgeStartPoint.crs(secondEdgeEndPoint);

        float inversedCrossDirection = 1 / firstDirectionPoint
                .crs(secondDirectionPoint);

        return new Vector2(
                (crossFirstEdge * secondDirectionPoint.x - crossSecondEdge
                        * firstDirectionPoint.x)
                        * inversedCrossDirection, (crossFirstEdge
                * secondDirectionPoint.y - crossSecondEdge
                * firstDirectionPoint.y)
                * inversedCrossDirection);
    }

    /**
     * It clips a Polygon to a given subjection Polygon.
     *
     * @param subjectPolygon The subjection Polygon.
     * @param clipPolygon    The Polygon to be clipped.
     * @return A list containing the vertices of the clipped Polygon.
     */
    public static List<Vector2> clipPolygons(List<Vector2> subjectPolygon,
                                             List<Vector2> clipPolygon) {
        List<Vector2> clippedPolygonVertices = new ArrayList<Vector2>(
                subjectPolygon);

        Vector2 clipEdgeStartPoint = clipPolygon.get(clipPolygon.size() - 1);

        for (Vector2 clipEdgeEndPoint : clipPolygon) {
            if (clippedPolygonVertices.isEmpty()) {
                break;
            }

            List<Vector2> inputList = new ArrayList<Vector2>(
                    clippedPolygonVertices);
            clippedPolygonVertices.clear();

            Vector2 testEdgeStartPoint = inputList.get(inputList.size() - 1);
            for (Vector2 testEdgeEndPoint : inputList) {
                if (isPointInsideEdge(testEdgeEndPoint, clipEdgeStartPoint,
                        clipEdgeEndPoint)) {
                    if (!isPointInsideEdge(testEdgeStartPoint,
                            clipEdgeStartPoint, clipEdgeEndPoint)) {
                        clippedPolygonVertices.add(getEdgesIntersection(
                                clipEdgeStartPoint, clipEdgeEndPoint,
                                testEdgeStartPoint, testEdgeEndPoint));
                    }

                    clippedPolygonVertices.add(testEdgeEndPoint);
                } else if (isPointInsideEdge(testEdgeStartPoint,
                        clipEdgeStartPoint, clipEdgeEndPoint)) {
                    clippedPolygonVertices.add(getEdgesIntersection(
                            clipEdgeStartPoint, clipEdgeEndPoint,
                            testEdgeStartPoint, testEdgeEndPoint));
                }

                testEdgeStartPoint = testEdgeEndPoint;
            }

            clipEdgeStartPoint = clipEdgeEndPoint;
        }

        return clippedPolygonVertices;
    }

    /**
     * Computes the Polygon Properties of a given Polygon.
     *
     * @param polygon The polygon to be analyzed.
     * @return The Polygon Properties computed.
     */
    public static PolygonProperties computePolygonProperties(Vector2[] polygon) {
        PolygonProperties polygonProperties = null;

        int count = polygon.length;

        if (count >= 3) {
            Vector2 centroid = new Vector2(0, 0);
            float area = 0;

            Vector2 refPoint = new Vector2(0, 0);
            float threeInverse = 1 / 3f;

            for (int i = 0; i < count; i++) {
                /*
				 * Create a new vector to represent the reference point for
				 * forming triangles. Then use refPoint, polygonVertex and
				 * thirdTriangleVertex as vertices of a triangle.
				 */
                refPoint.set(0, 0);
                Vector2 polygonVertex = polygon[i];
                Vector2 thirdTriangleVertex = i + 1 < count ? polygon[i + 1]
                        : polygon[0];

                Vector2 firstDirectionVector = polygonVertex.sub(refPoint);
                Vector2 secondDirectionVector = thirdTriangleVertex
                        .sub(refPoint);

                float triangleArea = firstDirectionVector
                        .crs(secondDirectionVector) / 2;
                area += triangleArea;

				/* Area weighted centroid */
                centroid.add(refPoint.add(polygonVertex)
                        .add(thirdTriangleVertex)
                        .scl(triangleArea * threeInverse));
            }

            if (area > EPSILON) {
                centroid.scl(1 / area);
            } else {
                area = 0;
            }

            polygonProperties = new PolygonProperties(centroid, area);
        }

        return polygonProperties;
    }

}
