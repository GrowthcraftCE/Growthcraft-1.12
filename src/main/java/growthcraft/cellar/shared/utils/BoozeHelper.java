package growthcraft.cellar.shared.utils;

import growthcraft.cellar.shared.definition.BoozeDefinition;
import net.minecraftforge.fluids.FluidStack;

public class BoozeHelper {

    public static FluidStack[] boozeDefintionsToFluidStacks (BoozeDefinition[] boozes ) {
        return boozeDefintionsToFluidStacks(boozes, 1000);
    }

    public static FluidStack[] boozeDefintionsToFluidStacks (BoozeDefinition[] boozes, int volume ) {
        final FluidStack[] fluidStacks = new FluidStack[boozes.length];
        for ( int i = 0; i < boozes.length; ++i ) {
            fluidStacks[i] = boozes[i].asFluidStack(volume);
        }
        return fluidStacks;
    }

}
