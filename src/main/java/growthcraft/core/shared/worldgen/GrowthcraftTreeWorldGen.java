package growthcraft.core.shared.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class GrowthcraftTreeWorldGen extends WorldGenAbstractTree {

    private int minTreeHeight = 5;
    private int maxTreeHeight = 6;

    private Block blockLog;
    private Block blockLeaves;

    private Random random = new Random();

    public GrowthcraftTreeWorldGen(Block blockLog, Block blockLeaves, int minTreeHeight, int maxTreeHeight, boolean notify) {
        super(notify);
        this.blockLog = blockLog;
        this.blockLeaves = blockLeaves;
        this.minTreeHeight = minTreeHeight;
        this.maxTreeHeight = maxTreeHeight;
    }

    public GrowthcraftTreeWorldGen(Block blockLog, Block blockLeaves, boolean notify) {
        super(notify);
        this.blockLog = blockLog;
        this.blockLeaves = blockLeaves;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos pos) {
        if (!worldIn.isRemote) {
            final int maxGrowthHeight = rand.nextInt(maxTreeHeight - minTreeHeight) + minTreeHeight;

            BlockPos baseBlockPos = pos.down();

            if (canGrow(worldIn, pos, maxGrowthHeight)) {
                // Spawn the wood logs for the tree.
                for (int i = 1; i <= maxGrowthHeight; i++) {
                    worldIn.setBlockState(baseBlockPos.up(i), blockLog.getDefaultState());
                }

                // Spawn the leaves downward, starting at max height + 1.
                for (int layerId = maxGrowthHeight + 1; layerId >= maxGrowthHeight - 4; layerId--) {
                    if (layerId == maxGrowthHeight + 1) {
                        spawnLeavesOnRadius(worldIn, pos.up(layerId), 1, false);
                    } else if (layerId == maxGrowthHeight) {
                        spawnLeavesOnRadius(worldIn, pos.up(layerId), 2, false);
                    } else if (layerId == maxGrowthHeight - 1) {
                        spawnLeavesOnRadius(worldIn, pos.up(layerId), 3, true);
                    } else if ( layerId == maxGrowthHeight - 2 ) {
                        spawnLeavesOnRadius(worldIn, pos.up(layerId), 3, true);
                    }
                }

                return true;
            }
        }
        return false;
    }

    private boolean canGrow(World worldIn, BlockPos pos, int growthHeight) {
        for (int i = 1; i <= growthHeight; i++) {
            Block block = worldIn.getBlockState(pos.up(i)).getBlock();
            if (!(block instanceof BlockAir)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Spawn a given block leaves at the given block pos.
     * @param worldIn The world instance.
     * @param pos BlockPos to spawn a leaf block.
     */
    private void spawnLeaves(World worldIn, BlockPos pos) {
        if (isReplaceable(worldIn, pos)) {
            worldIn.setBlockState(pos, blockLeaves.getDefaultState());
        }
    }

    /**
     * Spawn a set leaves around a give block position.
     * @param worldIn The world object.
     * @param centerPos Center of the area to spawn leaves.
     * @param radius Radius to spawn the leaves.
     * @param randomLeaves Determine if leaves should be random.
     */
    private void spawnLeavesOnRadius(World worldIn, BlockPos centerPos, int radius, boolean randomLeaves) {
        // List of block positions to spawn leaves.
        BlockPos posUpperLeft = new BlockPos(centerPos.getX() + radius, centerPos.getY(), centerPos.getZ() + radius );
        BlockPos posLowerRight = new BlockPos(centerPos.getX() - radius, centerPos.getY(), centerPos.getZ() - radius );

        Iterable<BlockPos> blockPosList = BlockPos.getAllInBox(posUpperLeft, posLowerRight);

        blockPosList.forEach( blockPos -> {
            boolean spawnBlock = true;
            // Check if there is already a block in this position.
            if ( worldIn.getBlockState(blockPos).getBlock() instanceof BlockAir ) {
                //if ( randomLeaves && random.nextInt(100) <= 5 ) spawnBlock = false;
                if ( spawnBlock ) spawnLeaves(worldIn, blockPos);
            }
        } );

    }

}
