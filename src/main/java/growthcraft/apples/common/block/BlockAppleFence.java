package growthcraft.apples.common.block;

import growthcraft.apples.shared.Reference;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockAppleFence extends BlockFence {

    public BlockAppleFence(String unlocalizedName) {
        super(Material.WOOD, MapColor.RED);
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHardness(2.0F);
        this.setHarvestLevel("axe", 1);
        this.setResistance(5.0F);
        this.useNeighborBrightness = true;
    }
}
