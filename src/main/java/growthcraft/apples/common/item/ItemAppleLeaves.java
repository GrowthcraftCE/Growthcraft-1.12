package growthcraft.apples.common.item;

import growthcraft.apples.common.block.BlockAppleLeaves;
import net.minecraft.item.ItemLeaves;
import net.minecraft.item.ItemStack;

public class ItemAppleLeaves extends ItemLeaves {

    private final BlockAppleLeaves leaves;    // NOTE: Same private field exists at superclass!

    public ItemAppleLeaves(BlockAppleLeaves leaves) {
        super(leaves);
        this.leaves = leaves;
    }

    /**
     * Converts the given ItemStack damage value into a metadata value to be placed in the world when this Item is
     * placed as a Block (mostly used with ItemBlocks).
     */
    @Override
    public int getMetadata(int damage) {
        return 4;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    @Override
    public String getTranslationKey(ItemStack stack) {
        return leaves.getTranslationKey();
    }
}
