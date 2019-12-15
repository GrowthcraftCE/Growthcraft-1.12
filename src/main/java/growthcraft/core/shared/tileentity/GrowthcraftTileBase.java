package growthcraft.core.shared.tileentity;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.io.nbt.IAltNBTSerializable;
import growthcraft.core.shared.io.stream.IStreamable;
import growthcraft.core.shared.tileentity.event.TileEventFunction;
import growthcraft.core.shared.tileentity.event.TileEventHandler;
import growthcraft.core.shared.tileentity.event.TileEventHandlerMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Extend this base class if you just need a Base tile with the event system.
 * <p>
 * Event handling system is a stripped version of the one seen in AE2, I've
 * copied the code for use in YATM, but I've ported it over to Growthcraft as
 * well.
 */
public abstract class GrowthcraftTileBase extends TileEntity implements IStreamable, IAltNBTSerializable {
    protected static TileEventHandlerMap<GrowthcraftTileBase> HANDLERS = new TileEventHandlerMap<GrowthcraftTileBase>();

    public void markForUpdate(boolean triggerRenderUpdate) {
        IBlockState curState = world.getBlockState(pos);
        if( triggerRenderUpdate ) {
        	world.markBlockRangeForRenderUpdate(pos, pos);
        	world.notifyBlockUpdate(pos, curState, curState, BlockFlags.UPDATE_AND_SYNC);
        }
        else if(!world.isRemote) {
        	world.notifyBlockUpdate(pos, curState, curState, BlockFlags.SYNC | BlockFlags.SUPRESS_RENDER);
        }
    }

    public void markDirtyAndUpdate(boolean triggerRenderUpdate) {
        markDirty();
        markForUpdate(triggerRenderUpdate);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (this.getCapability(capability, facing) != null)
            return true;
        return super.hasCapability(capability, facing);
    }

    protected List<TileEventFunction> getHandlersFor(@Nonnull TileEventHandler.EventType event) {
        return HANDLERS.getEventFunctionsForClass(getClass(), event);
    }

    @Override
    public final boolean writeToStream(ByteBuf stream) {
        final List<TileEventFunction> handlers = getHandlersFor(TileEventHandler.EventType.NETWORK_WRITE);
        if (handlers != null) {
            for (TileEventFunction func : handlers) {
                func.writeToStream(this, stream);
            }
        }
        return false;
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        final NBTTagCompound data = new NBTTagCompound();
        final ByteBuf stream = Unpooled.buffer();

        try {
            writeToStream(stream);
            if (stream.readableBytes() == 0) {
                return null;
            }
        } catch (Throwable t) {
            System.err.println(t);
        }


        // P, for payload
        data.setByteArray("P", stream.array());

        return new SPacketUpdateTileEntity(pos, 127, data);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = super.getUpdateTag();
        return writeToNBT(tag);
    }

    @Override
    public final boolean readFromStream(ByteBuf stream) {
        boolean shouldUpdate = false;
        final List<TileEventFunction> handlers = getHandlersFor(TileEventHandler.EventType.NETWORK_READ);
        if (handlers != null) {
            for (TileEventFunction func : handlers) {
                if (func.readFromStream(this, stream)) {
                    shouldUpdate = true;
                }
            }
        }
        return shouldUpdate;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        if (packet.getTileEntityType() == 127) {
            final NBTTagCompound tag = packet.getNbtCompound();
            boolean dirty = false;
            if (tag != null) {
                final ByteBuf stream = Unpooled.copiedBuffer(tag.getByteArray("P"));
                if (readFromStream(stream)) {
                    dirty = true;
                }
            }
            if (dirty) markForUpdate(false);
        }
    }

    public void readFromNBTForItem(NBTTagCompound tag) {
    }

    public void writeToNBTForItem(NBTTagCompound tag) {
    }

    @Override
    public final void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        final List<TileEventFunction> handlers = getHandlersFor(TileEventHandler.EventType.NBT_READ);
        if (handlers != null) {
            for (TileEventFunction func : handlers) {
                func.readFromNBT(this, nbt);
            }
        }
    }

    @Override
    public final NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        final List<TileEventFunction> handlers = getHandlersFor(TileEventHandler.EventType.NBT_WRITE);
        if (handlers != null) {
            for (TileEventFunction func : handlers) {
                func.writeToNBT(this, nbt);
            }
        }

        return nbt;
    }
}
