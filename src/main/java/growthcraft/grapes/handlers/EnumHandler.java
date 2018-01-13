package growthcraft.grapes.handlers;

import growthcraft.core.api.definition.IObjectVariant;
import growthcraft.core.api.definition.IItemStackFactory;
import growthcraft.grapes.init.GrowthcraftGrapesItems;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class EnumHandler {

    public enum GrapeTypes implements IStringSerializable, IItemStackFactory, IObjectVariant {
        PURPLE(EnumDyeColor.PURPLE.getMetadata(), EnumDyeColor.PURPLE.getUnlocalizedName()),
        GREEN(EnumDyeColor.GREEN.getMetadata(), EnumDyeColor.GREEN.getUnlocalizedName()),
        RED(EnumDyeColor.RED.getMetadata(), EnumDyeColor.RED.getUnlocalizedName());

        private int ID;
        private String NAME;

        GrapeTypes(int id, String name) {
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
        	return new ItemStack(GrowthcraftGrapesItems.grape,amount,ID);
        }
        
        @Override
        public ItemStack asStack() {
        	return asStack(1);
        }
    }

}
