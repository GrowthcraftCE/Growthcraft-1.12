package growthcraft.cellar.common.block;

import growthcraft.cellar.common.worldgen.WorldGenCorkTree;
import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.block.GrowthcraftBlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BlockCorkSapling extends GrowthcraftBlockSapling {
    public BlockCorkSapling(String unlocalizedName) {
        super(Reference.MODID, unlocalizedName);
    }

    @Override
    public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.generateTree(worldIn, pos, state, rand);
        WorldGenerator worldGenerator = new WorldGenCorkTree(true);
        worldGenerator.generate(worldIn, rand, pos);
    }
}
