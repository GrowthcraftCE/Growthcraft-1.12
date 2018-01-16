package growthcraft.milk.init;

import growthcraft.milk.Reference;
import growthcraft.milk.blocks.*;
import growthcraft.milk.client.render.RenderPancheon;
import growthcraft.milk.common.block.BlockButterChurn;
import growthcraft.milk.common.block.BlockCheeseBlock;
import growthcraft.milk.common.block.PREVBlockPancheon;
import growthcraft.milk.common.tileentity.TileEntityButterChurn;
import growthcraft.milk.common.tileentity.TileEntityCheeseBlock;
import growthcraft.milk.common.tileentity.TileEntityPancheon;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

import growthcraft.core.common.definition.BlockDefinition;
import growthcraft.core.common.definition.GrowthcraftBlockFluidDefinition;

public class GrowthcraftMilkBlocks {

    public static BlockDefinition thistle;
    public static BlockDefinition cheeseVat;
    public static GrowthcraftBlockFluidDefinition blockFluidMilk;
    public static GrowthcraftBlockFluidDefinition blockFluidRennet;
    public static GrowthcraftBlockFluidDefinition blockFluidButterMilk;
    public static GrowthcraftBlockFluidDefinition blockFluidCream;
    public static GrowthcraftBlockFluidDefinition blockFluidMilkCurds;
    public static GrowthcraftBlockFluidDefinition blockFluidPasteurizedMilk;
    public static GrowthcraftBlockFluidDefinition blockFluidSkimMilk;
    public static GrowthcraftBlockFluidDefinition blockFluidWhey;
    public static BlockDefinition pancheon;
    public static BlockDefinition churn;
    public static BlockDefinition agedCheeseBlock;

    // TODO: BlockCheese
    // TODO: BlockCheesePress
    // TODO: BlockCheeseCurds

    public static void preInit() {
        thistle = new BlockDefinition( new BlockThistle("thistle") );
        cheeseVat = new BlockDefinition( new BlockCheeseVat("cheese_vat") );
        blockFluidMilk = GrowthcraftMilkFluids.milk.getFluidBlockDefinition(); // new GrowthcraftBlockFluidDefinition( new BlockFluidMilk("fluid_milk") );
        blockFluidRennet = GrowthcraftMilkFluids.rennet.getFluidBlockDefinition(); // new BlockFluidRennet("fluid_rennet");
        blockFluidButterMilk = GrowthcraftMilkFluids.butterMilk.getFluidBlockDefinition(); // new BlockFluidButterMilk("fluid_butter_milk");
        blockFluidCream = GrowthcraftMilkFluids.cream.getFluidBlockDefinition(); // new BlockFluidCream("fluid_cream");
        blockFluidMilkCurds = GrowthcraftMilkFluids.curds.getFluidBlockDefinition(); // new BlockFluidMilkCurds("fluid_milk_curds");
        blockFluidPasteurizedMilk = GrowthcraftMilkFluids.pasteurizedMilk.getFluidBlockDefinition(); // new BlockFluidPasteurizedMilk("fluid_pasteurized_milk");
        blockFluidSkimMilk = GrowthcraftMilkFluids.skimMilk.getFluidBlockDefinition(); // new BlockFluidSkimMilk("fluid_skim_milk");
        blockFluidWhey = GrowthcraftMilkFluids.whey.getFluidBlockDefinition(); // new BlockFluidWhey("fluid_whey");
        pancheon = new BlockDefinition( new PREVBlockPancheon("pancheon") );
        churn = new BlockDefinition( new BlockButterChurn("churn") );
        agedCheeseBlock = new BlockDefinition( new BlockCheeseBlock() );
    }

    public static void register() {
        // registerBlock(thistle, false, false);
    	thistle.register(false);
        // registerBlock(blockCheeseVat, true, true);
    	cheeseVat.getBlock().setCreativeTab(tabGrowthcraft);
    	cheeseVat.register(true);
        //registerBlock(blockPancheon, true, true);
        pancheon.getBlock().setCreativeTab(tabGrowthcraft);
        pancheon.register(true);
        churn.getBlock().setCreativeTab(tabGrowthcraft);
        churn.register(true);
        agedCheeseBlock.register(new ResourceLocation(Reference.MODID, "cheese_aged"), false);
    }
    
    public static void registerRenders() {
        // registerRender(thistle);
    	thistle.registerRender();
        // registerRender(blockCheeseVat);
    	cheeseVat.registerRender();
        // registerRender(blockPancheon);
        pancheon.registerRender();
        churn.registerRender();
        agedCheeseBlock.registerRender();
    }
    
    public static void registerTileEntities() {
    	GameRegistry.registerTileEntity(TileEntityPancheon.class, Reference.MODID + ":pancheon");
    	GameRegistry.registerTileEntity(TileEntityButterChurn.class, Reference.MODID + ":churn");
    	GameRegistry.registerTileEntity(TileEntityCheeseBlock.class, Reference.MODID + ":cheese_aged");
    }
    
    public static void registerSpecialRenders() {
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPancheon.class, new RenderPancheon());
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
