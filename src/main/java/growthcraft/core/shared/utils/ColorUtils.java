package growthcraft.core.shared.utils;

import mezz.jei.util.MathUtil;

public class ColorUtils {
	private ColorUtils() {}
	
	public static float[] getFloat4ARGB(int color) {
		final float a = (float)(color >> 24 & 255) / 255.0F;
		final float r = (float)(color >> 16 & 255) / 255.0F;
		final float g = (float)(color >> 8 & 255) / 255.0F;
		final float b = (float)(color & 255) / 255.0F;
		return new float[] {a, r, g, b};
	}
	
	public static int getIntARGB(float[] f4) {
		int a = MathUtil.clamp((int)(f4[0] * 255.0f), 0, 255);
		int r = MathUtil.clamp((int)(f4[1] * 255.0f), 0, 255);
		int g = MathUtil.clamp((int)(f4[2] * 255.0f), 0, 255);
		int b = MathUtil.clamp((int)(f4[3] * 255.0f), 0, 255);
		return (a << 24) | (r << 16) | (g << 8) | b;
	}
}
