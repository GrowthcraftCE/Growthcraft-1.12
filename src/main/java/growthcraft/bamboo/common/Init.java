package growthcraft.bamboo.common;

import growthcraft.bamboo.GrowthcraftBamboo;
import growthcraft.bamboo.client.handler.ColorHandlerBlockBambooLeaves;
import growthcraft.bamboo.common.block.*;
import growthcraft.bamboo.common.item.ItemBambooCoal;
import growthcraft.bamboo.common.item.ItemBambooDoor;
import growthcraft.bamboo.common.item.ItemBambooStick;
import growthcraft.bamboo.shared.Reference;
import growthcraft.bamboo.shared.config.GrowthcraftBambooConfig;
import growthcraft.bamboo.shared.init.GrowthcraftBambooBlocks;
import growthcraft.bamboo.shared.init.GrowthcraftBambooItems;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.common.block.BlockRopeKnot;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.core.shared.definition.BlockTypeDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.init.GrowthcraftCoreBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Level;

import java.io.File;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;

public class Init {
    private Init() {
    }

    private static Configuration configuration;

    ////////
    // Blocks
    ////////

    public static void preInitBlocks() {
        GrowthcraftBambooBlocks.bambooPlank = new BlockDefinition(new BlockBambooPlank());
        GrowthcraftBambooBlocks.bambooSlabHalf = new BlockTypeDefinition<BlockSlab>(new BlockBambooSlabHalf("bamboo_slab_half"));
        GrowthcraftBambooBlocks.bambooSlabDouble = new BlockTypeDefinition<BlockSlab>(new BlockBambooSlabDouble("bamboo_slab_double"));
        GrowthcraftBambooBlocks.bambooStairs = new BlockDefinition(new BlockBambooStairs("bamboo_stairs", GrowthcraftBambooBlocks.bambooPlank.getDefaultState()));
        GrowthcraftBambooBlocks.bambooFence = new BlockDefinition(new BlockBambooFence("bamboo_fence"));
        GrowthcraftBambooBlocks.bambooFenceGate = new BlockDefinition(new BlockBambooFenceGate("bamboo_fence_gate"));
        GrowthcraftBambooBlocks.bambooLeaves = new BlockDefinition(new BlockBambooLeaves("bamboo_leaves"));
        GrowthcraftBambooBlocks.bambooStalk = new BlockDefinition(new BlockBambooStalk("bamboo_stalk"));
        GrowthcraftBambooBlocks.bambooShoot = new BlockDefinition(new BlockBambooShoot("bamboo_shoot"));
        GrowthcraftBambooBlocks.blockBambooDoor = new BlockDefinition(new BlockBambooDoor("bamboo_door"));
        GrowthcraftBambooBlocks.ropeKnotBamboo = new BlockDefinition(new BlockBambooFenceRopeKnot("rope_knot_bamboo", GrowthcraftBambooBlocks.bambooFence.getBlock()));
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        GrowthcraftBambooBlocks.bambooPlank.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftBambooBlocks.bambooPlank.registerBlock(registry);
        GrowthcraftBambooBlocks.bambooSlabHalf.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftBambooBlocks.bambooSlabHalf.registerBlock(registry);
        GrowthcraftBambooBlocks.bambooSlabDouble.registerBlock(registry);
        GrowthcraftBambooBlocks.bambooStairs.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftBambooBlocks.bambooStairs.registerBlock(registry);
        GrowthcraftBambooBlocks.bambooFence.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftBambooBlocks.bambooFence.registerBlock(registry);
        GrowthcraftBambooBlocks.bambooFenceGate.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftBambooBlocks.bambooFenceGate.registerBlock(registry);
        GrowthcraftBambooBlocks.bambooLeaves.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftBambooBlocks.bambooLeaves.registerBlock(registry);
        GrowthcraftBambooBlocks.bambooStalk.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftBambooBlocks.bambooStalk.registerBlock(registry);
        GrowthcraftBambooBlocks.bambooShoot.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftBambooBlocks.bambooShoot.registerBlock(registry);
        GrowthcraftBambooBlocks.blockBambooDoor.registerBlock(registry);
        GrowthcraftBambooBlocks.ropeKnotBamboo.registerBlock(registry);
    }

    public static void registerBlockItems(IForgeRegistry<Item> registry) {
        GrowthcraftBambooBlocks.bambooPlank.registerBlockItem(registry);
        GrowthcraftBambooBlocks.bambooSlabHalf.registerBlockItem(registry,
                new ItemSlab(GrowthcraftBambooBlocks.bambooSlabHalf.getBlock(),
                        GrowthcraftBambooBlocks.bambooSlabHalf.getBlock(),
                        GrowthcraftBambooBlocks.bambooSlabDouble.getBlock()));
        GrowthcraftBambooBlocks.bambooStairs.registerBlockItem(registry);
        GrowthcraftBambooBlocks.bambooFence.registerBlockItem(registry);
        GrowthcraftBambooBlocks.bambooFenceGate.registerBlockItem(registry);
        GrowthcraftBambooBlocks.bambooLeaves.registerBlockItem(registry);
        GrowthcraftBambooBlocks.bambooStalk.registerBlockItem(registry);
        GrowthcraftBambooBlocks.bambooShoot.registerBlockItem(registry);
        GrowthcraftBambooBlocks.ropeKnotBamboo.registerBlockItem(registry);
    }

    public static void registerBlockOres() {
        OreDictionary.registerOre("plankWood", GrowthcraftBambooBlocks.bambooPlank.getItem());
        OreDictionary.registerOre(GrowthcraftCore.ORE_ROPE_KNOT_FENCE, GrowthcraftBambooBlocks.ropeKnotBamboo.getBlock());
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
        GrowthcraftBambooBlocks.ropeKnotBamboo.registerItemRender();
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

    public static void preInitItems() {
        GrowthcraftBambooItems.bambooStick = new ItemDefinition(new ItemBambooStick("bamboo_stick"));
        GrowthcraftBambooItems.bambooCoal = new ItemDefinition(new ItemBambooCoal("bamboo_coal"));
        GrowthcraftBambooItems.itemBambooDoor = new ItemDefinition(new ItemBambooDoor("bamboo_door_item", GrowthcraftBambooBlocks.blockBambooDoor.getBlock()));
    }

    public static void registerItemOres() {
        OreDictionary.registerOre("stickWood", GrowthcraftBambooItems.bambooStick.getItem());
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        GrowthcraftBambooItems.bambooStick.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftBambooItems.bambooStick.registerItem(registry);
        GrowthcraftBambooItems.bambooCoal.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftBambooItems.bambooCoal.registerItem(registry);
        GrowthcraftBambooItems.itemBambooDoor.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftBambooItems.itemBambooDoor.registerItem(registry);
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

    public static void registerSmeltingRecipes() {
        GameRegistry.addSmelting(GrowthcraftBambooBlocks.bambooStalk.getItemAsStack(1), GrowthcraftBambooItems.bambooCoal.asStack(1), 0.4f);
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
            if (configuration.hasChanged()) {
                configuration.save();
            }
        }
    }

    private static void initDebugConfig(Configuration configuration) {
        GrowthcraftBambooConfig.logLevel = configuration.getString("logLevel", GrowthcraftBambooConfig.CATEGORY_GENERAL, GrowthcraftBambooConfig.logLevel, "Set standard logging levels. (INFO, ERROR, DEBUG)");
    }
}
