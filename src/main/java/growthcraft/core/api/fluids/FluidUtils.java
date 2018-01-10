package growthcraft.core.api.fluids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import growthcraft.core.lib.legacy.FluidContainerRegistry;
import growthcraft.core.lib.legacy.FluidContainerRegistry.FluidContainerData;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
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

public class FluidUtils
{
	private static Map<Fluid, List<FluidContainerData>> fluidData;
	
	private FluidUtils() {}
	
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
			
			// Universal bucket
//			final ItemStack bucket = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluidStack.getFluid());
//			fluidContainers.add(bucket);
//			addBucketIfAvailable(fluidContainers, fluidStack.getFluid());
			
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
			
			// Universal bucket
//			final ItemStack bucket = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluidStack.getFluid());
//			fluidContainers.add(bucket);
//			addBucketIfAvailable(fluidContainers, fluidStack.getFluid());
		}
		
		return fluidContainers;
	}
	
/*	private static void addBucketIfAvailable(List<ItemStack> toList, Fluid fluid) {
		final UniversalBucket universalBucket = ForgeModContainer.getInstance().universalBucket;
		final ItemStack bucket = UniversalBucket.getFilledBucket(universalBucket, fluid);
		if( bucket.getItem() != universalBucket || universalBucket.getFluid(bucket) != null )
			toList.add(bucket);
	}*/

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

	public static List<Fluid> getFluidsByNames(List<String> names)
	{
		final List<Fluid> fluids = new ArrayList<Fluid>();
		for (String name : names)
		{
			fluids.add(FluidRegistry.getFluid(name));
		}
		return fluids;
	}

	public static FluidStack exchangeFluid(FluidStack stack, Fluid newFluid)
	{
		return new FluidStack(newFluid, stack.amount);
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

	public static FluidTankInfo[] convertTankPropsToInfo(IFluidTankProperties[] tankProperties) {
		FluidTankInfo[] infos = new FluidTankInfo[tankProperties.length];
		for( int i = 0; i < tankProperties.length; i ++ ) {
			IFluidTankProperties prop = tankProperties[i];
			infos[i] = new FluidTankInfo(prop.getContents(), prop.getCapacity());
		}
		return infos;
	}
}
