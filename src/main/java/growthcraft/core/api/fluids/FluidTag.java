package growthcraft.core.api.fluids;

import javax.annotation.Nonnull;

import net.minecraft.client.resources.I18n;

public class FluidTag {
	// REVISE_ME 0
	
	private final String name;

	public FluidTag(@Nonnull String n)
	{
		this.name = n;
	}

	public String getName()
	{
		return name;
	}

	public String getUnlocalizedName()
	{
		return "grc.fluids.tag." + getName();
	}

	public String getLocalizedName()
	{
		return I18n.format(getUnlocalizedName());
	}

	public String toString()
	{
		return getName();
	}
}
