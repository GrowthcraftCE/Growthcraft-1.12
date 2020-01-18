package growthcraft.cellar.common.tileentity.device;

import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.processing.culturing.ICultureRecipe;
import growthcraft.cellar.common.tileentity.TileEntityCellarDevice;
import growthcraft.cellar.shared.processing.fermenting.IFermentationRecipe;
import growthcraft.core.shared.fluids.GrowthcraftFluidUtils;
import growthcraft.core.shared.tileentity.component.TileHeatingComponent;
import growthcraft.core.shared.tileentity.device.DeviceFluidSlot;
import growthcraft.core.shared.tileentity.device.DeviceInventorySlot;
import growthcraft.core.shared.tileentity.device.DeviceProgressive;

public class CultureGenerator extends DeviceProgressive<ICultureRecipe> {
    protected DeviceFluidSlot fluidSlot;
    protected DeviceInventorySlot invSlot;
    protected TileHeatingComponent heatComponent;

    /**
     * @param te             - parent tile entity
     * @param fluidSlotIndex - fluid slot id to use in parent
     *                       Fluid will be used from this slot
     * @param invSlotIndex   - inventory slot id to use in parent
     *                       Culture will be generated into this slot
     */
    public CultureGenerator(TileEntityCellarDevice te, TileHeatingComponent heatComp, int fluidSlotIndex, int invSlotIndex) {
        super(te);
        this.heatComponent = heatComp;
        this.fluidSlot = new DeviceFluidSlot(te, fluidSlotIndex);
        this.invSlot = new DeviceInventorySlot(te, invSlotIndex);
        setTimeMax(1200);
    }

    @Override
    protected ICultureRecipe loadRecipe() {
        return  CellarRegistry.instance().culturing().findRecipe(fluidSlot.get(), heatComponent.getHeatMultiplier());
    }

    public float getHeatMultiplier() {
        return heatComponent.getHeatMultiplier();
    }

    public boolean isHeated() {
        return heatComponent.isHeated();
    }

    protected boolean canProcess() {
        ICultureRecipe recipe = getWorkingRecipe();
        if(recipe == null) return false;
        if(!fluidSlot.hasContent()) return false;
        if (!fluidSlot.hasEnough(recipe.getInputFluidStack())) return false;
        return invSlot.isEmpty() || invSlot.hasMatchingWithCapacity(recipe.getOutputItemStack());
    }

    @Override
    protected void process(ICultureRecipe recipe) {
        if(! canProcess()){return;}
        fluidSlot.consume(recipe.getInputFluidStack(), true);
        invSlot.increaseStack(recipe.getOutputItemStack());
    }

    @Override
    public void update() {
        super.update();
    }
}
