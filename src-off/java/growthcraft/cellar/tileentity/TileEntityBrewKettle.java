package growthcraft.cellar.tileentity;

import growthcraft.core.handlers.FluidHandler;
import growthcraft.hops.items.ItemHops;
import growthcraft.milk.blocks.fluids.FluidRennet;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockMagma;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nullable;

public class TileEntityBrewKettle extends TileEntity implements ICapabilityProvider, ITickable {
	// REMOVEME
	
    private boolean heated;
    private int brewTime;
    private int maxBrewTime;

    private ItemStackHandler itemStackHandler;
    private FluidHandler fluidInputHandler;
    private FluidHandler fluidOutputHandler;


    public TileEntityBrewKettle() {
        itemStackHandler = new ItemStackHandler(2);
        fluidInputHandler = new FluidHandler(this, new FluidTank(5000));
        fluidOutputHandler = new FluidHandler(this, new FluidTank(5000));

        heated = false;
        maxBrewTime = 2000;
    }

    public boolean canBrew() {
        boolean goodItem = false;
        boolean goodFluid = false;
        boolean goodOutput = false;

        ItemStack inputStack = this.itemStackHandler.getStackInSlot(0);
        if (inputStack != null) {
            goodItem = inputStack.getItem() instanceof ItemHops;
        }

        FluidStack fluidStack = fluidInputHandler.getFluidTank().getFluid();
        if (fluidStack != null && fluidStack.amount > 0) {
            if (fluidStack.getFluid() instanceof FluidRennet) {
                goodFluid = true;
            }
        }

        // Check heat source
        this.heated = isHeated();

        return (goodItem && goodFluid && this.heated);
    }

    private void doBrewing() {
        if (canBrew()) {
            this.brewTime += 1;

            if (this.brewTime != 0 && this.brewTime >= this.maxBrewTime) {
                this.brewTime = 0;
                itemStackHandler.getStackInSlot(0).shrink(1);
                FluidStack drainedFluid = fluidInputHandler.drain(1000, true);
                fluidOutputHandler.fill(drainedFluid, true);
            }

            IBlockState iblockstate = this.world.getBlockState(pos);
            final int FLAGS = 3;
            world.notifyBlockUpdate(pos, iblockstate, iblockstate, FLAGS);
        }

        if (fluidOutputHandler.getFluidTank().getFluidAmount() >= 1000 && itemStackHandler.getStackInSlot(1).getItem() instanceof ItemBucket) {
            ItemStack filledBucket = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluidOutputHandler.getFluidTank().getFluid().getFluid());
            fluidOutputHandler.getFluidTank().drain(1000, true);
            itemStackHandler.setStackInSlot(1, filledBucket);
        }


    }

    public int getBrewTime() {
        return brewTime;
    }

    public int getMaxBrewTime() {
        return maxBrewTime;
    }

    public int getBrewProgress() {
        if (maxBrewTime == 0) {
            maxBrewTime = 2000;
        }
        float percentage = (brewTime * 100.0f) / maxBrewTime;
        return (int) percentage;
    }

    public boolean isHeated() {
        return world.getBlockState(pos.down()).getBlock() instanceof BlockFire || world.getBlockState(pos.down()).getBlock() instanceof BlockMagma;
    }

    public FluidStack getTankFluidStack() {
        return fluidInputHandler.getFluidTank().getFluid();
    }

    public int getTankAmount() {
        try {
            return getTank("input").getFluidAmount();
        } catch (NullPointerException npe) {
            return 0;
        }
    }

    public int getTankCapacity() {
        try {
            return getTank("input").getCapacity();
        } catch (NullPointerException npe) {
            return 0;
        }
    }

    public FluidTank getTank(String tankName) {
        switch (tankName) {
            case "input":
                return fluidInputHandler.getFluidTank();
            case "output":
                return fluidOutputHandler.getFluidTank();
            default:
                return fluidInputHandler.getFluidTank();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        brewTime = compound.getInteger("BrewTime");
        maxBrewTime = compound.getInteger("MaxBrewTime");
        itemStackHandler.deserializeNBT(compound.getCompoundTag("ItemInventory"));
        fluidInputHandler.setFluidTank(new FluidTank(5000).readFromNBT(compound.getCompoundTag("FluidInventory")));
        fluidOutputHandler.setFluidTank(new FluidTank(5000).readFromNBT(compound.getCompoundTag("FluidOutputInventory")));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("BrewTime", brewTime);
        compound.setInteger("MaxBrewTime", maxBrewTime);
        compound.setTag("ItemInventory", itemStackHandler.serializeNBT());

        FluidTank tank = fluidInputHandler.getFluidTank();
        NBTTagCompound tagTank = new NBTTagCompound();
        compound.setTag("FluidInventory", tank.writeToNBT(tagTank));

        FluidTank tankOutput = fluidOutputHandler.getFluidTank();
        NBTTagCompound tagTankOutput = new NBTTagCompound();
        compound.setTag("FluidOutputInventory", tankOutput.writeToNBT(tagTankOutput));

        return super.writeToNBT(compound);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        int metadata = getBlockMetadata();
        return new SPacketUpdateTileEntity(this.pos, metadata, compound);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return compound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound getTileData() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return compound;
    }

    /* -- ICapabilityProvider -- */

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {

            if (facing == null) {
                return (T) new CombinedInvWrapper(itemStackHandler);
            }

            switch (facing) {
                case UP:
                    return (T) this.itemStackHandler;
                case EAST:
                    return null;
                case WEST:
                    return null;
                case NORTH:
                    return null;
                case SOUTH:
                    return null;
                default:
                    return (T) this.itemStackHandler;
            }
        }

        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (facing == null) {
                return (T) this.fluidInputHandler;
            }

            switch (facing) {
                case UP:
                    return (T) this.fluidInputHandler;
                case EAST:
                    return (T) this.fluidOutputHandler;
                case WEST:
                    return (T) this.fluidOutputHandler;
                case NORTH:
                    return (T) this.fluidOutputHandler;
                case SOUTH:
                    return (T) this.fluidOutputHandler;
                default:
                    return (T) this.fluidInputHandler;
            }
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }

    /* -- ITickable -- */

    @Override
    public void update() {
        doBrewing();
    }

}
