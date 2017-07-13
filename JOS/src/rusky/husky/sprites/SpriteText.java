package rusky.husky.sprites;

import java.awt.Font;
import java.awt.Graphics2D;

import rusky.husky.Drawable;

public class SpriteText extends Drawable {
	
	public Font font;
	private String text = "";
	
	public SpriteText(String text) {
		this.text = text;
	}	
	
	public SpriteText() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public void paint(Graphics2D graphics) {
		graphics.setFont(font == null ? graphics.getFont().deriveFont(getDrawSize().getY()) : font.deriveFont(getDrawSize().getY()));
		graphics.drawString(text, 0, getDrawSize().getY());
		setWidth(graphics.getFontMetrics().stringWidth(text));
	}
	
	@Override
	public String toString() {
		return super.toString() + " (" + text + ")";
	}
}
