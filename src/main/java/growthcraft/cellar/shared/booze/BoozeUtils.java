package growthcraft.cellar.shared.booze;

import java.util.List;

import javax.annotation.Nullable;

import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.booze.BoozeEffect;
import growthcraft.cellar.shared.booze.BoozeTag;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.config.description.Describer;
import growthcraft.core.shared.fluids.UnitFormatter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BoozeUtils
{
	private BoozeUtils() {}

	public static float alcoholToTipsy(float alcoholRate)
	{
		return alcoholRate * 4;
	}

	public static boolean isFermentedBooze(Fluid booze)
	{
		return CoreRegistry.instance().fluidDictionary().hasFluidTags(booze, BoozeTag.FERMENTED);
	}

	public static void addEffects(Fluid booze, ItemStack stack, World world, EntityPlayer player)
	{
		if (booze == null) return;

		final BoozeEffect effect = CellarRegistry.instance().booze().getEffect(booze);
		if (effect != null)
		{
			effect.apply(world, player, world.rand, null);
		}
	}

	public static void addInformation(Fluid booze, ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (booze == null) return;
		final String s = UnitFormatter.fluidModifier(booze);
		if (s != null) tooltip.add(s);
		Describer.getDescription(tooltip, booze);
	}

	public static void addEffectInformation(Fluid booze, ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (booze == null) return;
		final BoozeEffect effect = CellarRegistry.instance().booze().getEffect(booze);
		if (effect != null)
		{
			effect.getDescription(tooltip);
		}
	}

	public static void addBottleInformation(Fluid booze, ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn, boolean showDetailed)
	{
		if (booze == null) return;
		addInformation(booze, stack, worldIn, tooltip, flagIn);
		if (showDetailed)
			addEffectInformation(booze, stack, worldIn, tooltip, flagIn);
	}

	public static boolean hasEffect(Fluid booze)
	{
		final BoozeEffect effect = CellarRegistry.instance().booze().getEffect(booze);
		if (effect != null) return effect.isValid();
		return false;
	}
}
