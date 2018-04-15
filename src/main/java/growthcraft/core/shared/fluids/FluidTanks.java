package growthcraft.core.shared.fluids;

import growthcraft.core.shared.io.nbt.INBTSerializableContext;
import growthcraft.core.shared.io.stream.IStreamable;
import growthcraft.core.shared.io.stream.StreamUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class FluidTanks implements IFluidTanks, INBTSerializableContext, IStreamable
{
	// REVISE_ME Multiple tanks are already supported by MC1.11.2 or newer. Remove this one!
	
	private FluidTank[] tanks;

	public FluidTanks(FluidTank[] ts)
	{
		this.tanks = ts;
	}

	public int getTankCount()
	{
		return tanks.length;
	}

	public FluidTankInfo[] getTankInfo(EnumFacing from)
	{
		final FluidTankInfo[] tankInfos = new FluidTankInfo[tanks.length];
		for (int i = 0; i < tanks.length; ++i)
		{
			tankInfos[i] = tanks[i].getInfo();
		}
		return tankInfos;
	}
	
	public IFluidTankProperties[] getTankProperties(EnumFacing from) {
		// count tank properties from all tanks
		int propCount = 0;
		for( FluidTank tank : tanks )
			propCount += tank.getTankProperties().length;
		IFluidTankProperties[] summedProps = new IFluidTankProperties[propCount];
		
		int i = 0;
		for( FluidTank tank : tanks ) {
			for( IFluidTankProperties prop : tank.getTankProperties() ) {
				summedProps[i ++] = prop;
			}
		}
		
		return summedProps;
	}

	@Override
	public FluidTank[] getFluidTanks()
	{
		return tanks;
	}

	@Override
	public FluidTank getFluidTank(int slot)
	{
		return tanks[slot];
	}

	@Override
	public void clearTank(int slot)
	{
		tanks[slot].setFluid(null);
	}

	@Override
	public int getFluidAmountScaled(int scalar, int slot)
	{
		final int cap = tanks[slot].getCapacity();
		if (cap <= 0) return 0;
		return this.getFluidAmount(slot) * scalar / cap;
	}

	@Override
	public float getFluidAmountRate(int slot)
	{
		final int cap = tanks[slot].getCapacity();
		if (cap <= 0) return 0;
		return (float)this.getFluidAmount(slot) / (float)cap;
	}

	@Override
	public boolean isFluidTankFilled(int slot)
	{
		return this.getFluidAmount(slot) > 0;
	}

	@Override
	public boolean isFluidTankFull(int slot)
	{
		return this.getFluidAmount(slot) >= tanks[slot].getCapacity();
	}

	@Override
	public boolean isFluidTankEmpty(int slot)
	{
		return this.getFluidAmount(slot) <= 0;
	}

	@Override
	public int getFluidAmount(int slot)
	{
		return tanks[slot].getFluidAmount();
	}

	@Override
	public FluidStack getFluidStack(int slot)
	{
		return tanks[slot].getFluid();
	}

	@Override
	public FluidStack drainFluidTank(int slot, int amount, boolean doDrain)
	{
		return tanks[slot].drain(amount, doDrain);
	}

	@Override
	public int fillFluidTank(int slot, FluidStack fluid, boolean doFill)
	{
		return tanks[slot].fill(fluid, doFill);
	}

	@Override
	public void setFluidStack(int slot, FluidStack stack)
	{
		tanks[slot].setFluid(stack);
	}

	@Override
	public Fluid getFluid(int slot)
	{
		final FluidStack stack = getFluidStack(slot);
		if (stack == null) return null;
		return stack.getFluid();
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		for (int i = 0; i < tanks.length; i++)
		{
			tanks[i].setFluid(null);
			if (nbt.hasKey("Tank" + i))
			{
				tanks[i].readFromNBT(nbt.getCompoundTag("Tank" + i));
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, String name)
	{
		if (nbt.hasKey(name))
		{
			readFromNBT(nbt.getCompoundTag(name));
		}
		else
		{
			// log error
		}
	}

	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("tank_count", tanks.length);
		for (int i = 0; i < tanks.length; i++)
		{
			final NBTTagCompound tag = new NBTTagCompound();
			tanks[i].writeToNBT(tag);
			nbt.setTag("Tank" + i, tag);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, String name)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		nbt.setTag(name, tag);
	}

	@Override
	public boolean readFromStream(ByteBuf stream)
	{
		for (int i = 0; i < tanks.length; i++)
		{
			StreamUtils.readFluidTank(stream, tanks[i]);
		}
		return false;
	}

	@Override
	public boolean writeToStream(ByteBuf stream)
	{
		for (int i = 0; i < tanks.length; i++)
		{
			StreamUtils.writeFluidTank(stream, tanks[i]);
		}
		return false;
	}
}
