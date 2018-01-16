package growthcraft.milk.common.item;

import growthcraft.core.api.nbt.NBTHelper;
import growthcraft.core.common.item.IItemTileBlock;
import growthcraft.milk.api.definition.EnumCheeseStage;
import growthcraft.milk.api.definition.ICheeseType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBlockCheeseBlock extends ItemBlock implements IItemTileBlock
{
	public ItemBlockCheeseBlock(Block block)
	{
		super(block);
		this.maxStackSize = 1;
	}

	private NBTTagCompound getTileTagCompoundABS(ItemStack stack)
	{
		final NBTTagCompound tag = NBTHelper.openItemStackTag(stack);
		if (!tag.hasKey("te_cheese_block"))
		{
			final NBTTagCompound cheeseTag = new NBTTagCompound();
			final ICheeseType cheese = EnumCheeseType.getSafeById(EnumCheeseType.getTypeFromMeta(stack.getItemDamage()));
			final EnumCheeseStage stage = EnumCheeseType.getStageFromMeta(stack.getItemDamage());
			cheese.writeToNBT(cheeseTag);
			stage.writeToNBT(cheeseTag);
			tag.setTag("te_cheese_block", cheeseTag);
		}
		return tag.getCompoundTag("te_cheese_block");
	}

	@Override
	public void setTileTagCompound(ItemStack stack, NBTTagCompound tileTag)
	{
		final NBTTagCompound tag = NBTHelper.openItemStackTag(stack);
		tag.setTag("te_cheese_block", tileTag);
	}

	@Override
	public NBTTagCompound getTileTagCompound(ItemStack stack)
	{
		final NBTTagCompound tag = getTileTagCompoundABS(stack);
		final ICheeseType type = getCheeseType(stack);
		final EnumCheeseStage stage = getCheeseStage(stack);
		int cheeseMeta = type.getMetaForStage(stage);
		if (stack.getItemDamage() != cheeseMeta)
		{
			stack.setItemDamage(cheeseMeta);
		}
		return tag;
	}

	public ICheeseType getCheeseType(ItemStack stack)
	{
		final NBTTagCompound tag = getTileTagCompoundABS(stack);
		return EnumCheeseType.loadFromNBT(tag);
	}

	public EnumCheeseStage getCheeseStage(ItemStack stack)
	{
		final NBTTagCompound tag = getTileTagCompoundABS(stack);
		return EnumCheeseStage.loadFromNBT(tag);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) +
			"." + getCheeseType(stack).getName() +
			"." + getCheeseStage(stack).getName();
	}

	public int getSlices(ItemStack stack)
	{
		return getTileTagCompound(stack).getInteger("slices");
	}

	public int getSlicesMax(ItemStack stack)
	{
		return getTileTagCompound(stack).getInteger("slices_max");
	}

	public static NBTTagCompound openNBT(ItemStack stack)
	{
		final Item item = stack.getItem();
		if (item instanceof ItemBlockCheeseBlock)
		{
			return ((ItemBlockCheeseBlock)item).getTileTagCompound(stack);
		}
		else
		{
			// throw error
		}
		return null;
	}
}
