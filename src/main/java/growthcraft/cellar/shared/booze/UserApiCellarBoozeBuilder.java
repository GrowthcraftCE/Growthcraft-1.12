package growthcraft.cellar.shared.booze;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.shared.config.UserApis;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.core.shared.definition.IMultiFluidStacks;
import growthcraft.core.shared.item.MultiStacksUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * This is a variant to the CellarBoozeBuilder provided in the API
 * This version will route all its registrations to the User API instead
 * of the internal registries.
 */
public class UserApiCellarBoozeBuilder extends CellarBoozeBuilder
{
	private UserApis userApis;

	public UserApiCellarBoozeBuilder(@Nonnull UserApis apis, @Nonnull Fluid fluid)
	{
		super(fluid);
		this.userApis = apis;
	}

	@Override
	public ICellarBoozeBuilder brewsTo(@Nonnull FluidStack result, @Nonnull Object stack, boolean requiresLid, int time, @Nullable Residue residue)
	{
		this.userApis.getUserBrewingRecipes().addDefault(stack, new FluidStack(fluid, result.amount), result, requiresLid, residue, time);
		return this;
	}

	@Override
	public ICellarBoozeBuilder brewsFrom(@Nonnull FluidStack src, @Nonnull Object stack, boolean requiresLid, int time, @Nullable Residue residue)
	{
		this.userApis.getUserBrewingRecipes().addDefault(stack, src, new FluidStack(fluid, src.amount), requiresLid, residue, time);
		return this;
	}
	
	@Override
	public ICellarBoozeBuilder brewsToFallback(@Nonnull FluidStack result, int time, @Nullable Residue residue)
	{
		this.userApis.getUserBrewingRecipes().addFallbackDefault(new FluidStack(fluid, result.amount), result, residue, time);
		return this;
	}

	@Override
	public ICellarBoozeBuilder brewsFromFallback(@Nonnull FluidStack src, int time, @Nullable Residue residue)
	{
		this.userApis.getUserBrewingRecipes().addFallbackDefault(src, new FluidStack(fluid, src.amount), residue, time);
		return this;
	}

	@Override
	public ICellarBoozeBuilder fermentsTo(@Nonnull FluidStack result, @Nonnull Object stack, int time)
	{
		this.userApis.getUserFermentingRecipes().addDefault(stack, new FluidStack(fluid, result.amount), result, time);
		return this;
	}

	@Override
	public ICellarBoozeBuilder fermentsFrom(@Nonnull Object srcFluid, @Nonnull Object stack, int time)
	{
		final IMultiFluidStacks src = MultiStacksUtil.toMultiFluidStacks(srcFluid);
		this.userApis.getUserFermentingRecipes().addDefault(stack, src, new FluidStack(fluid, src.getAmount()), time);
		return this;
	}
	
	@Override
	public ICellarBoozeBuilder fermentsToFallback(@Nonnull FluidStack result, int time) {
		this.userApis.getUserFermentingRecipes().addFallbackDefault(new FluidStack(fluid, result.amount), result, time);
		return this;
	}
	
	@Override
	public ICellarBoozeBuilder fermentsFromFallback(@Nonnull Object srcFluid, int time) {
		final IMultiFluidStacks src = MultiStacksUtil.toMultiFluidStacks(srcFluid);
		this.userApis.getUserFermentingRecipes().addFallbackDefault(src, new FluidStack(fluid, src.getAmount()), time);
		return this;
	}


	@Override
	public ICellarBoozeBuilder pressesFrom(@Nonnull Object stack, int time, int amount, @Nullable Residue residue)
	{
		this.userApis.getUserPressingRecipes().addDefault(stack, new FluidStack(fluid, amount), time, residue);
		return this;
	}

	@Override
	public ICellarBoozeBuilder culturesTo(int amount, @Nonnull ItemStack stack, float heat, int time)
	{
		this.userApis.getUserCultureRecipes().addDefault(new FluidStack(fluid, amount), stack, heat, time);
		return this;
	}
}