package growthcraft.cellar.client.utils;

import growthcraft.core.shared.fluids.IFluidTanks;
import growthcraft.core.shared.utils.BBox;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidRenderUtils {

    public static RenderedFluid getActualFluidBBoxForMax(BBox maxFluidBBox, IFluidTanks tanks) {
        int slotToRender = -1;
        double fluidHeight = -1.0;
        int numTanks = tanks.getTankCount();
        for (int i = 0; i < numTanks; i++) {
            if (!tanks.isFluidTankFilled(i))
                continue;
            float f = (float) tanks.getFluidAmount(i) / (float) tanks.getFluidTank(i).getCapacity();
            if (fluidHeight < f) {
                slotToRender = i;
                fluidHeight = f;
            }
        }

        fluidHeight *= maxFluidBBox.h();
        fluidHeight = roundToPixel(fluidHeight);

        if (slotToRender == -1 || fluidHeight <= 0) {
            fluidHeight = 0;
        }

        BBox fluidBBox = new BBox(maxFluidBBox.x0(), maxFluidBBox.y0(), maxFluidBBox.z0(),
                maxFluidBBox.x1(), maxFluidBBox.y0() + fluidHeight, maxFluidBBox.z1());
        FluidTank tank = slotToRender != -1 ? tanks.getFluidTank(slotToRender) : null;
        return new RenderedFluid(tanks, slotToRender, tank, fluidBBox);
    }

    private static double roundToPixel(double fluidHeight) {
        return MathHelper.floor(fluidHeight * 16.0 + 0.5) / 16.0;
    }

    ////////////

    public static class RenderedFluid {
        private final IFluidTanks tanks;
        private final int renderedTankSlot;

        private final FluidTank renderedTank;
        private final BBox fluidBBox;

        public RenderedFluid(IFluidTanks tanks, int renderedTankSlot, FluidTank renderedTank, BBox fluidBBox) {
            this.tanks = tanks;
            this.renderedTankSlot = renderedTankSlot;
            this.renderedTank = renderedTank;
            this.fluidBBox = fluidBBox;
        }

        public IFluidTanks getTanks() {
            return tanks;
        }

        public int getRenderedTankSlot() {
            return renderedTankSlot;
        }

        public FluidTank getRenderedTank() {
            return renderedTank;
        }

        public BBox getFluidBBox() {
            return fluidBBox;
        }

        public boolean isNone() {
            return renderedTank == null;
        }

        public FluidStack getFluidStack() {
            if (renderedTank == null)
                return null;
            return renderedTank.getFluid();
        }
    }
}
