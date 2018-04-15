package growthcraft.core.shared.item.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import growthcraft.core.shared.item.IItemStackComparator;
import growthcraft.core.shared.item.ItemUtils;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ShapelessItemComparableRecipe implements IRecipe
{
	public final List<ItemStack> recipeItems;
	private final ItemStack recipeOutput;
	private final IItemStackComparator comparator;

	public ShapelessItemComparableRecipe(@Nonnull IItemStackComparator pComparator, ItemStack result, List<ItemStack> list)
	{
		this.comparator = pComparator;
		this.recipeOutput = result;
		this.recipeItems = list;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return this.recipeOutput;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting crafting, World world)
	{
		final List<ItemStack> recipeList = new ArrayList<ItemStack>(this.recipeItems);
		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 3; ++j)
			{
				final ItemStack actual = crafting.getStackInRowAndColumn(j, i);
				if (!ItemUtils.isEmpty(actual))
				{
					boolean flag = false;
					final Iterator<ItemStack> iterator = recipeList.iterator();
					while (iterator.hasNext())
					{
						final ItemStack expected = iterator.next();
						if (comparator.equals(expected, actual))
						{
							flag = true;
							recipeList.remove(expected);
							break;
						}
					}
					if (!flag)
					{
						return false;
					}
				}
			}
		}
		return recipeList.isEmpty();
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting crafting)
	{
		return this.recipeOutput.copy();
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize()
	{
		return this.recipeItems.size();
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
        }

        return nonnulllist;
	}
}
