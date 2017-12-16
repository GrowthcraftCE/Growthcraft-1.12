package growthcraft.cellar.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.api.CellarRegistry;
import growthcraft.cellar.api.booze.BoozeEffect;
import growthcraft.cellar.api.processing.common.Residue;
import growthcraft.core.api.CoreRegistry;
import growthcraft.core.api.definition.IMultiFluidStacks;
import growthcraft.core.api.fluids.FluidTag;
import growthcraft.core.api.utils.MultiStacksUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * If you find yourself making some seriously gnarly spaghetti code, this may
 * save you.
 */
public class CellarBoozeBuilder implements ICellarBoozeBuilder
{
	protected Fluid fluid;

	public CellarBoozeBuilder(@Nonnull Fluid f)
	{
		this.fluid = f;
	}

	public Fluid getFluid()
	{
		return fluid;
	}

	@Override
	public ICellarBoozeBuilder tags(FluidTag... tags)
	{
		CoreRegistry.instance().fluidDictionary().addFluidTags(fluid, tags);
		return this;
	}

	@Override
	public ICellarBoozeBuilder brewsTo(@Nonnull FluidStack result, @Nonnull Object stack, int time, @Nullable Residue residue)
	{
		CellarRegistry.instance().brewing().addRecipe(new FluidStack(fluid, result.amount), stack, result, time, residue);
		return this;
	}

	@Override
	public ICellarBoozeBuilder brewsFrom(@Nonnull FluidStack src, @Nonnull Object stack, int time, @Nullable Residue residue)
	{
		CellarRegistry.instance().brewing().addRecipe(src, stack, new FluidStack(fluid, src.amount), time, residue);
		return this;
	}

	@Override
	public ICellarBoozeBuilder fermentsTo(@Nonnull FluidStack result, @Nonnull Object stack, int time)
	{
		CellarRegistry.instance().fermenting().addRecipe(result, new FluidStack(fluid, result.amount), stack, time);
		return this;
	}

	@Override
	public ICellarBoozeBuilder fermentsFrom(@Nonnull Object srcFluid, @Nonnull Object stack, int time)
	{
		final IMultiFluidStacks src = MultiStacksUtil.toMultiFluidStacks(srcFluid);
		CellarRegistry.instance().fermenting().addRecipe(new FluidStack(fluid, src.getAmount()), src, stack, time);
		return this;
	}

	@Override
	public ICellarBoozeBuilder pressesFrom(@Nonnull Object stack, int time, int amount, @Nullable Residue residue)
	{
		CellarRegistry.instance().pressing().addRecipe(stack, new FluidStack(fluid, amount), time, residue);
		return this;
	}

	@Override
	public ICellarBoozeBuilder culturesTo(int amount, @Nonnull ItemStack stack, float heat, int time)
	{
		CellarRegistry.instance().culturing().addRecipe(new FluidStack(fluid, amount), stack, heat, time);
		return this;
	}

	@Override
	public BoozeEffect getEffect()
	{
		return CellarRegistry.instance().booze().getEffect(fluid);
	}

	public static CellarBoozeBuilder create(Fluid f)
	{
		return new CellarBoozeBuilder(f);
	}
}
