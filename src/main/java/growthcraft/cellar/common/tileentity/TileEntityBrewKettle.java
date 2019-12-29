package growthcraft.cellar.common.tileentity;

import java.io.IOException;

import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.init.GrowthcraftCellarItems;
import growthcraft.cellar.client.particle.emitter.BrewKettleLidSteamEmitter;
import growthcraft.cellar.client.particle.params.FluidTanksParams;
import growthcraft.cellar.client.render.RenderBrewKettle;
import growthcraft.cellar.client.utils.FXHelperCellar;
import growthcraft.cellar.common.inventory.ContainerBrewKettle;
import growthcraft.cellar.common.tileentity.device.BrewKettle;
import growthcraft.cellar.common.tileentity.fluids.CellarTank;
import growthcraft.core.shared.client.utils.FXHelper;
import growthcraft.core.shared.inventory.GrowthcraftInternalInventory;
import growthcraft.core.shared.item.ItemUtils;
import growthcraft.core.shared.tileentity.device.DeviceInventorySlot;
import growthcraft.core.shared.tileentity.event.TileEventHandler;
import growthcraft.core.shared.tileentity.feature.IItemOperable;
import growthcraft.core.shared.tileentity.feature.ITileHeatedDevice;
import growthcraft.core.shared.tileentity.feature.ITileProgressiveDevice;
import growthcraft.core.shared.utils.Pair;
import growthcraft.core.shared.utils.PulseStepper;
import growthcraft.core.shared.utils.SpatialRandom;
import growthcraft.core.shared.utils.Triplet;
import growthcraft.milk.shared.init.GrowthcraftMilkFluids;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.MathUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityBrewKettle extends TileEntityCellarDevice implements ITickable, ITileHeatedDevice, ITileProgressiveDevice // , IItemOperable
{
    public static enum BrewKettleDataID {
        TIME,
        TIME_MAX,
        HEAT_AMOUNT,
        UNKNOWN;

        public static final BrewKettleDataID[] VALUES = new BrewKettleDataID[]
                {
                        TIME,
                        TIME_MAX,
                        HEAT_AMOUNT
                };

        public static BrewKettleDataID getByOrdinal(int ord) {
            if (ord >= 0 && ord < VALUES.length) return VALUES[ord];
            return UNKNOWN;
        }
    }

    private static final int[] rawSlotIDs = new int[]{0, 1, 2};
    private static final int[] residueSlotIDs = new int[]{0};
    private DeviceInventorySlot invSlotForLid = new DeviceInventorySlot(this, 2);

    private BrewKettle brewKettle = new BrewKettle(this, 0, 1, 2, 0, 1);

    // Crash: @SideOnly(Side.CLIENT)
    private BrewKettleLidSteamEmitter lidSteamEmitter = new BrewKettleLidSteamEmitter();
    // Crash: @SideOnly(Side.CLIENT)
    private SpatialRandom sprand = new SpatialRandom();
    // Crash: @SideOnly(Side.CLIENT)
    private boolean animLastLid = false;    // NOTE: Only use it inside update() on the client side.

    @Override
    protected FluidTank[] createTanks() {
        final int maxCap = GrowthcraftCellarConfig.brewKettleMaxCap;
        return new FluidTank[]{
                new CellarTank(maxCap, this),
                new CellarTank(maxCap, this)
        };
    }

    @Override
    public GrowthcraftInternalInventory createInventory() {
        return new GrowthcraftInternalInventory(this, 3);
    }

    @Override
    protected void markFluidDirty() {
        // Brew Kettles need to update their rendering state when a fluid
        // changes, most of the other cellar blocks are unaffected by this
        markForUpdate(true);
    }

    @Override
    public String getDefaultInventoryName() {
        return "container.growthcraft.brewKettle";
    }

    @Override
    public String getGuiID() {
        return "growthcraft_cellar:brew_kettle";
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerBrewKettle(playerInventory, this);
    }

    @Override
    public float getDeviceProgress() {
        return brewKettle.getProgress();
    }

    @Override
    public int getDeviceProgressScaled(int scale) {
        return brewKettle.getProgressScaled(scale);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            brewKettle.update();
        } else {
            if (hasFluid() && isHeated()) {
                if (hasLid()) {
                    lidSteamEmitter.update(world, pos);
                    animLastLid = true;
                } else {
                    if (animLastLid) {
                        // Emit a huge amount of smoke at once when removing the lid
                        for (int i = 0; i < 10; i++) {
                            if (world.rand.nextInt(10) < 3)
                                continue;
                            Pair<Double, Double> pPos = sprand.nextCenteredD2();
                            Pair<Double, Double> pVel = sprand.nextCenteredD2();

                            double px = (double) pos.getX() + 0.5 + pPos.left * (14.0 / 16.0);
                            double py = (double) pos.getY() + 15.0 / 16.0;
                            double pz = (double) pos.getZ() + 0.5 + pPos.right * (14.0 / 16.0);
                            double vx = pVel.left * 0.0625;
                            double vy = 0.0625;
                            double vz = pVel.right * 0.0625;
                            FXHelper.emitSteamBig(world, px, py, pz, vx, vy, vz);
                        }
                    }

                    // Make bubbles
                    if (world.rand.nextInt(10) < 3) {
                        Triplet<Double, Double, Double> pPos = sprand.nextCenteredD3();

                        double px = (double) pos.getX() + 0.5 + pPos.left * (12.0 / 16.0);
                        double py = (double) pos.getY() + 0.5; // 35 + (pPos.center + 0.5) * 0.0625;
                        double pz = (double) pos.getZ() + 0.5 + pPos.right * (12.0 / 16.0);
                        double vx = 0;
                        double vy = 0.125;
                        double vz = 0;
                        FluidTanksParams params = new FluidTanksParams(RenderBrewKettle.BBOX_FLUID
                                .grow(-1 * 0.0625, -0 * 0.0625, -1 * 0.0625)
                                .translate(0, 0 * 0.0625, 0),
                                this);
                        FXHelperCellar.emitKettleBubbles(world, px, py, pz, vx, vy, vz, params);
                    }

                    animLastLid = false;
                }
            } else {
                animLastLid = false;
            }
        }
    }

    @Override
    public int getHeatScaled(int range) {
        return (int) (MathHelper.clamp(brewKettle.getHeatMultiplier(), 0.0f, 1.0f) * range);
    }

    @Override
    public boolean isHeated() {
        return brewKettle.isHeated();
    }

    @Override
    public float getHeatMultiplier() {
        return brewKettle.getHeatMultiplier();
    }

    public boolean canBrew() {
        return brewKettle.canBrew();
    }

    public boolean hasFluid() {
        return brewKettle.hasFluid();
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        // 0 = raw
        // 1 = residue
        // 2 = lid
        return side == EnumFacing.DOWN ? rawSlotIDs : residueSlotIDs;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side) {
        return side != EnumFacing.DOWN || slot == 1;
    }

    @TileEventHandler(event = TileEventHandler.EventType.NBT_READ)
    public void readFromNBT_BrewKettle(NBTTagCompound nbt) {
/*		if (nbt.hasKey("time"))
		{
			// Pre 2.5
			brewKettle.setTime(nbt.getShort("time"));
			brewKettle.setGrain(nbt.getFloat("grain"));
		}
		else */
        {
            brewKettle.readFromNBT(nbt, "brew_kettle");
        }
    }

    @TileEventHandler(event = TileEventHandler.EventType.NBT_WRITE)
    public void writeToNBT_BrewKettle(NBTTagCompound nbt) {
        brewKettle.writeToNBT(nbt, "brew_kettle");
    }

    @TileEventHandler(event = TileEventHandler.EventType.NETWORK_READ)
    public boolean readFromStream_BrewKettle(ByteBuf stream) throws IOException {
        brewKettle.readFromStream(stream);
        return false;
    }

    @TileEventHandler(event = TileEventHandler.EventType.NETWORK_WRITE)
    public boolean writeToStream_BrewKettle(ByteBuf stream) throws IOException {
        brewKettle.writeToStream(stream);
        return false;
    }

    /************
     * PACKETS
     ************/

    /**
     * @param id - data id
     * @param v  - value
     */
    @Override
    public void receiveGUINetworkData(int id, int v) {
        super.receiveGUINetworkData(id, v);
        final BrewKettleDataID dataId = BrewKettleDataID.getByOrdinal(id);
        switch (dataId) {
            case TIME:
                brewKettle.setTime(v);
                break;
            case TIME_MAX:
                brewKettle.setTimeMax(v);
                break;
            case HEAT_AMOUNT:
                brewKettle.setHeatMultiplier((float) v / (float) 0x7FFF);
                break;
            default:
                break;
        }
    }

    @Override
    public void sendGUINetworkData(Container container, IContainerListener iCrafting) {
        super.sendGUINetworkData(container, iCrafting);
        iCrafting.sendWindowProperty(container, BrewKettleDataID.TIME.ordinal(), (int) brewKettle.getTime());
        iCrafting.sendWindowProperty(container, BrewKettleDataID.TIME_MAX.ordinal(), (int) brewKettle.getTimeMax());
        iCrafting.sendWindowProperty(container, BrewKettleDataID.HEAT_AMOUNT.ordinal(), (int) (brewKettle.getHeatMultiplier() * 0x7FFF));
    }

    @Override
    protected int doFill(EnumFacing from, FluidStack resource, boolean shouldFill) {
        if (hasLid())
            return 0;
        return fillFluidTank(0, resource, shouldFill);
    }

    @Override
    protected FluidStack doDrain(EnumFacing from, int maxDrain, boolean shouldDrain) {
        if (hasLid())
            return null;
        return drainFluidTank(1, maxDrain, shouldDrain);
    }

    @Override
    protected FluidStack doDrain(EnumFacing from, FluidStack stack, boolean shouldDrain) {
        if (hasLid())
            return null;
        if (stack == null || !stack.isFluidEqual(getFluidStack(1))) {
            return null;
        }
        return doDrain(from, stack.amount, shouldDrain);
    }

    public void switchTanks() {
        FluidStack f0 = null;
        FluidStack f1 = null;
        if (this.getFluidStack(0) != null) {
            f0 = this.getFluidStack(0).copy();
        }
        if (this.getFluidStack(1) != null) {
            f1 = this.getFluidStack(1).copy();
        }
        this.clearTank(0);
        this.clearTank(1);
        this.getFluidTank(0).fill(f1, true);
        this.getFluidTank(1).fill(f0, true);
        markForUpdate(true);
    }

    public boolean hasLid() {
        return GrowthcraftCellarItems.brewKettleLid.equals(getStackInSlot(2).getItem());
    }
/*
	@Override
	public boolean tryPlaceItem(Action action, EntityPlayer player, ItemStack stack) {
		if (IItemOperable.Action.RIGHT != action) return false;
		if (!ItemUtils.isEmpty(stack))
		{
			final Item item = stack.getItem();
			if( GrowthcraftCellarItems.brewKettleLid.equals(item) ) {
				if( invSlotForLid.isEmpty() ) {
					final ItemStack result = ItemUtils.decrPlayerCurrentInventorySlot(player, 1);
					invSlotForLid.set(result);
					markDirtyAndUpdate();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean tryTakeItem(Action action, EntityPlayer player, ItemStack stack) {
		if (IItemOperable.Action.RIGHT != action) return false;
		if( !player.isSneaking() )
			return false;
		final ItemStack result = invSlotForLid.yank();
		if (!ItemUtils.isEmpty(result))
		{
			ItemUtils.spawnItemStackAtTile(result, this, world.rand);
			markDirtyAndUpdate();
			return true;
		}
		return false;
	}
	*/
}
