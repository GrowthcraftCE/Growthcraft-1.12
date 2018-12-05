package growthcraft.core.common.worldgen;

import growthcraft.core.shared.config.GrowthcraftCoreConfig;
import growthcraft.core.shared.init.GrowthcraftCoreBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class BlockSaltGeneration implements IWorldGenerator {

    // Needs to be configurable
    private int minHieght = GrowthcraftCoreConfig.blockSaltOreMinHeight;
    private int maxHieght = GrowthcraftCoreConfig.blockSaltOreMaxHeight;

    private WorldGenerator salt_overworld;

    public BlockSaltGeneration() {
        salt_overworld = new WorldGenMinable(GrowthcraftCoreBlocks.salt_ore.getBlock().getDefaultState(), 9);
    }

    // runGenerator from CJMinecraft
    private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z,
                              int chancesToSpawn, int minHeight, int maxHeight) {

        if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
            throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

        int heightDiff = maxHeight - minHeight + 1;
        for (int i = 0; i < chancesToSpawn; i++) {
            int x = chunk_X * 16 + rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightDiff);
            int z = chunk_Z * 16 + rand.nextInt(16);
            generator.generate(world, rand, new BlockPos(x, y, z));
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
            case 0: // Overworld
                this.runGenerator(salt_overworld, world, random, chunkX, chunkZ, GrowthcraftCoreConfig.blockSaltOreChanceToSpawn, minHieght, maxHieght);
                break;
            case 1: // The End
                break;
            case -1: // The Nether
                break;
            default:
                break;
        }
    }
}
