package growthcraft.milk.common.tileentity.device;

import growthcraft.core.shared.tileentity.GrowthcraftTileDeviceBase;
import growthcraft.core.shared.tileentity.component.TileHeatingComponent;
import growthcraft.core.shared.tileentity.device.DeviceFluidSlot;
import growthcraft.core.shared.tileentity.device.DeviceProgressive;
import growthcraft.milk.shared.processing.cheesevat.ICheeseVatRecipe;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class CheeseVat extends DeviceProgressive<ICheeseVatRecipe> {
    private DeviceFluidSlot primaryFluidSlot;
    private DeviceFluidSlot rennetFluidSlot;
    private DeviceFluidSlot wasteFluidSlot;
    private TileHeatingComponent heatComponent;



    public CheeseVat(GrowthcraftTileDeviceBase te, int primFluidSlot_id, int rennetFluidSlot_id, int wasteFluidSlot_id) {
        super(te);
        primaryFluidSlot = new DeviceFluidSlot(te,primFluidSlot_id);
        rennetFluidSlot = new DeviceFluidSlot(te, rennetFluidSlot_id);
        wasteFluidSlot = new DeviceFluidSlot(te, wasteFluidSlot_id);
        this.heatComponent = new TileHeatingComponent(te, 0.5f);
    }


    public CheeseVat setHeatMultiplier(float h) {
        heatComponent.setHeatMultiplier(h);
        return this;
    }

    public float getHeatMultiplier() {
        return heatComponent.getHeatMultiplier();
    }

    public boolean isHeated() {
        return getHeatMultiplier() > 0;
    }
    @Override
    protected ICheeseVatRecipe loadRecipe() {
        //ICheeseVatRecipe recipe = MilkRegistry.instance().cheeseVat().findRecipe(fluids, items);
        return null;
    }

    @Override
    protected boolean canProcess() {
        ICheeseVatRecipe recipe = getWorkingRecipe();
        if (recipe == null) return false;

        return false;
    }



    //Data stuff
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        heatComponent.readFromNBT(data, "heat_component");
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        heatComponent.writeToNBT(data, "heat_component");
    }

    /**
     * @param buf - buffer to read from
     */
    @Override
    public boolean readFromStream(ByteBuf buf) {
        super.readFromStream(buf);
        heatComponent.readFromStream(buf);
        return false;
    }

    /**
     * @param buf - buffer to write to
     */
    @Override
    public boolean writeToStream(ByteBuf buf) {
        super.writeToStream(buf);
        heatComponent.writeToStream(buf);
        return false;
    }
}
