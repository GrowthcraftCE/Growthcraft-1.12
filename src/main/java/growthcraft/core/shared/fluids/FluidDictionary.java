package growthcraft.core.shared.fluids;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.GrowthcraftCore;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidDictionary
{
	// REVISE_ME 0
	
	private Map<Fluid, Set<FluidTag>> fluidToTagsMap = new HashMap<Fluid, Set<FluidTag>>();
	private Map<FluidTag, Set<Fluid>> tagToFluidsMap = new HashMap<FluidTag, Set<Fluid>>();

//	@Override
	public void addFluidTags(@Nonnull Fluid fluid, @Nonnull FluidTag... tags)
	{
		if (!fluidToTagsMap.containsKey(fluid))
		{
			GrowthcraftCore.logger.debug("Initializing new HashSet for fluid {%s}", fluid);
			fluidToTagsMap.put(fluid, new HashSet<FluidTag>());
		}

		final Set<FluidTag> tagSet = fluidToTagsMap.get(fluid);
		for (FluidTag tag : tags)
		{
			GrowthcraftCore.logger.debug("Adding tag '%s' to fluid {%s}", tag, fluid);
			tagSet.add(tag);
			if (!tagToFluidsMap.containsKey(tag))
			{
				tagToFluidsMap.put(tag, new HashSet<Fluid>());
			}
			tagToFluidsMap.get(tag).add(fluid);
		}
	}

//	@Override
	public Collection<FluidTag> getFluidTags(@Nullable Fluid fluid)
	{
		if (fluid == null) return null;
		return fluidToTagsMap.get(fluid);
	}

//	@Override
	public Collection<FluidTag> getFluidTags(@Nullable FluidStack stack)
	{
		if (stack == null) return null;
		return getFluidTags(stack.getFluid());
	}

//	@Override
	public boolean hasFluidTags(@Nonnull Fluid fluid, @Nonnull FluidTag... tags)
	{
		final Set<FluidTag> fluidTags = fluidToTagsMap.get(fluid);
		if (fluidTags == null) return false;
		for (FluidTag tag : tags)
		{
			if (!fluidTags.contains(tag)) return false;
		}
		return true;
	}

//	@Override
	public Collection<Fluid> getFluidsByTags(@Nonnull List<FluidTag> tags)
	{
		final Set<Fluid> fluids = new HashSet<Fluid>();
		if (tags.size() > 0)
		{
			final Set<Fluid> prim = tagToFluidsMap.get(tags.get(0));
			if (prim != null)
			{
				fluids.addAll(prim);
				for (int i = 1; i < tags.size(); ++i)
				{
					if (fluids.isEmpty()) break;
					final Set<Fluid> exc = tagToFluidsMap.get(tags.get(i));
					fluids.retainAll(exc);
				}
			}
		}
		return fluids;
	}

//	@Override
	public Collection<Fluid> getFluidsByTags(@Nonnull FluidTag... tags)
	{
		return getFluidsByTags(Arrays.asList(tags));
	}
}
