package growthcraft.fishtrap.tileentity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class TileEntityFishtrap extends TileEntity implements ITickable {

    private int cooldown;
    private int random;
    private int intMinCooldown = 256;
    private int intMaxCooldown = 768;

    public TileEntityFishtrap() {
        this.cooldown = 0;
        this.random = intMaxCooldown;

    }

    private int getRandomCooldown() {
        return new Random().nextInt((intMaxCooldown - intMinCooldown) + 1) + intMinCooldown;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.cooldown = compound.getInteger("Cooldown");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("Cooldown", this.cooldown);
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        this.cooldown++;
        this.cooldown %= intMaxCooldown;
        if ( cooldown == 0 ) {
            this.random = getRandomCooldown();
            if ( ! getWorld().isRemote ) {
                // Get a random item from the Fishing_Rod LootTable. Pull this from the EntityFishHook class.
                LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)this.world);
                List<ItemStack> result = this.world.getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(new Random(), lootcontext$builder.build());

                for (ItemStack itemstack : result) {
                    EntityItem item = new EntityItem(getWorld(), getPos().getX() + 0.5, getPos().getY() + 2, getPos().getZ() + 0.5, itemstack);
                    // TODO: Spawn into the EntityInventory. Don't forgot about checking the ItemStack first.
                    item.motionX = 0;
                    item.motionY = 0;
                    item.motionZ = 0;
                    getWorld().spawnEntity(item);
                }

            }

        }
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
}
