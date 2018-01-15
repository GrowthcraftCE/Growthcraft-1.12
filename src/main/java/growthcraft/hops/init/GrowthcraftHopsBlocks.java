package growthcraft.hops.init;

import growthcraft.hops.blocks.BlockHopsBush;
import growthcraft.hops.blocks.BlockHopsTrunk;
import growthcraft.core.common.definition.BlockDefinition;

public class GrowthcraftHopsBlocks {
    public static BlockDefinition block_hops;
    public static BlockDefinition hops_bush;

    public static void init() {
        block_hops = new BlockDefinition( new BlockHopsTrunk("hops_vine") );
        hops_bush = new BlockDefinition( new BlockHopsBush() );
    }

    public static void register() {
        // registerBlock(block_hops, false, true);
    	block_hops.register(true);
        // registerBlock(hops_bush, false, true);
    	hops_bush.register(true);
    }

    public static void  registerRenders() {
        // registerRender(block_hops);
    	block_hops.registerRender();
        // registerRender(hops_bush);
    	hops_bush.registerRender();
    }

/*    public static void registerBlock(Block block, boolean setCreativeTab, boolean registerItemBlock ) {
        GameRegistry.register(block);

        if(setCreativeTab) {
            block.setCreativeTab(tabGrowthcraft);
        }
        if(registerItemBlock) {
            GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }

    public static void registerRender(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(
                new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void  registerRender(Block block, int meta, String fileName){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta,
                new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    } */

}
