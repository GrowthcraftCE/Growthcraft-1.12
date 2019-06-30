package growthcraft.cellar.common.inventory;

import growthcraft.bees.common.inventory.ContainerBeeBox.SlotId;
import growthcraft.cellar.common.tileentity.TileEntityWineRack;
import growthcraft.core.shared.inventory.GrowthcraftContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerWineRack extends GrowthcraftContainer
{

	public ContainerWineRack(InventoryPlayer player, TileEntityWineRack wineRack)
	{
		super(wineRack);
		
		this.teWineRack = wineRack;
		
		/*
		 * 2x2 gui inventory
		 * accepts only bottles
		 */
		
		
	}

}
