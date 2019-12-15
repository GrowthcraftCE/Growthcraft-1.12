package growthcraft.cellar.common.tileentity;

import java.io.IOException;

import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.common.inventory.ContainerCultureJar;
import growthcraft.core.shared.tileentity.component.TileHeatingComponent;
import growthcraft.cellar.common.tileentity.device.CultureGenerator;
import growthcraft.cellar.common.tileentity.device.YeastGenerator;
import growthcraft.cellar.common.tileentity.fluids.CellarTank;
import growthcraft.core.shared.inventory.GrowthcraftInternalInventory;
import growthcraft.core.shared.tileentity.device.DeviceProgressive;
import growthcraft.core.shared.tileentity.event.TileEventHandler;
import growthcraft.core.shared.tileentity.feature.ITileHeatedDevice;
import growthcraft.core.shared.tileentity.feature.ITileProgressiveDevice;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileEntityCultureJar extends TileEntityCellarDevice implements ITickable, ITileHeatedDevice, ITileProgressiveDevice {
    public static enum CultureJarDataId {
        YEAST_GEN_TIME,
        YEAST_GEN_TIME_MAX,
        CULTURE_GEN_TIME,
        CULTURE_GEN_TIME_MAX,
        HEAT_AMOUNT,
        UNKNOWN;

        public static final CultureJarDataId[] VALID = new CultureJarDataId[]
                {
                        YEAST_GEN_TIME,
                        YEAST_GEN_TIME_MAX,
                        CULTURE_GEN_TIME,
                        CULTURE_GEN_TIME_MAX,
                        HEAT_AMOUNT
                };

        public static CultureJarDataId getByOrdinal(int ord) {
            if (ord >= 0 && ord < VALID.length) return VALID[ord];
            return UNKNOWN;
        }
    }

    private static final int[] accessibleSlots = new int[]{0};
    private TileHeatingComponent heatComponent;
    private CultureGenerator cultureGen;
    private YeastGenerator yeastGen;
    private int jarDeviceState;

    public TileEntityCultureJar() {
        super();
        this.heatComponent = new TileHeatingComponent(this, 1.0f);
        this.cultureGen = new CultureGenerator(this, heatComponent, 0, 0);
        this.yeastGen = new YeastGenerator(this, 0, 0);
        this.yeastGen.setTimeMax(GrowthcraftCellarConfig.cultureJarTimeMax);
        this.yeastGen.setConsumption(GrowthcraftCellarConfig.cultureJarConsumption);
    }

    public boolean isHeated() {
        return cultureGen.isHeated();
    }

    public float getHeatMultiplier() {
        return cultureGen.getHeatMultiplier();
    }

    public boolean isCulturing() {
        return jarDeviceState == 1;
    }

    private DeviceProgressive getActiveDevice() {
        if (cultureGen.isHeated()) {
            return cultureGen;
        }
        return yeastGen;
    }

    private DeviceProgressive getActiveClientDevice() {
        if (jarDeviceState == 1) {
            return cultureGen;
        }
        return yeastGen;
    }

    @Override
    protected FluidTank[] createTanks() {
        final int maxTankCap = GrowthcraftCellarConfig.cultureJarMaxCap;
        return new FluidTank[]{new CellarTank(maxTankCap, this)};
    }

    @Override
    public GrowthcraftInternalInventory createInventory() {
        return new GrowthcraftInternalInventory(this, 1);
    }

    @Override
    public String getDefaultInventoryName() {
        return "container.grc.CultureJar";
    }

    @Override
    public String getGuiID() {
        return "growthcraft_cellar:culture_jar";
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerCultureJar(playerInventory, this);
    }

    @Override
    protected void markFluidDirty() {
        // Ferment Jars need to update their rendering state when a fluid
        // changes, most of the other cellar blocks are unaffected by this
        markForUpdate(true);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return accessibleSlots;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing side) {
        return index == 0;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing side) {
        return false;
    }

    @Override
    protected int doFill(EnumFacing from, FluidStack resource, boolean shouldFill) {
        return fillFluidTank(0, resource, shouldFill);
    }

    @Override
    protected FluidStack doDrain(EnumFacing from, int maxDrain, boolean shouldDrain) {
        return drainFluidTank(0, maxDrain, shouldDrain);
    }

    @Override
    protected FluidStack doDrain(EnumFacing from, FluidStack resource, boolean shouldDrain) {
        if (resource == null || !resource.isFluidEqual(getFluidTank(0).getFluid())) {
            return null;
        }
        return doDrain(from, resource.amount, shouldDrain);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            heatComponent.update();
            final int lastState = jarDeviceState;
            final DeviceProgressive prog = getActiveDevice();
            if (prog == cultureGen) {
                this.jarDeviceState = 1;
                yeastGen.resetTime();
            } else {
                this.jarDeviceState = 0;
                cultureGen.resetTime();
            }
            getActiveDevice().update();
            if (jarDeviceState != lastState) {
                markDirtyAndUpdate(true);
            }
        }
    }

    @Override
    public void receiveGUINetworkData(int id, int v) {
        super.receiveGUINetworkData(id, v);
        switch (CultureJarDataId.getByOrdinal(id)) {
            case YEAST_GEN_TIME:
                yeastGen.setTime(v);
                break;
            case YEAST_GEN_TIME_MAX:
                yeastGen.setTimeMax(v);
                break;
            case CULTURE_GEN_TIME:
                cultureGen.setTime(v);
                break;
            case CULTURE_GEN_TIME_MAX:
                cultureGen.setTimeMax(v);
                break;
            case HEAT_AMOUNT:
                heatComponent.setHeatMultiplier((float) v / (float) 0x7FFF);
                break;
            default:
                // should warn about invalid Data ID
                break;
        }
    }

    @Override
    public void sendGUINetworkData(Container container, IContainerListener iCrafting) {
        super.sendGUINetworkData(container, iCrafting);
        iCrafting.sendWindowProperty(container, CultureJarDataId.YEAST_GEN_TIME.ordinal(), yeastGen.getTime());
        iCrafting.sendWindowProperty(container, CultureJarDataId.YEAST_GEN_TIME_MAX.ordinal(), yeastGen.getTimeMax());
        iCrafting.sendWindowProperty(container, CultureJarDataId.CULTURE_GEN_TIME.ordinal(), cultureGen.getTime());
        iCrafting.sendWindowProperty(container, CultureJarDataId.CULTURE_GEN_TIME_MAX.ordinal(), cultureGen.getTimeMax());
        iCrafting.sendWindowProperty(container, CultureJarDataId.HEAT_AMOUNT.ordinal(), (int) (heatComponent.getHeatMultiplier() * 0x7FFF));
    }

    @TileEventHandler(event = TileEventHandler.EventType.NBT_READ)
    public void readFromNBT_CultureJar(NBTTagCompound nbt) {
        yeastGen.readFromNBT(nbt, "yeastgen");
        cultureGen.readFromNBT(nbt, "culture_gen");
        heatComponent.readFromNBT(nbt, "heat_component");
    }

    @TileEventHandler(event = TileEventHandler.EventType.NBT_WRITE)
    public void writeToNBT_CultureJar(NBTTagCompound nbt) {
        yeastGen.writeToNBT(nbt, "yeastgen");
        cultureGen.writeToNBT(nbt, "culture_gen");
        heatComponent.writeToNBT(nbt, "heat_component");
    }

    @TileEventHandler(event = TileEventHandler.EventType.NETWORK_READ)
    public boolean readFromStream_YeastGen(ByteBuf stream) throws IOException {
        this.jarDeviceState = stream.readInt();
        yeastGen.readFromStream(stream);
        cultureGen.readFromStream(stream);
        heatComponent.readFromStream(stream);
        return false;
    }

    @TileEventHandler(event = TileEventHandler.EventType.NETWORK_WRITE)
    public boolean writeToStream_YeastGen(ByteBuf stream) throws IOException {
        stream.writeInt(jarDeviceState);
        yeastGen.writeToStream(stream);
        cultureGen.writeToStream(stream);
        heatComponent.writeToStream(stream);
        return false;
    }

    @Override
    public int getHeatScaled(int scale) {
        return (int) (scale * MathHelper.clamp(getHeatMultiplier(), 0f, 1f));
    }

    @Override
    public float getDeviceProgress() {
        return getActiveClientDevice().getProgress();
    }

    @Override
    public int getDeviceProgressScaled(int scale) {
        return getActiveClientDevice().getProgressScaled(scale);
    }
}
