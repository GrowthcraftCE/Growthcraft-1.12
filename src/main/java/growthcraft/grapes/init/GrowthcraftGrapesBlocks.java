package growthcraft.grapes.init;

import growthcraft.grapes.blocks.BlockGrapeVine;
import growthcraft.grapes.blocks.BlockGrapeVineBush;
import growthcraft.grapes.blocks.BlockGrapeVineFruit;
import growthcraft.cellar.common.definition.BlockBoozeDefinition;
import growthcraft.core.common.definition.BlockDefinition;

public class GrowthcraftGrapesBlocks {

    public static BlockDefinition grape_vine;
    public static BlockDefinition grape_vine_bush;
    public static BlockDefinition grape_vine_fruit;
    
	public static BlockBoozeDefinition[] grapeWineFluidBlocks;
	
    public static void init() {
        grape_vine = new BlockDefinition( new BlockGrapeVine("grape_vine") );
        grape_vine_bush = new BlockDefinition( new BlockGrapeVineBush("grape_vine_bush") );
        grape_vine_fruit = new BlockDefinition( new BlockGrapeVineFruit() );
        grapeWineFluidBlocks = null; // Is initialized in GrowthcraftGrapesFluids
    }

    public static void register() {
    	grape_vine.register(true);
    	grape_vine_bush.register(true);
    	grape_vine_fruit.register(true);
    }

    public static void  registerRenders() {
    	grape_vine.registerItemRender();
    	grape_vine_bush.registerItemRender();
    	grape_vine_fruit.registerItemRender();
    }
}
