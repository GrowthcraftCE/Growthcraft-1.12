package growthcraft.cellar.common.block;

import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.shared.init.GrowthcraftCellarBlocks;
import growthcraft.core.shared.block.GrowthcraftBlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCorkLeaves extends GrowthcraftBlockLeaves {
    public static final int LEAVES_COLOR = 0x013220;

    public BlockCorkLeaves(String unlocalizedName) {
        super(Reference.MODID, unlocalizedName, GrowthcraftCellarBlocks.blockCorkSapling);
    }
    
    // BlockRenderLayer.SOLID is currently a broken texture for this block.
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    
}
