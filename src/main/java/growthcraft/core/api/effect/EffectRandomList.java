package growthcraft.core.api.effect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import growthcraft.core.description.Describer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * A variation of the EffectList, this one will randomly apply one of its internal
 * effects, vs applying all of them.
 */
public class EffectRandomList extends AbstractEffectList
{
	// REVISE_ME 0
	
	/**
	 * Performs a shallow copy of the EffectList
	 *
	 * @return new effect list
	 */
	public EffectRandomList copy()
	{
		return new EffectRandomList().concat(effects);
	}

	/**
	 * Applies all of the internal effects to the targets
	 *
	 * @param world - world that the entity is currently present ing
	 * @param entity - entity to apply the effect to
	 * @param data - any extra data you want to pass along
	 */
	@Override
	public void apply(World world, Entity entity, Random random, Object data)
	{
		final int index = random.nextInt(effects.size());
		final IEffect effect = effects.get(index);
		if (effect != null) effect.apply(world, entity, random, data);
	}

	/**
	 * Adds the description of all the internal effects
	 *
	 * @param list - list to add description lines to
	 */
	@Override
	protected void getActualDescription(List<String> list)
	{
		if (effects.size() > 0)
		{
			final List<String> tempList = new ArrayList<String>();
			list.add(I18n.format("grc.effect.random_list.head"));
			for (IEffect effect : effects)
			{
				tempList.clear();
				effect.getDescription(tempList);
				Describer.addAllIndented(list, tempList);
			}
		}
	}
}
