package rusky.husky.clocks;

import rusky.husky.Clock;

public class OffsetClock implements Clock {

	public double offset;
	private double time;
	private double lastTick;
	private double elapsedTime;
	
	public OffsetClock(double offset){
		this.offset = offset;
	}
	
	@Override
	public double getTime() {
		return time;
	}

	@Override
	public void tick(double superTime) {
		elapsedTime = superTime - lastTick;
		lastTick += elapsedTime;
		time = superTime + offset;
	}

	@Override
	public double getElapsedTime() {
		return elapsedTime;
	}
}
