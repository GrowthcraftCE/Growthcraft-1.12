package growthcraft.core.api;

import javax.annotation.Nonnull;

import growthcraft.core.api.effect.EffectAddPotionEffect;
import growthcraft.core.api.effect.EffectChance;
import growthcraft.core.api.effect.EffectExtinguish;
import growthcraft.core.api.effect.EffectIgnite;
import growthcraft.core.api.effect.EffectList;
import growthcraft.core.api.effect.EffectNull;
import growthcraft.core.api.effect.EffectRandomList;
import growthcraft.core.api.effect.EffectRemovePotionEffect;
import growthcraft.core.api.effect.EffectWeightedRandomList;
import growthcraft.core.api.effect.IEffect;
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
