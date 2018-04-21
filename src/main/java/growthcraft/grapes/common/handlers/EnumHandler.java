package growthcraft.grapes.common.handlers;

import growthcraft.core.shared.definition.IObjectVariant;
import growthcraft.cellar.shared.definition.IObjectBooze;
import growthcraft.core.shared.definition.IItemStackFactory;
import growthcraft.grapes.api.definition.IGrapeType;
import growthcraft.grapes.common.init.GrowthcraftGrapesBlocks;
import growthcraft.grapes.common.init.GrowthcraftGrapesFluids;
import growthcraft.grapes.common.init.GrowthcraftGrapesItems;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class EnumHandler {

    public enum GrapeTypes implements IStringSerializable, IItemStackFactory, IObjectVariant, IGrapeType {
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
        public int getPlantSubTypeID() {
        	return ordinal();
        }
        
        @Override
        public ItemStack asStack(int amount) {
//        	return new ItemStack(GrowthcraftGrapesItems.grape,amount,ID);
        	return GrowthcraftGrapesItems.grape.asStack(amount, getVariantID());
        }
        
        @Override
        public ItemStack asStack() {
        	return asStack(1);
        }

		@Override
		public ItemStack asSeedsStack(int amount) {
			return GrowthcraftGrapesItems.grape_seed.asStack(amount, getVariantID());
		}
    }
    
    public enum WineTypes implements IObjectBooze, IStringSerializable, IItemStackFactory, IObjectVariant {
    	PURPLE_JUICE(0, "purple_juice"),
    	PURPLE_WINE(1, "purple_wine"),
    	PURPLE_WINE_POTENT(2, "purple_wine_potent"),
    	PURPLE_WINE_EXTENDED(3, "purple_wine_extended"),
    	PURPLE_AMBROSIA(4, "purple_ambrosia"),
    	PURPLE_PORTWINE(5, "purple_portwine"),
    	PURPLE_WINE_INTOXICATED(6, "purple_wine_intoxicated"),
    	PURPLE_WINE_POISONED(7, "purple_wine_poisoned"),
    	
    	GREEN_JUICE(8, "green_juice"),
    	GREEN_WINE(9, "green_wine"),
    	GREEN_WINE_POTENT(10, "green_wine_potent"),
    	GREEN_WINE_EXTENDED(11, "green_wine_extended"),
    	GREEN_AMBROSIA(12, "green_ambrosia"),
    	GREEN_PORTWINE(13, "green_portwine"),
    	GREEN_WINE_INTOXICATED(14, "green_wine_intoxicated"),
    	GREEN_WINE_POISONED(15, "green_wine_poisoned"),
    	
    	RED_JUICE(16, "red_juice"),
    	RED_WINE(17, "red_wine"),
    	RED_WINE_POTENT(18, "red_wine_potent"),
    	RED_WINE_EXTENDED(19, "red_wine_extended"),
    	RED_AMBROSIA(20, "red_ambrosia"),
    	RED_PORTWINE(21, "red_portwine"),
    	RED_WINE_INTOXICATED(22, "red_wine_intoxicated"),
    	RED_WINE_POISONED(23, "red_wine_poisoned");
    	
        private int ID;
        private String NAME;
        
        WineTypes(int id, String name) {
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
        	return GrowthcraftGrapesItems.grapeWine.asStack(amount, getVariantID());
        }
        
        @Override
        public ItemStack asStack() {
        	return asStack(1);
        }
        
        @Override
        public BoozeDefinition getFluidDefinition() {
        	return GrowthcraftGrapesFluids.grapeWineBooze[ordinal()];
        }
        
        @Override
        public BlockBoozeDefinition getBoozeBlockDefinition() {
        	return GrowthcraftGrapesBlocks.grapeWineFluidBlocks[ordinal()];
        }
    }

}
