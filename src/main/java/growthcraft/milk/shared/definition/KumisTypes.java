package growthcraft.milk.shared.definition;

import growthcraft.cellar.shared.definition.BlockBoozeDefinition;
import growthcraft.cellar.shared.definition.BoozeDefinition;
import growthcraft.cellar.shared.definition.IObjectBooze;
import growthcraft.core.shared.definition.IItemStackFactory;
import growthcraft.core.shared.definition.IObjectVariant;
import growthcraft.milk.shared.init.GrowthcraftMilkBlocks;
import growthcraft.milk.shared.init.GrowthcraftMilkFluids;
import growthcraft.milk.shared.init.GrowthcraftMilkItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;

public enum KumisTypes implements IObjectBooze, IStringSerializable, IItemStackFactory, IObjectVariant {

    KUMIS_FERMENTED(0, "kumis_fermented"),
    KUMIS_POTENT(1, "kumis_potent"),
    KUMIS_EXTENDED(2, "kumis_extended"),
    KUMIS_HYPEREXTENDED(3, "kumis_hyperextended"),
    KUMIS_POTENT_EXTENDED(4, "kumis_potent_extended"),
    KUMIS_POTENT_HYPEREXTENDED(5, "kumis_potent_hyperextended"),
    KUMIS_POISONED(6, "kumis_poisoned");

    private int ID;
    private String NAME;

    KumisTypes(int id, String name) {
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
        return GrowthcraftMilkItems.kumisBottle.asStack( 1);
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
        return GrowthcraftMilkFluids.kumisBooze[ordinal()];
    }

    @Override
    public BlockBoozeDefinition getBoozeBlockDefinition() {
        return GrowthcraftMilkBlocks.kumisFluidBlocks[ordinal()];
    }
}
