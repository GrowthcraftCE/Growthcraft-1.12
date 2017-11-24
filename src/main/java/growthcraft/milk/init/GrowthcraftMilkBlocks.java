package growthcraft.milk.init;

import growthcraft.milk.Reference;
import growthcraft.milk.blocks.*;
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
    public static Block blockCheeseVat;
    public static BlockFluidMilk blockFluidMilk;
    public static BlockFluidRennet blockFluidRennet;
    public static BlockFluidButterMilk blockFluidButterMilk;
    public static BlockFluidCream blockFluidCream;
    public static BlockFluidMilkCurds blockFluidMilkCurds;
    public static BlockFluidPasteurizedMilk blockFluidPasteurizedMilk;
    public static BlockFluidSkimMilk blockFluidSkimMilk;
    public static BlockFluidWhey blockFluidWhey;
    public static BlockPancheon blockPancheon;

    // TODO: BlockButterChurn
    // TODO: BlockCheese
    // TODO: BlockCheesePress
    // TODO: BlockCheeseCurds

    public static void init() {
        thistle = new BlockThistle("thistle");
        blockCheeseVat = new BlockCheeseVat("cheese_vat");
        blockFluidMilk = new BlockFluidMilk("fluid_milk");
        blockFluidRennet = new BlockFluidRennet("fluid_rennet");
        blockFluidButterMilk = new BlockFluidButterMilk("fluid_butter_milk");
        blockFluidCream = new BlockFluidCream("fluid_cream");
        blockFluidMilkCurds = new BlockFluidMilkCurds("fluid_milk_curds");
        blockFluidPasteurizedMilk = new BlockFluidPasteurizedMilk("fluid_pasteurized_milk");
        blockFluidSkimMilk = new BlockFluidSkimMilk("fluid_skim_milk");
        blockFluidWhey = new BlockFluidWhey("fluid_whey");
        blockPancheon = new BlockPancheon("pancheon");
    }

    public static void register() {
        registerBlock(thistle, false, false);
        registerBlock(blockCheeseVat, true, true);
        registerBlock(blockFluidMilk, true, false);
        registerBlock(blockFluidRennet, true, false);
        registerBlock(blockFluidButterMilk, true, false);
        registerBlock(blockFluidCream, true, false);
        registerBlock(blockFluidMilkCurds, true, false);
        registerBlock(blockFluidPasteurizedMilk, true, false);
        registerBlock(blockFluidSkimMilk, true, false);
        registerBlock(blockFluidWhey, true, false);
        registerBlock(blockPancheon, true, true);
    }

    public static void  registerRenders() {
        registerRender(thistle);
        registerRender(blockCheeseVat);
        registerRender(blockPancheon);
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
