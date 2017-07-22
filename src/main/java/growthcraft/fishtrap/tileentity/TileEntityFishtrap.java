package growthcraft.fishtrap.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class TileEntityFishtrap extends TileEntity implements ITickable, ICapabilityProvider {

    private int cooldown;
    private int randomMaxCooldown;
    private int intMinCooldown = 256;
    private int intMaxCooldown = 1024;
    private Random rand;
    private ItemStackHandler handler;

    public TileEntityFishtrap() {
        this.cooldown = 0;
        this.randomMaxCooldown = intMaxCooldown;
        this.handler = new ItemStackHandler(7);
        rand = new Random();
    }

    private int getRandomCooldown() {
        return new Random().nextInt((intMaxCooldown - intMinCooldown) + 1) + intMinCooldown;
    }

    private void doFishing() {
        if ( !getWorld().isRemote ) {
            // Get a random item from the Fishing_Rod LootTable. Pulled this from the EntityFishHook class.
            LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)this.world);
            List<ItemStack> result = this.world.getLootTableManager().getLootTableFromLocation(this.getLootTableList()).generateLootForPools(new Random(), lootcontext$builder.build());
            for (ItemStack itemstack : result) {
                if ( !this.isInventoryFull(this.handler)) {
                    this.addStackToInventory(this.handler, itemstack, false);
                }
            }
        }

        this.world.playSound((EntityPlayer)null, pos.getY(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_TRIPWIRE_CLICK_ON, SoundCategory.BLOCKS, 10000.0F, 0.8F + rand.nextFloat() * 0.2F);

    }

    /**
     * Determine which loot table should be used.
     * @return ResourceLocation of the LootTable
     */
    private ResourceLocation getLootTableList() {
        ResourceLocation lootTableList;
        lootTableList = LootTableList.GAMEPLAY_FISHING;

        if ( this.handler.getStackInSlot(0).getCount() > 0 ) {
            this.handler.getStackInSlot(0).shrink(1);
            lootTableList = LootTableList.GAMEPLAY_FISHING_FISH;
        }

        return lootTableList;
    }

    /**
     * Add the stack to the handler.
     * @param handler
     * @param stack
     * @param simulate
     * @return
     */
    private ItemStack addStackToInventory(IItemHandler handler, ItemStack stack, boolean simulate) {
        ItemStack remainder = stack;
        for(int slot = 1; slot < handler.getSlots(); slot++) {
            remainder = handler.insertItem(slot, stack, simulate);
            if ( remainder == ItemStack.EMPTY) break;
        }
        return remainder;
    }


    /**
     * Check if the inventory handler is full.
     * @param handler
     * @return
     */
    private boolean isInventoryFull(IItemHandler handler) {
        int filledSlots = 0;
        for ( int slot = 1; slot < handler.getSlots(); slot++ ) {
            if(handler.getStackInSlot(slot).getCount() == handler.getSlotLimit(slot)) filledSlots++;
        }
        return filledSlots == handler.getSlots() - 1; // -1 due to the bait slot.
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.cooldown = compound.getInteger("Cooldown");
        this.handler.deserializeNBT(compound.getCompoundTag("ItemStackHandler"));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("Cooldown", this.cooldown);
        compound.setTag("ItemStackHandler", this.handler.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        this.cooldown++;
        this.cooldown %= this.randomMaxCooldown;
        if (cooldown == 0) {
            this.randomMaxCooldown = getRandomCooldown();
            if (inWater() && !isInventoryFull(this.handler)) {
                this.doFishing();
            }
        }
    }

    /**
     * Check to determine if the surrounding blocks are static fluid.
     * @return
     */
    private Boolean inWater() {

        BlockPos[] neighborBlockPos = {
                pos.offset(EnumFacing.getFacingFromVector(pos.getX()+1, pos.getY(), pos.getZ()-1)),
                pos.offset(EnumFacing.getFacingFromVector(pos.getX()+1, pos.getY(), pos.getZ())),
                pos.offset(EnumFacing.getFacingFromVector(pos.getX()+1, pos.getY(), pos.getZ()+1)),
                pos.offset(EnumFacing.getFacingFromVector(pos.getX(), pos.getY(), pos.getZ()-1)),
                pos.offset(EnumFacing.getFacingFromVector(pos.getX(), pos.getY(), pos.getZ()+1)),
                pos.offset(EnumFacing.getFacingFromVector(pos.getX()-1, pos.getY(), pos.getZ()-1)),
                pos.offset(EnumFacing.getFacingFromVector(pos.getX()-1, pos.getY(), pos.getZ())),
                pos.offset(EnumFacing.getFacingFromVector(pos.getX()-1, pos.getY(), pos.getZ()+1))
        };

        for(BlockPos neighborPos : neighborBlockPos) {
            IBlockState state = this.world.getBlockState(neighborPos);
            Block block = state.getBlock();
            if(!(block instanceof BlockStaticLiquid)) {
                return false;
            }
        }

        return true;
    }



    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        int metadata = getBlockMetadata();
        return new SPacketUpdateTileEntity(this.pos, metadata, compound);
    }

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

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if ( capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T)this.handler;

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if ( capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }



}
