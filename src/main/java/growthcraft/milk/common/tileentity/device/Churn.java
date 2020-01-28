package growthcraft.milk.common.tileentity.device;

import growthcraft.core.shared.tileentity.GrowthcraftTileDeviceBase;
import growthcraft.core.shared.tileentity.device.DeviceFluidSlot;
import growthcraft.core.shared.tileentity.device.DeviceInventorySlot;
import growthcraft.core.shared.tileentity.device.DeviceProgressive;
import growthcraft.milk.shared.MilkRegistry;
import growthcraft.milk.shared.processing.churn.IChurnRecipe;

public class Churn extends DeviceProgressive<IChurnRecipe> {

    private DeviceFluidSlot inputFluidSlot;
    private DeviceFluidSlot outputFluidSlot;
    private DeviceInventorySlot outputSlot;

    public Churn(GrowthcraftTileDeviceBase te, int inputFluidSlot, int outputFluidSlot, int outputSlot) {
        super(te);
        this.outputFluidSlot = new DeviceFluidSlot(te, outputFluidSlot);
        this.inputFluidSlot = new DeviceFluidSlot(te, inputFluidSlot);
        this.outputSlot = new DeviceInventorySlot(te, outputSlot);
    }

    @Override
    protected IChurnRecipe loadRecipe() {
        return MilkRegistry.instance().churn().getRecipe(inputFluidSlot.get());
    }

    @Override
    protected boolean canProcess() {
        IChurnRecipe recipe = getWorkingRecipe();
        if(recipe == null) return false;
        //Checks for input fluids
        if(!inputFluidSlot.hasEnough(recipe.getInputFluidStack())) return false;
        //Checks for input items
        //Checks for output fluids
        if(!outputFluidSlot.hasCapacityFor(recipe.getOutputFluidStack())) return false;
        //Checks for output items
        if(!outputSlot.hasCapacityFor(recipe.getOutputItemStack())) return false;

        return true;
    }

    @Override
    public void process(IChurnRecipe recipe) {
        inputFluidSlot.consume(recipe.getInputFluidStack(), true);
        outputFluidSlot.fill(recipe.getOutputFluidStack(),true);
        outputSlot.increaseStack(recipe.getOutputItemStack());
    }

    //Churns
    //Using the time as a number of churns
    @Override
    public void update() {
        final IChurnRecipe recipe = getWorkingRecipe();
        if (canProcess()) {
            setTimeMax(recipe.getChurns());
            increaseTime();
            if (time >= timeMax) {
                resetTime();
                process(recipe);
            }
        } else {
            if (resetTime()) markForUpdate(true);
        }
    }

}
