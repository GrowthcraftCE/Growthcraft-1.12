package growthcraft.grapes.blocks;

import growthcraft.core.Reference;
import net.minecraft.block.BlockCrops;
import net.minecraft.util.ResourceLocation;

public class BlockGrapeVine extends BlockCrops {

    public BlockGrapeVine(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));

    }
}
