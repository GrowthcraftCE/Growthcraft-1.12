package growthcraft.apples.common.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

import growthcraft.apples.shared.init.GrowthcraftApplesBlocks;

public class WorldGenAppleTree extends WorldGenAbstractTree {

    private int minTreeHeight = 5;
    private int maxTreeHeight = 6;

    private static Block blockLog = GrowthcraftApplesBlocks.blockAppleLog.getBlock();
    private static Block blockLeaves = GrowthcraftApplesBlocks.blockAppleLeaves.getBlock();

    public WorldGenAppleTree(boolean notify) {
        super(notify);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos pos) {
        if ( !worldIn.isRemote ) {
            final int maxGrowthHeight = rand.nextInt(maxTreeHeight - minTreeHeight) + minTreeHeight;

            if (canGrow(worldIn, pos, maxGrowthHeight)) {
                worldIn.setBlockState(pos, blockLog.getDefaultState());
                for (int i = 1; i <= maxGrowthHeight; i++) {
                    worldIn.setBlockState(pos.up(i), blockLog.getDefaultState());
                    if (i == maxGrowthHeight) {
                        // Top layer
                        spawnLeaves(worldIn, pos.up(i+1));
                        spawnLeaves(worldIn, pos.up(i+1).north());
                        spawnLeaves(worldIn, pos.up(i+1).east());
                        spawnLeaves(worldIn, pos.up(i+1).south());
                        spawnLeaves(worldIn, pos.up(i+1).west());

                        // Top Layer -1
                        spawnLeaves(worldIn, pos.up(i).north());
                        spawnLeaves(worldIn, pos.up(i).north(2));

                        spawnLeaves(worldIn, pos.up(i).east());
                        spawnLeaves(worldIn, pos.up(i).east().north());
                        spawnLeaves(worldIn, pos.up(i).east().north(2));
                        spawnLeaves(worldIn, pos.up(i).east(2));
                        spawnLeaves(worldIn, pos.up(i).east(2).north());
                        spawnLeaves(worldIn, pos.up(i).east().south());
                        spawnLeaves(worldIn, pos.up(i).east().south(2));
                        spawnLeaves(worldIn, pos.up(i).east(2).south());

                        spawnLeaves(worldIn, pos.up(i).west());
                        spawnLeaves(worldIn, pos.up(i).west().north());
                        spawnLeaves(worldIn, pos.up(i).west().north(2));
                        spawnLeaves(worldIn, pos.up(i).west(2));
                        spawnLeaves(worldIn, pos.up(i).west(2).north());
                        spawnLeaves(worldIn, pos.up(i).west().south());
                        spawnLeaves(worldIn, pos.up(i).west().south(2));

                        spawnLeaves(worldIn, pos.up(i).south());
                        spawnLeaves(worldIn, pos.up(i).south(2));

                        // Top Layer -2
                        spawnLeaves(worldIn, pos.up(i - 1).north());
                        spawnLeaves(worldIn, pos.up(i - 1).north(2));
                        spawnLeaves(worldIn, pos.up(i - 1).north(3));

                        spawnLeaves(worldIn, pos.up(i - 1).east());
                        spawnLeaves(worldIn, pos.up(i - 1).east().north());
                        spawnLeaves(worldIn, pos.up(i - 1).east().north(2));
                        spawnLeaves(worldIn, pos.up(i - 1).east().north(3));
                        spawnLeaves(worldIn, pos.up(i - 1).east().south());
                        spawnLeaves(worldIn, pos.up(i - 1).east().south(2));
                        spawnLeaves(worldIn, pos.up(i - 1).east().south(3));
                        spawnLeaves(worldIn, pos.up(i - 1).east(2));
                        spawnLeaves(worldIn, pos.up(i - 1).east(2).north());
                        spawnLeaves(worldIn, pos.up(i - 1).east(2).north(2));
                        spawnLeaves(worldIn, pos.up(i - 1).east(2).south());
                        spawnLeaves(worldIn, pos.up(i - 1).east(2).south(2));
                        spawnLeaves(worldIn, pos.up(i - 1).east(3));
                        spawnLeaves(worldIn, pos.up(i - 1).east(3).north());
                        spawnLeaves(worldIn, pos.up(i - 1).east(3).north(2));
                        spawnLeaves(worldIn, pos.up(i - 1).east(3).south());

                        spawnLeaves(worldIn, pos.up(i - 1).west());
                        spawnLeaves(worldIn, pos.up(i - 1).west().north());
                        spawnLeaves(worldIn, pos.up(i - 1).west().north(2));
                        spawnLeaves(worldIn, pos.up(i - 1).west().north(3));
                        spawnLeaves(worldIn, pos.up(i - 1).west().south());
                        spawnLeaves(worldIn, pos.up(i - 1).west().south(2));
                        spawnLeaves(worldIn, pos.up(i - 1).west().south(3));
                        spawnLeaves(worldIn, pos.up(i - 1).west(2));
                        spawnLeaves(worldIn, pos.up(i - 1).west(2).north());
                        spawnLeaves(worldIn, pos.up(i - 1).west(2).north(2));
                        spawnLeaves(worldIn, pos.up(i - 1).west(2).south());
                        spawnLeaves(worldIn, pos.up(i - 1).west(2).south(1));
                        spawnLeaves(worldIn, pos.up(i - 1).west(3));
                        spawnLeaves(worldIn, pos.up(i - 1).west(3).north());
                        spawnLeaves(worldIn, pos.up(i - 1).west(3).south());

                        spawnLeaves(worldIn, pos.up(i - 1).south());
                        spawnLeaves(worldIn, pos.up(i - 1).south(2));
                        spawnLeaves(worldIn, pos.up(i - 1).south(3));

                        // Top Layer -3
                        spawnLeaves(worldIn, pos.up(i - 2).north());
                        spawnLeaves(worldIn, pos.up(i - 2).north(2));
                        spawnLeaves(worldIn, pos.up(i - 2).north(3));

                        spawnLeaves(worldIn, pos.up(i - 2).east());
                        spawnLeaves(worldIn, pos.up(i - 2).east().north());
                        spawnLeaves(worldIn, pos.up(i - 2).east().north(2));
                        spawnLeaves(worldIn, pos.up(i - 2).east().north(3));
                        spawnLeaves(worldIn, pos.up(i - 2).east().south());
                        spawnLeaves(worldIn, pos.up(i - 2).east().south(2));
                        spawnLeaves(worldIn, pos.up(i - 2).east(2));
                        spawnLeaves(worldIn, pos.up(i - 2).east(2).north());
                        spawnLeaves(worldIn, pos.up(i - 2).east(2).south());
                        spawnLeaves(worldIn, pos.up(i - 2).east(2).south(2));
                        spawnLeaves(worldIn, pos.up(i - 2).east(2).north(2));
                        spawnLeaves(worldIn, pos.up(i - 2).east(3));
                        spawnLeaves(worldIn, pos.up(i - 2).east(3).south());

                        spawnLeaves(worldIn, pos.up(i - 2).west());
                        spawnLeaves(worldIn, pos.up(i - 2).west().north());
                        spawnLeaves(worldIn, pos.up(i - 2).west().north(2));
                        spawnLeaves(worldIn, pos.up(i - 2).west().north(3));
                        spawnLeaves(worldIn, pos.up(i - 2).west().south());
                        spawnLeaves(worldIn, pos.up(i - 2).west().south(2));
                        spawnLeaves(worldIn, pos.up(i - 2).west().south(3));
                        spawnLeaves(worldIn, pos.up(i - 2).west(2));
                        spawnLeaves(worldIn, pos.up(i - 2).west(2).north());
                        spawnLeaves(worldIn, pos.up(i - 2).west(2).north(2));
                        spawnLeaves(worldIn, pos.up(i - 2).west(2).south());
                        spawnLeaves(worldIn, pos.up(i - 2).west(2).south(2));
                        spawnLeaves(worldIn, pos.up(i - 2).west(3));
                        spawnLeaves(worldIn, pos.up(i - 2).west(3).north());
                        spawnLeaves(worldIn, pos.up(i - 2).west(3).south());

                        spawnLeaves(worldIn, pos.up(i - 2).south());
                        spawnLeaves(worldIn, pos.up(i - 2).south(2));
                        spawnLeaves(worldIn, pos.up(i - 2).south(3));

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
