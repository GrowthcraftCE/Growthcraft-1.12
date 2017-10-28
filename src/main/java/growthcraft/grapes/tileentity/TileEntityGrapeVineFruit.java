package growthcraft.grapes.tileentity;

import growthcraft.core.utils.GrowthcraftPlaySound;
import growthcraft.grapes.init.GrowthcraftGrapesItems;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Random;

public class TileEntityGrapeVineFruit extends TileEntity implements ITickable, ICapabilityProvider {

    private int cooldown;
    private int randomMaxCooldown;
    private int intMinCooldown = 256;
    private int intMaxCooldown = 1024;

    private ItemStackHandler inventory;

    private Random rand;

    public TileEntityGrapeVineFruit() {
        this.inventory = new ItemStackHandler(1);
        this.cooldown = 0;
        this.randomMaxCooldown = intMaxCooldown;
        this.rand = new Random();
    }

    private int getRandomCooldown() {
        return rand.nextInt((intMaxCooldown - intMinCooldown) + 1) + intMinCooldown;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", this.inventory.serializeNBT());
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
        if ( capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) this.inventory;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }

    @Override
    public void update() {
        this.cooldown++;
        this.cooldown %= this.randomMaxCooldown;
        if (cooldown == 0) {
            if ( world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0 ) {
                this.grow( world, pos );
                // Reset the max cool down to something random.
                randomMaxCooldown = getRandomCooldown();
            }
        }

        markBlockUpdate(world, pos);
    }

    public void grow(World worldIn, BlockPos pos ) {
        TileEntityGrapeVineFruit te = (TileEntityGrapeVineFruit) worldIn.getTileEntity(pos);
        IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        for ( int slot = 0; slot < handler.getSlots(); slot++ ) {
            if ( handler.getStackInSlot(slot).isEmpty() ) {
                ItemStack stack = new ItemStack(GrowthcraftGrapesItems.grape, 1, rand.nextInt(3));
                handler.insertItem(slot, stack, false);
                GrowthcraftPlaySound.onlyNearByPlayers(this.world, pos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 16);
            }
        }
        markBlockUpdate(worldIn, pos);

    }

    private void markBlockUpdate(World worldIn, BlockPos pos ) {
        worldIn.markBlockRangeForRenderUpdate(pos, pos);
        worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 3);
        worldIn.scheduleBlockUpdate(pos, this.blockType, 0,0);
        markDirty();
    }
}
