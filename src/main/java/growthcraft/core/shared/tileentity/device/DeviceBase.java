package growthcraft.core.shared.tileentity.device;

import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.io.nbt.INBTSerializableContext;
import growthcraft.core.shared.io.stream.IStreamable;
import growthcraft.core.shared.tileentity.GrowthcraftTileBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class DeviceBase implements INBTSerializableContext, IStreamable {
    protected Random random = new Random();
    protected TileEntity parent;

    public DeviceBase(TileEntity te) {
        this.parent = te;
    }

    public TileEntity getTileEntity() {
        return parent;
    }

    public World getWorld() {
        return parent.getWorld();
    }

    public int getMetadata() {
        return parent.getBlockMetadata();
    }

    public IInventory getInventory() {
        if (parent instanceof IInventory) {
            return (IInventory) parent;
        }
        return null;
    }

    protected void markForUpdate(boolean triggerRenderUpdate) {
//    	System.out.println("CALLED DeviceBase.markForUpdate(" + triggerRenderUpdate + ")");		// DEBUG_BlockUpdate
    	
    	// TODO: Make all tile entities in Growthcraft being derived from GrowthcraftTileBase
    	if( parent instanceof GrowthcraftTileBase ) {
    		GrowthcraftTileBase ParentGrcTB = (GrowthcraftTileBase)parent;
    		ParentGrcTB.markForUpdate(triggerRenderUpdate);
    	}
    	else {
            BlockPos pos = parent.getPos();
            World world = getWorld();
            IBlockState curState = world.getBlockState(pos);

            if( triggerRenderUpdate ) {
            	world.markBlockRangeForRenderUpdate(pos, pos);
            	world.notifyBlockUpdate(pos, curState, curState, BlockFlags.UPDATE_AND_SYNC);
//	            getWorld().scheduleBlockUpdate(pos, parent.getBlockType(), 0, 0);
            }
            else if(!world.isRemote) {
	            getWorld().notifyBlockUpdate(pos, curState, curState, BlockFlags.SYNC | BlockFlags.SUPRESS_RENDER);
//	            getWorld().scheduleBlockUpdate(pos, parent.getBlockType(), 0, 0);
            }
    	}
    }

    protected void markDirty() {
//    	System.out.println("CALLED DeviceBase.markDirty()");		// DEBUG_BlockUpdate
        parent.markDirty();
    }
    
    protected void markDirtyAndUpdate(boolean triggerRenderUpdate) {
//    	System.out.println("CALLED DeviceBase.markDirtyAndUpdate()");		// DEBUG_BlockUpdate
    	markDirty();
    	markForUpdate(triggerRenderUpdate);
    }

    /**
     * @param data - nbt data to read from
     */
    public void readFromNBT(NBTTagCompound data) {
    }

    public void legacyReadFromNBT(NBTTagCompound data) {
    }

    /**
     * @param data - parent nbt data to read from
     * @param name - sub tag to read
     */
    @Override
    public void readFromNBT(NBTTagCompound data, String name) {
        if (data.hasKey(name)) {
            final NBTTagCompound tag = data.getCompoundTag(name);
            readFromNBT(tag);
        } else {
            // LOG upgrade!
            legacyReadFromNBT(data);
        }
    }

    /**
     * @param data - nbt to write to
     */
    public void writeToNBT(NBTTagCompound data) {
    }

    /**
     * @param data - nbt to write to
     * @param name - sub tag nbt to write to
     */
    @Override
    public void writeToNBT(NBTTagCompound data, String name) {
        final NBTTagCompound target = new NBTTagCompound();
        writeToNBT(target);
        data.setTag(name, target);
    }

    /**
     * @param buf - buffer to read from
     */
    @Override
    public boolean readFromStream(ByteBuf buf) {
        return false;
    }

    /**
     * @param buf - buffer to write to
     */
    @Override
    public boolean writeToStream(ByteBuf buf) {
        return false;
    }
}
