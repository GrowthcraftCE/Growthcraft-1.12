package growthcraft.cellar.events;

import growthcraft.cellar.api.processing.brewing.BrewingRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Event emitted when a BrewKettle brews something
 */
public class EventBrewed extends Event
{
	public final BrewingRecipe recipe;
	public final TileEntity tile;

	public EventBrewed(TileEntity te, BrewingRecipe re)
	{
		super();
		this.tile = te;
		this.recipe = re;
	}
}
