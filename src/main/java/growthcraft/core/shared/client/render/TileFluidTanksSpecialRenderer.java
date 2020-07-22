package growthcraft.core.shared.client.render;

import growthcraft.cellar.client.utils.FluidRenderUtils;
import growthcraft.core.shared.client.render.utils.RenderUtil;
import growthcraft.core.shared.fluids.IFluidTanks;
import growthcraft.core.shared.utils.BBox;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class TileFluidTanksSpecialRenderer<T extends TileEntity & IFluidTanks> extends TileEntitySpecialRenderer<T> {
    private final BBox fluidBBox;

    public TileFluidTanksSpecialRenderer(BBox fluidBBox) {
        this.fluidBBox = fluidBBox;
    }

    private double roundToPixel(double fluidHeight) {
        return MathHelper.floor(fluidHeight * 16.0 + 0.5) / 16.0;
    }

    public void renderMaxFluid(@Nonnull T te, double x, double y, double z) {
        FluidRenderUtils.RenderedFluid rf = FluidRenderUtils.getActualFluidBBoxForMax(fluidBBox, te);
        if (!rf.isNone()) {
            BBox renderedBBox = rf.getFluidBBox();
            RenderUtil.renderFluidCuboid(rf.getFluidStack(), te.getPos(), x, y, z,
                    renderedBBox.x0(), renderedBBox.y0(), renderedBBox.z0(),
                    renderedBBox.x1(), renderedBBox.y1(), renderedBBox.z1());
        }
    }

    public int getDefaultTotalCapacity(@Nonnull T te) {
        int totalCap = 0;
        for (int i = 0; i < te.getTankCount(); i++) {
            totalCap += te.getFluidTank(i).getCapacity();
        }

        return totalCap;
    }

    public void renderStackedFluid(@Nonnull T te, double x, double y, double z, int totalCap) {
        int numTanks = te.getTankCount();

        double y0 = fluidBBox.y0();
        double s = 1.0 / (double) totalCap;
        for (int i = 0; i < numTanks; i++) {
            if (!te.isFluidTankFilled(i))
                continue;
            FluidStack fluidStack = te.getFluidStack(i);

            double h = roundToPixel(fluidBBox.h() * s * (double) fluidStack.amount);
            if (h <= 0)
                continue;

            RenderUtil.renderFluidCuboid(fluidStack, te.getPos(), x, y, z,
                    fluidBBox.x0(), y0, fluidBBox.z0(),
                    fluidBBox.x1(), y0 + h, fluidBBox.z1());
            y0 += h;
        }
    }
}
