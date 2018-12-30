package growthcraft.core.shared.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public interface IPaddy {
    @Nonnull public FluidStack getFluidStack();
    @Nonnull public Fluid getFillingFluid();

    public int getMaxPaddyMeta(IBlockAccess world, int x, int y, int z);
    public boolean isFilledWithFluid(IBlockAccess world, BlockPos pos);
}
