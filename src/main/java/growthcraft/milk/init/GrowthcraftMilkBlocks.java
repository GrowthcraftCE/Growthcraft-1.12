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
import net.minecraftforge.oredict.OreDictionary;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

import growthcraft.core.common.definition.BlockDefinition;
import growthcraft.core.common.definition.GrowthcraftBlockFluidDefinition;

public class GrowthcraftMilkBlocks {

    public static BlockDefinition thistle;
    public static Block blockCheeseVat;
    public static GrowthcraftBlockFluidDefinition blockFluidMilk;
    public static GrowthcraftBlockFluidDefinition blockFluidRennet;
    public static GrowthcraftBlockFluidDefinition blockFluidButterMilk;
    public static GrowthcraftBlockFluidDefinition blockFluidCream;
    public static GrowthcraftBlockFluidDefinition blockFluidMilkCurds;
    public static GrowthcraftBlockFluidDefinition blockFluidPasteurizedMilk;
    public static GrowthcraftBlockFluidDefinition blockFluidSkimMilk;
    public static GrowthcraftBlockFluidDefinition blockFluidWhey;
    public static BlockPancheon blockPancheon;

    // TODO: BlockButterChurn
    // TODO: BlockCheese
    // TODO: BlockCheesePress
    // TODO: BlockCheeseCurds

    public static void init() {
        thistle = new BlockDefinition( new BlockThistle("thistle") );
        blockCheeseVat = new BlockCheeseVat("cheese_vat");
        blockFluidMilk = GrowthcraftMilkFluids.milk.getFluidBlockDefinition(); // new GrowthcraftBlockFluidDefinition( new BlockFluidMilk("fluid_milk") );
        blockFluidRennet = GrowthcraftMilkFluids.rennet.getFluidBlockDefinition(); // new BlockFluidRennet("fluid_rennet");
        blockFluidButterMilk = GrowthcraftMilkFluids.butterMilk.getFluidBlockDefinition(); // new BlockFluidButterMilk("fluid_butter_milk");
        blockFluidCream = GrowthcraftMilkFluids.cream.getFluidBlockDefinition(); // new BlockFluidCream("fluid_cream");
        blockFluidMilkCurds = GrowthcraftMilkFluids.curds.getFluidBlockDefinition(); // new BlockFluidMilkCurds("fluid_milk_curds");
        blockFluidPasteurizedMilk = GrowthcraftMilkFluids.pasteurizedMilk.getFluidBlockDefinition(); // new BlockFluidPasteurizedMilk("fluid_pasteurized_milk");
        blockFluidSkimMilk = GrowthcraftMilkFluids.skimMilk.getFluidBlockDefinition(); // new BlockFluidSkimMilk("fluid_skim_milk");
        blockFluidWhey = GrowthcraftMilkFluids.whey.getFluidBlockDefinition(); // new BlockFluidWhey("fluid_whey");
        blockPancheon = new BlockPancheon("pancheon");
    }

    public static void register() {
        // registerBlock(thistle, false, false);
    	thistle.register(false);
        registerBlock(blockCheeseVat, true, true);
/*        registerBlock(blockFluidMilk, true, false);
        registerBlock(blockFluidRennet, true, false);
        registerBlock(blockFluidButterMilk, true, false);
        registerBlock(blockFluidCream, true, false);
        registerBlock(blockFluidMilkCurds, true, false);
        registerBlock(blockFluidPasteurizedMilk, true, false);
        registerBlock(blockFluidSkimMilk, true, false);
        registerBlock(blockFluidWhey, true, false); */
        registerBlock(blockPancheon, true, true);
    }
    
    public static void  registerRenders() {
        // registerRender(thistle);
    	thistle.registerRender();
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
