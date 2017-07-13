package rusky.husky.transforms;

import rusky.husky.Easing;
import rusky.husky.Transform;

public abstract class FloatTransform<T> extends Transform<Float, T> {

	public FloatTransform(T object, Float endValue, double duration, Easing curve) {
		super(object, endValue, duration, curve);
	}

	@Override
	protected Float lerp(Float start, Float end, float lerp) {
		return (end-start)*lerp + start;
	}

}
