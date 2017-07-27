package rusky.husky;

import java.awt.Color;

import rusky.husky.math.Vector2;
import rusky.husky.transforms.AlphaTransform;
import rusky.husky.transforms.ColorTransform;
import rusky.husky.transforms.PositionTransform;
import rusky.husky.transforms.ScaleTransform;
import rusky.husky.transforms.SizeTransform;

public class TransformSequence {
	private final Drawable drawable;
	private double maxEndTime, delay;
	
	public TransformSequence(Drawable drawable) {
		this.drawable = drawable;
	}
	
	public TransformSequence resizeTo(Vector2 to, double duration, Easing curve) {
		drawable.transformTo(new SizeTransform(drawable, to, drawable.getClock().getTime() + duration + delay, curve), delay);
		maxEndTime = Math.max(maxEndTime, duration + delay);
		return this;
	}

	public TransformSequence scaleTo(Vector2 to, double duration, Easing curve) {
		drawable.transformTo(new ScaleTransform(drawable, to, drawable.getClock().getTime() + duration + delay, curve), delay);
		maxEndTime = Math.max(maxEndTime, duration + delay);
		return this;
	}

	public TransformSequence moveTo(Vector2 to, double duration, Easing curve) {
		drawable.transformTo(new PositionTransform(drawable, to, drawable.getClock().getTime() + duration + delay, curve), delay);
		maxEndTime = Math.max(maxEndTime, duration + delay);
		return this;
	}

	public TransformSequence fadeTo(Color to, double duration, Easing curve) {
		drawable.transformTo(new ColorTransform(drawable, to, drawable.getClock().getTime() + duration + delay, curve), delay);
		maxEndTime = Math.max(maxEndTime, duration + delay);
		return this;
	}
	
	public TransformSequence fadeTo(float to, double duration, Easing curve){
		drawable.transformTo(new AlphaTransform(drawable, to, drawable.getClock().getTime() + duration + delay, curve), delay);
		maxEndTime = Math.max(maxEndTime, duration + delay);
		return this;
	}
	
	public TransformSequence delay(double duration) {
		delay += duration;
		return this;
	}
	
	public TransformSequence then() {
		delay = Math.max(delay, maxEndTime);
		return this;
	}
}
