package growthcraft.cellar.common.item;

import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.item.GrowthcraftItemBase;
import net.minecraft.util.ResourceLocation;

public class ItemBarrelTap extends GrowthcraftItemBase {
	public ItemBarrelTap(String unlocalizedName) {
		super();
		setMaxDamage(0);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
}
