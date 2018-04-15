package growthcraft.core.common.lib.tileentity.feature;

public interface ITileHeatedDevice
{
	boolean isHeated();
	float getHeatMultiplier();
	int getHeatScaled(int scale);
}
