package rusky.husky.containers;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import rusky.husky.Container;
import rusky.husky.Drawable;
import rusky.husky.math.polygons.RegularPolygon;

public class CircularContainer<T extends Drawable> extends Container<T>{

	public CircularContainer() {
		masked = true;
	}

	@Override
	protected Shape createClip() {
		return new Ellipse2D.Float(0, 0, getDrawSize().getX(), getDrawSize().getY());
	}
	
	@Override
	public RegularPolygon getBounds() {
		return new RegularPolygon(getDrawPosition(), getDrawSize(), (int)getDrawSize().sqrt());
	}
}
