package growthcraft.bamboo.init;

import growthcraft.bamboo.Reference;
import growthcraft.bamboo.blocks.BlockBambooPlank;
import growthcraft.bamboo.blocks.BlockBambooSlabDouble;
import growthcraft.bamboo.blocks.BlockBambooSlabHalf;
import growthcraft.bamboo.blocks.BlockBambooStairs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftBambooBlocks {

    public static BlockBambooPlank bambooPlank;
    public static BlockBambooSlabHalf bambooSlabHalf;
    public static BlockBambooSlabDouble bambooSlabDouble;
    public static BlockBambooStairs bambooStairs;

    public static void init() {
        bambooPlank = new BlockBambooPlank();
        bambooSlabHalf = new BlockBambooSlabHalf("bamboo_slab_half");
        bambooSlabDouble = new BlockBambooSlabDouble("bamboo_slab_double" );
        bambooStairs = new BlockBambooStairs("bamboo_stairs", bambooPlank.getDefaultState());
    }

    public static void register() {
        registerBlock(bambooPlank);
        registerBlock(bambooSlabHalf, new ItemSlab(bambooSlabHalf, bambooSlabHalf, bambooSlabDouble));
        registerBlock(bambooSlabDouble, false, false);
        registerBlock(bambooStairs);
    }

    public static void  registerRenders() {
        registerRender(bambooPlank);
        registerRender(bambooSlabHalf);
        registerRender(bambooStairs);
    }

    public static void registerBlock(Block block) {
        registerBlock(block, true, true);
    }

    public static void registerBlock(Block block, ItemBlock itemBlock) {
        block.setCreativeTab(tabGrowthcraft);
        GameRegistry.register(block);
        GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
    }

    public static void registerBlock(Block block, boolean setCreativeTab, boolean registerItemBlock ) {
        GameRegistry.register(block);

        if(setCreativeTab) {
            block.setCreativeTab(tabGrowthcraft);
        }
        if(registerItemBlock) {
            GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }

    public static void registerRender(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
                new ModelResourceLocation(new ResourceLocation(Reference.MODID,
                        block.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void  registerRender(Block block, int meta, String fileName){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta,
                new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }
}
