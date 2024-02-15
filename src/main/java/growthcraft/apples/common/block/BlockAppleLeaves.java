package growthcraft.apples.common.block;

import growthcraft.apples.shared.Reference;
import growthcraft.apples.shared.init.GrowthcraftApplesBlocks;
import growthcraft.core.shared.block.GrowthcraftBlockLeaves;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockAppleLeaves extends GrowthcraftBlockLeaves implements IGrowable {
    public static final int LEAVES_COLOR = 0x58e21d; // 0x4fcb1a; // 0x48B518;

    // TODO: Make fields configurable
    private static final int APPLE_CHECK_AREA = 3;
    private static final int MAX_APPLES_IN_AREA = 2;

    public BlockAppleLeaves(String unlocalizedName) {
        super(Reference.MODID, unlocalizedName, GrowthcraftApplesBlocks.blockAppleSapling);
    }

    ///////
    // Growing apples
    ///////

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        if (!canSustainApple(worldIn, pos, state))
            return false;
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        if (worldIn.getBlockState(pos.down()).getBlock() instanceof BlockAir) {
            worldIn.setBlockState(pos.down(), GrowthcraftApplesBlocks.blockApple.getDefaultState());
        }
    }

    private boolean canSustainApple(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getBlock() != this || !state.getValue(DECAYABLE))
            return false;

        Block block = worldIn.getBlockState(pos.down()).getBlock();
        if (!(block instanceof BlockAir))
            return false;

        return true;
    }

    private boolean canSpawnApple(World worldIn, BlockPos pos, IBlockState state) {
        if (!canSustainApple(worldIn, pos, state))
            return false;

        final int iX = pos.getX();
        final int iY = pos.getY();
        final int iZ = pos.getZ();

        BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();
        int countApples = 0;

        for (int jX = -APPLE_CHECK_AREA; jX <= APPLE_CHECK_AREA; jX++) {
            for (int jY = -APPLE_CHECK_AREA; jY <= APPLE_CHECK_AREA; jY++) {
                for (int jZ = -APPLE_CHECK_AREA; jZ <= APPLE_CHECK_AREA; jZ++) {
                    mutpos.setPos(iX + jX, iY + jY, iZ + jZ);
                    IBlockState iblockstate = worldIn.getBlockState(mutpos);
                    if (iblockstate.getBlock() == GrowthcraftApplesBlocks.blockApple.getBlock()) {
                        if (++countApples >= MAX_APPLES_IN_AREA)
                            return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            // check the light level and pick a randomness for growth.
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
                if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(7) == 0)) {
                    if (canSpawnApple(worldIn, pos, state)) {
                        grow(worldIn, rand, pos, state);
                        ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                    }
                }
            }
        }

        super.updateTick(worldIn, pos, state, rand);
    }

    ////
    // DROPS
    ////

    @Override
    protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
        if (worldIn.rand.nextInt(chance) == 0) {
            spawnAsEntity(worldIn, pos, new ItemStack(Items.APPLE));
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return GrowthcraftApplesBlocks.blockAppleSapling.getItem();
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
