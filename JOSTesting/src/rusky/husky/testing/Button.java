package rusky.husky.testing;

import java.awt.Color;
import java.awt.Paint;
import java.util.EnumSet;

import rusky.husky.Anchor;
import rusky.husky.Axes;
import rusky.husky.Container;
import rusky.husky.Easing;
import rusky.husky.Drawable;
import rusky.husky.HasAccentColor;
import rusky.husky.InputState;
import rusky.husky.sprites.Box;
import rusky.husky.sprites.SpriteText;

public class Button extends Container<Drawable> implements HasAccentColor {

	private final SpriteText text;
	private final Box background;

	public Runnable action;

	public Button(String buttonText, Runnable action) {
		text = new SpriteText(buttonText);
		text.setRelativeSizeAxes(EnumSet.of(Axes.Y));
		text.setHeight(0.8f);
		text.anchor = Anchor.Center;
		text.origin = Anchor.Center;

		background = new Box();
		background.setRelativeSizeAxes(Axes.both());
		background.setColor(Color.BLACK);
		add(background);
		add(text);

		this.action = action;
	}

	@Override
	protected boolean onClick(InputState state) {
		if(action != null)
			action.run();
		return true;
	}

	public void setButtonText(String buttonText) {
		text.setText(buttonText);
	}

	public String getButtonText() {
		return text.getText();
	}

	public void setTextColor(Paint color) {
		text.setColor(color);
	}

	public Paint getTextColor() {
		return text.getColor();
	}

	@Override
	public void setAccentColor(Paint color) {
		background.setColor(color);
	}

	@Override
	public Paint getAccentColor() {
		return background.getColor();
	}
	
	@Override
	protected boolean onHover(InputState state) {
		fadeAccent(Color.GRAY, 200, Easing.QuadOut);
		return true;
	}
	
	@Override
	protected void onHoverLost(InputState state) {
		fadeAccent(Color.BLACK, 200, Easing.QuadOut);
	}
}
