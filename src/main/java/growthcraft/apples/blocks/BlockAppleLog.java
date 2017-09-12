package growthcraft.apples.blocks;

import growthcraft.apples.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockAppleLog extends Block {
    public BlockAppleLog(String unlocalizedName) {
        super(Material.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }
}
