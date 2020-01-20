package growthcraft.milk.common.tileentity.device;

import growthcraft.core.shared.tileentity.GrowthcraftTileDeviceBase;
import growthcraft.core.shared.tileentity.device.DeviceInventorySlot;
import growthcraft.core.shared.tileentity.device.DeviceProgressive;
import growthcraft.milk.common.tileentity.TileEntityCheesePress;
import growthcraft.milk.shared.MilkRegistry;
import growthcraft.milk.shared.processing.cheesepress.ICheesePressRecipe;

public class CheesePress extends DeviceProgressive<ICheesePressRecipe> {

    private DeviceInventorySlot invSlot;

    public CheesePress(GrowthcraftTileDeviceBase te) {
        super(te);
        invSlot = new DeviceInventorySlot(te, 0);
    }

    @Override
    protected ICheesePressRecipe loadRecipe() {
        return MilkRegistry.instance().cheesePress().findRecipe(invSlot.get());
    }

    @Override
    protected boolean canProcess() {
        ICheesePressRecipe recipe = getWorkingRecipe();
        if (invSlot.get() == null) return false;
        if (recipe == null) return false;
        if (!invSlot.hasEnough((recipe.getInputItemStack()))) return false;
        return true;
    }

    @Override
    protected float getSpeedMultiplier(){
        if(parent instanceof TileEntityCheesePress){
            return super.getSpeedMultiplier()*(((TileEntityCheesePress) parent).isPressed()? 1:0);
        }else{
            return super.getSpeedMultiplier();
        }
    }

    @Override
    public void process(ICheesePressRecipe recipe) {
        if (recipe != null) {
            invSlot.set(recipe.getOutputItemStack().copy());
        }
    }
}
