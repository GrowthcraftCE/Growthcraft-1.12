package growthcraft.apples.common;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;

import growthcraft.apples.client.handlers.GrowthcraftApplesColorHandler;
import growthcraft.apples.common.block.BlockApple;
import growthcraft.apples.common.block.BlockAppleDoor;
import growthcraft.apples.common.block.BlockAppleFence;
import growthcraft.apples.common.block.BlockAppleFenceGate;
import growthcraft.apples.common.block.BlockAppleLeaves;
import growthcraft.apples.common.block.BlockAppleLog;
import growthcraft.apples.common.block.BlockApplePlanks;
import growthcraft.apples.common.block.BlockAppleSapling;
import growthcraft.apples.common.block.BlockAppleSlabDouble;
import growthcraft.apples.common.block.BlockAppleSlabHalf;
import growthcraft.apples.common.block.BlockAppleStairs;
import growthcraft.apples.common.item.ItemAppleDoor;
import growthcraft.apples.shared.init.GrowthcraftApplesBlocks;
import growthcraft.apples.shared.init.GrowthcraftApplesItems;
import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.core.shared.definition.BlockTypeDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class Init {
	private Init() {}
	
	////////
	// Blocks
	////////
	
    public static void preInitBlocks() {
    	GrowthcraftApplesBlocks.blockApple = new BlockDefinition( new BlockApple("apple_crop") );
    	GrowthcraftApplesBlocks.blockAppleDoor = new BlockDefinition( new BlockAppleDoor("apple_door") );
    	GrowthcraftApplesBlocks.blockAppleFence = new BlockDefinition( new BlockAppleFence("apple_fence") );
    	GrowthcraftApplesBlocks.blockAppleFenceGate = new BlockDefinition( new BlockAppleFenceGate("apple_fence_gate") );
    	GrowthcraftApplesBlocks.blockAppleLeaves = new BlockDefinition( new BlockAppleLeaves("apple_leaves") );
    	GrowthcraftApplesBlocks.blockAppleLog = new BlockDefinition( new BlockAppleLog("apple_log") );
    	GrowthcraftApplesBlocks.blockApplePlanks = new BlockDefinition( new BlockApplePlanks("apple_planks") );
    	GrowthcraftApplesBlocks.blockAppleSapling = new BlockDefinition( new BlockAppleSapling("apple_sapling") );
    	GrowthcraftApplesBlocks.blockAppleSlabHalf = new BlockTypeDefinition<BlockSlab>( new BlockAppleSlabHalf("apple_slab_half") );
    	GrowthcraftApplesBlocks.blockAppleSlabDouble = new BlockTypeDefinition<BlockSlab>( new BlockAppleSlabDouble("apple_slab_double") );
    	GrowthcraftApplesBlocks.blockAppleStairs = new BlockDefinition( new BlockAppleStairs("apple_stairs", GrowthcraftApplesBlocks.blockApplePlanks.getDefaultState()) );
    }

    public static void registerBlocks() {
    	GrowthcraftApplesBlocks.blockApple.register(false);
    	GrowthcraftApplesBlocks.blockAppleDoor.register(false);
    	GrowthcraftApplesBlocks.blockAppleFence.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockAppleFence.register(true);
    	GrowthcraftApplesBlocks.blockAppleFenceGate.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockAppleFenceGate.register(true);
    	GrowthcraftApplesBlocks.blockAppleLeaves.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockAppleLeaves.register(true);
    	GrowthcraftApplesBlocks.blockAppleLog.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockAppleLog.register(true);
    	GrowthcraftApplesBlocks.blockApplePlanks.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockApplePlanks.register(true);
    	GrowthcraftApplesBlocks.blockAppleSapling.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockAppleSapling.register(true);
    	GrowthcraftApplesBlocks.blockAppleSlabHalf.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockAppleSlabHalf.register(new ItemSlab(GrowthcraftApplesBlocks.blockAppleSlabHalf.getBlock(),
    			                                                         GrowthcraftApplesBlocks.blockAppleSlabHalf.getBlock(),
    			                                                         GrowthcraftApplesBlocks.blockAppleSlabDouble.getBlock()));
    	GrowthcraftApplesBlocks.blockAppleSlabDouble.register(false);
    	GrowthcraftApplesBlocks.blockAppleStairs.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockAppleStairs.register(true);
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockRenders() {
    	GrowthcraftApplesBlocks.blockApple.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleDoor.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleFence.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleFenceGate.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleLeaves.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleLog.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleSapling.registerItemRender();
    	GrowthcraftApplesBlocks.blockApplePlanks.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleStairs.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleSlabHalf.registerItemRender();
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockColorHandlers() {
        registerBlockColorHandler(GrowthcraftApplesBlocks.blockAppleLeaves.getBlock());
    }

    /*
     * Credit to CJMinecraft for identifying how to ignore properties.
     */
    @SideOnly(Side.CLIENT)
    public static void setCustomBlockStateMappers() {
        ModelLoader.setCustomStateMapper(GrowthcraftApplesBlocks.blockAppleFenceGate.getBlock(), (new StateMap.Builder().ignore(BlockFenceGate.POWERED)).build());
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockColorHandler(Block block) {
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        blockColors.registerBlockColorHandler(new GrowthcraftApplesColorHandler(), block);
    }


	////////
	// Items
	////////
	
    public static void preInitItems() {
    	GrowthcraftApplesItems.itemAppleDoor = new ItemDefinition( new ItemAppleDoor("apple_door_item", GrowthcraftApplesBlocks.blockAppleDoor.getBlock()) );
    }
    
	private static void registerItemOres()
	{
		OreDictionary.registerOre("foodApple", Items.APPLE);
		OreDictionary.registerOre("foodFruit", Items.APPLE);
	}

    public static void registerItems() {
    	GrowthcraftApplesItems.itemAppleDoor.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesItems.itemAppleDoor.register();
    	
    	registerItemOres();
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemRenders() {
    	GrowthcraftApplesItems.itemAppleDoor.registerRender();
    }
    
	////////
	// Recipes
	////////
    
    public static void registerRecipes() {
        registerCraftingRecipes();
    }

    private static void registerCraftingRecipes() {

        GameRegistry.addRecipe(
                GrowthcraftApplesBlocks.blockApplePlanks.asStack(4),
                "W",
                'W', GrowthcraftApplesBlocks.blockAppleLog.getBlock());

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesBlocks.blockAppleStairs.asStack(4),
                "  P", " PP", "PPP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock());

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesBlocks.blockAppleStairs.asStack(4),
                "P  ", "PP ", "PPP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock());

        GameRegistry.addRecipe(
        		GrowthcraftApplesBlocks.blockAppleSlabHalf.asStack(4),
                "PPP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock());

        GameRegistry.addShapedRecipe(
                new ItemStack(Blocks.CHEST, 1),
                "PPP", "P P", "PPP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock());

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesBlocks.blockAppleFence.asStack(3),
                "PSP", "PSP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock(),
                'S', new ItemStack(Items.STICK, 1));

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesBlocks.blockAppleFenceGate.asStack(1),
                "SPS", "SPS",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock(),
                'S', new ItemStack(Items.STICK, 1));

        GameRegistry.addShapedRecipe(
                new ItemStack(Items.STICK, 4),
                "P", "P",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock() );

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesItems.itemAppleDoor.asStack(1),
                "PP ", "PP ", "PP ",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock()
        );

        GameRegistry.addShapelessRecipe(
        		GrowthcraftApplesBlocks.blockAppleSapling.asStack(1),
                new ItemStack(Items.APPLE, 1),
                new ItemStack(Blocks.SAPLING, 1),
                new ItemStack(Items.WOODEN_SWORD, 1) );

    }


}
