package rusky.husky.clocks;

import rusky.husky.Clock;

public class GameClock implements Clock {
	
	private final double startTime;
	private double lastTick;
	private double elapsedTime;
	private double time;
	
	public GameClock(){
		startTime = System.nanoTime() / 1000000d;
		lastTick = startTime;
	}
	
	@Override
	public double getTime() {
		return time;
	}

	@Override
	public void tick(double superTime) {
		elapsedTime = System.nanoTime() / 1000000d - lastTick;
		lastTick += elapsedTime;
		time = System.nanoTime() / 1000000d - startTime;
	}

	@Override
	public double getElapsedTime() {
		return elapsedTime;
	}
}
