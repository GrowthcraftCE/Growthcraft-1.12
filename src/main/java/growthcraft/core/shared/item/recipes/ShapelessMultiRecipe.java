package growthcraft.core.shared.item.recipes;

import growthcraft.core.shared.definition.IMultiFluidStacks;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.item.ItemUtils;
import growthcraft.core.shared.item.MultiItemStacks;
import growthcraft.core.shared.item.MultiStacksUtil;
import growthcraft.core.shared.item.OreItemStacks;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.*;

public class ShapelessMultiRecipe extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    private ItemStack output;
    private ArrayList<IMultiItemStacks> input = new ArrayList<IMultiItemStacks>();
    private ArrayList<IMultiFluidStacks> fluids = new ArrayList<IMultiFluidStacks>();
    private final String group;

    public ShapelessMultiRecipe(String group, Block result, Object... recipe) {
        this(group, new ItemStack(result), recipe);
    }

    public ShapelessMultiRecipe(String group, Item result, Object... recipe) {
        this(group, new ItemStack(result), recipe);
    }

    public ShapelessMultiRecipe(String group, ItemStack result, Object... recipe) {
        this.group = group;
        output = result.copy();
        for (Object in : recipe) {
            if (in instanceof ItemStack) {
                input.add(new MultiItemStacks(((ItemStack) in).copy()));
            } else if (in instanceof Item) {
                input.add(new MultiItemStacks(new ItemStack((Item) in)));
            } else if (in instanceof Block) {
                input.add(new MultiItemStacks(new ItemStack((Block) in)));
            } else if (in instanceof String) {
                input.add(new OreItemStacks((String) in));
            } else if (in instanceof IMultiItemStacks) {
                input.add((IMultiItemStacks) in);
            } else if (in instanceof FluidStack) {
                fluids.add(MultiStacksUtil.toMultiFluidStacks(in));
            } else if (in instanceof IMultiFluidStacks) {
                fluids.add((IMultiFluidStacks) in);
            } else {
                String ret = "Invalid shapeless multi recipe: ";
                for (Object tmp : recipe) {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1) {
        return output.copy();
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    public boolean matches(InventoryCrafting var1, World world) {
        final Map<IMultiFluidStacks, Integer> aggregateFluids = new HashMap<IMultiFluidStacks, Integer>();
        for (IMultiFluidStacks fluid : fluids) {
            aggregateFluids.put(fluid, fluid.getAmount());
        }

        final ArrayList<IMultiItemStacks> required = new ArrayList<IMultiItemStacks>(input);

        for (int x = 0; x < var1.getSizeInventory(); x++) {
            final ItemStack slot = var1.getStackInSlot(x);

            if (!ItemUtils.isEmpty(slot)) {
                boolean inRecipe = false;
                final Iterator<IMultiItemStacks> req = required.iterator();

                while (req.hasNext()) {
                    boolean match = false;

                    final IMultiItemStacks next = req.next();
                    final Iterator<ItemStack> itr = next.getItemStacks().iterator();
                    while (itr.hasNext() && !match) {
                        match = OreDictionary.itemMatches(itr.next(), slot,
                                false);
                    }

                    if (match) {
                        inRecipe = true;
                        required.remove(next);
                        break;
                    }
                }

                if (!inRecipe) {
                    boolean fluidConsumed = false;

                    if (FluidContainerRegistry.isFilledContainer(slot)) {
                        final FluidStack containerFluid = FluidContainerRegistry
                                .getFluidForFilledItem(slot).copy();

                        for (IMultiFluidStacks fluidStacks : fluids) {
                            final int aggregateAmount = aggregateFluids
                                    .containsKey(fluidStacks) ? aggregateFluids
                                    .get(fluidStacks) : 0;

                            if (fluidStacks.containsFluidStack(containerFluid)
                                    && aggregateAmount > 0) {
                                final int leftover = Math.max(0, aggregateAmount
                                        - containerFluid.amount);
                                if (leftover <= 1) {
                                    aggregateFluids.remove(fluidStacks);
                                } else {
                                    aggregateFluids.put(fluidStacks, leftover);
                                }
                                fluidConsumed = true;
                            }
                        }
                    }

                    if (!fluidConsumed) {
                        return false;
                    }
                }
            }
        }

        return required.isEmpty() && aggregateFluids.isEmpty();
    }

    /**
     * Returns the input for this recipe, any mod accessing this value should
     * never manipulate the values in this array as it will effect the recipe
     * itself.
     *
     * @return The recipes input vales.
     */
    public ArrayList<IMultiItemStacks> getInput() {
        return this.input;
    }

    public List<IMultiFluidStacks> getFluids() {
        return fluids;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            nonnulllist.set(i, FluidContainerRegistry.getContainerItemWithFallback(itemstack));
        }

        return nonnulllist;
    }

    @Override
    public boolean canFit(int width, int height) {
        // TODO: Check if working!
        return width * height >= (this.input.size() + this.fluids.size());
    }

    @Override
    @Nonnull
    public String getGroup() {
        return this.group;
    }
}
