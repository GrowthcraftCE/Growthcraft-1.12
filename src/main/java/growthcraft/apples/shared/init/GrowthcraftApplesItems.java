package growthcraft.apples.shared.init;

import growthcraft.cellar.shared.definition.BlockBoozeDefinition;
import growthcraft.cellar.shared.definition.BoozeDefinition;
import growthcraft.cellar.shared.definition.IObjectBooze;
import growthcraft.cellar.shared.item.ItemBoozeBottle;
import growthcraft.core.shared.definition.IItemStackFactory;
import growthcraft.core.shared.definition.IObjectVariant;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.definition.ItemTypeDefinition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class GrowthcraftApplesItems {
    public static ItemDefinition itemAppleDoor;

    public static ItemTypeDefinition<ItemBoozeBottle> appleCider;

    public enum AppleCiderTypes implements IObjectBooze, IStringSerializable, IItemStackFactory, IObjectVariant {
        APPLE_JUICE(0, "apple_juice"),
        APPLE_CIDER_FERMENTED(1, "apple_cider_fermented"),
        APPLE_CIDER_POTENT(2, "apple_cider_potent"),
        APPLE_CIDER_EXTENDED(3, "apple_cider_extended"),
        APPLE_CIDER_ETHEREAL(4, "apple_cider_ethereal"),
        APPLE_CIDER_INTOXICATED(5, "apple_cider_intoxicated"),
        APPLE_CIDER_POISONED(6, "apple_cider_poisoned"),
        APPLE_CIDER_POTENTEXTENDED(7, "apple_cider_potentextended");

        private int ID;
        private String NAME;

        AppleCiderTypes(int id, String name) {
            this.ID = id;
            this.NAME = name;
        }

        @Override
        public String toString() {
            return getName();
        }

        @Override
        public String getName() {
            return this.NAME;
        }

        @Override
        public int getVariantID() {
            return this.ID;
        }

        @Override
        public ItemStack asStack(int amount) {
            return GrowthcraftApplesItems.appleCider.asStack(amount, getVariantID());
        }

        @Override
        public ItemStack asStack() {
            return asStack(1);
        }

        @Override
        public BoozeDefinition getFluidDefinition() {
            return GrowthcraftApplesFluids.appleCiderBooze[ordinal()];
        }

        @Override
        public BlockBoozeDefinition getBoozeBlockDefinition() {
            return GrowthcraftApplesBlocks.appleCiderFluidBlocks[ordinal()];
        }
    }
}
