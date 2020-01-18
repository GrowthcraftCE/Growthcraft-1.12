package growthcraft.milk.common.tileentity.device;

import growthcraft.core.shared.fluids.IFluidTanks;
import growthcraft.core.shared.tileentity.device.DeviceFluidSlot;
import growthcraft.core.shared.tileentity.device.DeviceProgressive;
import growthcraft.milk.shared.MilkRegistry;
import growthcraft.milk.shared.processing.pancheon.IPancheonRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

public class Pancheon extends DeviceProgressive<IPancheonRecipe> {
    private DeviceFluidSlot inputSlot;
    private DeviceFluidSlot topSlot;
    private DeviceFluidSlot bottomSlot;

    /**
     * @param te       - tile entity
     * @param fsInput  - input fluid slot
     * @param fsTop    - top output slot
     * @param fsBottom - bottom output slot
     */
    public Pancheon(TileEntity te, int fsInput, int fsTop, int fsBottom) {
        super(te);
        if (te instanceof IFluidTanks) {
            final IFluidTanks ifl = (IFluidTanks) te;
            this.inputSlot = new DeviceFluidSlot(ifl, fsInput);
            this.topSlot = new DeviceFluidSlot(ifl, fsTop);
            this.bottomSlot = new DeviceFluidSlot(ifl, fsBottom);
        } else {
            throw new IllegalArgumentException("The provided TileEntity MUST implement the IFluidTanks interface");
        }
    }

    /**
     * Get the matching recipe
     *
     * @return recipe
     */
    @Override
    protected IPancheonRecipe loadRecipe() {
        IPancheonRecipe recipe = MilkRegistry.instance().pancheon().getRecipe(inputSlot.get());
        if (recipe == null) return null;
        if (!this.topSlot.hasMatchingWithCapacity(recipe.getTopOutputFluid())) return null;
        if (!this.bottomSlot.hasMatchingWithCapacity(recipe.getBottomOutputFluid())) return null;
        return recipe;
    }


    /**
     * Complete the process and commit the changes
     */
    private void commitRecipe() {
        final IPancheonRecipe recipe = loadRecipe();
        if (recipe != null) {
            this.inputSlot.consume(recipe.getInputFluid().amount, true);

            final FluidStack top = recipe.getTopOutputFluid();
            if (top != null) this.topSlot.fill(top, true);

            final FluidStack bottom = recipe.getBottomOutputFluid();
            if (bottom != null) this.bottomSlot.fill(bottom, true);
        }
    }

    /**
     * Tick update
     */
    public void update() {
        final IPancheonRecipe recipe = getWorkingRecipe();
        if (recipe != null) {
            setTimeMax(recipe.getTime());
            increaseTime();
            if (time >= timeMax) {
                resetTime();
                commitRecipe();
            }
            markDirtyAndUpdate(true);
        } else {
            if (resetTime())
            	markDirtyAndUpdate(false);
        }
    }
}
