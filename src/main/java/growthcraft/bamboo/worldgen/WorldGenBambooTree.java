package growthcraft.bamboo.worldgen;

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
            Block block = worldIn.getBlockState(pos).getBlock();
            if ( ! ( block instanceof BlockAir ) ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        final int height = rand.nextInt(maxBambooTreeHieght - minBambooTreeHieght) + minBambooTreeHieght;

        // Is there room to generate?
        if ( canGrow(worldIn, position, height) ) {

        }

        return false;
    }

}
