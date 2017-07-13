package rusky.husky.math;

import java.awt.Color;

public final class MathHelper {
	private MathHelper() {

	}

	public static double map(double value, double beforeMin, double beforeMax, double afterMin, double afterMax) {
		return (value - beforeMin) / (beforeMax - beforeMin) * (afterMax - afterMin) + afterMin;
	}

	public static double trim(double value, double min, double max) {
		return value < min ? min : value > max ? max : value;
	}

	public static double lerp(double start, double end, double lerp) {
		return (end - start) * lerp + start;
	}

	public static double colorDistance(Color c1, Color c2) {
		int r = c1.getRed() - c2.getRed();
		int g = c1.getGreen() - c2.getGreen();
		int b = c1.getBlue() - c2.getBlue();
		int a = c1.getAlpha() - c2.getAlpha();
		return Math.sqrt(r * r + g * g + b * b + a * a);
	}
}
