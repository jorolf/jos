package rusky.husky.testing.testCases;

import java.awt.Color;

import rusky.husky.Anchor;
import rusky.husky.Easing;
import rusky.husky.InputState;
import rusky.husky.clocks.StopwatchClock;
import rusky.husky.math.Vector2;
import rusky.husky.sprites.Box;
import rusky.husky.sprites.SpriteText;
import rusky.husky.testing.TestCase;

public class TestCaseEasings extends TestCase{

	@Override
	public String getDescription() {
		return "easy";
	}

	@Override
	protected void internalReset() {
		Box box = new Box();
		box.setSize(new Vector2(100));
		box.anchor = Anchor.Center;
		box.origin = Anchor.Center;
		box.setColor(Color.BLACK);
		add(box);
		
		StopwatchClock clock = new StopwatchClock(1);
		setClock(clock);
		
		addButton("bounce", () -> {
			box.setY(-200);
			box.moveTo(Vector2.ZERO.clone(), 1000, Easing.BounceOut);
		});
		
		addButton("elastic", () -> {
			box.setY(-200);
			box.moveTo(Vector2.ZERO.clone(), 1000, Easing.ElasticIn);
		});
		
		addButton("size", () -> {
			box.setWidth(200);
			box.resizeTo(new Vector2(100), 500, Easing.QuintIn);
		});
		
		addButton("2x speed", () -> clock.rate = 2);
		addButton("1x speed", () -> clock.rate = 1);
		addButton("0.5x speed", () -> clock.rate = 0.5f);
		
		SpriteText number = new SpriteText(){
			@Override
			protected void update(InputState state) {
				setText("Speed: " + (float) clock.rate);
			}
		};
		number.setHeight(15);
		number.origin = Anchor.BottomLeft;
		number.anchor = Anchor.BottomLeft;
		number.setColor(Color.BLACK);
		add(number);
	}

}
