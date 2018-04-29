package growthcraft.cellar.common.block;

import java.util.List;

import javax.annotation.Nullable;

import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.common.tileentity.TileEntityCultureJar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCultureJar extends BlockCellarContainer {
    
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(
            0.0625 * 4, 0.0625 * 0, 0.0625 * 4,
            0.0625 * 12, 0.0625 * 9, 0.0625 * 12);

    public BlockCultureJar(String unlocalizedName) {
        super(Material.GLASS);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHardness(0.3F);
        this.setSoundType(SoundType.GLASS);
		setTileEntityType(TileEntityCultureJar.class);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

}
