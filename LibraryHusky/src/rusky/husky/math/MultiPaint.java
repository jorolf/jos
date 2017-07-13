package rusky.husky.math;

import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MultiPaint implements Paint {

	private final Paint[] paints;

	public MultiPaint(Paint... paints) {
		this.paints = paints;
	}

	@Override
	public int getTransparency() {
		return Arrays.stream(paints).mapToInt(paint -> paint.getTransparency()).max().getAsInt();
	}

	@Override
	public PaintContext createContext(ColorModel arg0, Rectangle arg1, Rectangle2D arg2, AffineTransform arg3,
			RenderingHints arg4) {
		return new MultiPaintContext(Arrays.stream(paints)
				.map(paint -> paint.createContext(arg0, arg1, arg2, arg3, arg4)).collect(Collectors.toList()));
	}

	private class MultiPaintContext implements PaintContext {
		private final List<PaintContext> contexts;

		public MultiPaintContext(List<PaintContext> contexts) {
			this.contexts = contexts;
		}

		@Override
		public void dispose() {
			contexts.forEach(context -> context.dispose());
		}

		@Override
		public ColorModel getColorModel() {
			return ColorModel.getRGBdefault();
		}

		@Override
		public Raster getRaster(int x, int y, int w, int h) {
			int[] matrix = new int[w * h * 4];
			Arrays.fill(matrix, 255);
			for (PaintContext context : contexts) {
				int[] data = context.getRaster(x, y, w, h).getPixels(0, 0, w, h, new int[w * h * 4]);
				
				for(int i = 0; i < matrix.length; i++)
					matrix[i] *= data[i] / 255f;
				
			}
			DataBufferInt buffer = new DataBufferInt(matrix, matrix.length);
			int[] bandMasks = { 0xFF0000, 0xFF00, 0xFF, 0xFF000000 }; 
			return Raster.createPackedRaster(buffer, w, h, w, bandMasks, null);
		}

	}
}
