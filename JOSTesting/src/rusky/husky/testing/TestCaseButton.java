package rusky.husky.testing;

import java.awt.Color;

import rusky.husky.Anchor;
import rusky.husky.Axes;
import rusky.husky.Container;
import rusky.husky.Drawable;
import rusky.husky.Easing;
import rusky.husky.InputState;
import rusky.husky.sprites.Box;
import rusky.husky.sprites.SpriteText;

public class TestCaseButton extends Container<Drawable>{
	
	public Runnable action;
	
	private final Box background;
	private final SpriteText text;
	
	public TestCaseButton(String buttonText, String description, Runnable action) {
		text = new SpriteText(buttonText);
		text.setHeight(15);
		text.anchor = Anchor.TopCenter;
		text.origin = Anchor.TopCenter;
		
		SpriteText descriptionText = new SpriteText(description);
		descriptionText.setHeight(11);
		descriptionText.anchor = Anchor.BottomCenter;
		descriptionText.origin = Anchor.BottomCenter;
		descriptionText.setY(-2f);

		background = new Box();
		background.setRelativeSizeAxes(Axes.both());
		background.setColor(Color.BLACK);
		add(background);
		add(text);
		add(descriptionText);

		this.action = action;
	}

	public void setButtonText(String buttonText) {
		text.setText(buttonText);
	}

	public String getButtonText() {
		return text.getText();
	}

	@Override
	protected boolean onClick(InputState state) {
		if(action != null)
			action.run();
		return true;
	}
	
	@Override
	protected boolean onHover(InputState state) {
		background.fadeTo(Color.GRAY, 200, Easing.QuadOut);
		return true;
	}
	
	@Override
	protected void onHoverLost(InputState state) {
		background.fadeTo(Color.BLACK, 200, Easing.QuadOut);
	}
}
