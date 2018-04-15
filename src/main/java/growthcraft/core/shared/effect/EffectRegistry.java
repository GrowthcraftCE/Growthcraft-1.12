package growthcraft.core.shared.effect;

import javax.annotation.Nonnull;

import growthcraft.core.shared.AbstractClassRegistry;
import net.minecraft.nbt.NBTTagCompound;

public class EffectRegistry extends AbstractClassRegistry<IEffect> {
	// REVISE_ME 0
	
	public EffectRegistry initialize()
	{
		register("add_potion_effect", EffectAddPotionEffect.class);
		register("chance", EffectChance.class);
		register("extinguish", EffectExtinguish.class);
		register("ignite", EffectIgnite.class);
		register("list", EffectList.class);
		register("null", EffectNull.class);
		register("random_list", EffectRandomList.class);
		register("remove_potion_effect", EffectRemovePotionEffect.class);
		register("weighted_random_list", EffectWeightedRandomList.class);
		return this;
	}

//	@Override
	public IEffect loadEffectFromNBT(@Nonnull NBTTagCompound data, @Nonnull String name)
	{
		return loadObjectFromNBT(data, name);
	}
}
