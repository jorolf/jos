package rusky.husky.testing.testCases;

import java.awt.Color;
import java.util.EnumSet;

import rusky.husky.Anchor;
import rusky.husky.Axes;
import rusky.husky.Container;
import rusky.husky.Drawable;
import rusky.husky.InputState;
import rusky.husky.clocks.OffsetClock;
import rusky.husky.clocks.StopwatchClock;
import rusky.husky.math.Vector2;
import rusky.husky.sprites.Box;
import rusky.husky.sprites.SpriteNumber;
import rusky.husky.testing.TestCase;

public class TestCaseClocks extends TestCase {

	@Override
	public String getDescription() {
		return "tick tock";
	}

	
	@Override
	public void internalReset() {
		Bar normal = new Bar();
		normal.setHeight(50);
		add(normal);
		
		Bar offset = new Bar();
		offset.setHeight(50);
		offset.setY(75);
		offset.setClock(new OffsetClock(1000));
		add(offset);
		
		Bar stopwatch = new Bar();
		stopwatch.setHeight(50);
		stopwatch.setY(150);
		stopwatch.setClock(new StopwatchClock(2));
		add(stopwatch);
		
		Bar stopwatch2 = new Bar();
		stopwatch2.setHeight(50);
		stopwatch2.setY(225);
		stopwatch2.setClock(new StopwatchClock(0.5));
		add(stopwatch2);
		
		Bar reverse = new Bar();
		reverse.setHeight(50);
		reverse.setY(300);
		reverse.setClock(new StopwatchClock(-1));
		add(reverse);
	}
	
	private class Bar extends Container<Drawable> {
		private final SpriteNumber time;
		private final Box box;
		
		public Bar(){
			box = new Box();
			box.setRelativeSizeAxes(Axes.both());
			box.setColor(Color.BLACK);
			add(box);
			
			add(time = new SpriteNumber());
			time.setRelativeSizeAxes(EnumSet.of(Axes.Y));
			time.setHeight(0.25f);
			time.anchor = Anchor.CenterLeft;
			time.origin = Anchor.CenterLeft;
		}
		
		@Override
		protected void update(InputState state) {
			Vector2 drawSize = parent.getDrawSize();
			setWidth((float) (((getClock().getTime() % drawSize.getX()) + drawSize.getX()) % drawSize.getX()));
			time.setValue((float) (getClock().getTime()/1000));
		}
	}
}
