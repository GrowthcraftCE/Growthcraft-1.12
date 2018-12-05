package growthcraft.core.shared.tileentity.component;

import javax.annotation.Nonnull;

import growthcraft.core.shared.io.nbt.INBTSerializableContext;
import growthcraft.core.shared.io.stream.IStreamable;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileHeatingComponent implements INBTSerializableContext, IStreamable
{
	private TileEntity tileEntity;
	private HeatBlockComponent heatBlockComponent;
	private float heat;
	private float heatLoss = 0.01f;
	private float heatGain = 0.01f;

	public TileHeatingComponent(@Nonnull TileEntity te, float adjacentHeating)
	{
		this.tileEntity = te;
		this.heatBlockComponent = new HeatBlockComponent(tileEntity, adjacentHeating);
	}

	public void update()
	{
		final float heatMul = heatBlockComponent.getHeatMultiplier();
		if (heatMul > 0)
		{
			if (this.heat < heatMul)
			{
				this.heat += heatGain * heatMul;
			}
			else
			{
				this.heat -= heatGain * heatMul;
			}
		}
		else
		{
			if (heat > 0)
				this.heat -= heatLoss;
		}
	}

	public TileHeatingComponent setHeatMultiplier(float h)
	{
		this.heat = h;
		return this;
	}

	public float getHeatMultiplier()
	{
		return heat;
	}

	public boolean isHeated()
	{
		return heat > 0.0f;
	}

	private void readFromNBT(@Nonnull NBTTagCompound tag)
	{
		this.heat = tag.getFloat("heat");
	}

	@Override
	public void readFromNBT(@Nonnull NBTTagCompound tag, @Nonnull String name)
	{
		if (tag.hasKey(name))
		{
			readFromNBT(tag.getCompoundTag(name));
		}
		else
		{
			// WARN
		}
	}

	private void writeToNBT(@Nonnull NBTTagCompound tag)
	{
		tag.setFloat("heat", heat);
	}

	@Override
	public void writeToNBT(@Nonnull NBTTagCompound tag, @Nonnull String name)
	{
		final NBTTagCompound target = new NBTTagCompound();
		writeToNBT(target);
		tag.setTag(name, target);
	}

	@Override
	public boolean readFromStream(ByteBuf stream)
	{
		this.heat = stream.readFloat();
		return false;
	}

	@Override
	public boolean writeToStream(ByteBuf stream)
	{
		stream.writeFloat(heat);
		return false;
	}
}
