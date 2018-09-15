package growthcraft.core.shared.utils;

public class ColorUtils {
	private ColorUtils() {}
	
	public static float[] getARGB(int color) {
		final float a = (float)(color >> 24 & 255) / 255.0F;
		final float r = (float)(color >> 16 & 255) / 255.0F;
		final float g = (float)(color >> 8 & 255) / 255.0F;
		final float b = (float)(color & 255) / 255.0F;
		return new float[] {a, r, g, b};
	}
}
