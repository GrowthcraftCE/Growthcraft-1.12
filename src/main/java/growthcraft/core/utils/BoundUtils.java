package growthcraft.core.utils;

/**
 * Utility class for defining block bounds, cut from IceDragon200/YATM
 * This class has 2 styles of bounding boxes:
 *   Point bb (x, x2. etc..)
 *   Cuboid bb (x, y, z, width, depth, height)
 * Minecraft requires the former, while the latter is convient if you know the
 * position and the size.
 */
public class BoundUtils
{
	public static final double[] NORMALIZED_CLAMP = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, 1.0 };

	private BoundUtils() {}

	/**
	 * Clamps the target bounds using the provided `clamp` bounds
	 * @param target target bounds to modify
	 * @param clamp bounds to clamp with
	 * @return bounds
	 */
	public static double[] clampBounds(double[] target, double[] clamp)
	{
		assert target.length == 6;
		if (target[0] < clamp[0]) target[0] = clamp[0];
		if (target[1] < clamp[1]) target[1] = clamp[1];
		if (target[2] < clamp[2]) target[2] = clamp[2];
		if (target[3] > clamp[3]) target[3] = clamp[3];
		if (target[4] > clamp[4]) target[4] = clamp[4];
		if (target[5] > clamp[5]) target[5] = clamp[5];
		return target;
	}

	/**
	 * Clamps the target bounds using the NORMALIZED_CLAMP
	 * @param target target bounds to modify
	 * @return bounds
	 */
	public static double[] clampBounds(double[] target)
	{
		return clampBounds(target, NORMALIZED_CLAMP);
	}

	/**
	 * Adds the expander
	 *
	 * @param target target bounds to modify
	 * @param expander the bounds to add
	 * @return bounds
	 */
	public static double[] addBounds(double[] target, double[] expander)
	{
		assert target.length == 6;
		target[0] += expander[0];
		target[1] += expander[1];
		target[2] += expander[2];
		target[3] += expander[3];
		target[4] += expander[4];
		target[5] += expander[5];
		return target;
	}

	/**
	 * Adds the expander
	 *
	 * @param target target bounds to modify
	 * @param contractor the bounds to add
	 * @return bounds
	 */
	public static double[] subBounds(double[] target, double[] contractor)
	{
		assert target.length == 6;
		target[0] -= contractor[0];
		target[1] -= contractor[1];
		target[2] -= contractor[2];
		target[3] -= contractor[3];
		target[4] -= contractor[4];
		target[5] -= contractor[5];
		return target;
	}

	/**
	 * Creates a new 6 element float array for use as a Bounding Box
	 *
	 * @return bounding box
	 */
	public static double[] newBoundsArray()
	{
		return new double[6];
	}

	/**
	 * Scales the provided bounding box
	 *
	 * @param target - target bounding box
	 * @param x - translation x
	 * @param y - translation y
	 * @param z - translation z
	 * @param src - source bounding box
	 * @return target
	 */
	public static double[] translateBounds(double[] target, double x, double y, double z, double[] src)
	{
		assert target.length == 6;
		target[0] = src[0] + x;
		target[1] = src[1] + y;
		target[2] = src[2] + z;
		target[3] = src[3] + x;
		target[4] = src[4] + y;
		target[5] = src[5] + z;
		return target;
	}

	/**
	 * Scales the provided bounding box
	 *
	 * @param target - target bounding box
	 * @param scale - box scale
	 * @param x - source x
	 * @param y - source y
	 * @param z - source z
	 * @param x2 - source x2
	 * @param y2 - source y2
	 * @param z2 - source z2
	 * @return target
	 */
	public static double[] scaleBounds(double[] target, double scale, double x, double y, double z, double x2, double y2, double z2)
	{
		assert target.length == 6;
		target[0] = x * scale;
		target[1] = y * scale;
		target[2] = z * scale;
		target[3] = x2 * scale;
		target[4] = y2 * scale;
		target[5] = z2 * scale;
		return target;
	}

	/**
	 * Scales the provided bounding box
	 *
	 * @param target - target bounding box
	 * @param scale - box scale
	 * @param src - src bounding box
	 * @return target
	 */
	public static double[] scaleBounds(double[] target, double scale, double[] src)
	{
		assert src.length == 6;
		return scaleBounds(target, scale, src[0], src[1], src[2], src[3], src[4], src[5]);
	}

	/**
	 * Creates a new scaled bounding box
	 *
	 * @param scale - box scale
	 * @param x - source x
	 * @param y - source y
	 * @param z - source z
	 * @param x2 - source x2
	 * @param y2 - source y2
	 * @param z2 - source z2
	 * @return bounding box
	 */
	public static double[] newScaledBounds(double scale, double x, double y, double z, double x2, double y2, double z2)
	{
		final double[] result = newBoundsArray();
		return scaleBounds(result, scale, x, y, z, x2, y2, z2);
	}

	/**
	 * Converts a cuboid like bounding box to an actual bounding box.
	 *
	 * @param target - target bounding box
	 * @param x - x coord
	 * @param y - y coord
	 * @param z - z coord
	 * @param w - width
	 * @param d - depth
	 * @param h - height
	 * @return target
	 */
	public static double[] cubeToBounds(double[] target, double x, double y, double z, double w, double d, double h)
	{
		assert target.length == 6;
		target[0] = x;
		target[1] = y;
		target[2] = z;
		target[3] = x + w;
		target[4] = y + d;
		target[5] = z + h;
		return target;
	}
	/**
	 * Converts a cuboid like bounding box to an actual bounding box.
	 *
	 * @param target - target bounding box
	 * @param src - source bounding box
	 * @return target
	 */
	public static double[] cubeToBounds(double[] target, double[] src)
	{
		assert src.length == 6;
		return cubeToBounds(target, src[0], src[1], src[2], src[3], src[4], src[5]);
	}

	/**
	 * Creates a new Cuboid bounding box
	 *
	 * @param x - x coord
	 * @param y - y coord
	 * @param z - z coord
	 * @param w - width
	 * @param d - depth
	 * @param h - height
	 * @return cuboid bounding box
	 */
	public static double[] newCubeToBounds(double x, double y, double z, double w, double d, double h)
	{
		final double[] result = newBoundsArray();
		return cubeToBounds(result, x, y, z, w, d, h);
	}

	/**
	 * Initialize a Cuboid bounding box placing it the center
	 *
	 * @param w - width
	 * @param d - depth
	 * @param h - height
	 * @return target
	 */
	public static double[] centeredCubeBounds(double[] target, double w, double d, double h)
	{
		final double x = (1 - w) / 2;
		final double y = (1 - d) / 2;
		final double z = (1 - h) / 2;
		return cubeToBounds(target, x, y, z, w, d, h);
	}

	/**
	 * Creates a new Cuboid bounding box, centered
	 *
	 * @param w - width
	 * @param d - depth
	 * @param h - height
	 * @return cuboid bounding box
	 */
	public static double[] newCenteredCubeBounds(double w, double d, double h)
	{
		return centeredCubeBounds(newBoundsArray(), w, d, h);
	}
}
