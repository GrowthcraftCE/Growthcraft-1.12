package growthcraft.core.common.lib.tileentity.device;

import java.util.Random;

import growthcraft.core.common.lib.block.BlockFlags;
import growthcraft.core.common.lib.io.nbt.INBTSerializableContext;
import growthcraft.core.common.lib.io.stream.IStreamable;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DeviceBase implements INBTSerializableContext, IStreamable
{
	protected Random random = new Random();
	protected TileEntity parent;

	public DeviceBase(TileEntity te)
	{
		this.parent = te;
	}

	public TileEntity getTileEntity()
	{
		return parent;
	}

	public World getWorld()
	{
		return parent.getWorld();
	}

	public int getMetadata()
	{
		return parent.getBlockMetadata();
	}

	public IInventory getInventory()
	{
		if (parent instanceof IInventory)
		{
			return (IInventory)parent;
		}
		return null;
	}

	protected void markForUpdate()
	{
		BlockPos pos = parent.getPos();
		IBlockState curState = getWorld().getBlockState(pos);
		
//		getWorld().markBlockForUpdate(parent.xCoord, parent.yCoord, parent.zCoord);
//		getWorld().notifyBlockUpdate(pos, curState, curState, BlockFlags.UPDATE_AND_SYNC);
		getWorld().markBlockRangeForRenderUpdate(pos, pos);
		getWorld().notifyBlockUpdate(pos, curState, curState, BlockFlags.UPDATE_AND_SYNC);
		getWorld().scheduleBlockUpdate(pos, parent.getBlockType(), 0,0);
	}

	protected void markDirty()
	{
		parent.markDirty();
	}

	/**
	 * @param data - nbt data to read from
	 */
	public void readFromNBT(NBTTagCompound data)
	{
	}

	/**
	 * @param data - parent nbt data to read from
	 * @param name - sub tag to read
	 */
	@Override
	public void readFromNBT(NBTTagCompound data, String name)
	{
		if (data.hasKey(name))
		{
			final NBTTagCompound tag = data.getCompoundTag(name);
			readFromNBT(tag);
		}
		else
		{
			// LOG error
		}
	}

	/**
	 * @param data - nbt to write to
	 */
	public void writeToNBT(NBTTagCompound data)
	{
	}

	/**
	 * @param data - nbt to write to
	 * @param name - sub tag nbt to write to
	 */
	@Override
	public void writeToNBT(NBTTagCompound data, String name)
	{
		final NBTTagCompound target = new NBTTagCompound();
		writeToNBT(target);
		data.setTag(name, target);
	}

	/**
	 * @param buf - buffer to read from
	 */
	@Override
	public boolean readFromStream(ByteBuf buf)
	{
		return false;
	}

	/**
	 * @param buf - buffer to write to
	 */
	@Override
	public boolean writeToStream(ByteBuf buf)
	{
		return false;
	}
}
