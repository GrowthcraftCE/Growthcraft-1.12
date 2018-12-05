package growthcraft.core.shared.tileentity.feature;

public interface ITileHeatedDevice
{
	boolean isHeated();
	float getHeatMultiplier();
	int getHeatScaled(int scale);
}
