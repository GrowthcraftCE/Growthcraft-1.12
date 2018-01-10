package growthcraft.core.utils;

import java.util.Collection;

import javax.annotation.Nullable;

import com.google.common.base.Joiner;

import growthcraft.cellar.api.booze.BoozeTag;
import growthcraft.core.GrowthcraftConfiguration;
import growthcraft.core.api.CoreRegistry;
import growthcraft.core.api.fluids.FluidTag;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Utility class for formatting data into strings for descriptions, item names
 * and othre nicities
 */
public class UnitFormatter
{
	// REVISE_ME 0: remove cellar dependency. move to api
	
	public static final String fractionSeparator = TextFormatting.GRAY + " / ";

	private UnitFormatter() {}

	public static String fraction(String... args)
	{
		return Joiner.on(fractionSeparator).join(args);
	}

	public static String fractionNum(int a, int b)
	{
		return fraction("" + TextFormatting.WHITE + a, "" + TextFormatting.WHITE + b);
	}

	public static String fluidBucketName(Fluid fluid)
	{
		return I18n.format("item.bucket." + fluid.getUnlocalizedName() + ".name");
	}

	/**
	 * @param fluid - fluid to get a modifier for
	 * @return localized format of the boolean
	 */
	@Nullable
	public static String fluidModifier(Fluid fluid)
	{
		final String modifierSrc = fluid.getUnlocalizedName() + ".modifier";
		String modifierString = I18n.format(modifierSrc);

		// if there is not a modifier defined, create one by joining the tag names
		if (modifierSrc.equals(modifierString))
		{
			final Collection<FluidTag> tags = CoreRegistry.instance().fluidDictionary().getFluidTags(fluid);
			if (tags == null || tags.size() == 0) return null;
			String str = "";
			for (FluidTag tag : tags)
			{
				if (GrowthcraftConfiguration.hidePoisonedBooze && tag == BoozeTag.POISONED) continue;
				str += ((str.length() == 0) ? "" : ", ") + tag.getLocalizedName();
			}
			modifierString = str;
		}

		return TextFormatting.GREEN + modifierString;
	}

	@Nullable
	public static String fluidModifier(FluidStack fluid)
	{
		return fluidModifier(fluid.getFluid());
	}

	@Nullable
	public static String fluidName(FluidStack fluidStack)
	{
		if (fluidStack != null)
		{
			final Fluid fluid = fluidStack.getFluid();
			final String modifier = fluidModifier(fluid);

			if (modifier != null)
			{
				return I18n.format("grc.format.booze.name",
					TextFormatting.WHITE + fluidStack.getLocalizedName(), modifier);
			}
			else
			{
				return I18n.format("grc.format.fluid.name",
						TextFormatting.WHITE + fluidStack.getLocalizedName());
			}
		}
		return null;
	}

	/**
	 * Formats a fluid name, handling booze and their modifiers specially
	 *
	 * @param fluidStack - fluid source
	 * @return localized format of the fluid name + its modifiers if any
	 */
	@Nullable
	public static String fluidNameForContainer(FluidStack fluidStack)
	{
		final String name = fluidName(fluidStack);
		if (name != null) return name;
		return invalidFluid();
	}

	/**
	 * @return localized format of the boolean
	 */
	public static String noItem()
	{
		return TextFormatting.GRAY + I18n.format("format.itemslot.empty");
	}

	/**
	 * @return localized format of the boolean
	 */
	public static String invalidItem()
	{
		return TextFormatting.RED + I18n.format("format.itemslot.invalid");
	}

	/**
	 * @return localized format of the boolean
	 */
	public static String noFluid()
	{
		return TextFormatting.GRAY + I18n.format("grc.format.tank.empty");
	}

	/**
	 * @return localized format of the boolean
	 */
	public static String invalidFluid()
	{
		return TextFormatting.RED + I18n.format("format.invalid_fluid");
	}

	/**
	 * @param b - boolean to format
	 * @return localized format of the boolean
	 */
	public static String booleanAsState(boolean b)
	{
		return I18n.format("format.state." + b);
	}

	/**
	 * @param b - boolean to format
	 * @return localized format of the boolean
	 */
	public static String booleanAsValue(boolean b)
	{
		return I18n.format("format.value." + b);
	}
}
