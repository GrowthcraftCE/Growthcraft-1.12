package growthcraft.bees.shared.init;

import growthcraft.cellar.shared.definition.BlockBoozeDefinition;
import growthcraft.cellar.shared.definition.BoozeDefinition;
import growthcraft.cellar.shared.definition.IObjectBooze;
import growthcraft.cellar.shared.item.ItemBoozeBottle;
import growthcraft.core.shared.definition.IItemStackFactory;
import growthcraft.core.shared.definition.IObjectVariant;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.definition.ItemTypeDefinition;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;


public class GrowthcraftBeesItems {

    private GrowthcraftBeesItems() {
    }

    public static ItemDefinition honeyCombEmpty;
    public static ItemDefinition honeyCombFilled;
    public static ItemDefinition honeyJar;
    public static ItemDefinition bee;
    public static ItemDefinition beesWax;

    public static ItemTypeDefinition<ItemBoozeBottle> honeyMeadBottle;

    public enum BeesWaxTypes implements IStringSerializable, IItemStackFactory, IObjectVariant {
        WHITE(EnumDyeColor.WHITE.getMetadata(), EnumDyeColor.WHITE.getTranslationKey()),
        ORANGE(EnumDyeColor.ORANGE.getMetadata(), EnumDyeColor.ORANGE.getTranslationKey()),
        MAGENTA(EnumDyeColor.MAGENTA.getMetadata(), EnumDyeColor.MAGENTA.getTranslationKey()),
        LIGHTBLUE(EnumDyeColor.LIGHT_BLUE.getMetadata(), EnumDyeColor.LIGHT_BLUE.getTranslationKey()),
        NORMAL(EnumDyeColor.YELLOW.getMetadata(), "normal"),
        LIME(EnumDyeColor.LIME.getMetadata(), EnumDyeColor.LIME.getTranslationKey()),
        PINK(EnumDyeColor.PINK.getMetadata(), EnumDyeColor.PINK.getTranslationKey()),
        GRAY(EnumDyeColor.GRAY.getMetadata(), EnumDyeColor.GRAY.getTranslationKey()),
        SILVER(EnumDyeColor.SILVER.getMetadata(), EnumDyeColor.SILVER.getTranslationKey()),
        CYAN(EnumDyeColor.CYAN.getMetadata(), EnumDyeColor.CYAN.getTranslationKey()),
        PURPLE(EnumDyeColor.PURPLE.getMetadata(), EnumDyeColor.PURPLE.getTranslationKey()),
        BLUE(EnumDyeColor.BLUE.getMetadata(), EnumDyeColor.BLUE.getTranslationKey()),
        BROWN(EnumDyeColor.BROWN.getMetadata(), EnumDyeColor.BROWN.getTranslationKey()),
        GREEN(EnumDyeColor.GREEN.getMetadata(), EnumDyeColor.GREEN.getTranslationKey()),
        RED(EnumDyeColor.RED.getMetadata(), EnumDyeColor.RED.getTranslationKey()),
        BLACK(EnumDyeColor.BLACK.getMetadata(), EnumDyeColor.BLACK.getTranslationKey());

        private int ID;
        private String NAME;

        BeesWaxTypes(int id, String name) {
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
            return GrowthcraftBeesItems.beesWax.asStack(amount, getVariantID());
        }

        @Override
        public ItemStack asStack() {
            return asStack(1);
        }
    }

    public enum MeadTypes implements IObjectBooze, IStringSerializable, IItemStackFactory, IObjectVariant {
        MEAD_YOUNG(0, "mead_young"),
        MEAD_FERMENTED(1, "mead_fermented"),
        MEAD_POTENT(2, "mead_potent"),
        MEAD_EXTENDED(3, "mead_extended"),
        MEAD_ETHEREAL(4, "mead_ethereal"),
        MEAD_INTOXICATED(5, "mead_intoxicated"),
        MEAD_POISONED(6, "mead_poisoned"),
        MEAD_HYPEREXTENDED(7, "mead_hyperextended"),
        MEAD_POTENTEXTENDED( 8, "mead_potentextended");

        private int ID;
        private String NAME;

        MeadTypes(int id, String name) {
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
            return GrowthcraftBeesItems.honeyMeadBottle.asStack(amount, getVariantID());
        }

        @Override
        public ItemStack asStack() {
            return asStack(1);
        }

        @Override
        public BoozeDefinition getFluidDefinition() {
            return GrowthcraftBeesFluids.meadBooze[ordinal()];
        }

        @Override
        public BlockBoozeDefinition getBoozeBlockDefinition() {
            return GrowthcraftBeesBlocks.meadBoozeFluidBlocks[ordinal()];
        }
    }
}
