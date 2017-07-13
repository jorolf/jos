package rusky.husky.transforms;

import rusky.husky.Easing;
import rusky.husky.Drawable;

public class AlphaTransform extends FloatTransform<Drawable> {

	public AlphaTransform(Drawable object, Float endValue, double duration, Easing curve) {
		super(object, endValue, duration, curve);
	}

	@Override
	protected Float getCurrentValue(Drawable object) {
		return object.getAlpha();
	}

	@Override
	public void apply(Drawable object, Float value) {
		object.setAlpha(value);
	}

}
