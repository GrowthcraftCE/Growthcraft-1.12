package growthcraft.rice.shared.init;

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

import javax.annotation.Nullable;

public class GrowthcraftRiceItems {

    public GrowthcraftRiceItems() {}

    public static ItemDefinition rice;
    public static ItemDefinition rice_cooked;
    public static ItemDefinition rice_ball;
    public static ItemTypeDefinition<ItemBoozeBottle> sakeBottle;

    public enum SakeTypes implements IObjectBooze, IStringSerializable, IItemStackFactory, IObjectVariant {
        SAKE_MASH(0, "sake_mash"),
        SAKE_FERMENTED(1, "sake_fermented"),
        SAKE_POTENT(2, "sake_potent"),
        SAKE_EXTENDED(3, "sake_extended"),
        SAKE_HYPEREXTENDED(4, "sake_hyperextended"),
        SAKE_INTOXICATED(5,"sake_intoxicated"),
        SAKE_POISONED(6, "sake_poisoned");

        private int ID;
        private String NAME;

        SakeTypes(int id, String name) {
            this.ID = id;
            this.NAME = name;
        }

        @Override
        public String toString() {
            return getName();
        }

        @Override
        public int getVariantID() {
            return this.ID;
        }

        @Nullable
        @Override
        public ItemStack asStack(int size) {
            return GrowthcraftRiceItems.sakeBottle.asStack(size, getVariantID());
        }

        @Nullable
        @Override
        public ItemStack asStack() {
            return asStack(1);
        }

        @Override
        public String getName() {
            return this.NAME;
        }

        @Override
        public BoozeDefinition getFluidDefinition() {
            return GrowthcraftRiceFluids.sakeBooze[ordinal()];
        }

        @Override
        public BlockBoozeDefinition getBoozeBlockDefinition() {
            return GrowthcraftRiceBlocks.sakeFluidBlocks[ordinal()];
        }
    }


}
