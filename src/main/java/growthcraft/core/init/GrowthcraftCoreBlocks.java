package growthcraft.core.init;

import growthcraft.core.blocks.BlockRopeFence;
import growthcraft.core.blocks.BlockRopeKnot;
import growthcraft.core.blocks.BlockSalt;
import growthcraft.core.common.definition.BlockDefinition;
import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftCoreBlocks {

    public static BlockDefinition salt_block;
    public static BlockDefinition rope_knot;
    public static BlockDefinition rope_fence;

    public static void preInit() {
        rope_fence = new BlockDefinition( new BlockRopeFence( "rope_fence" ) );
        salt_block = new BlockDefinition( new BlockSalt("salt_block") );
        rope_knot = new BlockDefinition( new BlockRopeKnot("rope_knot") );
    }

    public static void register() {
    	salt_block.getBlock().setCreativeTab(tabGrowthcraft);
    	salt_block.register(true);
    	rope_fence.register(true);
    	rope_knot.register(true);
    }

    public static void  registerRenders() {
    	salt_block.registerRender();
    	rope_fence.registerRender();
    	rope_knot.registerRender();
    }
}
