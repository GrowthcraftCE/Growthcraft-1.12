package growthcraft.cellar.common.lib.events;

import growthcraft.cellar.common.lib.processing.brewing.IBrewingRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Event emitted when a BrewKettle brews something
 */
public class EventBrewed extends Event
{
	public final IBrewingRecipe recipe;
	public final TileEntity tile;

	public EventBrewed(TileEntity te, IBrewingRecipe re)
	{
		super();
		this.tile = te;
		this.recipe = re;
	}
}
