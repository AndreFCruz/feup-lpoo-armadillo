package com.lpoo.game.model.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

// FUTURE
// Substitute with LibGDX's Intersector ?
public class PolygonIntersector {

	/* A close to zero float epsilon value */
	public static final float EPSILON = 1.1920928955078125E-7f;

	/**
	 * Returns true if the given point is inside the segment.
	 * 
	 * @param point The point to check
	 * @param start Segment's first point
	 * @param end Segment's last point
	 * @return Whether the point is inside the segment
	 */
	public static boolean isPointInLineSegment(Vector2 point, Vector2 start,
			Vector2 end) {
		return isPointInLineSegment(point.x, point.y, start.x, start.y, end.x, end.y);
	}

	/**
	 * Returns true if the given point is inside the segment.
	 * 
	 * @param pointX X coordinate of the point
	 * @param pointY Y coordinate of the point
	 * @param startX X coordinate of the segment's first point
	 * @param startY Y coordinate of the segment's first point
	 * @param endX X coordinate of the segment's last point
	 * @param endY Y coordinate of the segment's last point
	 * @return Whether the point is inside the segment
	 */
	public static boolean isPointInLineSegment(float pointX, float pointY,
			float startX, float startY, float endX, float endY) {
		return (endX - startX) * (pointY - startY) > (endY - startY)
				* (pointX - startX);
	}

//	@Deprecated
	public static boolean isPointInsideEdge(Vector2 point,
			Vector2 edgeStartPoint, Vector2 edgeEndPoint) {
		return (edgeEndPoint.x - edgeStartPoint.x)
				* (point.y - edgeStartPoint.y) > (edgeEndPoint.y - edgeStartPoint.y)
				* (point.x - edgeStartPoint.x);
	}

//	@Deprecated
	public static Vector2 getEdgesIntersection(Vector2 firstEdgeStartPoint,
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

	public static Polygon intersectPolygons(Polygon subjectPolygon, Polygon clipPolygon) {
		return intersectPolygons(subjectPolygon.getVertices(), clipPolygon.getVertices());
	}
	
	public static Polygon intersectPolygons(float[] subjectVertices, float[] clipVertices) {
		return null;
	}
	
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
