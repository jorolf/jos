package rusky.husky.sprites;

import java.awt.Graphics2D;

import rusky.husky.Drawable;

public class Box extends Drawable {
	
	@Override
	public void paint(Graphics2D graphics) {
		graphics.fillRect(0, 0, getDrawSize().getXInt(), getDrawSize().getYInt());
	}
}
