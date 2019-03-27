package growthcraft.bees.common.worldgen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BeeHiveWorldGen extends WorldGenerator {

    @Override
    public boolean generate(World world, Random random, BlockPos blockPos) {
        return false;
    }

}
