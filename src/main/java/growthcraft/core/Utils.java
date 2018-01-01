package growthcraft.core;

import growthcraft.core.lib.legacy.FluidContainerRegistry;
import growthcraft.core.lib.legacy.IFluidContainerItem;
import growthcraft.core.lib.legacy.ILegacyFluidHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class Utils
{
	// How much fluid is drained from a container or tank with any given
	// action
	public static final int DRAIN_CAP = 1000;

	private Utils() {}

	public static void debug(String msg)
	{
		final boolean flag = true;
		if (flag) { System.out.println(msg); }
	}

	public static boolean isIDInList(int id, String list)
	{
		final String[] itemArray = list.split(";");
		for (int i = 0; i < itemArray.length; i++)
		{
			final String[] values = itemArray[i].split(",");
			final int tempID = parseInt(values[0], 2147483647);

			if (tempID != 2147483647)
			{
				if (tempID == id) return true;
			}
		}
		return false;
	}

	public static int parseInt(String string, int defaultValue)
	{
		try
		{
			return Integer.parseInt(string.trim());
		}
		catch (NumberFormatException ex)
		{
		}
		return defaultValue;
	}

	public static final boolean isIntegerInRange(int i, int floor, int ceiling)
	{
		if (i < floor || i > ceiling)
		{
			return false;
		}
		return true;
	}

	public static void spawnExp(int amount, float exp, EntityPlayer player)
	{
		int j;

		if (exp == 0.0F)
		{
			amount = 0;
		}
		else if (exp < 1.0F)
		{
			j = MathHelper.floor((float)amount * exp);

			if (j < MathHelper.ceil((float)amount * exp) && (float)Math.random() < (float)amount * exp - (float)j)
			{
				++j;
			}

			amount = j;
		}

		while (amount > 0)
		{
			j = EntityXPOrb.getXPSplit(amount);
			amount -= j;
			player.world.spawnEntity(new EntityXPOrb(player.world, player.posX, player.posY + 0.5D, player.posZ + 0.5D, j));
		}
	}

	public static boolean playerFillTank(World world, BlockPos pos, ILegacyFluidHandler tank, ItemStack held, EntityPlayer player)
	{
		if (held == null) return false;

		final EnumFacing direction = EnumFacing.NORTH;	// Doesn't matter for MC 1.11.2 or newer.

		if (held.getItem() instanceof IFluidContainerItem)
		{
			final IFluidContainerItem container = (IFluidContainerItem)held.getItem();

			final FluidStack willDrain = container.drain(held, DRAIN_CAP, false);
			if (willDrain == null || willDrain.amount <= 0) return false;

			final int used = tank.fill(direction, willDrain, false);
			if (used <= 0) return false;

			if (!world.isRemote)
			{
				tank.fill(direction, willDrain, true);
				container.drain(held, used, true);
			}
		}
		else
		{
			final FluidStack heldContents = FluidContainerRegistry.getFluidForFilledItem(held);
			if (heldContents == null) return false;

			final int used = tank.fill(direction, heldContents, false);
			if (used <= 0) return false;

			if (!world.isRemote)
			{
				tank.fill(direction, heldContents, true);
				final ItemStack containerItem = FluidContainerRegistry.drainFluidContainer(held);

				if (!player.inventory.addItemStackToInventory(containerItem))
				{
					if (containerItem == null)
					{
						// WARN about invalid container item
					}
					else
					{
						world.spawnEntity(new EntityItem(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 1.5D, (double)pos.getZ() + 0.5D, containerItem));
					}
				}
				else if (player instanceof EntityPlayerMP)
				{
					((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
				}

				if (!player.capabilities.isCreativeMode)
				{
					held.shrink(1);
					if (held.isEmpty())
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
					}
				}
			}
		}
		return true;
	}

	public static FluidStack playerDrainTank(World world, BlockPos pos, ILegacyFluidHandler tank, ItemStack held, EntityPlayer player, boolean expbool, int amount, float exp)
	{
		if (held == null) return null;

		final EnumFacing direction = EnumFacing.NORTH;	// Doesn't matter for MC 1.11.2 or newer.
		
		final FluidStack available = tank.drain(direction, DRAIN_CAP, false);
		if (available == null) return null;

		if (held.getItem() instanceof IFluidContainerItem)
		{
			final IFluidContainerItem container = (IFluidContainerItem)held.getItem();

			final int filled = container.fill(held, available, false);
			if (filled <= 0) return null;

			tank.drain(direction, filled, true);
			container.fill(held, available, true);
		}
		else
		{
			FluidStack heldContents = FluidContainerRegistry.getFluidForFilledItem(held);
			final ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, held);
			heldContents = FluidContainerRegistry.getFluidForFilledItem(filled);

			if (heldContents == null) return null;

			if (!player.inventory.addItemStackToInventory(filled))
			{
				world.spawnEntity(new EntityItem(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 1.5D, (double)pos.getZ() + 0.5D, filled));
			}
			else if (player instanceof EntityPlayerMP)
			{
				((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
			}

			if (!player.capabilities.isCreativeMode)
			{
				held.shrink(1);
				if (held.isEmpty())
				{
					player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
				}
			}

			if (expbool)
			{
				spawnExp(amount * heldContents.amount / tank.getTankInfo(direction)[0].capacity, exp, player);
			}
			tank.drain(direction, heldContents.amount, true);
		}
		return available;
	}

	public static FluidStack playerDrainTank(World world, BlockPos pos, ILegacyFluidHandler tank, ItemStack held, EntityPlayer player)
	{
		return playerDrainTank(world, pos, tank, held, player, false, 0, 0);
	}
}
