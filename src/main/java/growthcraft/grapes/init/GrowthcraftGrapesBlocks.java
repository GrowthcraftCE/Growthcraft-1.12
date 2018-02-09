package growthcraft.grapes.init;

import growthcraft.grapes.common.blocks.BlockGrapeFruit;
import growthcraft.grapes.common.blocks.BlockGrapeLeaves;
import growthcraft.grapes.common.blocks.BlockGrapeVine0;
import growthcraft.grapes.common.blocks.BlockGrapeVine1;
import growthcraft.cellar.common.definition.BlockBoozeDefinition;
import growthcraft.core.common.definition.BlockDefinition;
import growthcraft.core.common.definition.BlockTypeDefinition;

public class GrowthcraftGrapesBlocks {

//    public static BlockDefinition grape_vine;
//    public static BlockDefinition grape_vine_bush;
//    public static BlockDefinition grape_vine_fruit;
    
	public static BlockBoozeDefinition[] grapeWineFluidBlocks;
	public static BlockTypeDefinition<BlockGrapeVine0> grapeVine0;
	public static BlockTypeDefinition<BlockGrapeVine1> grapeVine1;
	public static BlockTypeDefinition<BlockGrapeLeaves> grapeLeaves;
	public static BlockDefinition grapeFruit;
	
    public static void init() {
//        grape_vine = new BlockDefinition( new BlockGrapeVine("grape_vine") );
//        grape_vine_bush = new BlockDefinition( new BlockGrapeVineBush("grape_vine_bush") );
//        grape_vine_fruit = new BlockDefinition( new BlockGrapeVineFruit() );
    	
		grapeVine0  = new BlockTypeDefinition<BlockGrapeVine0>(new BlockGrapeVine0("grape_vine0"));
		grapeVine1  = new BlockTypeDefinition<BlockGrapeVine1>(new BlockGrapeVine1("grape_vine1"));
		grapeLeaves = new BlockTypeDefinition<BlockGrapeLeaves>(new BlockGrapeLeaves("grape_vine_leaves"));
		grapeFruit  = new BlockDefinition(new BlockGrapeFruit("grape_fruit"));
    	
        grapeWineFluidBlocks = null; // Is initialized in GrowthcraftGrapesFluids
    }

    public static void register() {
//    	grape_vine.register(true);
//    	grape_vine_bush.register(true);
//    	grape_vine_fruit.register(true);
    	grapeVine0.register(false);
    	grapeVine1.register(false);
    	grapeLeaves.register(false);
    	grapeFruit.register(false);
    }

    public static void  registerRenders() {
//    	grape_vine.registerItemRender();
//    	grape_vine_bush.registerItemRender();
//    	grape_vine_fruit.registerItemRender();
    }
}
