package growthcraft.core.common.tileentity.feature;

public interface ITileHeatedDevice
{
	boolean isHeated();
	float getHeatMultiplier();
	int getHeatScaled(int scale);
}
