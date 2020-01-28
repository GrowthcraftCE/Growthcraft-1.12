package growthcraft.core.shared.compat.forestry;

import growthcraft.core.shared.definition.FluidDefinition;
import net.minecraftforge.fluids.FluidRegistry;

public class InitForestry {
    private InitForestry() {
    }

    ////////////
    // Fluids
    ////////////

    public static void preInitFluids() {
        ForestryModFluids.milk = new FluidDefinition(FluidRegistry.getFluid("milk"));
    }
}
