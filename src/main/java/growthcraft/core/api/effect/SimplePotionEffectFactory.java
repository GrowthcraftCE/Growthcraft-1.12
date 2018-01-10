package growthcraft.core.api.effect;

import java.util.List;
import java.util.Random;

import growthcraft.core.api.CoreRegistry;
import growthcraft.core.description.Describer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class SimplePotionEffectFactory implements IPotionEffectFactory
{
	private Potion potion;
	private int time;
	private int level;

	public SimplePotionEffectFactory(Potion potion, int tm, int lvl)
	{
		this.potion = potion;
		this.time = tm;
		this.level = lvl;
	}

	public Potion getPotion()
	{
		return potion;
	}

	public int getTime()
	{
		return time;
	}

	public int getLevel()
	{
		return level;
	}

	@Override
	public PotionEffect createPotionEffect(World world, Entity entity, Random random, Object data)
	{
		return new PotionEffect(getPotion(), getTime(), getLevel());
	}

	@Override
	public void getDescription(List<String> list)
	{
		final PotionEffect pe = createPotionEffect(null, null, null, null);
		Describer.getPotionEffectDescription(list, pe);
	}

	private void readFromNBT(NBTTagCompound data)
	{
		this.potion = Potion.getPotionById(data.getInteger("potion_id"));
		this.time = data.getInteger("time");
		this.level = data.getInteger("level");
	}

	@Override
	public void readFromNBT(NBTTagCompound data, String name)
	{
		if (data.hasKey(name))
		{
			final NBTTagCompound subData = data.getCompoundTag(name);
			readFromNBT(subData);
		}
		else
		{
			// LOG error
		}
	}

	private void writeToNBT(NBTTagCompound data)
	{
		data.setInteger("potion_id", Potion.getIdFromPotion(getPotion()));
		data.setInteger("time", getTime());
		data.setInteger("level", getLevel());
	}

	@Override
	public void writeToNBT(NBTTagCompound data, String name)
	{
		final NBTTagCompound target = new NBTTagCompound();
		final String factoryName = CoreRegistry.instance().getPotionEffectFactoryRegistry().getName(this.getClass());

		target.setString("__name__", factoryName);
		writeToNBT(target);

		data.setTag(name, target);
	}
}
