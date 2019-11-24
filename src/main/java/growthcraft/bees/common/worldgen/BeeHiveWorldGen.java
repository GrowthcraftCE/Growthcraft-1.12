package growthcraft.bees.common.worldgen;

import growthcraft.bees.GrowthcraftBees;
import growthcraft.bees.shared.config.GrowthcraftBeesConfig;
import growthcraft.bees.shared.init.GrowthcraftBeesBlocks;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import org.apache.logging.log4j.Level;

import java.util.Random;

public class BeeHiveWorldGen implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator iChunkGenerator, IChunkProvider iChunkProvider) {
        final MutableBlockPos mutableBlockPos = new MutableBlockPos();

        BlockPos chunkCenterPos = new BlockPos(
                chunkX * 16 + 8,
                world.getHeight(chunkX * 16 + 8, chunkZ * 16 + 8),
                chunkZ * 16 + 8);

        BlockPos chunkNorthWest = new BlockPos(
                chunkX * 16 + 4,
                world.getHeight(chunkX * 16 + 2, chunkZ * 16 + 2),
                chunkZ * 16 + 4);

        BlockPos chunkSouthEast = new BlockPos(
                chunkX * 16 + 12,
                world.getHeight(chunkX * 16 + 8, chunkZ * 16 + 8),
                chunkZ * 16 + 12);

        if(!isValidBiome(world)) {
            return;
        }

        // Check for randomness to generate a beehive in this chunk or not.
        if(random.nextInt(100) <= GrowthcraftBeesConfig.worldgenBeeHiveRarity) {
            int spawnedBeeHive = 0;

            mutableBlockPos.setPos(chunkCenterPos.getX(), chunkCenterPos.getY(), chunkCenterPos.getZ());
            IBlockState state = world.getBlockState(mutableBlockPos.down());

            if ( GrowthcraftBeesConfig.isDebug) {
                GrowthcraftBees.logger.log(Level.DEBUG,
                                "[DEBUG] Checking blocks in chunk [ %d, %d ] for generating BeeHive at [x = %d, y = %d, z = %d] %s",
                                chunkX,
                                chunkZ,
                                mutableBlockPos.getX(),
                                mutableBlockPos.getY(),
                                mutableBlockPos.getZ(),
                                state.getBlock().getRegistryName()

                );
            }

            // Get an area of blocks to check for leaves to spawn the beeHives
            Iterable<BlockPos> blocksInArea = BlockPos.getAllInBox(chunkNorthWest, chunkSouthEast);

            for ( BlockPos pos : blocksInArea ) {
                IBlockState blockState = world.getBlockState(pos);
                if ( blockState.getBlock() instanceof BlockLeaves
                        && world.getBlockState(pos.down()).getBlock() instanceof BlockAir ) {
                    setBlockToBeeHive(world, pos.down());
                    spawnedBeeHive++;
                }
                if ( spawnedBeeHive >= GrowthcraftBeesConfig.maxBeeHivesPerChunk ) {
                    break;
                }
            }

        }
    }

    /**
     * Attempt to generate a beehive.
     * @param world The current world instance
     * @param blockPos The BlockPos of the current chunk
     */
    private static void setBlockToBeeHive(World world, BlockPos blockPos) {
        if ( GrowthcraftBeesConfig.isDebug) {
            GrowthcraftBees.logger.log(Level.DEBUG, "Generating beehive at %d %d %d ", blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }
        world.setBlockState(blockPos, GrowthcraftBeesBlocks.beeHive.getDefaultState());
    }

    /**
     * Determine if the current biome is fit for generating bee hives.
     * @param world The current world instance
     * @return
     */
    private static boolean isValidBiome(World world) {
        return (world.provider.getDimension() == 0);
    }

}
