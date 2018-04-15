package growthcraft.cellar.common.inventory;

import growthcraft.cellar.common.inventory.slot.SlotBrewKettleResidue;
import growthcraft.cellar.common.inventory.slot.SlotInputBrewing;
import growthcraft.cellar.common.tileentity.PREVTileEntityBrewKettle;
import net.minecraft.entity.player.InventoryPlayer;

public class PREVContainerBrewKettle extends CellarContainer
{
	public PREVContainerBrewKettle(InventoryPlayer player, PREVTileEntityBrewKettle brewKettle)
	{
		super(brewKettle);
		// Slot Indexes:
		//0            raw
		//1            residue
		//2 - 28 (29)  player.inv.backpack
		//29 - 37 (38) player.inv.hotbar

		addSlotToContainer(new SlotInputBrewing(brewKettle, 0, 80, 35));
		addSlotToContainer(new SlotBrewKettleResidue(brewKettle, 1, 141, 17));

		bindPlayerInventory(player, 8, 84);
	}
}
