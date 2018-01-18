package growthcraft.milk.events;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class EventCheeseVat extends Event
{
	public final TileEntity tile;

	public EventCheeseVat(TileEntity te)
	{
		super();
		this.tile = te;
	}

	public static class EventCheeseVatMadeCurds extends EventCheeseVat
	{
		public EventCheeseVatMadeCurds(TileEntity te)
		{
			super(te);
		}
	}

	public static class EventCheeseVatMadeCheeseFluid extends EventCheeseVat
	{
		public EventCheeseVatMadeCheeseFluid(TileEntity te)
		{
			super(te);
		}
	}
}
