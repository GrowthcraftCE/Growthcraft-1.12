package growthcraft.core.blocks;

import growthcraft.core.Reference;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockFenceRope extends BlockFence {
    public BlockFenceRope(String unlocalizedName) {
        super(Material.WOOD, Material.WOOD.getMaterialMapColor());
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHardness(3);
        this.setResistance(20);
        this.useNeighborBrightness = true;
    }
}
