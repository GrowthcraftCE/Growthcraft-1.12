package growthcraft.bees.common.worldgen;

import growthcraft.bees.shared.init.GrowthcraftBeesBlocks;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BeeHiveWorldGen extends WorldGenerator {

    public BeeHiveWorldGen() {
    }

    @Override
    public boolean generate(World world, Random random, BlockPos blockPos) {
        IBlockState state = world.getBlockState(blockPos);
        IBlockState blockStateDown = world.getBlockState(blockPos.down());

        if ( state.getBlock() instanceof BlockLeaves && blockStateDown.getBlock() instanceof BlockAir) {
            world.setBlockState(blockPos.down(), GrowthcraftBeesBlocks.beeHive.getDefaultState());
            return true;
        }

        return false;
    }

}
