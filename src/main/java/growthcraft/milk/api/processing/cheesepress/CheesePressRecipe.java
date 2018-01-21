package growthcraft.milk.api.processing.cheesepress;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

public class CheesePressRecipe implements ICheesePressRecipe
{
	private ItemStack inputStack;
	private ItemStack outputStack;
	private int time;

	public CheesePressRecipe(@Nonnull ItemStack pInputStack, @Nonnull ItemStack pOutputStack, int pTime)
	{
		this.inputStack = pInputStack;
		this.outputStack = pOutputStack;
		this.time = pTime;
	}

	@Override
	public ItemStack getInputItemStack()
	{
		return inputStack;
	}

	@Override
	public ItemStack getOutputItemStack()
	{
		return outputStack;
	}

	@Override
	public int getTimeMax()
	{
		return time;
	}

	@Override
	public boolean isMatchingRecipe(@Nonnull ItemStack stack)
	{
		if (inputStack.isItemEqual(stack))
		{
			return stack.getCount() >= inputStack.getCount();
		}
		return false;
	}

	@Override
	public String toString()
	{
		return String.format("CheesePressRecipe({%s} / %d = {%s})", getOutputItemStack(), time, getInputItemStack());
	}
}
