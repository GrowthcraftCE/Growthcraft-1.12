package growthcraft.core.init;

import growthcraft.core.Reference;
import growthcraft.core.blocks.BlockRopeFence;
import growthcraft.core.blocks.BlockRopeKnot;
import growthcraft.core.blocks.BlockSalt;
import growthcraft.core.common.definition.BlockDefinition;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftCoreBlocks {

    public static BlockDefinition salt_block;
    public static BlockDefinition rope_knot;
    public static BlockDefinition rope_fence;

    public static void init() {
        rope_fence = new BlockDefinition( new BlockRopeFence( "rope_fence" ) );
        salt_block = new BlockDefinition( new BlockSalt("salt_block") );
        rope_knot = new BlockDefinition( new BlockRopeKnot("rope_knot") );
    }

    public static void register() {
//        registerBlock(salt_block);
    	salt_block.getBlock().setCreativeTab(tabGrowthcraft);
    	salt_block.register(true);
    	// registerBlock(rope_fence, false, true);
    	rope_fence.register(true);
        // registerBlock(rope_knot, false, true);
    	rope_knot.register(true);
    }

    public static void  registerRenders() {
        // registerRender(salt_block);
    	salt_block.registerRender();
        // registerRender(rope_fence);
    	rope_fence.registerRender();
        // registerRender(rope_knot);
    	rope_knot.registerRender();
    }

/*    public static void registerBlock(Block block) {
        registerBlock(block, true, true);
    }

    public static void registerBlock(Block block, boolean setCreativeTab, ItemBlock itemBlock) {
        GameRegistry.register(block);

        if(setCreativeTab) {
            block.setCreativeTab(tabGrowthcraft);
        }

        GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
    }

    public static void registerBlock(Block block, boolean setCreativeTab, boolean registerItemBlock) {
        GameRegistry.register(block);

        if(setCreativeTab) {
            block.setCreativeTab(tabGrowthcraft);
        }
        if(registerItemBlock) {
            GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }

    public static void registerRender(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void  registerRender(Block block, int meta, String fileName){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    } */

}
