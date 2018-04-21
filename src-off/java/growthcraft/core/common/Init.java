package growthcraft.core.common;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

import growthcraft.core.common.block.BlockRopeFence;
import growthcraft.core.common.block.BlockRopeKnot;
import growthcraft.core.common.block.BlockSalt;
import growthcraft.core.common.block.BlockSaltOre;
import growthcraft.core.common.item.ItemCrowbar;
import growthcraft.core.common.item.ItemRope;
import growthcraft.core.common.item.ItemSalt;
import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.init.GrowthcraftCoreBlocks;
import growthcraft.core.shared.init.GrowthcraftCoreItems;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class Init {

	//////////////
    // Blocks
    //////////////

    public static void preInitBlocks() {
        GrowthcraftCoreBlocks.rope_fence = new BlockDefinition( new BlockRopeFence( "rope_fence" ) );
        GrowthcraftCoreBlocks.salt_block = new BlockDefinition( new BlockSalt("salt_block") );
        GrowthcraftCoreBlocks.salt_ore = new BlockDefinition( new BlockSaltOre("salt_ore") );
        GrowthcraftCoreBlocks.rope_knot = new BlockDefinition( new BlockRopeKnot("rope_knot") );
    }
    
    private static void registerBlockOres() {
		OreDictionary.registerOre("oreSalt", GrowthcraftCoreBlocks.salt_ore.getBlock());
    }

    public static void registerBlocks() {
    	GrowthcraftCoreBlocks.salt_block.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftCoreBlocks.salt_block.register(true);
    	GrowthcraftCoreBlocks.rope_fence.register(false);
    	GrowthcraftCoreBlocks.rope_knot.register(false);
    	GrowthcraftCoreBlocks.salt_ore.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftCoreBlocks.salt_ore.register(true);
    	
    	registerBlockOres();
    }

    public static void registerBlockRenders() {
    	GrowthcraftCoreBlocks.salt_block.registerItemRender();
    	GrowthcraftCoreBlocks.salt_ore.registerItemRender();
    }

    //////////////
    // Items
    //////////////
    
    public static void preInitItems() {
        GrowthcraftCoreItems.crowbar = new ItemDefinition( new ItemCrowbar("crowbar") );
        GrowthcraftCoreItems.salt = new ItemDefinition( new ItemSalt("salt") );
        GrowthcraftCoreItems.rope = new ItemDefinition( new ItemRope("rope") );
    }

    public static void registerItems() {
    	GrowthcraftCoreItems.crowbar.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftCoreItems.crowbar.register();
    	GrowthcraftCoreItems.salt.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftCoreItems.salt.register();
    	GrowthcraftCoreItems.rope.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftCoreItems.rope.register();
        
        registerItemOres();
    }
    
    private static void registerItemOres() {
		OreDictionary.registerOre("materialSalt", GrowthcraftCoreItems.salt.getItem());
		OreDictionary.registerOre("foodSalt", GrowthcraftCoreItems.salt.getItem());
		OreDictionary.registerOre("dustSalt", GrowthcraftCoreItems.salt.getItem());
		OreDictionary.registerOre("itemSalt", GrowthcraftCoreItems.salt.getItem());
		OreDictionary.registerOre("lumpSalt", GrowthcraftCoreItems.salt.getItem());
    }

    public static void registerItemRenders() {
    	GrowthcraftCoreItems.salt.registerRender();
    	GrowthcraftCoreItems.rope.registerRender();
    	GrowthcraftCoreItems.crowbar.registerRenders(GrowthcraftCoreItems.CrowbarTypes.class);
    }
    
    //////////////
    // Recipes
    //////////////
    
    public static void registerRecipes() {
        registerCraftingRecipes();
    }

    private static void registerCraftingRecipes() {

        Item ironIngot = Items.IRON_INGOT;

        // Crowbar Recipes
        for ( EnumDyeColor dye : EnumDyeColor.values() ) {
            GameRegistry.addShapedRecipe( GrowthcraftCoreItems.crowbar.asStack(1, dye.getMetadata() ),
                    "  I", "DI ", "ID ",
                    'I', ironIngot,
                    'D', new ItemStack(Items.DYE, 1, dye.getDyeDamage())
                    );
        }

        GameRegistry.addRecipe( GrowthcraftCoreBlocks.salt_block.asStack(1),
                "SSS", "SSS", "SSS",
                'S', GrowthcraftCoreItems.salt.getItem()
        );
        GameRegistry.addRecipe( GrowthcraftCoreItems.salt.asStack(9),
                "S",
                'S', GrowthcraftCoreBlocks.salt_block.getBlock()
        );
        
		GameRegistry.addRecipe(GrowthcraftCoreItems.rope.asStack(8), new Object[] {"A", 'A', Items.LEAD});
    }

    
}
