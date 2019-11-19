package growthcraft.bees.shared.init;

import growthcraft.bees.common.worldgen.BeeHiveWorldGen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class GrowthcraftBeesWorldGen implements IWorldGenerator {

    private BeeHiveWorldGen beeHiveWorldGen = new BeeHiveWorldGen();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator iChunkGenerator, IChunkProvider iChunkProvider) {
        if(!isValidBiome(world, chunkX, chunkZ)) {
            return;
        }

        int x = chunkX + random.nextInt(16);
        int z = chunkX + random.nextInt(16);
        int y = chunkX + random.nextInt(16) + 64;

        // TODO: Replace this with a private method instead of calling a new WorldGenerator
        beeHiveWorldGen.generate(world, random, new BlockPos(x, y, z));
    }

    private static boolean isValidBiome(World world, int chunkX, int chunkZ) {
        final Biome biome = world.getBiomeForCoordsBody(new BlockPos(chunkX, 0, chunkZ));
        return (BiomeDictionary.hasType(biome, BiomeDictionary.Type.END) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER));
    }

}
