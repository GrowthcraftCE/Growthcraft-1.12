package growthcraft.bees.common.handlers;

import growthcraft.bees.shared.init.GrowthcraftBeesItems;
import growthcraft.core.shared.definition.IItemStackFactory;
import growthcraft.core.shared.definition.IObjectVariant;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class EnumHandler {
	private EnumHandler() {}
	
    public enum BeesWaxTypes implements IStringSerializable, IItemStackFactory, IObjectVariant {
        NORMAL(0, "normal"),
        RED(1, "red"),
        BLACK(2, "black")
        ;

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

}
