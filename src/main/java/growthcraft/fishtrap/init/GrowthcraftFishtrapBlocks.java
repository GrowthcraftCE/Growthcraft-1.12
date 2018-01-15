package growthcraft.fishtrap.init;

import growthcraft.fishtrap.Reference;
import growthcraft.fishtrap.blocks.BlockFishtrap;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

import growthcraft.core.common.definition.BlockDefinition;

public class GrowthcraftFishtrapBlocks {

    public static BlockDefinition fishtrap;

    public static void init() {
        fishtrap = new BlockDefinition( new BlockFishtrap("fishtrap") );

    }

    public static void register() {
        // registerBlock(fishtrap);
    	fishtrap.getBlock().setCreativeTab(tabGrowthcraft);
    	fishtrap.register(true);
    }

    public static void  registerRenders() {
        // registerRender(fishtrap);
    	fishtrap.registerRender();
    }
    
/*
    public static void registerBlock(Block block) {
        block.setCreativeTab(tabGrowthcraft);
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    public static void registerBlock(Block block, ItemBlock itemBlock) {
        block.setCreativeTab(tabGrowthcraft);
        GameRegistry.register(block);
        GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
    }

    public static void registerRender(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void  registerRender(Block block, int meta, String fileName){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }
*/

}
