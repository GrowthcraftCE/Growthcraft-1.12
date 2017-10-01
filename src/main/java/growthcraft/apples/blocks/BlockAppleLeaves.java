package growthcraft.apples.blocks;

import growthcraft.apples.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockAppleLeaves extends Block {
    public BlockAppleLeaves(String unlocalizedName) {
        super(Material.LEAVES);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

    // TODO: On random tick, generate BlockApple.
}
