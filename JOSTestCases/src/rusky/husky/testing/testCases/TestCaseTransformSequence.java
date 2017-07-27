package rusky.husky.testing.testCases;

import java.awt.Color;
import java.util.EnumSet;

import rusky.husky.Anchor;
import rusky.husky.Axes;
import rusky.husky.Drawable;
import rusky.husky.Easing;
import rusky.husky.containers.CircularContainer;
import rusky.husky.math.Vector2;
import rusky.husky.sprites.Box;
import rusky.husky.testing.TestCase;

public class TestCaseTransformSequence extends TestCase {

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	protected void internalReset() {
		CircularContainer<Drawable> circleContainer = new CircularContainer<>();
		circleContainer.anchor = Anchor.CenterLeft;
		circleContainer.origin = Anchor.CenterLeft;
		circleContainer.setRelativePositionAxes(EnumSet.of(Axes.X));
		circleContainer.setSize(new Vector2(100));
		
		Box circle = new Box();
		circle.setRelativeSizeAxes(Axes.both());
		circle.setColor(Color.BLACK);
		circleContainer.add(circle);
		add(circleContainer);
		
		addButton("start", () ->{
			circleContainer.transform().moveTo(Vector2.RIGHT.divide(2), 3000, Easing.QuadInOut)
			                           .resizeTo(new Vector2(200), 1500, Easing.QuadIn).delay(1500)
			                           .resizeTo(new Vector2(100), 1500, Easing.QuadOut);
		});
		
		addButton("back", () -> {
			circleContainer.transform().moveTo(Vector2.ZERO, 3000, Easing.Linear);
			circleContainer.transform().fadeTo(0, 1500, Easing.Linear).then()
									   .fadeTo(1, 1500, Easing.Linear);
		});
	}

}
