package growthcraft.milk.init;

import growthcraft.milk.Reference;
import growthcraft.milk.blocks.*;
import growthcraft.milk.client.render.RenderCheeseVat;
import growthcraft.milk.client.render.RenderPancheon;
import growthcraft.milk.common.block.BlockButterChurn;
import growthcraft.milk.common.block.BlockCheeseBlock;
import growthcraft.milk.common.block.BlockHangingCurds;
import growthcraft.milk.common.block.PREVBlockCheeseVat;
import growthcraft.milk.common.block.PREVBlockPancheon;
import growthcraft.milk.common.tileentity.TileEntityButterChurn;
import growthcraft.milk.common.tileentity.TileEntityCheeseBlock;
import growthcraft.milk.common.tileentity.TileEntityCheeseVat;
import growthcraft.milk.common.tileentity.TileEntityHangingCurds;
import growthcraft.milk.common.tileentity.TileEntityPancheon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
    public static BlockDefinition waxedCheeseBlock;
    public static BlockDefinition agedCheeseCurds;
    public static BlockDefinition waxedCheeseCurds;
    public static BlockDefinition simpleCheeseCurds;

    // TODO: BlockCheesePress

    public static void preInit() {
        thistle = new BlockDefinition( new BlockThistle("thistle") );
        cheeseVat = new BlockDefinition( new PREVBlockCheeseVat("cheese_vat") );
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
        waxedCheeseBlock = new BlockDefinition( new BlockCheeseBlock() );
        agedCheeseCurds = new BlockDefinition(new BlockHangingCurds());
        waxedCheeseCurds = new BlockDefinition(new BlockHangingCurds());
        simpleCheeseCurds = new BlockDefinition(new BlockHangingCurds());
    }

    public static void register() {
    	thistle.register(false);
    	cheeseVat.getBlock().setCreativeTab(tabGrowthcraft);
    	cheeseVat.register(true);
        pancheon.getBlock().setCreativeTab(tabGrowthcraft);
        pancheon.register(true);
        churn.getBlock().setCreativeTab(tabGrowthcraft);
        churn.register(true);
        agedCheeseBlock.getBlock().setCreativeTab(tabGrowthcraft);
        agedCheeseBlock.register(new ResourceLocation(Reference.MODID, "cheese_aged"), false);
        waxedCheeseBlock.getBlock().setCreativeTab(tabGrowthcraft);
        waxedCheeseBlock.register(new ResourceLocation(Reference.MODID, "cheese_waxed"), false);
        agedCheeseCurds.getBlock().setCreativeTab(tabGrowthcraft);
        agedCheeseCurds.register(new ResourceLocation(Reference.MODID, "curds_aged"), false);
        waxedCheeseCurds.getBlock().setCreativeTab(tabGrowthcraft);
        waxedCheeseCurds.register(new ResourceLocation(Reference.MODID, "curds_waxed"), false);
        simpleCheeseCurds.getBlock().setCreativeTab(tabGrowthcraft);
        simpleCheeseCurds.register(new ResourceLocation(Reference.MODID, "curds_simple"), false);
    }
    
    public static void registerRenders() {
    	thistle.registerRender();
    	cheeseVat.registerRender();
        pancheon.registerRender();
        churn.registerRender();
        agedCheeseBlock.registerRender();
        waxedCheeseBlock.registerRender();
        agedCheeseCurds.registerRender();
        waxedCheeseCurds.registerRender();
        simpleCheeseCurds.registerRender();
    }
    
    public static void registerTileEntities() {
    	GameRegistry.registerTileEntity(TileEntityPancheon.class, Reference.MODID + ":pancheon");
    	GameRegistry.registerTileEntity(TileEntityButterChurn.class, Reference.MODID + ":churn");
    	GameRegistry.registerTileEntity(TileEntityCheeseBlock.class, Reference.MODID + ":cheese_block");
    	GameRegistry.registerTileEntity(TileEntityHangingCurds.class, Reference.MODID + ":cheese_curds");
    }
    
    public static void registerSpecialRenders() {
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPancheon.class, new RenderPancheon());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCheeseVat.class, new RenderCheeseVat());
    }
}
