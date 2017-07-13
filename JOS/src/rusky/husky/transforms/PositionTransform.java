package rusky.husky.transforms;

import rusky.husky.Easing;
import rusky.husky.Drawable;
import rusky.husky.math.Vector2;

public class PositionTransform extends Vector2Transform<Drawable> {

	public PositionTransform(Drawable drawable, Vector2 endValue, double duration, Easing curve) {
		super(drawable, endValue, duration, curve);
	}

	@Override
	protected Vector2 getCurrentValue(Drawable drawable) {
		return drawable.getPosition();
	}

	@Override
	public void apply(Drawable drawable, Vector2 value) {
		drawable.setPosition(value);
	}

}
