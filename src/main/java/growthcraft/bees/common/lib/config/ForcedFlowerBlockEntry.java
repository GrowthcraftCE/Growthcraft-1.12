package growthcraft.bees.common.lib.config;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class ForcedFlowerBlockEntry extends AbstractFlowerBlockEntry {
    public ForcedFlowerBlockEntry(IBlockState blockState) {
        super(blockState);
    }

    public ForcedFlowerBlockEntry(Block block, int meta) {
        super(block, meta);
    }

    @Override
    public boolean canPlaceAt(World world, BlockPos pos) {
        final IBlockState existingBlockState = world.getBlockState(pos);
        if (existingBlockState != null) {
            if (!existingBlockState.getBlock().isReplaceable(world, pos)) return false;
        }
        final BlockPos posDown = pos.down();
        final IBlockState soilBlockState = world.getBlockState(posDown);
        if (soilBlockState == null)
            return false;
        if (getBlock() instanceof IPlantable) {
            return soilBlockState.getBlock().canSustainPlant(soilBlockState, world, posDown, EnumFacing.UP, (IPlantable) getBlock());
        }
        return true;
    }
}
