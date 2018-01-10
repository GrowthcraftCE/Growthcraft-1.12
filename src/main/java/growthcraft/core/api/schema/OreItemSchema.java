package growthcraft.core.api.schema;

import java.util.ArrayList;
import java.util.List;

import growthcraft.core.api.definition.IItemStackListProvider;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreItemSchema implements IItemStackListProvider, IValidatable, ICommentable
{
	public String comment = "";
	public String name;
	public int amount;

	/**
	 * @param n - ore name
	 * @param a - amount
	 */
	public OreItemSchema(String n, int a)
	{
		this.name = n;
		this.amount = a;
	}

	public OreItemSchema() {}

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

	/**
	 * @return list of ores or null if the name was invalid
	 */
	public List<ItemStack> getOres()
	{
		if (name != null) return OreDictionary.getOres(name);
		return null;
	}

	/**
	 * @return list with ores, this list may be empty if the ores were invalid
	 */
	@Override
	public List<ItemStack> getItemStacks()
	{
		final List<ItemStack> result = new ArrayList<ItemStack>();
		final List<ItemStack> ores = getOres();
		if (ores != null)
		{
			for (ItemStack stack : ores)
			{
				final ItemStack newStack = stack.copy();
				newStack.setCount(amount);
				result.add(newStack);
			}
		}
		return result;
	}

	@Override
	public String toString()
	{
		return String.format("Schema<OreItem>(name: '%s', amount: %s)", name, amount);
	}

	@Override
	public boolean isValid()
	{
		return name != null;
	}

	@Override
	public boolean isInvalid()
	{
		return !isValid();
	}
}