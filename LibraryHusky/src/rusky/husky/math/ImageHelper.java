package rusky.husky.math;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import rusky.husky.math.polygons.Quad;

public final class ImageHelper {

	private ImageHelper() {
	}

	public static BufferedImage mapImage(BufferedImage originalImage, Quad map, Vector2 size) {
		if (size.getXInt() <= 0 && size.getYInt() <= 0)
			throw new IllegalArgumentException("Size cannot be zero");

		BufferedImage image = new BufferedImage(size.getXInt(), size.getYInt(), BufferedImage.TYPE_INT_ARGB);
		int[] data = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		Vector2 originalImageSize = new Vector2(originalImage.getWidth(null), originalImage.getHeight(null));

		for (int x = 0; x < image.getWidth(null); x++) {
			for (int y = 0; y < image.getHeight(null); y++) {
				Vector2 original = map
						.map(new Vector2((float) x / image.getWidth(null), y / (float) image.getHeight(null)))
						.multiply(originalImageSize);
				original = original.setX((float) MathHelper.trim(original.getX(), 0, image.getWidth()))
						.setY((float) MathHelper.trim(original.getY(), 0, image.getHeight()));
				data[y * image.getWidth() + x] = originalImage.getRGB(original.getXInt(), original.getYInt());
			}
		}
		return image;
	}

	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img
	 *            The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		return bimage;
	}
}
