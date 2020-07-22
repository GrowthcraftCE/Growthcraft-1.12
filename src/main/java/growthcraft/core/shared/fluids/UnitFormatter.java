package growthcraft.core.shared.fluids;

import com.google.common.base.Joiner;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.shared.CoreRegistry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Utility class for formatting data into strings for descriptions, item names
 * and othre nicities
 */
public class UnitFormatter {
    // REVISE_ME 0: remove cellar dependency. move to api

    public static final String fractionSeparator = TextFormatting.GRAY + " / ";

    private UnitFormatter() {
    }

    public static String fraction(String... args) {
        return Joiner.on(fractionSeparator).join(args);
    }

    public static String fractionNum(int a, int b) {
        return fraction("" + TextFormatting.WHITE + a, "" + TextFormatting.WHITE + b);
    }

    @SuppressWarnings("deprecation")
    public static String fluidBucketName(Fluid fluid) {
        return I18n.translateToLocal("item.bucket." + fluid.getUnlocalizedName() + ".name");
    }

    /**
     * @param fluid - fluid to get a modifier for
     * @return localized format of the boolean
     */
    @Nullable
    @SuppressWarnings("deprecation")
    public static String fluidModifier(Fluid fluid) {
        final String modifierSrc = fluid.getUnlocalizedName() + ".modifier";
        String modifierString = I18n.translateToLocal(modifierSrc);

        // if there is not a modifier defined, create one by joining the tag names
        if (modifierSrc.equals(modifierString)) {
            final Collection<FluidTag> tags = CoreRegistry.instance().fluidDictionary().getFluidTags(fluid);
            if (tags == null || tags.size() == 0) return null;
            String str = "";
            for (FluidTag tag : tags) {
                if (GrowthcraftCore.config.getHidePoisonedBooze() && tag.getName().equals("poisoned"))
                    continue;    // REVISE_ME
                str += ((str.length() == 0) ? "" : ", ") + tag.getLocalizedName();
            }
            modifierString = str;
        }

        return TextFormatting.GREEN + modifierString;
    }

    @Nullable
    public static String fluidModifier(FluidStack fluid) {
        return fluidModifier(fluid.getFluid());
    }

    @SuppressWarnings("deprecation")
    @Nullable
    public static String fluidName(FluidStack fluidStack) {
        if (fluidStack != null) {
            final Fluid fluid = fluidStack.getFluid();
            final String modifier = fluidModifier(fluid);

            if (modifier != null) {
                return I18n.translateToLocalFormatted("grc.format.booze.name",
                        TextFormatting.WHITE + fluidStack.getLocalizedName(), modifier);
            } else {
                return I18n.translateToLocalFormatted("grc.format.fluid.name",
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
    public static String fluidNameForContainer(FluidStack fluidStack) {
        final String name = fluidName(fluidStack);
        if (name != null) return name;
        return invalidFluid();
    }

    /**
     * @return localized format of the boolean
     */
    @SuppressWarnings("deprecation")
    public static String noItem() {
        return TextFormatting.GRAY + I18n.translateToLocal("format.itemslot.empty");
    }

    /**
     * @return localized format of the boolean
     */
    @SuppressWarnings("deprecation")
    public static String invalidItem() {
        return TextFormatting.RED + I18n.translateToLocal("format.itemslot.invalid");
    }

    /**
     * @return localized format of the boolean
     */
    @SuppressWarnings("deprecation")
    public static String noFluid() {
        return TextFormatting.GRAY + I18n.translateToLocal("grc.format.tank.empty");
    }

    /**
     * @return localized format of the boolean
     */
    @SuppressWarnings("deprecation")
    public static String invalidFluid() {
        return TextFormatting.RED + I18n.translateToLocal("format.invalid_fluid");
    }

    /**
     * @param b - boolean to format
     * @return localized format of the boolean
     */
    @SuppressWarnings("deprecation")
    public static String booleanAsState(boolean b) {
        return I18n.translateToLocal("format.state." + b);
    }

    /**
     * @param b - boolean to format
     * @return localized format of the boolean
     */
    @SuppressWarnings("deprecation")
    public static String booleanAsValue(boolean b) {
        return I18n.translateToLocal("format.value." + b);
    }
}
