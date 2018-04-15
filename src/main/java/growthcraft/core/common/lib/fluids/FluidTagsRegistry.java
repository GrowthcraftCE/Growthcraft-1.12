package growthcraft.core.common.lib.fluids;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import growthcraft.core.common.lib.definition.DuplicateRegistrationException;

public class FluidTagsRegistry
{
	// REVISE_ME 0
	
	private Map<String, FluidTag> nameToTag = new HashMap<String, FluidTag>();

//	@Override
	public void registerTag(@Nonnull FluidTag tag)
	{
		if (nameToTag.containsKey(tag.getName()))
		{
			throw DuplicateRegistrationException.newFor(tag);
		}
		nameToTag.put(tag.getName(), tag);
	}

//	@Override
	public FluidTag createTag(@Nonnull String name)
	{
		final FluidTag tag = new FluidTag(name);
		registerTag(tag);
		return tag;
	}

//	@Override
	public Collection<String> getNames()
	{
		return nameToTag.keySet();
	}

//	@Override
	public Collection<FluidTag> getTags()
	{
		return nameToTag.values();
	}

//	@Override
	public FluidTag findTag(@Nonnull String name)
	{
		return nameToTag.get(name);
	}

//	@Override
	public List<FluidTag> expandTagNames(@Nonnull List<String> tagNames)
	{
		final List<FluidTag> tags = new ArrayList<FluidTag>();
		for (String name : tagNames)
			tags.add(findTag(name));
		return tags;
	}
}
