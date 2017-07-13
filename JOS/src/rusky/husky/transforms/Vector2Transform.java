package rusky.husky.transforms;

import rusky.husky.Easing;
import rusky.husky.Transform;
import rusky.husky.math.Vector2;

public abstract class Vector2Transform<T> extends Transform<Vector2, T> {
	
	public Vector2Transform(T drawable, Vector2 endValue, double duration, Easing curve) {
		super(drawable, endValue, duration, curve);
	}

	@Override
	protected final Vector2 lerp(Vector2 start, Vector2 end, float lerp) {
		return start.lerp(end, lerp);
	}
}
