package rusky.husky;

import rusky.husky.math.MathHelper;

public abstract class Transform<TValue, TApplicant> {

	public double endTime, startTime;

	public TValue startValue, endValue;

	private Easing curve;

	private TApplicant object;

	public Transform(TApplicant object, TValue endValue, double endTime, Easing curve) {
		this.object = object;
		this.startValue = getCurrentValue(object);
		this.endValue = endValue;
		this.endTime = endTime;
		this.curve = curve;
	}

	protected abstract TValue getCurrentValue(TApplicant object);

	public void update(Clock time) {
		if(getProgress(time.getTime()) >= 0 && getProgress(time.getTime()) <= 1)
			apply(object, lerp(startValue, endValue, curve.apply((float) getProgress(time.getTime()))));
		else if(getProgress(time.getTime()) >= 1 && getProgress(time.getTime() - time.getElapsedTime()) <= 1)
			apply(object, endValue);
		else if(getProgress(time.getTime()) <= 0 && getProgress(time.getTime() - time.getElapsedTime()) >= 0)
			apply(object, startValue);
	}

	protected abstract TValue lerp(TValue start, TValue end, float lerp);

	public double getDuration() {
		return endTime - startTime;
	}
	
	public double getProgress(double time){
		return MathHelper.map(time, startTime, endTime, 0, 1);
	}

	public abstract void apply(TApplicant object, TValue value);

	public boolean isFinished(double time) {
		if(time >= endTime){
			apply(object, endValue);
			return true;
		}else
			return false;
	}
}
