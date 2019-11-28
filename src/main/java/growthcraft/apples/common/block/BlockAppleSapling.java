package growthcraft.apples.common.block;

import growthcraft.apples.common.worldgen.WorldGenAppleTree;
import growthcraft.apples.shared.Reference;
import growthcraft.core.shared.block.GrowthcraftBlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BlockAppleSapling extends GrowthcraftBlockSapling {

    public BlockAppleSapling(String unlocalizedName) {
        super(Reference.MODID, unlocalizedName);
    }

    @Override
    public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.generateTree(worldIn, pos, state, rand);
        WorldGenerator worldGenerator = new WorldGenAppleTree(true);
        worldGenerator.generate(worldIn, rand, pos);
    }
}
