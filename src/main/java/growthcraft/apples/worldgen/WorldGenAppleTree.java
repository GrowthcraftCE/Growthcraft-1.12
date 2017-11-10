package growthcraft.apples.worldgen;

import growthcraft.apples.init.GrowthcraftApplesBlocks;
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
                worldIn.setBlockState(pos, blockLog.getDefaultState());
                for ( int i = 1; i <= maxGrowthHeight; i++ ) {
                    worldIn.setBlockState(pos.up(i), blockLog.getDefaultState());
                    if ( i == maxGrowthHeight ) {
                        spawnLeaves(worldIn, pos.up(i+1));
                        spawnLeaves(worldIn, pos.up(i+1).north());
                        spawnLeaves(worldIn, pos.up(i+1).east());
                        spawnLeaves(worldIn, pos.up(i+1).south());
                        spawnLeaves(worldIn, pos.up(i+1).west());

                        spawnLeaves(worldIn, pos.up(i).north());
                        spawnLeaves(worldIn, pos.up(i).east().north());
                        spawnLeaves(worldIn, pos.up(i).east());
                        spawnLeaves(worldIn, pos.up(i).east().south());
                        spawnLeaves(worldIn, pos.up(i).south());
                        spawnLeaves(worldIn, pos.up(i).west().north());
                        spawnLeaves(worldIn, pos.up(i).west());
                        spawnLeaves(worldIn, pos.up(i).west().south());
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean canGrow(World worldIn, BlockPos pos, int growthHeight) {
        for ( int i = 1; i <= growthHeight; i++ ) {
            Block block = worldIn.getBlockState(pos.up(i)).getBlock();
            if ( ! ( block instanceof BlockAir ) ) {
                return false;
            }
        }
        return true;
    }

    private void spawnLeaves(World worldIn, BlockPos pos ) {
        if ( isReplaceable(worldIn, pos)) {
            worldIn.setBlockState(pos, blockLeaves.getDefaultState());
        }
    }
}
