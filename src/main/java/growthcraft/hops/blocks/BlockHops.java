package growthcraft.hops.blocks;

import growthcraft.hops.Reference;
import net.minecraft.block.BlockBush;
import net.minecraft.util.ResourceLocation;

public class BlockHops extends BlockBush {

    public BlockHops (String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

}
