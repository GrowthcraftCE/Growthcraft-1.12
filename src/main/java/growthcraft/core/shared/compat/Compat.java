package growthcraft.core.shared.compat;

import growthcraft.core.shared.compat.forestry.ForestryModFluids;
import growthcraft.milk.shared.init.GrowthcraftMilkFluids;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// Note: no imports from other mods should be added here 

public class Compat {
    private Compat() {
    }

    public static final String MODID_FORESTRY = "forestry";
    public static final String MODID_RUSTIC = "rustic";
    public static final String MODID_THAUMCRAFT = "thaumcraft";
    public static final String MODID_ANIMANIA = "animania";

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

    public static boolean isModAvailable_Animania() { return Loader.isModLoaded(MODID_ANIMANIA); }

    ////////////
    // Fluids
    ////////////

    public static List<Fluid> getMilkFluids() {
        final Set<Fluid> milks = new HashSet<Fluid>();
        if (GrowthcraftMilkFluids.milk != null)
            milks.add(GrowthcraftMilkFluids.milk.getFluid());
        if (ForestryModFluids.milk != null)
            milks.add(ForestryModFluids.milk.getFluid());

        //Animania Milk
        String[] milk_names = {"milk_holstein","milk_friesian","milk_jersey","milk_goat","milk_sheep"};
        if(isModAvailable_Animania()){
            for(String milk : milk_names){
                if(FluidRegistry.isFluidRegistered(milk)) {
                    milks.add(FluidRegistry.getFluid(milk));
                }
            }
        }
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
