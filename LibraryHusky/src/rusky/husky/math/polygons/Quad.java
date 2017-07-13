package rusky.husky.math.polygons;

import java.util.Arrays;
import java.util.List;

import rusky.husky.math.Pair;
import rusky.husky.math.Polygon;
import rusky.husky.math.Vector2;

public class Quad extends Polygon<Quad> {

	/**
	 * p1 = top left <br> p2 = top right <br> p3 = bottom left <br> p4 = bottom right
	 */
	public Vector2 p1, p2, p3, p4;

	public Quad(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
	}

	@Override
	public List<Vector2> getPoints() {
		return Arrays.asList(p1, p2, p3, p4);
	}

	@Override
	public boolean contains(Vector2 pos) {
		Pair<Vector2, Vector2> greatestDistance = getPoints().stream()
				   											 .flatMap(vec1 -> getPoints().stream()
				   													 					 .map(vec2 -> new Pair<Vector2, Vector2>(vec1, vec2)))
				   											 .max((pair1, pair2) -> pair1.getFirst().substract(pair1.getSecond()).sqrt() > pair2.getFirst().substract(pair2.getSecond()).sqrt() ? 1 : -1)
				   											 .get();
		Pair<Vector2, Vector2> other = getPoints().stream()
				   								  .flatMap(vec1 -> getPoints().stream()
				   													 		  .map(vec2 -> new Pair<Vector2, Vector2>(vec1, vec2)))
				   								  .filter(pair -> !greatestDistance.contains(pair.getFirst()) && !greatestDistance.contains(pair.getSecond()))
				   								  .findFirst()
				   								  .get();
		return new Triangle(greatestDistance.getFirst(), other.getFirst(), other.getSecond()).contains(pos) || new Triangle(greatestDistance.getSecond(), other.getFirst(), other.getSecond()).contains(pos);
	}

	public Vector2 map(Vector2 vec) {
		return map(vec.getX(), vec.getY());
	}

	private Vector2 map(float x, float y) {
		Vector2 top = p1.lerp(p2, x);
		Vector2 bottom = p3.lerp(p4, x);
		return top.lerp(bottom, y);
	}

	@Override
	public Quad fromPoints(List<Vector2> points) {
		return new Quad(points.get(0), points.get(1), points.get(2), points.get(3));
	}
}
