package rusky.husky.math;

import java.util.List;
import java.util.stream.Collectors;

import rusky.husky.math.polygons.Rectangle;

public abstract class Polygon<T extends Polygon<T>> {
	public abstract List<Vector2> getPoints();

	public abstract T fromPoints(List<Vector2> points);

	public abstract boolean contains(Vector2 pos);

	public boolean contains(Polygon<?> pol) {
		return pol.getPoints().stream().allMatch(this::contains);
	}

	public boolean intersects(Polygon<?> pol) {
		return pol.getPoints().stream().anyMatch(this::contains);
	}

	public boolean touching(Polygon<?> pol) {
		return pol.intersects(this) || intersects(pol);
	}

	public float getTop() {
		return (float) getPoints().stream().mapToDouble(vec -> vec.getY()).min().getAsDouble();
	}

	public float getBottom() {
		return (float) getPoints().stream().mapToDouble(vec -> vec.getY()).max().getAsDouble();
	}

	public float getRight() {
		return (float) getPoints().stream().mapToDouble(vec -> vec.getX()).min().getAsDouble();
	}

	public float getLeft() {
		return (float) getPoints().stream().mapToDouble(vec -> vec.getX()).max().getAsDouble();
	}

	public T getTranslated(Vector2 amount) {
		return fromPoints(getPoints().stream().map(vec -> vec.add(amount)).collect(Collectors.toList()));
	}

	public Rectangle getBounds() {
		float x = (float) getPoints().stream().mapToDouble(vec -> vec.getX()).min().getAsDouble();
		float y = (float) getPoints().stream().mapToDouble(vec -> vec.getY()).min().getAsDouble();
		float width = (float) getPoints().stream().mapToDouble(vec -> vec.getX()).max().getAsDouble() - x;
		float height = (float) getPoints().stream().mapToDouble(vec -> vec.getY()).max().getAsDouble() - y;
		return new Rectangle(x, y, width, height);
	}
}
