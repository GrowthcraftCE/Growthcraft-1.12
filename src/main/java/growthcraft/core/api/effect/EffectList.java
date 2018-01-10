package growthcraft.core.api.effect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import growthcraft.core.description.Describer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * An EffectList, as its name implies, is a list of IEffects, when it is applied
 * it will apply all of its children effects.
 * When the description is retrieved, it will join them together.
 */
public class EffectList extends AbstractEffectList
{
	// REVISE_ME 0
	
	/**
	 * Performs a shallow copy of the EffectList
	 *
	 * @return new effect list
	 */
	public EffectList copy()
	{
		return new EffectList().concat(effects);
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
		for (IEffect effect : effects)
		{
			effect.apply(world, entity, random, data);
		}
	}

	protected void addDescriptionHead(List<String> list)
	{
		list.add(I18n.format("effect.list.head"));
	}

	/**
	 * Adds the description of all the internal effects
	 *
	 * @param list - list to add description lines to
	 */
	@Override
	protected void getActualDescription(List<String> list)
	{
		if (effects.size() > 1)
		{
			addDescriptionHead(list);
			for (IEffect effect : effects)
			{
				final List<String> tempList = new ArrayList<String>();
				effect.getDescription(tempList);
				Describer.addAllIndented(list, tempList);
			}
		}
		else if (effects.size() == 1)
		{
			effects.get(0).getDescription(list);
		}
	}
}
