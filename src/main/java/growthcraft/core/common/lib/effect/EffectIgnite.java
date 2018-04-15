package growthcraft.core.common.lib.effect;

import java.util.List;
import java.util.Random;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * I set fireeeeeee to the blaaaze, okay no.
 */
public class EffectIgnite extends AbstractEffect
{
	// REVISE_ME 0
	
	private int time;

	public EffectIgnite(int t)
	{
		this.time = t;
	}

	public EffectIgnite()
	{
		this(15);
	}

	public int getTime()
	{
		return time;
	}

	public EffectIgnite setTime(int t)
	{
		this.time = t;
		return this;
	}

	@Override
	public void apply(World world, Entity entity, Random random, Object data)
	{
		// ???????
		entity.extinguish();
	}

	@Override
	protected void getActualDescription(List<String> list)
	{
		list.add(I18n.format("effect.ignite.desc", time));
	}

	@Override
	protected void readFromNBT(NBTTagCompound data)
	{
		this.time = data.getInteger("time");
	}

	@Override
	protected void writeToNBT(NBTTagCompound data)
	{
		data.setInteger("time", time);
	}
}
