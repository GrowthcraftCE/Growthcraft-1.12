package growthcraft.bamboo.worldgen;

import growthcraft.bamboo.init.GrowthcraftBambooBlocks;
import growthcraft.core.utils.GrowthcraftLogger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenBambooTree extends WorldGenAbstractTree {

    private int minBambooTreeHieght = 6;
    private int maxBambooTreeHieght = 9;

    private final boolean useExtraRandomHeight;

    public WorldGenBambooTree(boolean notify, boolean useExtraRandomHeightIn) {
        super(notify);
        this.useExtraRandomHeight = useExtraRandomHeightIn;
    }

    public boolean canGrow(World worldIn, BlockPos pos, int height) {
        for ( int i = 1; i <= height; i++ ) {
            Block block = worldIn.getBlockState(pos.up(i)).getBlock();
            if ( ! ( block instanceof BlockAir ) ) {
                GrowthcraftLogger.getLogger().info("canGrow tree is false, there isn't BlockAir at pos.up(" + i + ") there is a " + block.getUnlocalizedName() );
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos pos) {
        GrowthcraftLogger.getLogger().info("worldgenerator.generate has been called!");
        final int height = rand.nextInt(maxBambooTreeHieght - minBambooTreeHieght) + minBambooTreeHieght;

        // Is there room to generate?
        if ( canGrow(worldIn, pos, height) ) {
            for ( int i = 1; 1 <= height; i++ ) {
                worldIn.setBlockState(pos.up(i), GrowthcraftBambooBlocks.bambooStalk.getDefaultState());
                //TODO: Fix the tree generation.
            }
            return true;
        }

        return false;
    }

}
