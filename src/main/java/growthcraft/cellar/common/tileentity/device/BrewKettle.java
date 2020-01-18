package growthcraft.cellar.common.tileentity.device;

import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.events.EventBrewed;
import growthcraft.cellar.shared.init.GrowthcraftCellarItems;
import growthcraft.cellar.shared.processing.brewing.IBrewingRecipe;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.cellar.common.tileentity.TileEntityCellarDevice;
import growthcraft.core.shared.tileentity.component.TileHeatingComponent;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.fluids.GrowthcraftFluidUtils;
import growthcraft.core.shared.tileentity.device.DeviceBase;
import growthcraft.core.shared.tileentity.device.DeviceFluidSlot;
import growthcraft.core.shared.tileentity.device.DeviceInventorySlot;
import growthcraft.core.shared.tileentity.device.DeviceProgressive;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidStack;

public class BrewKettle extends DeviceProgressive<IBrewingRecipe> {
    // TODO: Create same recipe caching mechanism as for barrels. Is more performant, if recipe check is avoided on each TileEntity update.

    private float grain;
    private DeviceInventorySlot brewingSlot;
    private DeviceInventorySlot residueSlot;
    private DeviceInventorySlot lidSlot;
    private DeviceFluidSlot inputFluidSlot;
    private DeviceFluidSlot outputFluidSlot;
    private TileHeatingComponent heatComponent;

    public BrewKettle(TileEntityCellarDevice te, int brewSlotId, int residueSlotId, int lidSlotId, int inputFluidSlotId, int outputFluidSlotId) {
        super(te);
        this.brewingSlot = new DeviceInventorySlot(te, brewSlotId);
        this.residueSlot = new DeviceInventorySlot(te, residueSlotId);
        this.lidSlot = new DeviceInventorySlot(te, lidSlotId);
        this.inputFluidSlot = new DeviceFluidSlot(te, inputFluidSlotId);
        this.outputFluidSlot = new DeviceFluidSlot(te, outputFluidSlotId);
        this.heatComponent = new TileHeatingComponent(te, 0.5f);
    }

    public void setGrain(float g) {
        this.grain = g;
    }


    public BrewKettle setHeatMultiplier(float h) {
        heatComponent.setHeatMultiplier(h);
        return this;
    }

    public float getHeatMultiplier() {
        return heatComponent.getHeatMultiplier();
    }

    public boolean isHeated() {
        return getHeatMultiplier() > 0;
    }

    public boolean hasFluid() {
        return inputFluidSlot.hasContent() || outputFluidSlot.hasContent();
    }

    @Override
    protected IBrewingRecipe loadRecipe() {
        boolean hasLid = GrowthcraftCellarItems.brewKettleLid.equals(lidSlot.get().getItem());
        return CellarRegistry.instance().brewing().findRecipe(GrowthcraftFluidUtils.removeStackTags(inputFluidSlot.get()), brewingSlot.get(), hasLid);
    }

    @Override
    public IBrewingRecipe getWorkingRecipe() {
        if (!isHeated()) return null;

        final IBrewingRecipe recipe = loadRecipe();
        if (recipe == null) return null;

        if (brewingSlot.isEmpty()) return null;

        // Avatair: Why the double check for input item and input fluid?
        if (!CellarRegistry.instance().brewing().isFallbackRecipe(recipe)) {
            final IMultiItemStacks expected = recipe.getInputItemStack();
            if (!brewingSlot.hasEnough(expected)) return null;
        }

        final FluidStack inputFluid = recipe.getInputFluidStack();
        if (!inputFluidSlot.hasEnough(inputFluid)) return null;

        if (outputFluidSlot.isEmpty()) return recipe;

        final FluidStack outputFluid = recipe.asFluidStack();
        if (!outputFluidSlot.hasCapacityFor(outputFluid)) return null;

        return super.getWorkingRecipe();
    }

    @Override
    public boolean canProcess() {
        return getWorkingRecipe() != null;
    }

    private void produceGrain(IBrewingRecipe recipe) {
        final Residue res = recipe.getResidue();
        if (res != null) {
            this.grain = this.grain + res.pomaceRate;
            while (this.grain >= 1.0F) {
                this.grain -= 1.0F;
                residueSlot.increaseStack(res.residueItem);
            }
        }
    }

    protected void process(IBrewingRecipe recipe) {
        if(!canProcess()) return;
        produceGrain(recipe);
        inputFluidSlot.consume(GrowthcraftFluidUtils.replaceFluidStackTags(recipe.getInputFluidStack(), inputFluidSlot.get()), true);
        outputFluidSlot.fill(recipe.asFluidStack(), true);
        if (!CellarRegistry.instance().brewing().isFallbackRecipe(recipe)) {
            brewingSlot.consume(recipe.getInputItemStack());
        } else {
            brewingSlot.consume(1);
        }
        markForUpdate(true);
        MinecraftForge.EVENT_BUS.post(new EventBrewed(parent, recipe));
    }

    @Override
    protected float getSpeedMultiplier(){
        return super.getSpeedMultiplier()*getHeatMultiplier();
    }

    public void update() {
        heatComponent.update();
        super.update();
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.grain = data.getFloat("grain");
        heatComponent.readFromNBT(data, "heat_component");
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setFloat("grain", grain);
        heatComponent.writeToNBT(data, "heat_component");
    }

    /**
     * @param buf - buffer to read from
     */
    @Override
    public boolean readFromStream(ByteBuf buf) {
        super.readFromStream(buf);
        this.grain = buf.readFloat();
        heatComponent.readFromStream(buf);
        return false;
    }

    /**
     * @param buf - buffer to write to
     */
    @Override
    public boolean writeToStream(ByteBuf buf) {
        super.writeToStream(buf);
        buf.writeFloat(grain);
        heatComponent.writeToStream(buf);
        return false;
    }
}
