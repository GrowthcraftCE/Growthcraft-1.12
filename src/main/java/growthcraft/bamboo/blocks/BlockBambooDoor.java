package growthcraft.bamboo.blocks;

import growthcraft.bamboo.Reference;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockBambooDoor extends BlockDoor {

    public BlockBambooDoor() {
        super(Material.WOOD);
        this.setUnlocalizedName("bamboo_door");
        this.setRegistryName(new ResourceLocation(Reference.MODID, "bamboo_door"));
    }
}
