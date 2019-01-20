package growthcraft.milk.common.block;

import java.util.List;

import javax.annotation.Nullable;

import growthcraft.core.shared.block.GrowthcraftBlockContainer;
import growthcraft.milk.common.tileentity.TileEntityPancheon;
import growthcraft.milk.shared.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPancheon extends GrowthcraftBlockContainer {

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 5, 0.0625 * 16);

    public BlockPancheon(String unlocalizedName) {
        super(Material.CLAY);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHardness(0.6F);
        this.setHarvestLevel("pickaxe", 1);
        setTileEntityType(TileEntityPancheon.class);
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        if (face == EnumFacing.UP)
        {
            return BlockFaceShape.BOWL;
        }
        else
        {
            return face != EnumFacing.DOWN ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
        }
    }
}