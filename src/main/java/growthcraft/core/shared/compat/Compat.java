package growthcraft.core.shared.compat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import growthcraft.core.shared.compat.forestry.ForestryModFluids;
import growthcraft.milk.shared.init.GrowthcraftMilkFluids;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;

// Note: no imports from other mods should be added here 

public class Compat {
    private Compat() {
    }

    public static final String MODID_FORESTRY = "forestry";
    public static final String MODID_RUSTIC = "rustic";
    public static final String MODID_THAUMCRAFT = "thaumcraft";


    ////////////
    // Common
    ////////////

    public static boolean isModAvailable_Rustic() {
        return Loader.isModLoaded(MODID_RUSTIC);
    }

    public static boolean isModAvailable_Forestry() {
        return Loader.isModLoaded(MODID_FORESTRY);
    }

    public static boolean isModAvailable_Thaumcraft() {
        return Loader.isModLoaded(MODID_THAUMCRAFT);
    }

    ////////////
    // Fluids
    ////////////

    public static List<Fluid> getMilkFluids() {
        final Set<Fluid> milks = new HashSet<Fluid>();
        if (GrowthcraftMilkFluids.milk != null)
            milks.add(GrowthcraftMilkFluids.milk.getFluid());
        if (ForestryModFluids.milk != null)
            milks.add(ForestryModFluids.milk.getFluid());

        // Automagy Milk
        final Fluid fluidmilk = FluidRegistry.getFluid("fluidmilk");
        if (fluidmilk != null)
            milks.add(fluidmilk);

        // Other milk
        final Fluid milk = FluidRegistry.getFluid("milk");
        if (milk != null)
            milks.add(milk);

        return milks.stream().collect(Collectors.toList());
    }
}
