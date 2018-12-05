package growthcraft.core.shared.tileentity.device;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class DeviceProgressive extends DeviceBase
{
	protected int time;
	protected int timeMax;

	public DeviceProgressive(TileEntity te)
	{
		super(te);
	}

	public float getProgress()
	{
		if (timeMax <= 0) return 0f;
		return (float)time / timeMax;
	}

	public int getProgressScaled(int scale)
	{
		if (timeMax <= 0) return 0;
		return this.time * scale / timeMax;
	}

	public int getTime()
	{
		return time;
	}

	public int getTimeMax()
	{
		return timeMax;
	}

	public void setTime(int t)
	{
		this.time = t;
	}

	public void setTimeMax(int t)
	{
		this.timeMax = t;
	}

	public boolean resetTime()
	{
		if (this.time != 0)
		{
			setTime(0);
			return true;
		}
		return false;
	}


	public void increaseTime()
	{
		time++;
	}

	public void update()
	{
	}

	/**
	 * @param data - nbt data to read from
	 */
	@Override
	public void readFromNBT(NBTTagCompound data)
	{
		super.readFromNBT(data);
		this.time = data.getInteger("time");
		//this.timeMax = data.getInteger("timeMax");
	}

	/**
	 * @param data - nbt to write to
	 */
	@Override
	public void writeToNBT(NBTTagCompound data)
	{
		super.writeToNBT(data);
		data.setInteger("time", time);
		//data.setInteger("timeMax", timeMax);
	}

	/**
	 * @param buf - buffer to read from
	 */
	@Override
	public boolean readFromStream(ByteBuf buf)
	{
		super.readFromStream(buf);
		this.time = buf.readInt();
		//this.timeMax = buf.readInt();
		return false;
	}

	/**
	 * @param buf - buffer to write to
	 */
	@Override
	public boolean writeToStream(ByteBuf buf)
	{
		super.writeToStream(buf);
		buf.writeInt(time);
		//buf.writeInt(timeMax);
		return false;
	}
}
