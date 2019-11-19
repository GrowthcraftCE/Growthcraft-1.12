package growthcraft.bees.common.worldgen;

import growthcraft.bees.shared.init.GrowthcraftBeesBlocks;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class BeeHiveWorldGen implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator iChunkGenerator, IChunkProvider iChunkProvider) {
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockPos chunk = new BlockPos(chunkX, 0, chunkZ);

        if(!isValidBiome(world, chunk)) {
            return;
        }

        int x = chunk.getX() + random.nextInt(16);
        int z = chunk.getZ() + random.nextInt(16);
        int y = world.getHeight(x, z) - 1;

        mutableBlockPos.setPos(x, y, z);
        IBlockState state = world.getBlockState(mutableBlockPos);
        IBlockState blockStateDown = world.getBlockState(mutableBlockPos.down());

        if (state.getBlock() instanceof BlockLeaves && blockStateDown.getBlock() instanceof BlockAir) {
            generateBeeHive(world, random, mutableBlockPos.down());
        }

    }

    /**
     * Attempt to generate a beehive.
     * @param world The current world instance
     * @param random A random seed for randomness in generating
     * @param blockPos The BlockPos of the current chunk
     */
    private static void generateBeeHive(World world, Random random, BlockPos blockPos) {
        world.setBlockState(blockPos, GrowthcraftBeesBlocks.beeHive.getDefaultState());
    }

    /**
     * Determine if the current biome is fit for generating bee hives.
     * @param world The current world instance
     * @param chunkX
     * @param chunkZ
     * @return
     */
    private static boolean isValidBiome(World world, BlockPos chunk) {
        final Biome biome = world.getBiomeForCoordsBody(new BlockPos(chunk.getX(), chunk.getY(), chunk.getZ()));
        return (BiomeDictionary.hasType(biome, BiomeDictionary.Type.END) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER));
    }

}
