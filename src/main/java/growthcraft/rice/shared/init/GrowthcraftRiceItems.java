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

    public static ItemTypeDefinition cultivator;
    public static ItemDefinition rice;
    public static ItemDefinition rice_cooked;
    public static ItemDefinition rice_ball;
    public static ItemTypeDefinition<ItemBoozeBottle> sakeBottle;

    public enum SakeTypes implements IObjectBooze, IStringSerializable, IItemStackFactory, IObjectVariant {
        SAKE_WATER(0, "sake_water"),
        SAKE_MASH(1, "sake_mash"),
        SAKE_FERMENTED(2, "sake_fermented"),
        SAKE_POTENT(3, "sake_potent"),
        SAKE_EXTENDED(4, "sake_extended"),
        SAKE_HYPEREXTENDED(5, "sake_hyperextended"),
        SAKE_POTENT_EXTENDED(6, "sake_potent_extended"),
        SAKE_POTENT_HYPEREXTENDED(7, "sake_potent_hyperextended"),
        SAKE_POISONED(8, "sake_poisoned");

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
