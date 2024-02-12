package growthcraft.core.shared.init;

import growthcraft.core.shared.definition.IItemStackFactory;
import growthcraft.core.shared.definition.IObjectVariant;
import growthcraft.core.shared.definition.ItemDefinition;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class GrowthcraftCoreItems {

    public static ItemDefinition crowbar;
    public static ItemDefinition salt;
    public static ItemDefinition rope;

    public enum CrowbarTypes implements IStringSerializable, IItemStackFactory, IObjectVariant {
        WHITE(EnumDyeColor.WHITE.getMetadata(), EnumDyeColor.WHITE.getTranslationKey()),
        ORANGE(EnumDyeColor.ORANGE.getMetadata(), EnumDyeColor.ORANGE.getTranslationKey()),
        MAGENTA(EnumDyeColor.MAGENTA.getMetadata(), EnumDyeColor.MAGENTA.getTranslationKey()),
        LIGHTBLUE(EnumDyeColor.LIGHT_BLUE.getMetadata(), EnumDyeColor.LIGHT_BLUE.getTranslationKey()),
        YELLOW(EnumDyeColor.YELLOW.getMetadata(), EnumDyeColor.YELLOW.getTranslationKey()),
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

        CrowbarTypes(int id, String name) {
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
            return GrowthcraftCoreItems.crowbar.asStack(amount, getVariantID());
        }

        @Override
        public ItemStack asStack() {
            return asStack(1);
        }
    }

}
