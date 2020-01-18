package growthcraft.core.shared.tileentity.feature;

public interface ITileProgressiveDevice extends ITileDevice{
    float getDeviceProgress();

    int getDeviceProgressScaled(int scale);
}
