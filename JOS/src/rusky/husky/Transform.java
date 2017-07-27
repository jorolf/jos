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
		if(startTime > time.getTime()) 
			startValue = getCurrentValue(object);
		
		if(startTime <= time.getTime() && time.getTime() <= endTime)
			apply(object, lerp(startValue, endValue, curve.apply((float) getProgress(time.getTime()))));
		else if(time.getTime() >= endTime && time.getTime() - time.getElapsedTime() <= endTime)
			apply(object, endValue);
		else if(time.getTime() <= startTime && time.getTime() - time.getElapsedTime() >= startTime)
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
