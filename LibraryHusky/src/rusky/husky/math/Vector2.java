package rusky.husky.math;

public class Vector2 implements Cloneable{
	public static final Vector2 UP = new FinalVector2(new Vector2(0, -1));
	public static final Vector2 DOWN = new FinalVector2(new Vector2(0, 1));
	public static final Vector2 LEFT = new FinalVector2(new Vector2(-1, 0));
	public static final Vector2 RIGHT = new FinalVector2(new Vector2(1, 0));
	public static final Vector2 ZERO = new FinalVector2(new Vector2(0));
	public static final Vector2 ONE = new FinalVector2(new Vector2(1));

	private float x, y;

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2(float i) {
		this(i, i);
	}

	public float getX() {
		return x;
	}

	public int getXInt() {
		return (int) x;
	}

	public Vector2 setX(float x) {
		this.x = x;
		return this;
	}

	public float getY() {
		return y;
	}

	public int getYInt() {
		return (int) y;
	}

	public Vector2 setY(float y) {
		this.y = y;
		return this;
	}

	public Vector2 setVec(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2 setVec(Vector2 vec) {
		this.x = vec.x;
		this.y = vec.y;
		return this;
	}

	public static Vector2 add(Vector2 summand1, Vector2 summand2) {
		return new Vector2(summand1.x + summand2.x, summand1.y + summand2.y);
	}

	public static Vector2 multiply(Vector2 factor1, Vector2 factor2) {
		return new Vector2(factor1.x * factor2.x, factor1.y * factor2.y);
	}

	public static Vector2 substract(Vector2 vec1, Vector2 vec2) {
		return new Vector2(vec1.x - vec2.x, vec1.y - vec2.y);
	}

	public static Vector2 divide(Vector2 vec1, Vector2 vec2) {
		return new Vector2(vec1.x / vec2.x, vec1.y / vec2.y);
	}

	public Vector2 add(Vector2 summand) {
		return new Vector2(this.x + summand.x, this.y + summand.y);
	}

	public Vector2 multiply(Vector2 factor) {
		return new Vector2(this.x * factor.x, this.y * factor.y);
	}

	public Vector2 substract(Vector2 vec) {
		return new Vector2(this.x - vec.x, this.y - vec.y);
	}

	public Vector2 divide(Vector2 vec) {
		return new Vector2(this.x / vec.x, this.y / vec.y);
	}

	public Vector2 center(Vector2 vec) {
		return new Vector2((this.x + vec.x) / 2, (this.y + vec.y) / 2);
	}

	public Vector2 add(float summand) {
		return add(new Vector2(summand, summand));
	}

	public Vector2 multiply(float factor) {
		return multiply(new Vector2(factor, factor));
	}

	public Vector2 substract(float num) {
		return substract(new Vector2(num, num));
	}

	public Vector2 divide(float num) {
		return divide(new Vector2(num, num));
	}

	@Override
	public String toString() {
		return "Vector2 [x=" + x + ", y=" + y + "]";
	}

	public Vector2 lerp(Vector2 other, float lerpAmt) {
		return new Vector2((other.x - x) * lerpAmt + x, (other.y - y) * lerpAmt + y);
	}

	@Override
	public Vector2 clone() {
		try {
			return (Vector2) super.clone();
		} catch (CloneNotSupportedException e) {
			return new Vector2(x, y);
		}
	}

	public float sqrt() {
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2 other = (Vector2) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}
	
	public Vector2 componentMax(Vector2 other){
		return new Vector2(x > other.x ? x : other.x, y > other.y ? y : other.y);
	}
	
	public Vector2 componentMin(Vector2 other){
		return new Vector2(x < other.x ? x : other.x, y < other.y ? y : other.y);
	}
	
	public Vector2 componentTrim(Vector2 min, Vector2 max){
		return componentMin(max).componentMax(min);
	}

	public static final class FinalVector2 extends Vector2 {

		public FinalVector2(Vector2 vec) {
			super(vec.x, vec.y);
		}

		@Override
		public Vector2 setX(float x) {
			throw new UnsupportedOperationException("You cannot set X when the vector is final");
		}

		@Override
		public Vector2 setY(float y) {
			throw new UnsupportedOperationException("You cannot set Y when the vector is final");
		}

		@Override
		public Vector2 setVec(float x, float y) {
			throw new UnsupportedOperationException("You cannot change the vector when it's final");
		}

		@Override
		public Vector2 setVec(Vector2 vec) {
			throw new UnsupportedOperationException("You cannot change the vector when it's final");
		}
		
		@Override
		public Vector2 clone() {
			return new Vector2(getX(), getY());
		}
	}
}
