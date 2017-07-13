package rusky.husky.sprites;

public class SpriteNumber extends SpriteText {
	
	public SpriteNumber(float value) {
		setValue(value);
	}	
	
	public SpriteNumber() {
	}

	public float getValue() {
		return Float.parseFloat(getText());
	}

	public void setValue(float value) {
		setText(Float.toString(value));
	}
}
