package growthcraft.hops.items;

import growthcraft.hops.Reference;
import growthcraft.hops.init.GrowthcraftHopsBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.ResourceLocation;

public class ItemSeedHops extends ItemSeeds {

    public ItemSeedHops(String unlocalizedName) {
        super(GrowthcraftHopsBlocks.block_hops, Blocks.FARMLAND);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }
}
