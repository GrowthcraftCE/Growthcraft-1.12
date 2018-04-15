package growthcraft.cellar.api.booze;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import growthcraft.cellar.api.CellarRegistry;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.effect.IPotionEffectFactory;
import growthcraft.core.shared.fluids.FluidTag;
import growthcraft.core.shared.config.description.Describer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BoozePotionEffectFactory implements IPotionEffectFactory {
	// REVISE_ME 0
	
	private Potion potion;
	private int time;
	private int level;
	private Fluid booze;

	public BoozePotionEffectFactory(@Nonnull Fluid b, Potion potion, int tm, int lvl)
	{
		this.booze = b;
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

	public PotionEffect createPotionEffect(World world, Entity entity, Random random, Object data)
	{
		final Collection<FluidTag> tags = CoreRegistry.instance().fluidDictionary().getFluidTags(booze);

		if (tags != null)
		{
			int tm = getTime();
			int lv = getLevel();
			for (FluidTag tag : tags)
			{
				final IModifierFunction func = CellarRegistry.instance().booze().getModifierFunction(tag);
				if (func != null)
				{
					tm = func.applyTime(tm);
					lv = func.applyLevel(lv);
				}
			}
			return new PotionEffect(getPotion(), tm, lv);
		}
		return null;
	}

	public void getDescription(List<String> list)
	{
		final PotionEffect pe = createPotionEffect(null, null, null, null);
		Describer.getPotionEffectDescription(list, pe);
	}

	private void readFromNBT(NBTTagCompound data)
	{
		this.booze = null;
		this.potion = Potion.getPotionById(data.getInteger("id"));
		this.time = data.getInteger("time");
		this.level = data.getInteger("level");
		if (data.hasKey("fluid.name"))
		{
			this.booze = FluidRegistry.getFluid(data.getString("fluid.name"));
		}
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
		data.setInteger("id", Potion.getIdFromPotion(getPotion()));
		data.setInteger("time", getTime());
		data.setInteger("level", getLevel());
		if (booze != null)
		{
			data.setString("fluid.name", booze.getName());
		}
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
