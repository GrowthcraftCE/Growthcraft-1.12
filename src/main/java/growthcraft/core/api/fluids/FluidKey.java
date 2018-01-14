package growthcraft.core.api.fluids;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.api.definition.IFluidStackFactory;
import growthcraft.core.api.nbt.NBTHelper;
import growthcraft.core.api.utils.HashKey;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * As the name implies, this class is used in place of a List for Fluid keys
 */
public class FluidKey extends HashKey implements IFluidStackFactory
{
	public final Fluid fluid;
	public final NBTTagCompound compoundTag;

	/**
	 * @param pFluid - the fluid to set
	 * @param tag - a tag compound to set
	 *   If the tag hasNoTags(), the FluidKey will set the compoundTag to null
	 */
	public FluidKey(@Nonnull Fluid pfluid, @Nullable NBTTagCompound tag)
	{
		super();
		this.fluid = pfluid;
		this.compoundTag = NBTHelper.compoundTagPresence(tag);
		generateHashCode();
	}

	public FluidKey(@Nonnull Fluid pfluid)
	{
		this(pfluid, null);
	}

	public FluidKey(@Nonnull FluidStack pfluidStack)
	{
		this(pfluidStack.getFluid(), pfluidStack.tag);
	}

	private void generateHashCode()
	{
		this.hash = fluid.hashCode();
		this.hash = 31 * hash + (compoundTag != null ? compoundTag.hashCode() : 0);
	}

	@Override
	public FluidStack asFluidStack(int size)
	{
		return new FluidStack(fluid, size, NBTHelper.copyCompoundTag(compoundTag));
	}

	@Override
	public FluidStack asFluidStack()
	{
		return asFluidStack(1);
	}
}
