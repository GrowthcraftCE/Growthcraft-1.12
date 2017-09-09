package growthcraft.bamboo.worldgen;

import growthcraft.bamboo.init.GrowthcraftBambooBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenBambooTree extends WorldGenAbstractTree {

    private int minBambooTreeHieght = 6;
    private int maxBambooTreeHieght = 12;

    private final boolean useExtraRandomHeight;

    public WorldGenBambooTree(boolean notify, boolean useExtraRandomHeightIn) {
        super(notify);
        this.useExtraRandomHeight = useExtraRandomHeightIn;
    }

    public boolean canGrow(World worldIn, BlockPos pos, int height) {
        for ( int i = 1; i <= height; i++ ) {
            Block block = worldIn.getBlockState(pos.up(i)).getBlock();
            if ( ! ( block instanceof BlockAir ) ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos pos) {

        if (!worldIn.isRemote) {
            final int height = rand.nextInt(maxBambooTreeHieght - minBambooTreeHieght) + minBambooTreeHieght;
            // Is there room to generate?
            if (canGrow(worldIn, pos, height)) {

                // Grow the Tree
                worldIn.setBlockState(pos, GrowthcraftBambooBlocks.bambooStalk.getDefaultState());
                for (int i = 1; i <= height; i++) {
                    worldIn.setBlockState(pos.up(i), GrowthcraftBambooBlocks.bambooStalk.getDefaultState());

                    if ( i == height ) {
                        // Then this is last iteration and we need to generate the leaves.
                        spawnLeaves(worldIn, pos.up(i+1));
                        spawnLeaves(worldIn, pos.up(i).north());
                        spawnLeaves(worldIn, pos.up(i).east());
                        spawnLeaves(worldIn, pos.up(i).south());
                        spawnLeaves(worldIn, pos.up(i).west());
                        // Lower ring of leaves
                        spawnLeaves(worldIn, pos.up(i-3).north());
                        spawnLeaves(worldIn, pos.up(i-3).east());
                        spawnLeaves(worldIn, pos.up(i-3).south());
                        spawnLeaves(worldIn, pos.up(i-3).west());
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void spawnLeaves(World worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos).getBlock() instanceof BlockAir ) {
            worldIn.setBlockState(pos, GrowthcraftBambooBlocks.bambooLeaves.getDefaultState());
        }
    }

}
