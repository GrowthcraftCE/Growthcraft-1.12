package growthcraft.milk.common.tileentity;

import java.io.IOException;

import growthcraft.core.shared.fluids.FluidTest;
import growthcraft.core.shared.inventory.AccesibleSlots;
import growthcraft.core.shared.inventory.GrowthcraftInternalInventory;
import growthcraft.core.shared.tileentity.GrowthcraftTileDeviceBase;
import growthcraft.core.shared.tileentity.device.DeviceFluidSlot;
import growthcraft.core.shared.tileentity.device.DeviceInventorySlot;
import growthcraft.core.shared.tileentity.event.TileEventHandler;
import growthcraft.core.shared.tileentity.feature.IItemOperable;
import growthcraft.core.shared.item.ItemUtils;
import growthcraft.milk.shared.MilkRegistry;
import growthcraft.milk.shared.processing.churn.IChurnRecipe;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileEntityButterChurn extends GrowthcraftTileDeviceBase implements IItemOperable
{
	public static enum WorkState
	{
		NONE,
		CHURN,
		PRODUCE;
	}

	private static AccesibleSlots accessibleSlots = new AccesibleSlots(new int[][] {
		{ 0 },
		{   },
		{ 0 },
		{ 0 },
		{ 0 },
		{ 0 }
	});

	private int shaftState;
	private int churns;
	private DeviceFluidSlot inputFluidSlot = new DeviceFluidSlot(this, 0);
	private DeviceFluidSlot outputFluidSlot = new DeviceFluidSlot(this, 1);
	private DeviceInventorySlot outputInventorySlot = new DeviceInventorySlot(this, 0);

	@Override
	protected FluidTank[] createTanks()
	{
		return new FluidTank[] {
			// cream
			new FluidTank(1000),
			// buttermilk
			new FluidTank(1000)
		};
	}
	
	public int getShaftState() {
		return shaftState;
	}

	@Override
	public GrowthcraftInternalInventory createInventory()
	{
		return new GrowthcraftInternalInventory(this, 1);
	}

	@Override
	public String getDefaultInventoryName()
	{
		return "container.grcmilk.ButterChurn";
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemstack)
	{
		return true;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return accessibleSlots.slotsAt(side);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing side)
	{
		return accessibleSlots.sideContains(side, index);
	}

	private IChurnRecipe getWorkingRecipe()
	{
		final FluidStack stack = inputFluidSlot.get();
		if (stack != null)
		{
			final IChurnRecipe recipe = MilkRegistry.instance().churn().getRecipe(stack);
			return recipe;
		}
		return null;
	}

	public WorkState doWork()
	{
		WorkState state = WorkState.NONE;
		final IChurnRecipe recipe = getWorkingRecipe();
		if (recipe != null)
		{
			state = WorkState.CHURN;
			this.churns++;
			if (churns >= recipe.getChurns())
			{
				this.churns = 0;
				inputFluidSlot.consume(recipe.getInputFluidStack(), true);
				outputFluidSlot.fill(recipe.getOutputFluidStack(), true);
				outputInventorySlot.increaseStack(recipe.getOutputItemStack());
				state = WorkState.PRODUCE;
			}

			if (shaftState == 0)
			{
				this.shaftState = 1;
			}
			else
			{
				this.shaftState = 0;
			}
			markForUpdate();
		}
		else
		{
			if (shaftState != 0)
			{
				this.shaftState = 0;
				markForUpdate();
			}
			this.churns = 0;
		}
		return state;
	}

	private DeviceFluidSlot getActiveFluidSlot()
	{
		if (outputFluidSlot.hasContent()) return outputFluidSlot;
		return inputFluidSlot;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid)
	{
		return MilkRegistry.instance().churn().isFluidIngredient(fluid);
	}

	/**
	 * @param dir - direction to drain from
	 * @param amount - amount of fluid to drain
	 * @param doDrain - should any draining actually take place?
	 * @return fluid drained
	 */
	@Override
	protected FluidStack doDrain(EnumFacing dir, int amount, boolean doDrain)
	{
//		if (dir == EnumFacing.UP) return null;
		return getActiveFluidSlot().consume(amount, doDrain);
	}

	/**
	 * @param dir - direction to drain from
	 * @param stack - fluid stack (as filter) to drain
	 * @param doDrain - should any draining actually take place?
	 * @return fluid drained
	 */
	@Override
	protected FluidStack doDrain(EnumFacing dir, FluidStack stack, boolean doDrain)
	{
//		if (dir == EnumFacing.UP) return null;
		final DeviceFluidSlot fluidSlot = getActiveFluidSlot();
		if (FluidTest.areStacksEqual(fluidSlot.get(), stack))
		{
			return fluidSlot.consume(stack, doDrain);
		}
		return null;
	}

	/**
	 * When filling the Churn, only CREAM fluids are accepted
	 *
	 * @param dir - direction being filled from
	 * @param stack - fluid to fill with
	 * @param doFill - should we actually fill this?
	 * @return how much fluid was actually used
	 */
	@Override
	protected int doFill(EnumFacing dir, FluidStack stack, boolean doFill)
	{
//		if (dir == EnumFacing.UP) return 0;
		int result = 0;

		if (MilkRegistry.instance().churn().isFluidIngredient(stack))
		{
			result = inputFluidSlot.fill(stack, doFill);
		}

		return result;
	}

	/**
	 * Items cannot be placed into a Butter Churn.
	 *
	 * @param player - player placing the item
	 * @param stack - item stack being placed
	 * @return false
	 */
	@Override
	public boolean tryPlaceItem(IItemOperable.Action action, EntityPlayer player, ItemStack stack)
	{
		return false;
	}

	/**
	 * Attempts to remove the item in the butter churn
	 *
	 * @param player - player trying to remove the butter
	 * @param onHand - hand must be empty
	 * @return true, the item was removed, false otherwise
	 */
	@Override
	public boolean tryTakeItem(IItemOperable.Action action, EntityPlayer player, ItemStack onHand)
	{
		if (IItemOperable.Action.RIGHT != action) return false;
		final ItemStack stack = outputInventorySlot.yank();
		if (!ItemUtils.isEmpty(stack))
		{
			ItemUtils.addStackToPlayer(stack, player, false);
			return true;
		}
		return false;
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_READ)
	public void readFromNBT_ButterChurn(NBTTagCompound nbt)
	{
		this.shaftState = nbt.getInteger("shaft_state");
		this.churns = nbt.getInteger("churns");
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_WRITE)
	public void writeToNBT_ButterChurn(NBTTagCompound nbt)
	{
		nbt.setInteger("shaft_state", shaftState);
		nbt.setInteger("churns", churns);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_ButterChurn(ByteBuf stream) throws IOException
	{
		this.shaftState = stream.readInt();
		this.churns = stream.readInt();
		return false;
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_ButterChurn(ByteBuf stream) throws IOException
	{
		stream.writeInt(shaftState);
		stream.writeInt(churns);
		return false;
	}
}
