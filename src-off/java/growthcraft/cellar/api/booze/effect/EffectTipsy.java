package growthcraft.cellar.api.booze.effect;

import java.util.List;
import java.util.Random;

import growthcraft.core.api.IAchievement;
import growthcraft.core.api.effect.AbstractEffect;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EffectTipsy extends AbstractEffect
{
	// REVISE_ME 0
	
	public static Potion potionTipsy;
	public static IAchievement achievement;
	private boolean hasTipsyEffect;
	private float tipsyChance;
	private int tipsyTime;

	public EffectTipsy clear()
	{
		this.hasTipsyEffect = false;
		this.tipsyChance = 0.0f;
		this.tipsyTime = 0;
		return this;
	}

	public EffectTipsy setTipsy(float chance, int time)
	{
		this.hasTipsyEffect = true;
		this.tipsyChance = MathHelper.clamp(chance, 0.1F, 1.0F);
		this.tipsyTime = time;
		return this;
	}

	public boolean canCauseTipsy()
	{
		return hasTipsyEffect;
	}

	public float getTipsyChance()
	{
		return tipsyChance;
	}

	public int getTipsyTime()
	{
		return tipsyTime;
	}

	@Override
	public void apply(World world, Entity entity, Random random, Object data)
	{
		if (entity instanceof EntityLivingBase)
		{
			final EntityLivingBase entitylb = (EntityLivingBase)entity;
			if (!canCauseTipsy()) return;
			if (world.rand.nextFloat() > getTipsyChance()) return;

			int amplifier = 0;
			int time = 1200;
			if (entitylb.isPotionActive(potionTipsy))
			{
				amplifier = entitylb.getActivePotionEffect(potionTipsy).getAmplifier() + 1;
				if (amplifier > 4)
				{
					amplifier = 4;
				}
			}

			switch (amplifier)
			{
				case 1: time = 3000; break;
				case 2: time = 6750; break;
				case 3: time = 12000; break;
				case 4: time = 24000; break;
				default:
					break;
			}

			entitylb.addPotionEffect(new PotionEffect(potionTipsy, time, amplifier));

			if (entitylb instanceof EntityPlayer)
			{
				final EntityPlayer player = (EntityPlayer)entitylb;
				if (amplifier >= 4) achievement.addStat(player, 1);
			}
		}
	}

	@Override
	protected void getActualDescription(List<String> list)
	{
		final PotionEffect nausea = new PotionEffect(MobEffects.NAUSEA, getTipsyTime(), 0);
		final String p = I18n.format("format.tipsy_chance", Math.round(getTipsyChance() * 100));

		String n = "";
		if (nausea.getDuration() > 20)
		{
			n = "(" + Potion.getPotionDurationString(nausea, nausea.getDuration()) + ")";
		}
		list.add(TextFormatting.GRAY + p + TextFormatting.GRAY + " " + n);
	}

	@Override
	protected void readFromNBT(NBTTagCompound data)
	{
		this.hasTipsyEffect = data.getBoolean("has_tipsy_effect");
		this.tipsyChance = data.getFloat("tipsy_chance");
		this.tipsyTime = data.getInteger("tipsy_time");
	}

	@Override
	protected void writeToNBT(NBTTagCompound data)
	{
		data.setBoolean("has_tipsy_effect", hasTipsyEffect);
		data.setFloat("tipsy_chance", tipsyChance);
		data.setInteger("tipsy_time", tipsyTime);
	}
}
