package growthcraft.core.common.lib.effect;

import java.util.Random;

import growthcraft.core.common.lib.config.description.IDescribable;
import growthcraft.core.common.lib.io.nbt.INBTSerializableContext;
import net.minecraft.entity.Entity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public interface IPotionEffectFactory extends IDescribable, INBTSerializableContext {
	// REVISE_ME 0
	
	PotionEffect createPotionEffect(World world, Entity entity, Random random, Object data);
}
