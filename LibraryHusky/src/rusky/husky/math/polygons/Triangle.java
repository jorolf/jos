package rusky.husky.math.polygons;

import java.util.Arrays;
import java.util.List;

import rusky.husky.math.Polygon;
import rusky.husky.math.Vector2;

public class Triangle extends Polygon<Triangle> {
	public Vector2 p1, p2, p3;

	public Triangle(Vector2 p1, Vector2 p2, Vector2 p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	public Triangle getCopy() {
		Triangle tri = new Triangle(p1.clone(), p2.clone(), p3.clone());
		return tri;
	}

	@Override
	public boolean contains(Vector2 pos) {
		Triangle tri1 = new Triangle(p1, p2, pos);
		Triangle tri2 = new Triangle(p1, p3, pos);
		Triangle tri3 = new Triangle(p3, p2, pos);
		return Math.abs((tri1.getArea() + tri2.getArea() + tri3.getArea()) - getArea()) > .000001;
	}

	public float getArea() {
		return p1.substract(p2).sqrt() * p1.substract(p3).sqrt();
	}

	@Override
	public List<Vector2> getPoints() {
		return Arrays.asList(p1, p2, p3);
	}

	@Override
	public Triangle fromPoints(List<Vector2> points) {
		return new Triangle(points.get(0), points.get(1), points.get(2));
	}
}
