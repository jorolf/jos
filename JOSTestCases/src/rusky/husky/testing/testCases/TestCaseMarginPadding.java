package rusky.husky.testing.testCases;

import java.awt.Color;

import rusky.husky.Anchor;
import rusky.husky.Axes;
import rusky.husky.Container;
import rusky.husky.Drawable;
import rusky.husky.MarginPadding;
import rusky.husky.math.Vector2;
import rusky.husky.sprites.Box;
import rusky.husky.testing.TestCase;

public class TestCaseMarginPadding extends TestCase {

	@Override
	public String getDescription() {
		return "Mr. Paddington";
	}
	
	@Override
	public void internalReset() {
		Box outerBox = new Box();
		outerBox.setSize(new Vector2(250));
		outerBox.origin = Anchor.Center;
		outerBox.anchor = Anchor.Center;
		outerBox.setColor(Color.BLACK);
		add(outerBox);
		
		Container<Drawable> outer = new Container<Drawable>();
		outer.setSize(new Vector2(250));
		outer.origin = Anchor.Center;
		outer.anchor = Anchor.Center;
		outer.setPadding(new MarginPadding(50));
		add(outer);
		
		Box middleBox = new Box();
		middleBox.setRelativeSizeAxes(Axes.both());
		middleBox.setColor(Color.RED);
		outer.add(middleBox);
		
		Container<Drawable> middle = new Container<Drawable>();
		middle.setRelativeSizeAxes(Axes.both());
		middle.setMargin(new MarginPadding(50));
		outer.add(middle);
		
		Box innerBox = new Box();
		innerBox.setRelativeSizeAxes(Axes.both());
		innerBox.setColor(Color.BLUE);
		middle.add(innerBox);
	}
}
