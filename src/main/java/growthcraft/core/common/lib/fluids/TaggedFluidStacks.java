package growthcraft.core.common.lib.fluids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.common.lib.CoreRegistry;
import growthcraft.core.common.lib.definition.IMultiFluidStacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class TaggedFluidStacks implements IMultiFluidStacks
{
	public int amount;
	private List<String> tags;
	private List<String> exclusionTags;
	private List<FluidTag> fluidTags;
	private List<FluidTag> exclusionFluidTags;
	private List<Fluid> fluidCache;
	private transient List<ItemStack> fluidContainers;

	/**
	 * @param amt - expected fluid stack size
	 * @param ptags - fluid tag names
	 * @param pextags - fluid tag names
	 */
	public TaggedFluidStacks(int amt, @Nonnull List<String> ptags, @Nonnull List<String> pextags)
	{
		this.amount = amt;
		this.tags = ptags;
		this.exclusionTags = pextags;
		this.fluidTags = CoreRegistry.instance().fluidTags().expandTagNames(tags);
		this.exclusionFluidTags = CoreRegistry.instance().fluidTags().expandTagNames(exclusionTags);
	}

	/**
	 * @param amt - expected fluid stack size
	 * @param ptags - fluid tag names
	 */
	public TaggedFluidStacks(int amt, @Nonnull String... ptags)
	{
		this(amt, Arrays.asList(ptags), new ArrayList<String>());
	}

	/**
	 * The tags to filter by
	 *
	 * @return tags
	 */
	public List<String> getTags()
	{
		return tags;
	}

	/**
	 * The tags to filter by
	 *
	 * @return tags
	 */
	public List<String> getExclusionTags()
	{
		return exclusionTags;
	}

	/**
	 * All fluids registered under the tags
	 *
	 * @return fluids
	 */
	public Collection<Fluid> getFluids()
	{
		if (fluidCache == null)
		{
			this.fluidCache = new ArrayList<Fluid>();
			fluidCache.addAll(CoreRegistry.instance().fluidDictionary().getFluidsByTags(fluidTags));
			fluidCache.removeAll(CoreRegistry.instance().fluidDictionary().getFluidsByTags(exclusionFluidTags));
		}
		return fluidCache;
	}

	@Override
	public int getAmount()
	{
		return amount;
	}

	@Override
	public List<FluidStack> getFluidStacks()
	{
		final Collection<Fluid> fluids = getFluids();
		final List<FluidStack> result = new ArrayList<FluidStack>();
		for (Fluid fluid : fluids)
		{
			result.add(new FluidStack(fluid, amount));
		}
		return result;
	}

	@Override
	public boolean containsFluid(@Nullable Fluid expectedFluid)
	{
		if (!FluidTest.isValid(expectedFluid)) return false;
		for (Fluid fluid : getFluids())
		{
			if (fluid == expectedFluid) return true;
		}
		return false;
	}

	@Override
	public boolean containsFluidStack(@Nullable FluidStack stack)
	{
		if (!FluidTest.isValid(stack)) return false;
		final Fluid expected = stack.getFluid();
		return containsFluid(expected);
	}

	@Override
	public List<ItemStack> getItemStacks()
	{
		if (fluidContainers == null)
		{
			fluidContainers = GrowthcraftFluidUtils.getFluidContainers(getFluidStacks());
		}

		return fluidContainers;
	}
}
