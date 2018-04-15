package growthcraft.milk.common.tileentity;

import growthcraft.core.api.fluids.FluidTest;
import growthcraft.core.common.block.IGrowthcraftTankOperable;
import growthcraft.core.common.inventory.GrowthcraftTileDeviceBase;
import growthcraft.core.common.tileentity.feature.ITileProgressiveDevice;
import growthcraft.milk.common.tileentity.device.Pancheon;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileEntityPancheon extends GrowthcraftTileDeviceBase implements ITickable, ITileProgressiveDevice, IPancheonTile
{
	private Pancheon pancheon = new Pancheon(this, 0, 2, 1);

	@Override
	public float getDeviceProgress()
	{
		return pancheon.getProgress();
	}

	@Override
	public int getDeviceProgressScaled(int scale)
	{
		return pancheon.getProgressScaled(scale);
	}

	/**
	 * Pancheons have 3 fluid slots, the first is its `input` slot
	 * The second slot is its `bottom` output slot
	 * And the thirs is its `top` slot
	 * Though the capacity of each is 1000 mB, the pancheon can only contain
	 * a total of 1000 mB, not 3k
	 *
	 * @return fluid tanks
	 */
	@Override
	protected FluidTank[] createTanks()
	{
		return new FluidTank[] {
			new FluidTank(1000),
			new FluidTank(1000),
			new FluidTank(1000)
		};
	}

	protected int getPresentTankIndex()
	{
		for (int i = getTankCount() - 1; i > 0; --i)
		{
			if (isFluidTankFilled(i))
			{
				return i;
			}
		}
		return 0;
	}

	/**
	 * Pancheon tanks are treated as a Stack.
	 * When a tank at the end if filled, it will be returned, if its
	 * empty then it returns the tank before it and so forth.
	 *
	 * @return the active fluid tank
	 */
	public FluidTank getPresentTank()
	{
		return getFluidTank(getPresentTankIndex());
	}

	public boolean outputTanksHaveFluid()
	{
		return isFluidTankFilled(1) || isFluidTankFilled(2);
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			pancheon.update();
		}
	}

	@Override
	protected FluidStack doDrain(EnumFacing dir, int amount, boolean doDrain)
	{
		return getPresentTank().drain(amount, doDrain);
	}

	@Override
	protected FluidStack doDrain(EnumFacing dir, FluidStack stack, boolean doDrain)
	{
		/**
		 * @todo Drain from bottom fluid tank when dir == DOWN
		 */

		if (!FluidTest.isValid(stack)) return null;
		final FluidTank tank = getPresentTank();
		final FluidStack expected = tank.getFluid();
		if (expected != null && expected.isFluidEqual(stack))
		{
			return tank.drain(stack.amount, doDrain);
		}
		return null;
	}

	@Override
	protected int doFill(EnumFacing dir, FluidStack stack, boolean doFill)
	{
		if (outputTanksHaveFluid()) return 0;
		return fillFluidTank(0, stack, doFill);
	}

	@Override
	protected void markFluidDirty()
	{
		super.markFluidDirty();
		markDirtyAndUpdate();
	}

	@Override
	public IGrowthcraftTankOperable getPancheonFluidHandler() {
		return this;
	}
}
