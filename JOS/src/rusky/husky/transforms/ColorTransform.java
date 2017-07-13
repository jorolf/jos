package rusky.husky.transforms;

import java.awt.Color;

import rusky.husky.Easing;
import rusky.husky.Drawable;
import rusky.husky.Transform;
import rusky.husky.math.MathHelper;

public class ColorTransform extends Transform<Color, Drawable> {

	public ColorTransform(Drawable drawable, Color endValue, double duration, Easing curve) {
		super(drawable, endValue, duration, curve);
	}

	@Override
	protected Color getCurrentValue(Drawable drawable) {
		return (Color) drawable.getColor();
	}

	@Override
	protected Color lerp(Color start, Color end, float lerp) {
		return new Color((int)MathHelper.lerp(start.getRed(), end.getRed(), lerp), (int)MathHelper.lerp(start.getGreen(), end.getGreen(), lerp), (int)MathHelper.lerp(start.getBlue(), end.getBlue(), lerp), (int)MathHelper.lerp(start.getAlpha(), end.getAlpha(), lerp));
	}

	@Override
	public void apply(Drawable drawable, Color value) {
		drawable.setColor(value);
	}

}
