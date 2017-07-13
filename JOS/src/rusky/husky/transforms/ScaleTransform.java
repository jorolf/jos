package rusky.husky.transforms;

import rusky.husky.Easing;
import rusky.husky.Drawable;
import rusky.husky.math.Vector2;

public class ScaleTransform extends Vector2Transform<Drawable> {

	public ScaleTransform(Drawable drawable, Vector2 endValue, double duration, Easing curve) {
		super(drawable, endValue, duration, curve);
	}

	@Override
	protected Vector2 getCurrentValue(Drawable drawable) {
		return drawable.scale;
	}

	@Override
	public void apply(Drawable drawable, Vector2 value) {
		drawable.scale = value;
	}

}
