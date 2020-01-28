package growthcraft.cellar.common.tileentity.device;

import growthcraft.cellar.common.tileentity.TileEntityFermentBarrel;
import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.processing.fermenting.IFermentationRecipe;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.fluids.GrowthcraftFluidUtils;
import growthcraft.core.shared.io.nbt.NBTHelper;
import growthcraft.core.shared.item.ItemUtils;
import growthcraft.core.shared.tileentity.device.DeviceFluidSlot;
import growthcraft.core.shared.tileentity.device.DeviceInventorySlot;
import growthcraft.core.shared.tileentity.device.DeviceProgressive;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class FermentBarrel extends DeviceProgressive<IFermentationRecipe> {

    private DeviceInventorySlot fermentSlot;
    private DeviceFluidSlot fluidSlot;

    public FermentBarrel(TileEntityFermentBarrel te, int fermentSlotId, int fluidSlotId) {
        super(te);
        this.timeMax = GrowthcraftCellarConfig.fermentTime;
        this.fermentSlot = new DeviceInventorySlot(te, fermentSlotId);
        this.fluidSlot = new DeviceFluidSlot(te, fluidSlotId);
    }


    @Override
    protected IFermentationRecipe loadRecipe() {
        return CellarRegistry.instance().fermenting().findRecipe(GrowthcraftFluidUtils.removeStackTags(fluidSlot.get()), fermentSlot.get());
    }

    @Override
    protected boolean canProcess() {
        IFermentationRecipe recipe = getWorkingRecipe();
        if(recipe == null) return false;
        //Checks for input fluids
        if(!recipe.getInputFluidStack().containsFluidStack(fluidSlot.get())) return false;
        //Checks for input items
        return recipe.getFermentingItemStack().containsItemStack(fermentSlot.get());
    }

    @Override
    public void process(IFermentationRecipe recipe) {
        final ItemStack fermentItem = fermentSlot.get();
        if (!ItemUtils.isEmpty(fermentItem) && recipe != null) {
                final FluidStack outputFluidStack = recipe.getOutputFluidStack();
                if (outputFluidStack != null) {
                    fluidSlot.set(GrowthcraftFluidUtils.exchangeFluid(fluidSlot.get(), outputFluidStack.getFluid()));
                }

                if (CellarRegistry.instance().fermenting().isFallbackRecipe(recipe)) {
                    fermentSlot.consume(1);
                } else {
                    final IMultiItemStacks fermenter = recipe.getFermentingItemStack();
                    if (!ItemUtils.isEmpty(fermenter)) {
                        fermentSlot.consume(fermenter.getStackSize());
                    }
                }
        }
    }


    // I/O Stuff

    @Override
    public void legacyReadFromNBT(NBTTagCompound data) {
        // Used to migrate NBT datasets from older saves to newest structure.
        time = 0;
        if (data.hasKey("time"))
            time = NBTHelper.getInteger(data, "time");
        // NOTE: Initialize newer fields here if any!
    }


}
