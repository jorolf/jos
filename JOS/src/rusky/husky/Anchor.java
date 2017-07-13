package rusky.husky;

import java.util.function.Function;

import rusky.husky.math.Vector2;

public enum Anchor implements Function<Vector2, Vector2>{
	TopLeft(0, 0),
	TopCenter(0.5f, 0),
	TopRight(1, 0),
	CenterLeft(0, 0.5f),
	Center(0.5f, 0.5f),
	CenterRight(1, 0.5f),
	BottomLeft(0, 1),
	BottomCenter(0.5f, 1),
	BottomRight(1, 1);
	
	private final float x, y;
	
	private Anchor(float x, float y){
		this.x = x;
		this.y = y;
	}

	@Override
	public Vector2 apply(Vector2 vec) {
		return vec.multiply(new Vector2(x, y));
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
