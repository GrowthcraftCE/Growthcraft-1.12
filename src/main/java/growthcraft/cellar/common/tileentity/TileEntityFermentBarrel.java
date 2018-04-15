package growthcraft.cellar.common.tileentity;

import java.io.IOException;

import growthcraft.cellar.GrowthcraftCellarConfig;
import growthcraft.cellar.common.inventory.ContainerFermentBarrel;
import growthcraft.cellar.common.lib.CellarRegistry;
import growthcraft.cellar.common.lib.processing.fermenting.IFermentationRecipe;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.fluids.FluidTest;
import growthcraft.core.shared.fluids.GrowthcraftFluidUtils;
import growthcraft.core.shared.io.nbt.INBTItemSerializable;
import growthcraft.core.shared.io.nbt.NBTHelper;
import growthcraft.core.shared.inventory.GrowthcraftInternalInventory;
import growthcraft.core.shared.inventory.InventoryProcessor;
import growthcraft.core.shared.tileentity.event.TileEventHandler;
import growthcraft.core.shared.tileentity.feature.ITileProgressiveDevice;
import growthcraft.cellar.common.fluids.CellarTank;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileEntityFermentBarrel extends TileEntityCellarDevice implements ITickable, ITileProgressiveDevice, INBTItemSerializable
{
	public static enum FermentBarrelDataID
	{
		TIME,
		TIME_MAX,
		UNKNOWN;

		public static final FermentBarrelDataID[] VALID = new FermentBarrelDataID[]
		{
			TIME,
			TIME_MAX
		};

		public static FermentBarrelDataID getByaOrdinal(int ord)
		{
			if (ord >= 0 && ord <= VALID.length) return VALID[ord];
			return UNKNOWN;
		}
	}

	// Constants
	private static final int[] accessableSlotIds = new int[] {0};

	// Other Vars.
	protected int time;
	private int timemax = GrowthcraftCellarConfig.fermentTime;
	private boolean shouldUseCachedRecipe = GrowthcraftCellarConfig.fermentBarrelUseCachedRecipe;
	private boolean recheckRecipe = true;
	private boolean lidOn = true;
	private IFermentationRecipe activeRecipe;

	@Override
	protected FluidTank[] createTanks()
	{
		return new FluidTank[] { new CellarTank(GrowthcraftCellarConfig.fermentBarrelMaxCap, this) };
	}

	@Override
	public GrowthcraftInternalInventory createInventory()
	{
		return new GrowthcraftInternalInventory(this, 2);
	}

	@Override
	public String getDefaultInventoryName()
	{
		return "container.grc.fermentBarrel";
	}

	@Override
	public String getGuiID()
	{
		return "growthcraft_cellar:ferment_barrel";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerFermentBarrel(playerInventory, this);
	}

	protected void markForRecipeRecheck()
	{
		this.recheckRecipe = true;
	}

	/**
	 * @return time was reset, false otherwise
	 */
	protected boolean resetTime()
	{
		if (time != 0)
		{
			this.time = 0;
			return true;
		}
		return false;
	}

	private IFermentationRecipe loadRecipe()
	{
		return CellarRegistry.instance().fermenting().findRecipe(getFluidStack(0), getStackInSlot(0));
	}

	private IFermentationRecipe refreshRecipe()
	{
		final IFermentationRecipe recipe = loadRecipe();
		if (recipe != null && recipe != activeRecipe)
		{
			if (activeRecipe != null)
			{
				resetTime();
			}
			this.activeRecipe = recipe;
			markDirty();
		}
		else
		{
			if (activeRecipe != null)
			{
				this.activeRecipe = null;
				resetTime();
				markDirty();
			}
		}
		return activeRecipe;
	}

	private IFermentationRecipe getWorkingRecipe()
	{
		if (shouldUseCachedRecipe)
		{
			if (activeRecipe == null) refreshRecipe();
			return activeRecipe;
		}
		return loadRecipe();
	}

	public int getTime()
	{
		return this.time;
	}

	public int getTimeMax()
	{
		// if this is the server, just return the recipe time
		// clients will have their timemax synced from the server in the gui
		if (!world.isRemote)
		{
			final IFermentationRecipe result = getWorkingRecipe();
			if (result != null)
			{
				return result.getTime();
			}
		}
		return this.timemax;
	}

	private boolean canFerment()
	{
		if (getStackInSlot(0) == null) return false;
		if (isFluidTankEmpty(0)) return false;
		return getWorkingRecipe() != null;
	}

	public void fermentItem()
	{
		final ItemStack fermentItem = getStackInSlot(0);
		if (fermentItem != null)
		{
			final IFermentationRecipe recipe = getWorkingRecipe();
			if (recipe != null)
			{
				final FluidStack outputFluidStack = recipe.getOutputFluidStack();
				if (outputFluidStack != null)
				{
					getFluidTank(0).setFluid(GrowthcraftFluidUtils.exchangeFluid(getFluidStack(0), outputFluidStack.getFluid()));
				}
				final IMultiItemStacks fermenter = recipe.getFermentingItemStack();
				if (fermenter != null && !fermenter.isEmpty())
				{
					decrStackSize(0, fermenter.getStackSize());
				}
			}
		}
	}

	@Override
	public float getDeviceProgress()
	{
		final int tmx = getTimeMax();
		if (tmx > 0)
		{
			return (float)time / (float)tmx;
		}
		return 0.0f;
	}

	@Override
	public int getDeviceProgressScaled(int scale)
	{
		final int tmx = getTimeMax();
		if (tmx > 0)
		{
			return this.time * scale / tmx;
		}
		return 0;
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			if (recheckRecipe)
			{
				this.recheckRecipe = false;
				refreshRecipe();
			}

			if (canFerment())
			{
				this.time++;

				if (time >= getTimeMax())
				{
					resetTime();
					fermentItem();
					markDirty();
				}
			}
			else
			{
				if (time != 0)
				{
					resetTime();
					markDirty();
				}
			}
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return accessableSlotIds;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemstack)
	{
		return index == 0;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side)	{
		return InventoryProcessor.instance().canInsertItem(this, stack, slot);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side)
	{
		return InventoryProcessor.instance().canExtractItem(this, stack, slot);
	}

	@Override
	protected void readTanksFromNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("Tank"))
		{
			getFluidTank(0).readFromNBT(nbt.getCompoundTag("Tank"));
		}
		else
		{
			super.readTanksFromNBT(nbt);
		}
	}

	private void readFermentTimeFromNBT(NBTTagCompound nbt)
	{
		this.time = NBTHelper.getInteger(nbt, "time");
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readFermentTimeFromNBT(nbt);
		if (nbt.hasKey("lid_on"))
		{
			this.lidOn = nbt.getBoolean("lid_on");
		}
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_READ)
	public void readFromNBT_FermentBarrel(NBTTagCompound nbt)
	{
		readFermentTimeFromNBT(nbt);
	}

	private void writeFermentTimeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("time", time);
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeFermentTimeToNBT(nbt);
		nbt.setBoolean("lid_on", lidOn);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_WRITE)
	public void writeToNBT_FermentBarrel(NBTTagCompound nbt)
	{
		writeFermentTimeToNBT(nbt);
	}

	@Override
	public void receiveGUINetworkData(int id, int v)
	{
		super.receiveGUINetworkData(id, v);
		switch (FermentBarrelDataID.getByaOrdinal(id))
		{
			case TIME:
				this.time = v;
				break;
			case TIME_MAX:
				this.timemax = v;
				break;
			default:
				// should warn about invalid Data ID
				break;
		}
	}

	@Override
	public void sendGUINetworkData(Container container, IContainerListener iCrafting)
	{
		super.sendGUINetworkData(container, iCrafting);
		iCrafting.sendWindowProperty(container, FermentBarrelDataID.TIME.ordinal(), time);
		iCrafting.sendWindowProperty(container, FermentBarrelDataID.TIME_MAX.ordinal(), getTimeMax());
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_FermentBarrel(ByteBuf stream) throws IOException
	{
		this.time = stream.readInt();
		this.timemax = stream.readInt();
		return false;
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_FermentBarrel(ByteBuf stream) throws IOException
	{
		stream.writeInt(time);
		stream.writeInt(getTimeMax());
		return false;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid)
	{
		final FluidStack fluidStack = getFluidStack(0);
		if (fluidStack == null || fluidStack.getFluid() == null) return true;
		return FluidTest.fluidMatches(fluidStack, fluid);
	}

	@Override
	protected int doFill(EnumFacing from, FluidStack resource, boolean shouldFill)
	{
		return fillFluidTank(0, resource, shouldFill);
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid)
	{
		return FluidTest.fluidMatches(getFluidStack(0), fluid);
	}

	@Override
	protected FluidStack doDrain(EnumFacing from, int maxDrain, boolean shouldDrain)
	{
		return drainFluidTank(0, maxDrain, shouldDrain);
	}

	@Override
	protected FluidStack doDrain(EnumFacing from, FluidStack resource, boolean shouldDrain)
	{
		if (resource == null || !resource.isFluidEqual(getFluidStack(0)))
		{
			return null;
		}
		return doDrain(from, resource.amount, shouldDrain);
	}

	@Override
	protected void markFluidDirty()
	{
		super.markFluidDirty();
		markForRecipeRecheck();
	}

	@Override
	public void onInventoryChanged(IInventory inv, int index)
	{
		super.onInventoryChanged(inv, index);
		markForRecipeRecheck();
	}
}