package growthcraft.hops.blocks;

import growthcraft.core.blocks.BlockRopeFence;
import growthcraft.hops.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockHopsBush extends BlockBush implements IGrowable {
    private static final AxisAlignedBB BOUNDING_BOX =
            new AxisAlignedBB(0.0625 * 0, 0.0625 * 0, 0.0625 * 0, 0.0625 * 16, 0.0625 * 16, 0.0625 * 16);

    public BlockHopsBush(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        BlockPos[] blockPositions = new BlockPos[] { pos.north(), pos.east(), pos.south(), pos.west(), pos.down() };

        for ( BlockPos blockPosition : blockPositions ) {
            Block block = worldIn.getBlockState(blockPosition).getBlock();
            if ( block instanceof BlockRopeFence || block instanceof BlockAir) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return false;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {

    }
}
