package rusky.husky.math.polygons;

import java.util.ArrayList;
import java.util.List;

import rusky.husky.math.Polygon;
import rusky.husky.math.Vector2;

public class RegularPolygon extends Polygon<RegularPolygon> {

	public List<Vector2> points = new ArrayList<>();

	public RegularPolygon(Vector2 position, Vector2 size, int points) {
		double theta = 2 * Math.PI / points;
		for (int i = 0; i < points; ++i) {
			this.points.add(new Vector2((float) Math.cos(theta * i), (float) Math.sin(theta * i)));
		}
	}

	private RegularPolygon(List<Vector2> points) {
		this.points = points;
	}

	@Override
	public List<Vector2> getPoints() {
		return points;
	}

	@Override
	public RegularPolygon fromPoints(List<Vector2> points) {
		return new RegularPolygon(points);
	}

	@Override
	public boolean contains(Vector2 pos) {
		return getBounds().getCenter().substract(pos).divide(getBounds().getSize().divide(2)).sqrt() <= 1;
	}

}
