package growthcraft.core.common.lib.tileentity.feature;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;

public interface IGuiNetworkSync
{
	void sendGUINetworkData(Container container, IContainerListener icrafting);
	void receiveGUINetworkData(int id, int value);
}
