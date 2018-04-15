package growthcraft.cellar.api.booze;

import java.util.Collection;

import javax.annotation.Nonnull;

import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.fluids.FluidTag;
import net.minecraftforge.fluids.Fluid;

public class BoozeEntry {
	// REVISE_ME 0
	
	private int healAmount;
	private float saturation;
	private final Fluid fluid;
	private final BoozeEffect effect;

	public BoozeEntry(@Nonnull Fluid flus)
	{
		this.fluid = flus;
		this.effect = new BoozeEffect(fluid);
	}

	public BoozeEntry setFoodStats(int heal, float sat)
	{
		this.healAmount = heal;
		this.saturation = sat;
		return this;
	}

	public int getHealAmount()
	{
		return healAmount;
	}

	public float getSaturation()
	{
		return saturation;
	}

	public BoozeEffect getEffect()
	{
		return effect;
	}

	public Fluid getFluid()
	{
		return fluid;
	}

	public Collection<FluidTag> getTags()
	{
		return CoreRegistry.instance().fluidDictionary().getFluidTags(fluid);
	}

	public void addTags(FluidTag... newtags)
	{
		CoreRegistry.instance().fluidDictionary().addFluidTags(fluid, newtags);
	}

	public boolean hasTags(FluidTag... checktags)
	{
		return CoreRegistry.instance().fluidDictionary().hasFluidTags(fluid, checktags);
	}

}
