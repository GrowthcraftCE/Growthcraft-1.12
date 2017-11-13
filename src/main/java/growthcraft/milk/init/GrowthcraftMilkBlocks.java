package growthcraft.milk.init;

import growthcraft.milk.Reference;
import growthcraft.milk.blocks.BlockFluidMilk;
import growthcraft.milk.blocks.BlockFluidRennet;
import growthcraft.milk.blocks.BlockThistle;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftMilkBlocks {

    public static Block thistle;
    public static BlockFluidMilk blockFluidMilk;
    public static BlockFluidRennet blockFluidRennet;

    public static void init() {
        thistle = new BlockThistle("thistle");
        blockFluidMilk = new BlockFluidMilk("fluid_milk");
       blockFluidRennet = new BlockFluidRennet("fluid_rennet");
    }

    public static void register() {
        registerBlock(thistle, false, false);
        registerBlock(blockFluidMilk, true, true);
        registerBlock(blockFluidRennet, true, true);
    }

    public static void  registerRenders() {
        registerRender(thistle);
        //registerRender(blockFluidMilk);
        //registerRender(blockFluidRennet);
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

    public static void registerBlock(Block block, ItemBlock itemBlock) {
        block.setCreativeTab(tabGrowthcraft);
        GameRegistry.register(block);
        GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
    }

    public static void registerRender(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(
                new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void  registerRender(Block block, int meta, String fileName){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta,
                new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }



}
