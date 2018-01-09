package growthcraft.cellar.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Event emitted when a player drains a fermenting barrel
 */
public class EventBarrelDrained extends Event
{
	public final EntityPlayer player;
	public final World world;
	public final BlockPos pos;
	public FluidStack fluid;

	public EventBarrelDrained(EntityPlayer pPlayer, World pWorld, BlockPos pos, FluidStack pFluid)
	{
		this.player = pPlayer;
		this.world = pWorld;
		this.pos = pos;
		this.fluid = pFluid;
	}
}
