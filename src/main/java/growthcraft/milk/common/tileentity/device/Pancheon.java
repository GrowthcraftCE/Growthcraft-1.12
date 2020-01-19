package growthcraft.milk.common.tileentity.device;

import growthcraft.core.shared.tileentity.GrowthcraftTileDeviceBase;
import growthcraft.core.shared.tileentity.device.DeviceFluidSlot;
import growthcraft.core.shared.tileentity.device.DeviceProgressive;
import growthcraft.milk.shared.MilkRegistry;
import growthcraft.milk.shared.processing.pancheon.IPancheonRecipe;
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
    public Pancheon(GrowthcraftTileDeviceBase te, int fsInput, int fsTop, int fsBottom) {
        super(te);
        this.inputSlot = new DeviceFluidSlot(te, fsInput);
        this.topSlot = new DeviceFluidSlot(te, fsTop);
        this.bottomSlot = new DeviceFluidSlot(te, fsBottom);
    }

    /**
     * Get the matching recipe
     *
     * @return recipe
     */
    @Override
    protected IPancheonRecipe loadRecipe() {
        return MilkRegistry.instance().pancheon().getRecipe(inputSlot.get());
    }

    @Override
    protected boolean canProcess() {
        IPancheonRecipe recipe = getWorkingRecipe();
        if(recipe == null) return false;
        //Checks for input fluids
        if(!inputSlot.hasEnough(recipe.getInputFluid())) return false;
        //Checks for output fluids
        if(!topSlot.hasCapacityFor(recipe.getTopOutputFluid())) return false;
        if(!bottomSlot.hasCapacityFor(recipe.getBottomOutputFluid())) return false;

        return true;
    }

    /**
     * Complete the process and commit the changes
     */
    protected void process(IPancheonRecipe recipe) {
        inputSlot.consume(recipe.getInputFluid(), true);
        final FluidStack top = recipe.getTopOutputFluid();
        if (top != null) this.topSlot.fill(top, true);
        final FluidStack bottom = recipe.getBottomOutputFluid();
        if (bottom != null) this.bottomSlot.fill(bottom, true);
    }

}
