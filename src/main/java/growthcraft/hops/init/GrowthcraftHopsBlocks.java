package growthcraft.hops.init;

import growthcraft.cellar.common.definition.BlockBoozeDefinition;
import growthcraft.core.common.definition.BlockDefinition;
import growthcraft.hops.common.block.BlockHops;

public class GrowthcraftHopsBlocks {
	public static BlockBoozeDefinition[] hopAleFluidBlocks;
	public static BlockBoozeDefinition[] lagerFluidBlocks;
    public static BlockDefinition hops;

    public static void preInit() {
    	hops = new BlockDefinition( new BlockHops("hops") );
    	
    	hopAleFluidBlocks = null; // Initialized in GrowthcraftHopsFluids
    	lagerFluidBlocks = null; // Initialized in GrowthcraftHopsFluids
    }

    public static void register() {
    	hops.register(false);
    }

    public static void registerRenders() {
    }
}
