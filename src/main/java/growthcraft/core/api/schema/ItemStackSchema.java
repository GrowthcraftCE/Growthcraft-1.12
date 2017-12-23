package growthcraft.core.api.schema;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import growthcraft.core.api.definition.IItemStackFactory;
import growthcraft.core.api.definition.IItemStackListProvider;
import growthcraft.core.api.definition.IMultiItemStacks;
import growthcraft.core.api.item.ItemKey;
import growthcraft.core.api.item.MultiItemStacks;
import growthcraft.core.api.utils.StringUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemStackSchema implements IItemStackFactory, IItemStackListProvider, IValidatable, ICommentable
{
	public String comment;
	public String mod_id;
	public String name;
	public int amount;
	public int meta;

	public ItemStackSchema(@Nonnull String mid, @Nonnull String nm, int amt, int mt)
	{
		this.mod_id = mid;
		this.name = nm;
		this.amount = amt;
		this.meta = mt;
		this.comment = "";
	}

	public ItemStackSchema(@Nonnull ItemStack stack)
	{
		final ResourceLocation resloc = stack.getItem().getRegistryName();
//		final UniqueIdentifier uuid = GameRegistry.findUniqueIdentifierFor(stack.getItem());
		this.mod_id = resloc.getResourceDomain(); // uuid.modId;
		this.name = resloc.getResourcePath(); // uuid.name;
		this.amount = stack.getCount();
		this.meta = stack.getItemDamage();
		this.comment = stack.getDisplayName();
	}

	public ItemStackSchema()
	{
		this.amount = 1;
		this.meta = ItemKey.WILDCARD_VALUE;
		this.comment = "";
	}

	@Override
	public void setComment(String comm)
	{
		this.comment = comm;
	}

	@Override
	public String getComment()
	{
		return comment;
	}

	public Item getItem()
	{
		if (mod_id == null || name == null) return null;
		return Item.getByNameOrId(mod_id + ":" + name);
//		return GameRegistry.findItem(mod_id, name);
	}

	@Override
	public ItemStack asStack(int a)
	{
		final Item item = getItem();
		if (item == null) return null;
		return new ItemStack(item, a, meta < 0 ? ItemKey.WILDCARD_VALUE : meta);
	}

	@Override
	public ItemStack asStack()
	{
		return asStack(amount);
	}

	@Override
	public List<ItemStack> getItemStacks()
	{
		final List<ItemStack> result = new ArrayList<ItemStack>();
		final ItemStack stack = asStack();
		if (stack != null) result.add(stack);
		return result;
	}

	public List<IMultiItemStacks> getMultiItemStacks()
	{
		final List<IMultiItemStacks> result = new ArrayList<IMultiItemStacks>();
		final ItemStack stack = asStack();
		if (stack != null) result.add(new MultiItemStacks(stack));
		return result;
	}

	@Override
	public String toString()
	{
		return String.format("Schema<ItemStack>(comment: '%s', mod_id: '%s', name: '%s', meta: %d, amount: %d)",
			StringUtils.inspect(comment), mod_id, name, meta, amount);
	}

	@Override
	public boolean isValid()
	{
		return asStack() != null;
	}

	@Override
	public boolean isInvalid()
	{
		return !isValid();
	}
}
