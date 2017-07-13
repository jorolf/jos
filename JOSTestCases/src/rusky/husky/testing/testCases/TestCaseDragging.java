package rusky.husky.testing.testCases;

import java.awt.Color;

import rusky.husky.Anchor;
import rusky.husky.InputState;
import rusky.husky.math.MathHelper;
import rusky.husky.math.Vector2;
import rusky.husky.sprites.Box;
import rusky.husky.testing.TestCase;

public class TestCaseDragging extends TestCase {

	@Override
	public String getDescription() {
		return "dragg";
	}

	@Override
	protected void internalReset() {
		Box box = new Box(){
			@Override
			protected void onMouseDrag(InputState state) {
				setPosition(getPosition().add(state.mouse.getDelta()));
			}
			
			@Override
			protected boolean onMouseDragStart(InputState state) {
				return true;
			}
			
			@Override
			protected boolean onMouseDown(InputState state) {
				setColor(Color.RED);
				return super.onMouseDown(state);
			}
			
			@Override
			protected boolean onMouseUp(InputState state) {
				setColor(Color.BLUE);
				return super.onMouseUp(state);
			}
			
			@Override
			protected boolean onWheel(InputState state) {
				setAlpha((float) MathHelper.trim(getAlpha() + (state.mouse.getWheelDelta() * 0.1f), 0, 1));
				return super.onWheel(state);
			}
		};
		box.anchor = Anchor.Center;
		box.origin = Anchor.Center;
		box.setColor(Color.BLUE);
		box.setSize(new Vector2(100));
		add(box);
	}

}
