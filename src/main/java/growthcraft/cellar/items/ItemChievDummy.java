package growthcraft.cellar.items;

import growthcraft.cellar.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemChievDummy extends Item {
	// REVISE_ME 0
	
    public ItemChievDummy(String unlocalizedName) {
    	this.setCreativeTab(null);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }
}
