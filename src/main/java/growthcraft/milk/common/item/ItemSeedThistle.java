package growthcraft.milk.common.item;

import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.init.GrowthcraftMilkBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.ResourceLocation;

public class ItemSeedThistle extends ItemSeeds {

    public ItemSeedThistle(String unlocalizedName) {
        super(GrowthcraftMilkBlocks.thistle.getBlock(), Blocks.FARMLAND );
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

}
