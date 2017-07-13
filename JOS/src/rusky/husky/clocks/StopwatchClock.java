package rusky.husky.clocks;

import rusky.husky.Clock;

public class StopwatchClock implements Clock {

	public double rate;
	private double time;
	private double elapsedTime;
	private double lastTick;
	
	public StopwatchClock(double rate){
		this.rate = rate;
	}
	
	@Override
	public double getTime() {
		return time;
	}

	@Override
	public void tick(double superTime) {
		elapsedTime = (superTime - lastTick) * rate;
		time += elapsedTime;
		lastTick = superTime;
	}

	@Override
	public double getElapsedTime() {
		return elapsedTime;
	}
}
