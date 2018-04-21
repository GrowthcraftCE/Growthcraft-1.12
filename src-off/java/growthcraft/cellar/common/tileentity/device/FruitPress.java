package growthcraft.cellar.common.tileentity.device;

import growthcraft.cellar.common.block.BlockFruitPresser;
import growthcraft.cellar.common.block.BlockFruitPresser.PressState;
import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.cellar.shared.processing.pressing.IPressingRecipe;
import growthcraft.cellar.common.tileentity.TileEntityCellarDevice;
import growthcraft.core.shared.tileentity.device.DeviceFluidSlot;
import growthcraft.core.shared.tileentity.device.DeviceInventorySlot;
import growthcraft.core.shared.tileentity.device.DeviceProgressive;
import growthcraft.core.shared.item.ItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class FruitPress extends DeviceProgressive
{
	private float pomace;
	private DeviceFluidSlot fluidSlot;
	private DeviceInventorySlot inputSlot;
	private DeviceInventorySlot residueSlot;
	private IPressingRecipe currentResult;

	/**
	 * @param te - parent tile
	 * @param fs - fluid tank id
	 * @param is - input inventory slot id
	 * @param rs - residue inventory slot id
	 */
	public FruitPress(TileEntityCellarDevice te, int fs, int is, int rs)
	{
		super(te);
		this.fluidSlot = new DeviceFluidSlot(te, fs);
		this.inputSlot = new DeviceInventorySlot(te, is);
		this.residueSlot = new DeviceInventorySlot(te, rs);
	}

	/**
	 * @return meta - the metadata for the FruitPresser usually above the fruit press
	 */
	public boolean isPressed()
	{
		return getWorld().getBlockState(parent.getPos().up()).getValue(BlockFruitPresser.TYPE_PRESSED) == PressState.PRESSED;
	}

	private boolean preparePressing()
	{
		this.currentResult = null;
		final ItemStack primarySlotItem = inputSlot.get();
		if (primarySlotItem == null) return false;

		if (!isPressed()) return false;

		if (fluidSlot.isFull()) return false;

		final IPressingRecipe result = CellarRegistry.instance().pressing().getPressingRecipe(primarySlotItem);
		if (result == null) return false;
		if (!inputSlot.hasEnough(result.getInput())) return false;
		this.currentResult = result;
		setTimeMax(currentResult.getTime());

		if (fluidSlot.isEmpty()) return true;

		final FluidStack stack = currentResult.getFluidStack();
		return stack.isFluidEqual(fluidSlot.get());
	}

	public void producePomace()
	{
		if (currentResult == null) return;
		final Residue residue = currentResult.getResidue();
		if (residue != null)
		{
			this.pomace = this.pomace + residue.pomaceRate;
			if (this.pomace >= 1.0F)
			{
				this.pomace = this.pomace - 1.0F;
				final ItemStack residueResult = ItemUtils.mergeStacks(residueSlot.get(), residue.residueItem);
				if (!ItemUtils.isEmpty(residueResult)) residueSlot.set(residueResult);
			}
		}
	}

	public void pressItem()
	{
		if (currentResult == null) return;
		final ItemStack pressingItem = inputSlot.get();
		producePomace();
		final FluidStack fluidstack = currentResult.getFluidStack();
		fluidSlot.fill(fluidstack, true);
		inputSlot.consume(currentResult.getInput());
	}

	public void update()
	{
		if (preparePressing())
		{
			increaseTime();
			if (getTime() >= getTimeMax())
			{
				resetTime();
				pressItem();
				markDirty();
			}
		}
		else
		{
			if (resetTime()) markDirty();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound data)
	{
		super.readFromNBT(data);
		this.pomace = data.getFloat("pomace");
	}

	@Override
	public void writeToNBT(NBTTagCompound data)
	{
		super.writeToNBT(data);
		data.setFloat("pomace", pomace);
	}
}
