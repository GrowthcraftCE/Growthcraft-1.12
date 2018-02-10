package growthcraft.grapes.handlers;

import growthcraft.core.api.definition.IObjectVariant;
import growthcraft.cellar.api.booze.Booze;
import growthcraft.cellar.api.definition.IObjectBooze;
import growthcraft.cellar.common.block.BlockFluidBooze;
import growthcraft.cellar.common.definition.BlockBoozeDefinition;
import growthcraft.cellar.common.definition.BoozeDefinition;
import growthcraft.core.api.definition.IItemStackFactory;
import growthcraft.grapes.api.definition.IGrapeType;
import growthcraft.grapes.init.GrowthcraftGrapesBlocks;
import growthcraft.grapes.init.GrowthcraftGrapesFluids;
import growthcraft.grapes.init.GrowthcraftGrapesItems;
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
    }
    
    public enum WineTypes implements IObjectBooze, IStringSerializable, IItemStackFactory, IObjectVariant {
    	PURPLE_JUICE(0, "juice"),
    	PURPLE_WINE(1, "wine"),
    	PURPLE_WINE_POTENT(2, "wine_potent"),
    	PURPLE_WINE_EXTENDED(3, "wine_extended"),
    	PURPLE_AMBROSIA(4, "ambrosia"),
    	PURPLE_PORTWINE(5, "portwine"),
    	PURPLE_WINE_INTOXICATED(6, "wine_intoxicated"),
    	PURPLE_WINE_POISONED(7, "wine_poisoned");
    	
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
