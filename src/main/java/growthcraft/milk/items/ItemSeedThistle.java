package growthcraft.milk.items;

import growthcraft.milk.Reference;
import growthcraft.milk.init.GrowthcraftMilkBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.ResourceLocation;

public class ItemSeedThistle extends ItemSeeds {

    public ItemSeedThistle(String unlocalizedName) {
        super(GrowthcraftMilkBlocks.thistle, Blocks.FARMLAND );
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

}
