package growthcraft.core.handlers;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.IStringSerializable;

public class EnumHandler {

    public enum CrowbarTypes implements IStringSerializable {
        WHITE(EnumDyeColor.WHITE.getMetadata(), EnumDyeColor.WHITE.getUnlocalizedName()),
        ORANGE(EnumDyeColor.ORANGE.getMetadata(), EnumDyeColor.ORANGE.getUnlocalizedName()),
        MAGENTA(EnumDyeColor.MAGENTA.getMetadata(), EnumDyeColor.MAGENTA.getUnlocalizedName()),
        LIGHT_BLUE(EnumDyeColor.LIGHT_BLUE.getMetadata(), EnumDyeColor.LIGHT_BLUE.getUnlocalizedName()),
        YELLOW(EnumDyeColor.YELLOW.getMetadata(), EnumDyeColor.YELLOW.getUnlocalizedName()),
        LIME(EnumDyeColor.LIME.getMetadata(), EnumDyeColor.LIME.getUnlocalizedName()),
        PINK(EnumDyeColor.PINK.getMetadata(), EnumDyeColor.PINK.getUnlocalizedName()),
        GRAY(EnumDyeColor.GRAY.getMetadata(), EnumDyeColor.GRAY.getUnlocalizedName()),
        SILVER(EnumDyeColor.SILVER.getMetadata(), EnumDyeColor.SILVER.getUnlocalizedName()),
        CYAN(EnumDyeColor.CYAN.getMetadata(), EnumDyeColor.CYAN.getUnlocalizedName()),
        PURPLE(EnumDyeColor.PURPLE.getMetadata(), EnumDyeColor.PURPLE.getUnlocalizedName()),
        BLUE(EnumDyeColor.BLUE.getMetadata(), EnumDyeColor.BLUE.getUnlocalizedName()),
        BROWN(EnumDyeColor.BROWN.getMetadata(), EnumDyeColor.BROWN.getUnlocalizedName()),
        GREEN(EnumDyeColor.GREEN.getMetadata(), EnumDyeColor.GREEN.getUnlocalizedName()),
        RED(EnumDyeColor.RED.getMetadata(), EnumDyeColor.RED.getUnlocalizedName()),
        BLACK(EnumDyeColor.BLACK.getMetadata(), EnumDyeColor.BLACK.getUnlocalizedName())
        ;

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

        public int getID() {
            return this.ID;
        }
    }
}
