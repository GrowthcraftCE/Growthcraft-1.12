package growthcraft.bamboo.common;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;

import java.io.File;

import org.apache.logging.log4j.Level;

import growthcraft.bamboo.GrowthcraftBamboo;
import growthcraft.bamboo.client.handler.ColorHandlerBlockBambooLeaves;
import growthcraft.bamboo.common.block.BlockBambooDoor;
import growthcraft.bamboo.common.block.BlockBambooFence;
import growthcraft.bamboo.common.block.BlockBambooFenceGate;
import growthcraft.bamboo.common.block.BlockBambooLeaves;
import growthcraft.bamboo.common.block.BlockBambooPlank;
import growthcraft.bamboo.common.block.BlockBambooShoot;
import growthcraft.bamboo.common.block.BlockBambooSlabDouble;
import growthcraft.bamboo.common.block.BlockBambooSlabHalf;
import growthcraft.bamboo.common.block.BlockBambooStairs;
import growthcraft.bamboo.common.block.BlockBambooStalk;
import growthcraft.bamboo.common.item.ItemBambooCoal;
import growthcraft.bamboo.common.item.ItemBambooDoor;
import growthcraft.bamboo.common.item.ItemBambooStick;
import growthcraft.bamboo.shared.Reference;
import growthcraft.bamboo.shared.config.GrowthcraftBambooConfig;
import growthcraft.bamboo.shared.init.GrowthcraftBambooBlocks;
import growthcraft.bamboo.shared.init.GrowthcraftBambooItems;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.core.shared.definition.BlockTypeDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.item.ItemSlab;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Init {
	private Init() {}
	
	private static Configuration configuration;
	
	////////
	// Blocks
	////////
	
    public static void initBlocks() {
    	GrowthcraftBambooBlocks.bambooPlank = new BlockDefinition( new BlockBambooPlank() );
    	GrowthcraftBambooBlocks.bambooSlabHalf = new BlockTypeDefinition<BlockSlab>( new BlockBambooSlabHalf("bamboo_slab_half") );
    	GrowthcraftBambooBlocks.bambooSlabDouble = new BlockTypeDefinition<BlockSlab>( new BlockBambooSlabDouble("bamboo_slab_double" ) );
    	GrowthcraftBambooBlocks.bambooStairs = new BlockDefinition( new BlockBambooStairs("bamboo_stairs", GrowthcraftBambooBlocks.bambooPlank.getDefaultState()) );
    	GrowthcraftBambooBlocks.bambooFence = new BlockDefinition( new BlockBambooFence("bamboo_fence") );
    	GrowthcraftBambooBlocks.bambooFenceGate = new BlockDefinition( new BlockBambooFenceGate("bamboo_fence_gate") );
    	GrowthcraftBambooBlocks.bambooLeaves = new BlockDefinition( new BlockBambooLeaves("bamboo_leaves") );
    	GrowthcraftBambooBlocks.bambooStalk = new BlockDefinition( new BlockBambooStalk("bamboo_stalk") );
    	GrowthcraftBambooBlocks.bambooShoot = new BlockDefinition( new BlockBambooShoot("bamboo_shoot") );
    	GrowthcraftBambooBlocks.blockBambooDoor = new BlockDefinition( new BlockBambooDoor("bamboo_door") );
    }

    public static void registerBlocks() {
    	GrowthcraftBambooBlocks.bambooPlank.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftBambooBlocks.bambooPlank.register(true);
    	GrowthcraftBambooBlocks.bambooSlabHalf.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftBambooBlocks.bambooSlabHalf.register(new ItemSlab(GrowthcraftBambooBlocks.bambooSlabHalf.getBlock(), GrowthcraftBambooBlocks.bambooSlabHalf.getBlock(), GrowthcraftBambooBlocks.bambooSlabDouble.getBlock()));
    	GrowthcraftBambooBlocks.bambooSlabDouble.register(false);
    	GrowthcraftBambooBlocks.bambooStairs.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftBambooBlocks.bambooStairs.register(true);
    	GrowthcraftBambooBlocks.bambooFence.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftBambooBlocks.bambooFence.register(true);
    	GrowthcraftBambooBlocks.bambooFenceGate.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftBambooBlocks.bambooFenceGate.register(true);
    	GrowthcraftBambooBlocks.bambooLeaves.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftBambooBlocks.bambooLeaves.register(true);
    	GrowthcraftBambooBlocks.bambooStalk.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftBambooBlocks.bambooStalk.register(true);
    	GrowthcraftBambooBlocks.bambooShoot.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftBambooBlocks.bambooShoot.register(true);
    	GrowthcraftBambooBlocks.blockBambooDoor.register(false);
    }

    public static void registerBlockRenders() {
    	GrowthcraftBambooBlocks.bambooPlank.registerItemRender();
    	GrowthcraftBambooBlocks.bambooSlabHalf.registerItemRender();
    	GrowthcraftBambooBlocks.bambooStairs.registerItemRender();
    	GrowthcraftBambooBlocks.bambooFence.registerItemRender();
    	GrowthcraftBambooBlocks.bambooFenceGate.registerItemRender();
    	GrowthcraftBambooBlocks.bambooLeaves.registerItemRender();
    	GrowthcraftBambooBlocks.bambooStalk.registerItemRender();
    	GrowthcraftBambooBlocks.bambooShoot.registerItemRender();
    	GrowthcraftBambooBlocks.blockBambooDoor.registerItemRender();
    }

    public static void registerBlockColorHandlers() {
        registerBlockColorHandler(GrowthcraftBambooBlocks.blockBambooDoor.getBlock());
    }

    /*
     * Credit to CJMinecraft for identifying how to ignore properties.
     */
    @SideOnly(Side.CLIENT)
    public static void setBlockCustomStateMappers() {
        ModelLoader.setCustomStateMapper(GrowthcraftBambooBlocks.bambooFenceGate.getBlock(), (new StateMap.Builder().ignore(BlockFenceGate.POWERED)).build());
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockColorHandler(Block block) {
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        blockColors.registerBlockColorHandler(new ColorHandlerBlockBambooLeaves(), block);
    }	
	
	////////
	// Items
	////////
	
    public static void initItems() {
    	GrowthcraftBambooItems.bambooStick = new ItemDefinition( new ItemBambooStick("bamboo_stick") );
    	GrowthcraftBambooItems.bambooCoal = new ItemDefinition( new ItemBambooCoal("bamboo_coal") );
    	GrowthcraftBambooItems.itemBambooDoor = new ItemDefinition( new ItemBambooDoor("bamboo_door_item", GrowthcraftBambooBlocks.blockBambooDoor.getBlock()) );
    }

    public static void registerItems() {
    	GrowthcraftBambooItems.bambooStick.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftBambooItems.bambooStick.register();
    	GrowthcraftBambooItems.bambooCoal.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftBambooItems.bambooCoal.register();
    	GrowthcraftBambooItems.itemBambooDoor.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftBambooItems.itemBambooDoor.register();
    }

    public static void registerItemRenders() {
    	GrowthcraftBambooItems.bambooStick.registerRender();
    	GrowthcraftBambooItems.bambooCoal.registerRender();
    	GrowthcraftBambooItems.itemBambooDoor.registerRender();
    }
    
	////////
	// Recipes
	////////
	
    public static void registerRecipes() {
        registerCraftingRecipes();
    }

    private static void registerCraftingRecipes() {

    }
    
    // Config
    
    public void preInitConfig(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        configuration = new Configuration(new File(directory.getPath(), "modtut.cfg"));
        readConfig();
    }

    private static void readConfig() {
        Configuration configuration = GrowthcraftBamboo.configuration;
        try {
            configuration.load();
            initDebugConfig(configuration);
        } catch (Exception e) {
            GrowthcraftLogger.getLogger(Reference.MODID).log(Level.ERROR, "Unable to load configuration files for Growthcraft Bamboo!", e);
        } finally {
            if ( configuration.hasChanged() ) {
                configuration.save();
            }
        }
    }

    private static void initDebugConfig(Configuration configuration) {
        GrowthcraftBambooConfig.logLevel = configuration.getString("logLevel", GrowthcraftBambooConfig.CATEGORY_GENERAL, GrowthcraftBambooConfig.logLevel, "Set standard logging levels. (INFO, ERROR, DEBUG)");
    }
}
