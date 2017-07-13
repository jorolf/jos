package rusky.husky.testing.testCases;

import java.awt.Color;

import rusky.husky.Anchor;
import rusky.husky.sprites.SpriteText;
import rusky.husky.testing.TestCase;

public class TestCaseAnchor extends TestCase {

	@Override
	public String getDescription() {
		return "A Choir";
	}

	
	@Override
	public void internalReset() {
		setColor(Color.RED);
		
		for(Anchor anchor : Anchor.values()){
			SpriteText text = new SpriteText(anchor.toString());
			text.anchor = anchor;
			text.origin = anchor;
			text.setColor(Color.BLACK);
			text.setHeight(30);
			add(text);
		}
	}
}
