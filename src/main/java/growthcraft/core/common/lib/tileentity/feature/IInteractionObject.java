package growthcraft.core.common.lib.tileentity.feature;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

/**
 * Backport from Minecraft 1.8.9
 */
public interface IInteractionObject
{
	Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn);
	String getGuiID();
}
