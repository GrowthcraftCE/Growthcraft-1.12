package growthcraft.core.common.lib.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import growthcraft.core.GrowthcraftCore;
import growthcraft.core.common.lib.definition.IMultiFluidStacks;
import growthcraft.core.common.lib.definition.IMultiItemStacks;
import growthcraft.core.common.lib.fluids.MultiFluidStacks;
import growthcraft.core.common.lib.fluids.TaggedFluidStacks;
import growthcraft.core.common.lib.item.MultiItemStacks;
import growthcraft.core.common.lib.item.OreItemStacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MultiStacksUtil
{
	private MultiStacksUtil() {}

	/**
	 * @author IceDragon
	 * @note I want to die now. This is just... uggh, the insanity!
	 */
	private static <T> List<List<T>> expandedCombinations(@Nonnull List<List<T>> expandedStacks)
	{
		final int columnCount = expandedStacks.size();
		final int[] colIndices = new int[columnCount];
		final int[] colMax = new int[columnCount];
		final List<List<T>> result = new ArrayList<List<T>>();

		for (int i = 0; i < colIndices.length; ++i)
		{
			colIndices[i] = 0;
			colMax[i] = expandedStacks.get(i).size();
		}

		final int lastCol = colIndices.length - 1;
		boolean abortOuter = false;
		while (colIndices[lastCol] < colMax[lastCol])
		{
			final List<T> row = new ArrayList<T>();
			for (int col = 0; col < colIndices.length; ++col)
			{
				if (colIndices[col] >= colMax[col])
				{
					if (col < (columnCount - 1))
					{
						colIndices[col] = 0;
						colIndices[col + 1]++;
					}
					else
					{
						abortOuter = true;
						break;
					}
				}
				final List<T> colStacks = expandedStacks.get(col);
				final int colIndex = colIndices[col];
				row.add(colStacks.get(colIndex));
			}
			if (abortOuter) break;
			if (row.size() == columnCount) result.add(row);
			colIndices[0]++;
		}

		return result;
	}

	public static List<List<ItemStack>> expandedItemStackCombinations(@Nonnull List<IMultiItemStacks> srcList)
	{
		final List<List<ItemStack>> expandedStacks = new ArrayList<List<ItemStack>>();
		for (IMultiItemStacks stacks : srcList)
		{
			expandedStacks.add(stacks.getItemStacks());
		}
		return MultiStacksUtil.<ItemStack>expandedCombinations(expandedStacks);
	}

	public static List<List<FluidStack>> expandedFluidStackCombinations(@Nonnull List<IMultiFluidStacks> srcList)
	{
		final List<List<FluidStack>> expandedStacks = new ArrayList<List<FluidStack>>();
		for (IMultiFluidStacks stacks : srcList)
		{
			expandedStacks.add(stacks.getFluidStacks());
		}
		return MultiStacksUtil.<FluidStack>expandedCombinations(expandedStacks);
	}

	public static IMultiItemStacks toMultiItemStacks(@Nonnull Object obj)
	{
		if (obj instanceof IMultiItemStacks)
		{
			return (IMultiItemStacks)obj;
		}
		else if (obj instanceof ItemStack)
		{
			return new MultiItemStacks((ItemStack)obj);
		}
		else
		{
			throw new IllegalArgumentException("Wrong type, expected a ItemStack or IMultiItemStacks");
		}
	}

	public static IMultiFluidStacks toMultiFluidStacks(@Nonnull Object obj)
	{
		if (obj instanceof IMultiFluidStacks)
		{
			return (IMultiFluidStacks)obj;
		}
		else if (obj instanceof FluidStack)
		{
			return new MultiFluidStacks((FluidStack)obj);
		}
		else
		{
			throw new IllegalArgumentException("Wrong type, expected a FluidStack or IMultiFluidStacks");
		}
	}

	public static List<ItemStack> expandItemStacks(@Nonnull List<ItemStack> list, @Nonnull Object obj)
	{
		if (obj instanceof ItemStack)
		{
			list.add((ItemStack)obj);
		}
		else if (obj instanceof OreItemStacks)
		{
			final OreItemStacks oreItemStack = (OreItemStacks)obj;
			final List<ItemStack> stacks = oreItemStack.getItemStacks();
			if (stacks.isEmpty())
			{
				GrowthcraftCore.logger.error("Ore stack '%s' was empty!", oreItemStack.getName());
			}
			else
			{
				GrowthcraftCore.logger.debug("Expanded OreItemStack '%s' to %s", oreItemStack.getName(), stacks);
				list.addAll(stacks);
			}
		}
		else if (obj instanceof IMultiItemStacks)
		{
			list.addAll(((IMultiItemStacks)obj).getItemStacks());
		}
		else
		{
			throw new IllegalArgumentException("Wrong type, expected a ItemStack or IMultiItemStacks");
		}
		return list;
	}

	public static List<FluidStack> expandFluidStacks(@Nonnull List<FluidStack> list, @Nonnull Object obj)
	{
		if (obj instanceof FluidStack)
		{
			list.add((FluidStack)obj);
		}
		else if (obj instanceof TaggedFluidStacks)
		{
			final TaggedFluidStacks taggedFluidStack = (TaggedFluidStacks)obj;
			final List<FluidStack> stacks = taggedFluidStack.getFluidStacks();
			if (stacks.isEmpty())
			{
				GrowthcraftCore.logger.error("Tagged Fluid stack (tags: %s) was empty!", taggedFluidStack.getTags());
			}
			else
			{
				GrowthcraftCore.logger.debug("Expanded TaggedFluidStacks(tags: %s) to [%s]", taggedFluidStack.getTags(), stacks);
				list.addAll(stacks);
			}
		}
		else if (obj instanceof IMultiFluidStacks)
		{
			list.addAll(((IMultiFluidStacks)obj).getFluidStacks());
		}
		else
		{
			throw new IllegalArgumentException("Wrong type, expected a FluidStack or TaggedFluidStacks");
		}
		return list;
	}
}
