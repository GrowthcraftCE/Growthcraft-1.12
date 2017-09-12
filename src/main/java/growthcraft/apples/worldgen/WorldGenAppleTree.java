package growthcraft.apples.worldgen;

import growthcraft.apples.init.GrowthcraftApplesBlocks;
import growthcraft.core.utils.GrowthcraftLogger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenAppleTree extends WorldGenAbstractTree {

    private int minTreeHeight = 4;
    private int maxTreeHeight = 5;

    private static Block blockLog = GrowthcraftApplesBlocks.blockAppleLog;
    private static Block blockLeaves = GrowthcraftApplesBlocks.blockAppleLeaves;

    public WorldGenAppleTree(boolean notify) {
        super(notify);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos pos) {
        if ( !worldIn.isRemote ) {
            final int maxGrowthHeight = rand.nextInt(maxTreeHeight - minTreeHeight) + minTreeHeight;

            if (canGrow(worldIn, pos, maxGrowthHeight)) {
                GrowthcraftLogger.getLogger().info("Grow a apple tree!");
                worldIn.setBlockState(pos, blockLog.getDefaultState());
                for ( int i = 1; i <= maxGrowthHeight; i++ ) {
                    worldIn.setBlockState(pos.up(i), blockLog.getDefaultState());
                }

            }
        }
        return false;
    }

    private boolean canGrow(World worldIn, BlockPos pos, int growthHeight) {
        // Can this tree fit in the space provided?
        for ( int i = 1; i <= growthHeight; i++) {
            if (!(worldIn.getBlockState(pos).getBlock() instanceof BlockAir))
                return false;
        }

        return true;
    }

    private void spawnLeaves(World worldIn, BlockPos pos ) {
        if ( isReplaceable(worldIn, pos)) {
            worldIn.setBlockState(pos, blockLeaves.getDefaultState());
        }
    }
}
