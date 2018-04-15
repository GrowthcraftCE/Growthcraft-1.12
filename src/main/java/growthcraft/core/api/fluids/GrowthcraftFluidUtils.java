package growthcraft.core.api.fluids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import growthcraft.core.Utils;
import growthcraft.core.lib.legacy.FluidContainerRegistry;
import growthcraft.core.lib.legacy.IFluidContainerItem;
import growthcraft.core.lib.legacy.IGrowthcraftTankOperable;
import growthcraft.core.lib.legacy.FluidContainerRegistry.FluidContainerData;
import growthcraft.core.utils.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class GrowthcraftFluidUtils
{
	// How much fluid is drained from a container or tank with any given
	// action
	public static final int DRAIN_CAP = FluidContainerRegistry.BUCKET_VOLUME;

	private static Map<Fluid, List<FluidContainerData>> fluidData;
	
	private GrowthcraftFluidUtils() {}
	
	/////////
	// Fluid registry helpers
	/////////
	
	public static Map<Fluid, List<FluidContainerData>> getFluidData()
	{
		if (fluidData == null || fluidData.size() == 0)
		{
			fluidData = new HashMap<Fluid, List<FluidContainerData>>();
			for (FluidContainerData data : Arrays.asList(FluidContainerRegistry.getRegisteredFluidContainerData()))
			{
				if (!fluidData.containsKey(data.fluid.getFluid()))
				{
					fluidData.put(data.fluid.getFluid(), new ArrayList<FluidContainerData>());
				}
				fluidData.get(data.fluid.getFluid()).add(data);
			}
		}
		
		return fluidData;
	}
	
	public static List<ItemStack> getFluidContainers(FluidStack... fluids)
	{
		if (fluids.length == 1)
		{
			final ArrayList<ItemStack> fluidContainers = new ArrayList<ItemStack>();
			final FluidStack fluidStack = fluids[0];

			for (FluidContainerData data : getFluidData().get(fluidStack.getFluid()))
			{
				if (data.fluid.amount >= fluidStack.amount)
				{
					fluidContainers.add(data.filledContainer);
				}
			}
			
			return fluidContainers;
		} 
		else
		{
			return getFluidContainers(Arrays.asList(fluids));
		}
	}
	
	public static List<ItemStack> getFluidContainers(Collection<FluidStack> fluids)
	{
		final ArrayList<ItemStack> fluidContainers = new ArrayList<ItemStack>();
		
		for (FluidStack fluidStack : fluids)
		{
			fluidContainers.addAll(getFluidContainers(fluidStack));
		}
		
		return fluidContainers;
	}
	
	public static List<Fluid> getFluidsByNames(List<String> names)
	{
		final List<Fluid> fluids = new ArrayList<Fluid>();
		for (String name : names)
		{
			fluids.add(FluidRegistry.getFluid(name));
		}
		return fluids;
	}

	public static boolean doesFluidExist(String name)
	{
		return FluidRegistry.getFluid(name) != null && FluidRegistry.isFluidRegistered(name);
	}

	public static boolean doesFluidExist(Fluid fluid)
	{
		return fluid != null && FluidRegistry.isFluidRegistered(fluid);
	}

	public static boolean doesFluidsExist(Fluid[] fluid)
	{
		for (int i = 0; i < fluid.length; ++i)
		{
			if (!doesFluidExist(fluid[i]))
			{
				return false;
			}
		}
		return true;
	}
	
	/////////
	// Fluid interactions
	/////////
	
	public static FluidStack drainFluidBlock(World world, BlockPos pos, boolean doDrain)
	{
		final Block block = world.getBlockState(pos).getBlock();
		if (block instanceof BlockFluidBase)
		{
			final BlockFluidBase bfb = (BlockFluidBase)block;
			return bfb.drain(world, pos, doDrain);
		}
		else if (block == Blocks.LAVA)
		{
			if (doDrain) world.setBlockToAir(pos);
			return new FluidStack(FluidRegistry.LAVA, FluidContainerRegistry.BUCKET_VOLUME);
		}
		else if (block == Blocks.WATER)
		{
			if (doDrain) world.setBlockToAir(pos);
			return new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME);
		}
		return null;
	}
	
	public static boolean playerFillTank(World world, BlockPos pos, IGrowthcraftTankOperable tank, ItemStack held, EntityPlayer player)
	{
		if (ItemUtils.isEmpty(held)) return false;

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
						player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
					}
				}
			}
		}
		return true;
	}

	public static FluidStack playerDrainTank(World world, BlockPos pos, IGrowthcraftTankOperable tank, ItemStack held, EntityPlayer player, boolean expbool, int amount, float exp)
	{
		if (ItemUtils.isEmpty(held)) return null;

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
					player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
				}
			}

			if (expbool)
			{
				Utils.spawnExp(amount * heldContents.amount / tank.getTankInfo(direction)[0].capacity, exp, player);
			}
			tank.drain(direction, heldContents.amount, true);
		}
		return available;
	}

	public static FluidStack playerDrainTank(World world, BlockPos pos, IGrowthcraftTankOperable tank, ItemStack held, EntityPlayer player)
	{
		return playerDrainTank(world, pos, tank, held, player, false, 0, 0);
	}

	/////////
	// Fluidstack utils
	/////////
	
	public static FluidStack exchangeFluid(FluidStack stack, Fluid newFluid)
	{
		return new FluidStack(newFluid, stack.amount);
	}

	public static FluidStack replaceFluidStack(String fluidId, FluidStack srcStack)
	{
		final Fluid fluid = FluidRegistry.getFluid(fluidId);
		if (fluid == null)
		{
			// An invalid fluid
			return null;
		}
		return replaceFluidStack(fluid, srcStack);
	}

	public static FluidStack replaceFluidStack(Fluid fluid, FluidStack srcStack)
	{
		if (fluid == null || srcStack == null)
		{
			return new FluidStack(FluidRegistry.WATER, 0);
		}
		return new FluidStack(fluid, srcStack.amount);
	}

	
	public static FluidStack updateFluidStackAmount(FluidStack srcStack, int amount)
	{
		if (srcStack == null)
		{
			return new FluidStack(FluidRegistry.WATER, amount);
		}
		srcStack.amount = amount;
		return srcStack;
	}
	
	/////////
	// MC1.7 to MC1.11.2 compatibility layer
	/////////
	
	public static FluidTankInfo[] convertTankPropsToInfo(IFluidTankProperties[] tankProperties) {
		FluidTankInfo[] infos = new FluidTankInfo[tankProperties.length];
		for( int i = 0; i < tankProperties.length; i ++ ) {
			IFluidTankProperties prop = tankProperties[i];
			infos[i] = new FluidTankInfo(prop.getContents(), prop.getCapacity());
		}
		return infos;
	}
}
