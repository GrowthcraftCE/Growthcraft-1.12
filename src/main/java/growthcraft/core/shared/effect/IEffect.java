package growthcraft.core.shared.effect;

import java.util.Random;

import growthcraft.core.shared.config.description.IDescribable;
import growthcraft.core.shared.io.nbt.INBTSerializableContext;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * This is the main interface for Growthcraft's Effect system.
 * Its meant to solve the problem with constructing complex item effects,
 * where data along just won't cut it.
 */
public interface IEffect extends IDescribable, INBTSerializableContext
{
	// REVISE_ME 0
	
	/**
	 * This method is called when the effect needs to be applied to the
	 * given world and entity.
	 *
	 * @param world - world that the entity is currently present ing
	 * @param entity - entity to apply the effect to
	 * @param data - any extra data you want to pass along
	 */
	void apply(World world, Entity entity, Random random, Object data);
}
