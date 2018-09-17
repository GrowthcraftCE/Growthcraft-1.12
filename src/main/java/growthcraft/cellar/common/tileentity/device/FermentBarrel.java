package growthcraft.cellar.common.tileentity.device;

import growthcraft.cellar.common.tileentity.TileEntityFermentBarrel;
import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.processing.fermenting.IFermentationRecipe;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.fluids.GrowthcraftFluidUtils;
import growthcraft.core.shared.io.nbt.NBTHelper;
import growthcraft.core.shared.item.ItemUtils;
import growthcraft.core.shared.tileentity.device.DeviceBase;
import growthcraft.core.shared.tileentity.device.DeviceFluidSlot;
import growthcraft.core.shared.tileentity.device.DeviceInventorySlot;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class FermentBarrel extends DeviceBase {
	
	private int timemax = GrowthcraftCellarConfig.fermentTime;
	private boolean shouldUseCachedRecipe = GrowthcraftCellarConfig.fermentBarrelUseCachedRecipe;

	protected int time;
	private boolean recheckRecipe = true;
//	private boolean lidOn = true;
	private IFermentationRecipe activeRecipe;
	
	private DeviceInventorySlot fermentSlot;
	private DeviceFluidSlot fluidSlot;

	public FermentBarrel(TileEntityFermentBarrel te, int fermentSlotId, int fluidSlotId) {
		super(te);
		this.fermentSlot = new DeviceInventorySlot(te, fermentSlotId);
		this.fluidSlot = new DeviceFluidSlot(te, fluidSlotId);
	}

	
	public void markForRecipeRecheck()
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
		return CellarRegistry.instance().fermenting().findRecipe(GrowthcraftFluidUtils.removeStackTags(fluidSlot.get()), fermentSlot.get());
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

	public void setTime(int time) {
		this.time = time;
	}
	
	public int getTime()
	{
		return this.time;
	}

	public int getTimeMax()
	{
		// if this is the server, just return the recipe time
		// clients will have their timemax synced from the server in the gui
		if (!this.getTileEntity().getWorld().isRemote)
		{
			// TODO: Shouldn't be called here set timemax according to recipe in update()
			final IFermentationRecipe result = getWorkingRecipe();
			if (result != null)
			{
				return result.getTime();
			}
		}
		return this.timemax;
	}
	
	public void setTimeMaxDefault(int timeMax) {
		this.timemax = timeMax;
	}
	
	public int getTimeMaxDefault() {
		return this.timemax;
	}

	private boolean canFerment()
	{
		if (ItemUtils.isEmpty(fermentSlot.get()) ) return false;
		if (fluidSlot.isEmpty() ) return false;
		return getWorkingRecipe() != null;
	}

	public void fermentItem()
	{
		final ItemStack fermentItem = fermentSlot.get();
		if (!ItemUtils.isEmpty(fermentItem))
		{
			final IFermentationRecipe recipe = getWorkingRecipe();
			if (recipe != null)
			{
				final FluidStack outputFluidStack = recipe.getOutputFluidStack();
				if (outputFluidStack != null)
				{
					fluidSlot.set(GrowthcraftFluidUtils.exchangeFluid(fluidSlot.get(), outputFluidStack.getFluid()));
				}
				final IMultiItemStacks fermenter = recipe.getFermentingItemStack();
				if (fermenter != null && !fermenter.isEmpty())
				{
					fermentSlot.consume(fermenter.getStackSize());
				}
			}
		}
	}
	
	public float getProgress()
	{
		final int tmx = getTimeMax();
		if (tmx > 0)
		{
			return (float)time / (float)tmx;
		}
		return 0.0f;
	}

	public int getProgressScaled(int scale)
	{
		final int tmx = getTimeMax();
		if (tmx > 0)
		{
			return this.time * scale / tmx;
		}
		return 0;
	}
	
	public void update() {
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
	
	// I/O Stuff
	
	@Override
	public void legacyReadFromNBT(NBTTagCompound data) {
		// Used to migrate NBT datasets from older saves to newest structure.
		time = 0;
		if( data.hasKey("time") )
			time = NBTHelper.getInteger(data, "time");
		// NOTE: Initialize newer fields here if any!
	}
	
//	public void readFromNBTForItem(NBTTagCompound nbt) {
//		setTime(NBTHelper.getInteger(nbt, "time"));
//	}
	
//	public void writeToNBTForItem(NBTTagCompound nbt) {
//		nbt.setInteger("time", getTime());
//	}
	
	@Override
	public void readFromNBT(NBTTagCompound data) {
		this.time = NBTHelper.getInteger(data, "time");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound data) {
		data.setInteger("time", this.time);
	}
	
	@Override
	public boolean readFromStream(ByteBuf buf) {
		this.time = buf.readInt();
		this.timemax = buf.readInt();
		return false;
	}
	
	public boolean writeToStream(ByteBuf buf) {
		buf.writeInt(this.time);
		buf.writeInt(this.timemax);	// Change of logic! Not using getTimeMax()
		return false;
	}

}
