package rusky.husky.sprites;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.EnumSet;

import rusky.husky.Drawable;
import rusky.husky.Invalidation;
import rusky.husky.math.ImageHelper;
import rusky.husky.math.Vector2;
import rusky.husky.math.polygons.Quad;

public class MappedSprite extends Drawable {

	private BufferedImage image;
	private BufferedImage originalImage;

	private Quad map = new Quad(Vector2.ZERO, Vector2.RIGHT, Vector2.DOWN, Vector2.ONE);

	public MappedSprite(Image image, Quad map) {
		this.originalImage = ImageHelper.toBufferedImage(image);
		this.map = map;
	}

	public MappedSprite(Image image) {
		this.originalImage = ImageHelper.toBufferedImage(image);
		mapImage();
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
		this.originalImage = ImageHelper.toBufferedImage(image);
		mapImage();
	}

	@Override
	protected void onInvalidation(EnumSet<Invalidation> invalidation) {
		if (invalidation.contains(Invalidation.SIZE_IN_PARENT_SPACE))
			mapImage();
	}

	private void mapImage() {
		if (getDrawSize().getXInt() <= 0 && getDrawSize().getYInt() <= 0)
			return;
		image = ImageHelper.mapImage(originalImage, map, getDrawSize());

	}

	public Quad getMap() {
		return map;
	}

	public void setMap(Quad map) {
		this.map = map;
		mapImage();
	}
}
