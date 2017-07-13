package rusky.husky.math.polygons;

import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

import rusky.husky.math.Polygon;
import rusky.husky.math.Vector2;

public class Rectangle extends Polygon<Rectangle> {

	public float x, y, width, height;

	public Rectangle(float width, float height) {
		this(0, 0, width, height);
	}

	public Rectangle(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Rectangle(Vector2 size) {
		this(size.getX(), size.getY());
	}

	public Rectangle(Vector2 position, Vector2 size) {
		this(position.getX(), position.getY(), size.getX(), size.getY());
	}

	public Vector2 getPosition() {
		return new Vector2(x, y);
	}

	public Vector2 getSize() {
		return new Vector2(width, height);
	}

	@Override
	public float getBottom() {
		return y + height;
	}

	@Override
	public float getRight() {
		return x + width;
	}

	public Vector2 getBottomLeft() {
		return new Vector2(x, y + height);
	}

	public Vector2 getBottomRight() {
		return getPosition().add(getSize());
	}

	public Vector2 getTopRight() {
		return new Vector2(x + width, y);
	}

	@Override
	public boolean contains(Vector2 pos) {
		return pos.getX() >= x && pos.getX() <= getRight() && pos.getY() >= y && pos.getY() <= getBottom();
	}

	public Vector2 getCenter() {
		return getPosition().center(getBottomRight());
	}

	@Override
	public String toString() {
		return "Rectangle [position= " + getPosition() + ", size= " + getSize() + "]";
	}

	@Override
	public List<Vector2> getPoints() {
		return Arrays.asList(getPosition(), getTopRight(), getBottomLeft(), getBottomRight());
	}

	@Override
	public Rectangle fromPoints(List<Vector2> points) {
		return new Rectangle(points.get(0), points.get(3));
	}
	
	public Rectangle2D getNative(){
		return new Rectangle2D.Float(x, y, width, height);
	}
}
