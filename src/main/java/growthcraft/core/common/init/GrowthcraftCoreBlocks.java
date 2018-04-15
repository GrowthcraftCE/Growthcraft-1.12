package growthcraft.core.common.init;

import growthcraft.core.common.block.BlockRopeFence;
import growthcraft.core.common.block.BlockRopeKnot;
import growthcraft.core.common.block.BlockSalt;
import growthcraft.core.common.block.BlockSaltOre;
import growthcraft.core.common.lib.definition.BlockDefinition;
import net.minecraftforge.oredict.OreDictionary;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftCoreBlocks {

	public static BlockDefinition salt_block;
    public static BlockDefinition salt_ore;
    public static BlockDefinition rope_knot;
    public static BlockDefinition rope_fence;

    public static void preInit() {
        rope_fence = new BlockDefinition( new BlockRopeFence( "rope_fence" ) );
        salt_block = new BlockDefinition( new BlockSalt("salt_block") );
        salt_ore = new BlockDefinition( new BlockSaltOre("salt_ore") );
        rope_knot = new BlockDefinition( new BlockRopeKnot("rope_knot") );
    }
    
    private static void registerOres() {
		OreDictionary.registerOre("oreSalt", salt_ore.getBlock());
    }

    public static void register() {
    	salt_block.getBlock().setCreativeTab(tabGrowthcraft);
    	salt_block.register(true);
    	rope_fence.register(false);
    	rope_knot.register(false);
    	salt_ore.getBlock().setCreativeTab(tabGrowthcraft);
    	salt_ore.register(true);
    	
    	registerOres();
    }

    public static void registerRenders() {
    	salt_block.registerItemRender();
//    	rope_fence.registerItemRender();
//    	rope_knot.registerItemRender();
    	salt_ore.registerItemRender();
    }
}
