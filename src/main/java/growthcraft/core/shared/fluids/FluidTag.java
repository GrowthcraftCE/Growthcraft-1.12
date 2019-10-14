package growthcraft.core.shared.fluids;

import javax.annotation.Nonnull;

import net.minecraft.util.text.translation.I18n;

public class FluidTag {
    // REVISE_ME 0

    private final String name;

    public FluidTag(@Nonnull String n) {
        this.name = n;
    }

    public String getName() {
        return name;
    }

    public String getUnlocalizedName() {
        return "fluids.tag." + getName();
    }

    @SuppressWarnings("deprecation")
    public String getLocalizedName() {
        return I18n.translateToLocal(getUnlocalizedName());
    }

    public String toString() {
        return getName();
    }
}
