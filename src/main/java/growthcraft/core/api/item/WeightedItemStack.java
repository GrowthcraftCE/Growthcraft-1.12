package growthcraft.core.api.item;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class WeightedItemStack extends WeightedRandom.Item
{
	public final ItemStack itemStack;
	private ItemKey key;

	public WeightedItemStack(int weight, @Nonnull ItemStack stack)
	{
		super(weight <= 0 ? 1 : weight);
		this.itemStack = stack;
		this.key = new ItemKey(itemStack);
	}

	@Override
	public int hashCode()
	{
		return key.hashCode();
	}

	@Override
	public boolean equals(Object other)
	{
		if (other instanceof WeightedItemStack)
		{
			return hashCode() == other.hashCode();
		}
		return false;
	}
}
