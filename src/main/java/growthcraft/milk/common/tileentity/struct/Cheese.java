package growthcraft.milk.common.tileentity.struct;

import growthcraft.core.shared.io.stream.IStreamable;
import growthcraft.milk.GrowthcraftMilk;
import growthcraft.milk.common.handlers.EnumHandler;
import growthcraft.milk.shared.MilkRegistry;
import growthcraft.milk.shared.cheese.CheeseIO;
import growthcraft.milk.shared.config.GrowthcraftMilkConfig;
import growthcraft.milk.shared.definition.EnumCheeseStage;
import growthcraft.milk.shared.definition.ICheeseType;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;

public class Cheese implements IStreamable
{
	public boolean needClientUpdate = true;

	private int ageMax = GrowthcraftMilkConfig.cheeseMaxAge;
	private int age;
	private int slicesMax = 4; // GrowthcraftMilkConfig.cheeseMaxSlices;
	private int slices = 4; // GrowthcraftMilkConfig.cheeseMaxSlices;
	private final int cheesePerSlice = GrowthcraftMilkConfig.cheeseItemPerBlockSlice;
	private ICheeseType cheese = EnumHandler.WaxedCheeseTypes.CHEDDAR;
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

	public int getId()
	{
		return MilkRegistry.instance().cheese().getCheeseId(cheese);
	}

	public int getStageId()
	{
		return cheeseStage.index;
	}

	public int getSlices()
	{
		return slices;
	}

	public int getSlicesMax()
	{
		return slicesMax;
	}

	public boolean hasSlices()
	{
		return getSlices() > 0;
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
				this.slices -= yankedCount;
				setStage(EnumCheeseStage.CUT);
			}
			return cheese.getCheeseItems().asStack(quantity);
		}
		return ItemStack.EMPTY;
	}

	public ItemStack asFullStack()
	{
		return yankSlices(getSlices(), false);
	}

	public boolean tryWaxing(ItemStack stack)
	{
		if (isUnwaxed() && getType().canWax(stack))
		{
			setStage(EnumCheeseStage.UNAGED);
			return true;
		}
		return false;
	}

	public void writeToNBT(NBTTagCompound nbt)
	{
		CheeseIO.writeToNBT(nbt, cheese);
		cheeseStage.writeToNBT(nbt);
		nbt.setInteger("age", age);
		nbt.setInteger("slices", slices);
		nbt.setInteger("slices_max", slicesMax);
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
			this.slices = nbt.getInteger("slices");
		}
		if (nbt.hasKey("slices_max"))
		{
			this.slicesMax = nbt.getInteger("slices_max");
		}
	}

	@Override
	public boolean readFromStream(ByteBuf stream)
	{
		this.cheese = CheeseIO.loadFromStream(stream);
		this.cheeseStage = EnumCheeseStage.loadFromStream(stream);
		this.age = stream.readInt();
		this.slices = stream.readInt();
		this.slicesMax = stream.readInt();
		return false;
	}

	@Override
	public boolean writeToStream(ByteBuf stream)
	{
		CheeseIO.writeToStream(stream, cheese);
		cheeseStage.writeToStream(stream);
		stream.writeInt(age);
		stream.writeInt(slices);
		stream.writeInt(slicesMax);
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
