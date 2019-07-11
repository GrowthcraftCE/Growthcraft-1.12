package growthcraft.milk.common.item;

import growthcraft.milk.shared.Reference;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;

public class ItemKnife extends ItemSword {

	public ItemKnife(String unlocalizedName) {
		super(ToolMaterial.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setContainerItem(this);
    }
}
