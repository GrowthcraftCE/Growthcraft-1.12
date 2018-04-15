package growthcraft.core.shared.effect;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import growthcraft.core.shared.CoreRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * As its name implies, this Effect, will ADD a Potion Effect to the target.
 */
public class EffectAddPotionEffect extends AbstractEffect
{
	// REVISE_ME 0
	
	private IPotionEffectFactory potionFactory;

	public EffectAddPotionEffect(@Nonnull IPotionEffectFactory factory)
	{
		this.potionFactory = factory;
	}

	public EffectAddPotionEffect() {}

	public EffectAddPotionEffect setPotionFactory(@Nonnull IPotionEffectFactory factory)
	{
		this.potionFactory = factory;
		return this;
	}

	/**
	 * @return potion factory
	 */
	public IPotionEffectFactory getPotionFactory()
	{
		return potionFactory;
	}

	/**
	 * Adds the potion effect to the entity, if the entity is a EntityLivingBase.
	 *
	 * @param world - world that the entity is currently present ing
	 * @param entity - entity to apply the effect to
	 * @param data - any extra data you want to pass along
	 */
	@Override
	public void apply(World world, Entity entity, Random random, Object data)
	{
		if (potionFactory == null) return;

		if (entity instanceof EntityLivingBase)
		{
			final PotionEffect effect = potionFactory.createPotionEffect(world, entity, random, data);

			((EntityLivingBase)entity).addPotionEffect(effect);
		}
	}

	@Override
	protected void getActualDescription(List<String> list)
	{
		if (potionFactory == null) return;
		potionFactory.getDescription(list);
	}

	@Override
	protected void readFromNBT(NBTTagCompound data)
	{
		if (data.hasKey("potion_factory"))
		{
			this.potionFactory = CoreRegistry.instance().getPotionEffectFactoryRegistry().loadPotionEffectFactoryFromNBT(data, "potion_factory");
		}
	}

	@Override
	protected void writeToNBT(NBTTagCompound data)
	{
		if (potionFactory != null)
		{
			potionFactory.writeToNBT(data, "potion_factory");
		}
	}
}
