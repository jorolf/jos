package rusky.husky;

import java.awt.Color;
import java.awt.Paint;

import rusky.husky.math.MathHelper;

public interface HasAccentColor extends IDrawable {
	Paint getAccentColor();
	void setAccentColor(Paint color);
	
	default void fadeAccent(Color to, double duration, Easing curve) {
		transformTo(new AccentColorTransform(this, to, getClock().getTime() + duration, curve));
	}
	
	class AccentColorTransform extends Transform<Color, HasAccentColor> {
		public AccentColorTransform(HasAccentColor object, Color endValue, double endTime, Easing curve) {
			super(object, endValue, endTime, curve);
		}

		@Override
		protected Color getCurrentValue(HasAccentColor object) {
			return (Color) object.getAccentColor();
		}

		@Override
		protected Color lerp(Color start, Color end, float lerp) {
			return new Color((int)MathHelper.lerp(start.getRed(), end.getRed(), lerp), (int)MathHelper.lerp(start.getGreen(), end.getGreen(), lerp), (int)MathHelper.lerp(start.getBlue(), end.getBlue(), lerp), (int)MathHelper.lerp(start.getAlpha(), end.getAlpha(), lerp));
		}

		@Override
		public void apply(HasAccentColor object, Color value) {
			object.setAccentColor(value);
		}

	}
}
