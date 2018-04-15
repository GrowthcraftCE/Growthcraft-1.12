package growthcraft.core.shared.config.description;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

/**
 * A nice way to add descriptions to a list if the Object is an IDescribable
 */
public class Describer
{
	// REVISE_ME 0: Move to utils
	
	private Describer() {}

	public static void getDescription(List<String> list, Object obj)
	{
		if (obj instanceof IDescribable)
		{
			((IDescribable)obj).getDescription(list);
		}
	}

	public static void getPotionEffectDescription(List<String> list, PotionEffect pe)
	{
		if (pe == null) return;

		String s = I18n.format(pe.getEffectName()).trim();
		final Potion potion = pe.getPotion();
		if (potion != null)
		{
			if (potion.isBadEffect())
				s = TextFormatting.RED + s;
		}

		if (pe.getAmplifier() > 0)
		{
			s += " " + I18n.format("potion.potency." + pe.getAmplifier()).trim();
		}

		if (pe.getDuration() > 20)
		{
			s += "" + TextFormatting.GRAY + " (" + Potion.getPotionDurationString(pe, pe.getDuration()) + ")";
		}
		list.add(s);
	}

	public static void addAllPrefixed(String prefix, List<String> dest, List<String> src)
	{
		for (String str : src)
		{
			dest.add(prefix + str);
		}
	}

	public static void addAllIndented(List<String> dest, List<String> src)
	{
		addAllPrefixed("  ", dest, src);
	}

	/**
	 * Attempts to compact the src list into the dest list.
	 * If the src list only has 1 entry, then it will inline with the head
	 * else it is treated as an indented list
	 *
	 * @param head - str to inline with or use as header
	 * @param dest - destination list
	 * @param src - source list
	 */
	public static void compactDescription(String head, List<String> dest, List<String> src)
	{
		if (src.size() > 0)
		{
			if (src.size() == 1)
			{
				final String line = src.get(0);
				dest.add(head + " " + line);
			}
			else
			{
				dest.add(head);
				addAllIndented(dest, src);
			}
		}
	}
}
