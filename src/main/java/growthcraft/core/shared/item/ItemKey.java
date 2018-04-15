package growthcraft.core.shared.item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.shared.definition.IItemStackFactory;
import growthcraft.core.shared.io.nbt.NBTHelper;
import growthcraft.core.shared.utils.HashKey;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * As the name implies, this class is used in place of a List for Item keys
 */
public class ItemKey extends HashKey implements IItemStackFactory
{
	public static final int WILDCARD_VALUE = OreDictionary.WILDCARD_VALUE;

	public final Item item;
	public final int meta;
	public final NBTTagCompound compoundTag;

	public ItemKey(@Nonnull Item pitem, int pmeta, @Nullable NBTTagCompound tag)
	{
		super();
		this.item = pitem;
		this.meta = pmeta;
		this.compoundTag = NBTHelper.compoundTagPresence(tag);
		generateHashCode();
	}

	public ItemKey(@Nonnull Item pitem, int pmeta)
	{
		this(pitem, pmeta, null);
	}

	public ItemKey(@Nonnull Block block, int pmeta, @Nullable NBTTagCompound tag)
	{
		super();
		final Item pitem = Item.getItemFromBlock(block);
		if (pitem == null)
		{
			throw new IllegalArgumentException("Invalid Block given for ItemKey (block=" + block + " meta=" + pmeta + ")");
		}
		this.item = pitem;
		this.meta = pmeta;
		this.compoundTag = NBTHelper.compoundTagPresence(tag);
		generateHashCode();
	}

	public ItemKey(@Nonnull Block block, int pmeta)
	{
		this(block, pmeta, null);
	}

	public ItemKey(@Nonnull ItemStack stack)
	{
		this(stack.getItem(), stack.getItemDamage(), stack.getTagCompound());
	}

	private void generateHashCode()
	{
		this.hash = item.hashCode();
		this.hash = 31 * hash + meta;
		this.hash = 31 * hash + (compoundTag != null ? compoundTag.hashCode() : 0);
	}

	@Override
	public ItemStack asStack(int size)
	{
		final ItemStack result = new ItemStack(item, size, meta);
		result.setTagCompound(NBTHelper.copyCompoundTag(compoundTag));
		return result;
	}

	@Override
	public ItemStack asStack()
	{
		return asStack(1);
	}

	public static ItemKey newWoNBT(@Nonnull ItemStack stack)
	{
		return new ItemKey(stack.getItem(), stack.getItemDamage());
	}
}
