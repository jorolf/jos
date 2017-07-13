package rusky.husky.sprites;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.EnumSet;

import rusky.husky.Drawable;
import rusky.husky.Invalidation;

public class Sprite extends Drawable {

	private Image image;
	private Image originalImage;

	public Sprite(Image image) {
		this.originalImage = image;
		scaleImage();
	}

	@Override
	public void paint(Graphics2D graphics) {
		graphics.drawImage(image, 0, 0, null);
		super.paint(graphics);
	}

	public Image getImage() {
		return originalImage;
	}

	public void setImage(Image image) {
		this.originalImage = image;
		scaleImage();
	}

	@Override
	protected void onInvalidation(EnumSet<Invalidation> invalidation) {
		if (invalidation.contains(Invalidation.SIZE_IN_PARENT_SPACE))
			scaleImage();
	}

	private void scaleImage() {
		if (getWidth() > 0 && getHeight() > 0)
			image = originalImage.getScaledInstance(getDrawSize().getXInt(), getDrawSize().getYInt(), Image.SCALE_DEFAULT);
	}
}
