package growthcraft.core.shared.utils;

import net.minecraft.util.math.AxisAlignedBB;

//Same as the minecraft AxisAlignedBB, except it has #scale and works with BoundUtils
public class BBox
{
	public double[] boundsData;

	public BBox()
	{
		this.boundsData = new double[6];
	}

	public BBox(float[] bounds)
	{
		this();
		assert bounds.length == 6;
		this.boundsData[0] = bounds[0];
		this.boundsData[1] = bounds[1];
		this.boundsData[2] = bounds[2];
		this.boundsData[3] = bounds[3];
		this.boundsData[4] = bounds[4];
		this.boundsData[5] = bounds[5];
	}

	public BBox(double px0, double py0, double pz0, double px1, double py1, double pz1)
	{
		this();
		this.boundsData[0] = px0;
		this.boundsData[1] = py0;
		this.boundsData[2] = pz0;
		this.boundsData[3] = px1;
		this.boundsData[4] = py1;
		this.boundsData[5] = pz1;
	}

	/**
	 * @param x - translate the `x` coord of the bounding box
	 * @param y - translate the `y` coord of the bounding box
	 * @param z - translate the `z` coord of the bounding box
	 * @return this
	 */
	public BBox translate(double x, double y, double z)
	{
		BoundUtils.translateBounds(boundsData, x, y, z, boundsData);
		return this;
	}

	public BBox scale(double px0, double py0, double pz0, double px1, double py1, double pz1)
	{
		boundsData[0] *= px0;
		boundsData[1] *= py0;
		boundsData[2] *= pz0;
		boundsData[3] *= px1;
		boundsData[4] *= py1;
		boundsData[5] *= pz1;
		return this;
	}

	public BBox scale(double x, double y, double z)
	{
		return scale(x, y, z, x, y, z);
	}

	public BBox scale(double ps)
	{
		return scale(ps, ps, ps);
	}

	public double x0() { return boundsData[0]; }
	public double y0() { return boundsData[1]; }
	public double z0() { return boundsData[2]; }
	public double x1() { return boundsData[3]; }
	public double y1() { return boundsData[4]; }
	public double z1() { return boundsData[5]; }

	public double w() { return x1() - x0(); }
	public double h() { return y1() - y0(); }
	public double l() { return z1() - z0(); }

	public boolean contains(double px, double py, double pz)
	{
		return x0() >= px && x1() <= px &&
			y0() >= py && y1() <= py &&
			z0() >= pz && z1() <= pz;
	}

	public static BBox newCube(double x, double y, double z, double w, double h, double l)
	{
		return new BBox(x, y, z, x + w, y + h, z + l);
	}

	public static BBox newCentered(double pw, double ph, double pl)
	{
		final BBox result = new BBox();
		BoundUtils.centeredCubeBounds(result.boundsData, pw, ph, pl);
		return result;
	}
	
	public AxisAlignedBB toAxisAlignedBB() {
		return new AxisAlignedBB(x0(), y0(), z0(), x1(), y1(), z1());
	}
}
