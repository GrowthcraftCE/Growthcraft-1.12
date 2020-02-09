package growthcraft.core.common;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;

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
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class Init {

    //////////////
    // Blocks
    //////////////

    public static void preInitBlocks() {
        GrowthcraftCoreBlocks.rope_fence = new BlockDefinition(new BlockRopeFence("rope_fence"));
        GrowthcraftCoreBlocks.salt_block = new BlockDefinition(new BlockSalt("salt_block"));
        GrowthcraftCoreBlocks.salt_ore = new BlockDefinition(new BlockSaltOre("salt_ore"));
        GrowthcraftCoreBlocks.rope_knot = new BlockDefinition(new BlockRopeKnot("rope_knot"));

        // region RopeKnotBlocks
        GrowthcraftCoreBlocks.rope_knot_dark_oak = new BlockDefinition(
                new BlockRopeKnot("rope_knot_dark_oak", Blocks.DARK_OAK_FENCE));
        // endregion
    }

    public static void registerBlockOres() {
        OreDictionary.registerOre("oreSalt", GrowthcraftCoreBlocks.salt_ore.getBlock());

        // region RopeKnotBlocks
        OreDictionary.registerOre("ropeKnotFence", GrowthcraftCoreBlocks.rope_knot_dark_oak.getBlock());
        // endregion
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        GrowthcraftCoreBlocks.salt_block.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftCoreBlocks.salt_block.registerBlock(registry);
        GrowthcraftCoreBlocks.salt_ore.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftCoreBlocks.salt_ore.registerBlock(registry);

        // region NonItemBlocks
        GrowthcraftCoreBlocks.rope_fence.registerBlock(registry);
        GrowthcraftCoreBlocks.rope_knot.registerBlock(registry);
        //endregion

        // region RopeKnotBlocks
        GrowthcraftCoreBlocks.rope_knot_dark_oak.registerBlock(registry);
        // endregion
    }

    public static void registerBlockItems(IForgeRegistry<Item> registry) {
        GrowthcraftCoreBlocks.salt_block.registerBlockItem(registry);
        GrowthcraftCoreBlocks.salt_ore.registerBlockItem(registry);

        // region RopeKnotBlockItems
        GrowthcraftCoreBlocks.rope_knot_dark_oak.registerBlockItem(registry);
        // endregion
    }

    public static void registerBlockRenders() {
        GrowthcraftCoreBlocks.salt_block.registerItemRender();
        GrowthcraftCoreBlocks.salt_ore.registerItemRender();

        // region RopeKnotBlocks
        GrowthcraftCoreBlocks.rope_knot_dark_oak.registerItemRender();
        // endregion

    }

    //////////////
    // Items
    //////////////

    public static void preInitItems() {
        GrowthcraftCoreItems.crowbar = new ItemDefinition(new ItemCrowbar("crowbar"));
        GrowthcraftCoreItems.salt = new ItemDefinition(new ItemSalt("salt"));
        GrowthcraftCoreItems.rope = new ItemDefinition(new ItemRope("rope"));
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        GrowthcraftCoreItems.crowbar.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftCoreItems.crowbar.registerItem(registry);
        GrowthcraftCoreItems.salt.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftCoreItems.salt.registerItem(registry);
        GrowthcraftCoreItems.rope.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftCoreItems.rope.registerItem(registry);

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
        // TODO: RECIPE_REGISTER!
        // TODO: Recipes are done via JSON now. You could try and just do a bunch of json-based recipes, if you haven't already.

//        Item ironIngot = Items.IRON_INGOT;
//
//        // Crowbar Recipes
//        for ( EnumDyeColor dye : EnumDyeColor.values() ) {
//            GameRegistry.addShapedRecipe( GrowthcraftCoreItems.crowbar.asStack(1, dye.getMetadata() ),
//                    "  I", "DI ", "ID ",
//                    'I', ironIngot,
//                    'D', new ItemStack(Items.DYE, 1, dye.getDyeDamage())
//                    );
//        }
//
//        GameRegistry.addRecipe( GrowthcraftCoreBlocks.salt_block.asStack(1),
//                "SSS", "SSS", "SSS",
//                'S', GrowthcraftCoreItems.salt.getItem()
//        );
//        GameRegistry.addRecipe( GrowthcraftCoreItems.salt.asStack(9),
//                "S",
//                'S', GrowthcraftCoreBlocks.salt_block.getBlock()
//        );
//        
//		GameRegistry.addRecipe(GrowthcraftCoreItems.rope.asStack(8), new Object[] {"A", 'A', Items.LEAD});
    }


}
