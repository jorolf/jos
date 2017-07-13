package rusky.husky.testing.testCases;

import java.awt.Color;

import rusky.husky.Anchor;
import rusky.husky.Axes;
import rusky.husky.Container;
import rusky.husky.Drawable;
import rusky.husky.containers.CircularContainer;
import rusky.husky.math.Vector2;
import rusky.husky.sprites.Box;
import rusky.husky.sprites.SpriteText;
import rusky.husky.testing.TestCase;

public class TestCaseMasking extends TestCase {

	@Override
	public String getDescription() {
		return "masquerade";
	}

	@Override
	protected void internalReset() {
		Container<Drawable> topLeft = new Container<>();
		topLeft.setSize(new Vector2(100));
		topLeft.masked = true;
		
		SpriteText text = new SpriteText("this text is too long");
		text.setHeight(15);
		text.setColor(Color.black);
		topLeft.add(text);
		add(topLeft);

		CircularContainer<Drawable> bottomLeft = new CircularContainer<>();
		bottomLeft.anchor = Anchor.BottomLeft;
		bottomLeft.origin = Anchor.BottomLeft;
		bottomLeft.setSize(new Vector2(100));
		
		Box box1 = new Box();
		box1.setRelativeSizeAxes(Axes.both());
		box1.setColor(Color.black);
		bottomLeft.add(box1);
		add(bottomLeft);

		Container<Drawable> topRight = new Container<>();
		topRight.masked = true;
		topRight.cornerRadius = 15;
		topRight.anchor = Anchor.TopRight;
		topRight.origin = Anchor.TopRight;
		topRight.setSize(new Vector2(100));
		
		Box box2 = new Box();
		box2.setRelativeSizeAxes(Axes.both());
		box2.setColor(Color.black);
		topRight.add(box2);
		add(topRight);
	}

}
