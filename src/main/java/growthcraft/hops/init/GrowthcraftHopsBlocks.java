package growthcraft.hops.init;

import growthcraft.core.common.definition.BlockDefinition;
import growthcraft.hops.common.block.BlockHops;

public class GrowthcraftHopsBlocks {
//    public static BlockDefinition block_hops;
//    public static BlockDefinition hops_bush;
    public static BlockDefinition hops;

    public static void init() {
//        block_hops = new BlockDefinition( new BlockHopsTrunk("hops_vine") );
//        hops_bush = new BlockDefinition( new BlockHopsBush() );
    	hops = new BlockDefinition( new BlockHops("hops") );
    }

    public static void register() {
//    	block_hops.register(true);
//    	hops_bush.register(true);
    	hops.register(false);
    }

    public static void registerRenders() {
//    	block_hops.registerItemRender();
//    	hops_bush.registerItemRender();
    }
}
