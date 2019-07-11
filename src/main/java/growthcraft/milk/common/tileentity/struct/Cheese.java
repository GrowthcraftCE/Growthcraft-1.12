package growthcraft.milk.common.tileentity.struct;

import growthcraft.core.shared.io.stream.IStreamable;
import growthcraft.milk.GrowthcraftMilk;
import growthcraft.milk.common.item.ItemBlockCheeseBlock;
import growthcraft.milk.shared.MilkRegistry;
import growthcraft.milk.shared.cheese.CheeseIO;
import growthcraft.milk.shared.cheese.CheeseUtils;
import growthcraft.milk.shared.config.GrowthcraftMilkConfig;
import growthcraft.milk.shared.definition.EnumCheeseStage;
import growthcraft.milk.shared.definition.ICheeseBlockStackFactory;
import growthcraft.milk.shared.definition.ICheeseType;
import growthcraft.milk.shared.init.GrowthcraftMilkItems.WaxedCheeseTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;

public class Cheese implements IStreamable
{
	public boolean needClientUpdate = true;

	private int ageMax = GrowthcraftMilkConfig.cheeseMaxAge;
	private int age;
	private int topSlicesMax = 4; // GrowthcraftMilkConfig.cheeseMaxSlices;
	private int topSlices = 4; // GrowthcraftMilkConfig.cheeseMaxSlices;
	private boolean isDoubleStacked = false;
	private final int cheesePerSlice = GrowthcraftMilkConfig.cheeseItemPerBlockSlice;
	private ICheeseType cheese = WaxedCheeseTypes.CHEDDAR;
	private EnumCheeseStage cheeseStage = EnumCheeseStage.UNWAXED; // EnumCheeseType.CHEDDAR.stages.get(0);
	
	public Cheese() {
		
	}

	public ICheeseType getType()
	{
		return cheese;
	}

	public EnumCheeseStage getStage()
	{
		return cheeseStage;
	}
	
	public Cheese setStage(EnumCheeseStage stage)
	{
		this.cheeseStage = stage;
		this.needClientUpdate = true;
		return this;
	}
	
	public Cheese setDoubleStacked(boolean isDoubleStacked) {
		if( this.isDoubleStacked != isDoubleStacked ) {
			this.age = 0;	// HACK: Reset age if changed. Change if different stack elements can have different age
			this.isDoubleStacked = isDoubleStacked;
		}
		return this;
	}

	public int getId()
	{
		return MilkRegistry.instance().cheese().getCheeseId(cheese);
	}

	public int getStageId()
	{
		return cheeseStage.index;
	}

	public int getSlices() {
		return (isDoubleStacked?topSlicesMax:0) + topSlices;
	}
	
	public int getTopSlices()
	{
		return topSlices;
	}

	public int getTopSlicesMax()
	{
		return topSlicesMax;
	}

	public boolean hasSlices()
	{
		return getSlices() > 0;
	}
	
	public boolean isDoubleStacked() {
		return isDoubleStacked;
	}

	public float getAgeProgress()
	{
		return (float)age / (float)ageMax;
	}

	public boolean canAge()
	{
		return cheeseStage == EnumCheeseStage.UNAGED;
	}

	public boolean isAged()
	{
		return cheeseStage == EnumCheeseStage.AGED ||
			cheeseStage == EnumCheeseStage.CUT;
	}

	public boolean isUnwaxed()
	{
		return cheeseStage == EnumCheeseStage.UNWAXED;
	}

	public ItemStack yankSlices(int count, boolean doYank)
	{
		final int yankedCount = MathHelper.clamp(count, 0, getSlices());
		final int quantity = yankedCount * cheesePerSlice;
		if (quantity > 0)
		{
			if (doYank)
			{
				if( this.topSlices < yankedCount ) {
					if( isDoubleStacked ) {
						this.topSlices = this.topSlicesMax + this.topSlices - yankedCount;
						isDoubleStacked = false;
					}
					else {
						// Should never happen. Something is wrong with getSlices() implementation
						GrowthcraftMilk.logger.warn("Something is wrong with cheese yanking. Please report as bug.");
						return ItemStack.EMPTY;
					}
				}
				else
					this.topSlices -= yankedCount;
				setStage(EnumCheeseStage.CUT);
			}
			return cheese.getCheeseItems().asStack(quantity);
		}
		return ItemStack.EMPTY;
	}
	
	public boolean stackWheel(ItemStack stack, boolean doStack) {
		if( isDoubleStacked )
			return false;
		
		if( stack.getItem() == getBlockTopItemStack().getItem() ) {
			int meta = stack.getMetadata();
			
			EnumCheeseStage stage = CheeseUtils.getStageFromMeta(meta);
			if( stage != cheeseStage ) {
				if( stage != EnumCheeseStage.CUT && stage != EnumCheeseStage.AGED )
					return false;
				if( cheeseStage != EnumCheeseStage.CUT && cheeseStage != EnumCheeseStage.AGED )
					return false;
			}

			int variantID = CheeseUtils.getVariantIDFromMeta(meta);
			if( variantID != cheese.getVariantID() )
				return false;

			// Do stacking
			int curSlices = CheeseUtils.getTopSlicesFromMeta(meta);
			if( curSlices != topSlicesMax ) {
				if( topSlices != topSlicesMax )
					return false;	// At least one of them must be a full wheel!
				
				if( doStack ) {
					isDoubleStacked = true;
					topSlices = curSlices;
				}
			}
			else {
				if( doStack )
					isDoubleStacked = true;
			}
			
			return true;
		}
		return false;
	}

	public ItemStack asFullStack()
	{
		return yankSlices(getSlices(), false);
	}
	
	public ItemStack getBlockTopItemStack() {
		return cheese.getCheeseBlocks().asStackForStage(topSlices, cheeseStage);
	}

	public int canWaxing(ItemStack stack)
	{
		if (isUnwaxed() && getType().canWax(stack))
		{
			int requiredAmount = isDoubleStacked?2:1;
			if( stack.getCount() >= requiredAmount )
				return requiredAmount;
		}
		return 0;
	}
	
	public int canStack(ItemStack stack) {
		return stackWheel(stack, false) ? 1 : 0;
	}

	public void writeToNBT(NBTTagCompound nbt)
	{
		CheeseIO.writeToNBT(nbt, cheese);
		cheeseStage.writeToNBT(nbt);
		nbt.setInteger("age", age);
		nbt.setInteger("slices", topSlices);
		nbt.setInteger("slices_max", topSlicesMax);
		nbt.setBoolean("double_stacked", isDoubleStacked);
	}

	/**
	 * When the tileentity is reloaded from an ItemStack
	 *
	 * @param nbt  tag compound to read
	 */
	public void readFromNBT(NBTTagCompound nbt)
	{
		this.cheese = CheeseIO.loadFromNBT(nbt);
		this.cheeseStage = EnumCheeseStage.loadFromNBT(nbt);
		if (nbt.hasKey("age"))
		{
			this.age = nbt.getInteger("age");
		}
		if (nbt.hasKey("slices"))
		{
			this.topSlices = nbt.getInteger("slices");
		}
		if (nbt.hasKey("slices_max"))
		{
			this.topSlicesMax = nbt.getInteger("slices_max");
		}
		if (nbt.hasKey("double_stacked"))
		{
			this.isDoubleStacked = nbt.getBoolean("double_stacked");
		}
	}

	@Override
	public boolean readFromStream(ByteBuf stream)
	{
		this.cheese = CheeseIO.loadFromStream(stream);
		this.cheeseStage = EnumCheeseStage.loadFromStream(stream);
		this.age = stream.readInt();
		this.topSlices = stream.readInt();
		this.topSlicesMax = stream.readInt();
		this.isDoubleStacked = stream.readBoolean();
		return false;
	}

	@Override
	public boolean writeToStream(ByteBuf stream)
	{
		CheeseIO.writeToStream(stream, cheese);
		cheeseStage.writeToStream(stream);
		stream.writeInt(age);
		stream.writeInt(topSlices);
		stream.writeInt(topSlicesMax);
		stream.writeBoolean(isDoubleStacked);
		return false;
	}

	public void update()
	{
		if (!isAged())
		{
			if (canAge())
			{
				if (this.age < this.ageMax)
				{
					this.age += 1;
				}
				else
				{
					setStage(EnumCheeseStage.AGED);
				}
			}
		}
	}
}
