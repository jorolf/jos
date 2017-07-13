package rusky.husky.testing.testCases;

import java.awt.Color;

import rusky.husky.Anchor;
import rusky.husky.InputState;
import rusky.husky.math.Vector2;
import rusky.husky.sprites.Box;
import rusky.husky.sprites.SpriteText;
import rusky.husky.testing.TestCase;

public class TestCaseFocus extends TestCase {

	@Override
	public String getDescription() {
		return "out of focus";
	}

	private SpriteText text;
	
	@Override
	protected void internalReset() {
		Box box1 = new Box(){
			@Override
			protected boolean onFocus(InputState state) {
				setColor(Color.GRAY);
				return true;
			}
			
			@Override
			protected void onFocusLost(InputState state) {
				setColor(Color.BLACK);
			}
		};
		box1.setSize(new Vector2(100));
		box1.setColor(Color.BLACK);
		add(box1);
		
		text = new SpriteText("type something");
		text.origin = Anchor.BottomLeft;
		text.anchor = Anchor.BottomLeft;
		text.setHeight(15);
		text.setColor(Color.black);
		add(text);
	}

	
	public void onKeyTyped(char character) {
		System.out.println(character);
		if(character == '\b')
			text.setText(text.getText().substring(0, text.getText().length()-1));
		else
			text.setText(text.getText() + character);
	};
	
	@Override
	protected boolean onFocus(InputState state) {
		super.onFocus(state);
		return true;
	}
}
