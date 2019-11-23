package growthcraft.bees.shared.init;

import growthcraft.bees.common.worldgen.BeeHiveWorldGen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class GrowthcraftBeesWorldGen implements IWorldGenerator {

    private BeeHiveWorldGen beeHiveWorldGen = new BeeHiveWorldGen();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator iChunkGenerator, IChunkProvider iChunkProvider) {
        int x = chunkX + random.nextInt(15);
        int z = chunkX + random.nextInt(15);
        int y = chunkX + random.nextInt(15) + 64;

        beeHiveWorldGen.generate(world, random, new BlockPos(x, y, z));
    }
}
