package growthcraft.apples.blocks;

import growthcraft.apples.Reference;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockAppleFence extends BlockFence {

    public BlockAppleFence(String unlocalizedName) {
        super(Material.WOOD, MapColor.RED);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHardness(2.0F);
        this.setHarvestLevel("axe", 1);
        this.setResistance(5.0F);
        this.useNeighborBrightness = true;
    }
}
