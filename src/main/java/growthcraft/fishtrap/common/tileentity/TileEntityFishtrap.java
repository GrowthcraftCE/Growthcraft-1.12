package growthcraft.fishtrap.common.tileentity;

import growthcraft.core.shared.tileentity.GrowthcraftTileInventoryBase;
import growthcraft.core.shared.tileentity.feature.IInteractionObject;
import growthcraft.fishtrap.common.container.ContainerFishtrap;
import growthcraft.fishtrap.common.utils.GrowthcraftPlaySound;
import growthcraft.fishtrap.shared.config.GrowthcraftFishtrapConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class TileEntityFishtrap extends GrowthcraftTileInventoryBase implements ITickable, ICapabilityProvider, IInteractionObject {

    private int cooldown;
    private int randomMaxCooldown;
    private int intMinCooldown = 256;
    private int intMaxCooldown = 1024;
    private Random rand;
    private ItemStackHandler handlerOutput;
    private ItemStackHandler handlerInput;


    public TileEntityFishtrap() {
        this.cooldown = 0;
        this.randomMaxCooldown = intMaxCooldown;
        this.handlerOutput = new ItemStackHandler(6) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                markDirty();
            }
        };
        this.handlerInput = new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                markDirty();
            }
        };
        rand = new Random();
    }

    private int getRandomCooldown() {
        return rand.nextInt((intMaxCooldown - intMinCooldown) + 1) + intMinCooldown;
    }

    private void doFishing() {
        if ( !getWorld().isRemote ) {
            // If strictBait is required then we need to ensure there is bait also if bait is required then we need
            // to ensure that there is any bait.
            if (GrowthcraftFishtrapConfig.strictBait && !hasBait(GrowthcraftFishtrapConfig.strictBait)
                || GrowthcraftFishtrapConfig.baitRequired && !hasBait() ) {
                return;
            }

            // Get a random item from the Fishing_Rod LootTable. Pulled this from the EntityFishHook class.
            LootContext.Builder lootContextBuilder = new LootContext.Builder((WorldServer)this.world);
            List<ItemStack> result = this.world.getLootTableManager().getLootTableFromLocation(
                    this.getLootTableList()).generateLootForPools(new Random(),
                    lootContextBuilder.build());

            for (ItemStack itemstack : result) {
                if ( !this.isInventoryFull(this.handlerOutput)) {
                    this.addStackToInventory(this.handlerOutput, itemstack, false);
                }
            }
        }
        GrowthcraftPlaySound.onlyNearByPlayers(this.world, pos, SoundEvents.BLOCK_TRIPWIRE_CLICK_ON, SoundCategory.BLOCKS, 3);

    }

    public boolean hasBait() {
        return hasBait(false);
    }

    public boolean hasBait(boolean strict){
        if ( strict ) {
            List<ItemStack> oreDictionaryBaitFishing = OreDictionary.getOres("baitFishing");
            ItemStack baitStack = this.handlerInput.getStackInSlot(0);
            for ( ItemStack itemStack: oreDictionaryBaitFishing ) {
                if (itemStack.getUnlocalizedName().contentEquals(baitStack.getUnlocalizedName()) ) {
                    return true;
                }
            }
            return false;
        }
        return this.handlerInput.getStackInSlot(0).getCount() > 0;
    }
    /**
     * Determine which loot table should be used.
     * @return ResourceLocation of the LootTable
     */
    private ResourceLocation getLootTableList() {
        ResourceLocation lootTableList;
        lootTableList = LootTableList.GAMEPLAY_FISHING;

        if ( this.handlerInput.getStackInSlot(0).getCount() > 0 ) {
            this.handlerInput.getStackInSlot(0).shrink(1);
            lootTableList = LootTableList.GAMEPLAY_FISHING_FISH;
        }

        return lootTableList;
    }

    /**
     * Add the stack to the handler.
     * @param handler Inventory handler.
     * @param stack ItemStack to be added to the given Inventory Handler
     * @param simulate Not implemented
     * @return ItemStack that cannot be added to the inventory.
     */
    private ItemStack addStackToInventory(IItemHandler handler, ItemStack stack, boolean simulate) {
        ItemStack remainder = stack;
        for(int slot = 0; slot < handler.getSlots(); slot++) {
            remainder = handler.insertItem(slot, stack, simulate);
            if ( remainder == ItemStack.EMPTY) break;
        }
        markDirtyAndUpdate();
        return remainder;
    }

    /**
     * Check if the inventory handler is full.
     */
    private boolean isInventoryFull(IItemHandler handler) {
        int filledSlots = 0;
        for ( int slot = 0; slot < handler.getSlots(); slot++ ) {
            if(handler.getStackInSlot(slot).getCount() == handler.getSlotLimit(slot)) filledSlots++;
        }
        return filledSlots == handler.getSlots();
    }

    @Override
    public void update() {
        this.cooldown++;
        this.cooldown %= this.randomMaxCooldown;
        if (cooldown == 0) {
            this.randomMaxCooldown = getRandomCooldown();
            if (inWater() && !isInventoryFull(this.handlerOutput)) {
                this.doFishing();
            }
        }
    }

    /**
     * Check to determine if the surrounding blocks are static fluid.
     * @return returns true if the block is surrounded by water
     */
    private boolean inWater() {

        boolean underFluid = true;

        BlockPos[] neighborBlockPos = {
                pos.offset(EnumFacing.NORTH),
                pos.offset(EnumFacing.EAST),
                pos.offset(EnumFacing.SOUTH),
                pos.offset(EnumFacing.WEST)
        };

        BlockPos[] upDownNeighborBlockPos = {
                pos.offset(EnumFacing.UP),
                pos.offset(EnumFacing.DOWN)
        };

        for(BlockPos neighborPos : neighborBlockPos) {
            IBlockState state = this.world.getBlockState(neighborPos);
            Block block = state.getBlock();
            if(!(block instanceof BlockStaticLiquid)) {
                underFluid = false;
                break;
            }
        }

        /*
         * If underFluid is still false, then let's see if there is fluid on the .UP and the .DOWN. This will allow you
         * to have a cluster of fishtraps, but you can only branch them out horizontally.
         */
        if (!underFluid) {
            Block block0 = this.world.getBlockState(upDownNeighborBlockPos[0]).getBlock();
            Block block1 = this.world.getBlockState(upDownNeighborBlockPos[1]).getBlock();
            if ( block0 instanceof BlockStaticLiquid && block1 instanceof BlockStaticLiquid ) {
                underFluid = true;
            }
        }

        return underFluid;
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

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if ( capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {

            if (facing == null) {
                return (T) new CombinedInvWrapper(handlerInput, handlerOutput);
            }

            switch (facing) {
                case UP:
                    return (T)this.handlerInput;
                case EAST:
                    return null;
                case WEST:
                    return null;
                case NORTH:
                    return null;
                case SOUTH:
                    return null;
                default:
                    return (T)this.handlerOutput;
            }
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(@Nullable Capability<?> capability, @Nullable EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerFishtrap(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return "growthcraft_fishtrap:fishtrap";
    }

    @Override
    public void onInventoryChanged(IInventory inv, int index) {
        super.onInventoryChanged(inv, index);
        if (index == 0) {
            markDirty();
        } else if (index > 0) {
            markDirtyAndUpdate();
        }
    }
}
