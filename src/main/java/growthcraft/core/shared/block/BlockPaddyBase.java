package growthcraft.core.shared.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nonnull;

public class BlockPaddyBase extends GrowthcraftBlockBase implements IPaddy {

    public BlockPaddyBase(Material material) {
        super(material);
        this.setTickRandomly(true);
    }

    @Nonnull
    @Override
    public Block getFluidBlock() {
        return null;
    }

    @Nonnull
    @Override
    public Fluid getFillingFluid() {
        return null;
    }

    @Override
    public int getMaxPaddyMeta(IBlockAccess world, int x, int y, int z) {
        return 0;
    }

    @Override
    public boolean isFilledWithFluid(IBlockAccess world, int x, int y, int z, int meta) {
        return false;
    }

    @Override
    public boolean canConnectPaddyTo(IBlockAccess world, int x, int y, int z, int meta) {
        return false;
    }

    @Override
    public boolean isBelowFillingFluid(IBlockAccess world, int x, int y, int z) {
        return false;
    }
}
