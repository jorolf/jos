package rusky.husky;

public interface Clock {
	public double getTime();
	public void tick(double superTime);
	public double getElapsedTime();
}
