package growthcraft.core.api.effect;

import java.util.Random;

import growthcraft.core.api.nbt.INBTSerializableContext;
import growthcraft.core.description.IDescribable;
import net.minecraft.entity.Entity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public interface IPotionEffectFactory extends IDescribable, INBTSerializableContext {
	// REVISE_ME 0
	
	PotionEffect createPotionEffect(World world, Entity entity, Random random, Object data);
}
