package rusky.husky;

import java.util.function.Function;

public enum Easing implements Function<Float,Float>{
	Linear,
	QuadIn(time -> time * time),
	QuadOut(QuadIn, true),
	QuadInOut(QuadIn, QuadOut),
	CubicIn(time -> time*time*time),
	CubicOut(CubicIn, true),
	CubicInOut(CubicIn, CubicOut),
	SineIn(time -> (float)-Math.cos(time * (Math.PI/2)) + 1),
	SineOut(SineIn, true),
	SineInOut(SineIn, SineOut),
	QuartIn(time -> (float)Math.pow(time, 4)),
	QuartOut(QuartIn, true),
	QuartInOut(QuartIn, QuartOut),
	QuintIn(time -> (float)Math.pow(time, 5)),
	QuintOut(QuintIn, true),
	QuintInOut(QuintIn, QuintOut),
	ExpoIn(time -> (float)Math.pow(2, 10 * (time - 1))),
	ExpoOut(ExpoIn, true),
	ExpoInOut(ExpoIn, ExpoOut),
	CircIn(time -> -((float)Math.sqrt(1 - time * time) - 1)),
	CircOut(CircIn, true),
	CircInOut(CircIn, CircOut),
	ElasticIn(time -> elastic(0.3f, time)),
	ElasticOut(ElasticIn, true),
	ElasticInOut(ElasticIn, ElasticOut),
	ElasticInHalf(time -> elastic((float) Math.sqrt(0.3f), time)),
	ElasticOutHalf(ElasticInHalf, true),
	ElasticInOutHalf(ElasticInHalf, ElasticOutHalf),
	ElasticInQuarter(time -> elastic((float) Math.sqrt(Math.sqrt(0.3f)), time)),
	ElasticOutQuarter(ElasticInQuarter, true),
	ElasticInOutQuarter(ElasticInQuarter, ElasticOutQuarter),
	BounceOut(Easing::bounce),
	BounceIn(BounceOut, true),
	BounceInOut(BounceIn, BounceOut);


	private Easing(){
		this(time -> time);
	}

	private Easing(Function<Float, Float> in, Function<Float, Float> out){
		this.in = in;
		this.out = out;
	}

	private Easing(Function<Float, Float> func){
		this(func, false);
	}

	private Easing(Function<Float,Float> func, boolean reversed){
		this.in = func;
		this.reversed = reversed;
	}

	private Function<Float, Float> in, out;
	private boolean reversed;

	/**
	 * @param time from 0 to 1
	 * @return value from 0 to 1 
	 */
	@Override
	public Float apply(Float time){
		if(out == null) return reversed ? 1 - in.apply(1 - time) : in.apply(time);

		time *= 2;
		if(time < 1) return 0.5f * in.apply(time);
		time--;
		return 0.5f * (out.apply(time) + 1);
	}

	private static Float elastic(Float multiplier, Float time){
		return (float) (Math.pow(2,-10*time) * Math.sin((time-multiplier/4)*(2*Math.PI)/multiplier) + 1);
	}
	
	private static Float bounce(Float time){
		if ((time/=1) < (1/2.75f)) {
			return (7.5625f*time*time);
		} else if (time < (2/2.75f)) {
			return (7.5625f*(time-=(1.5f/2.75f))*time + .75f);
		} else if (time < (2.5/2.75)) {
			return (7.5625f*(time-=(2.25f/2.75f))*time + .9375f);
		} else {
			return (7.5625f*(time-=(2.625f/2.75f))*time + .984375f);
		}
	}
}
