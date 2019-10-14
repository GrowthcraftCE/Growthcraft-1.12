package growthcraft.milk.shared.definition;

import net.minecraft.item.ItemStack;

public interface ICheeseBlockStackFactory {
    ItemStack asStackForStage(int size, int slices, EnumCheeseStage stage);

    default ItemStack asStackForStage(int slices, EnumCheeseStage stage) {
        return asStackForStage(1, slices, stage);
    }

    EnumCheeseStage getInitialStage();
}
