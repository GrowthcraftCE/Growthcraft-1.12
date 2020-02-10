package growthcraft.core.common;

import growthcraft.core.GrowthcraftCore;
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

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;

public class Init {

    private Init() {
        /* Do Nothing */
    }
    //////////////
    // Blocks
    //////////////

    public static void preInitBlocks() {
        GrowthcraftCoreBlocks.rope_fence = new BlockDefinition(new BlockRopeFence("rope_fence"));
        GrowthcraftCoreBlocks.salt_block = new BlockDefinition(new BlockSalt("salt_block"));
        GrowthcraftCoreBlocks.salt_ore = new BlockDefinition(new BlockSaltOre("salt_ore"));
        GrowthcraftCoreBlocks.rope_knot = new BlockDefinition(new BlockRopeKnot("rope_knot"));

        // region RopeKnotBlocks
        GrowthcraftCoreBlocks.ropeKnotOak = new BlockDefinition(
                new BlockRopeKnot("rope_knot_oak", Blocks.OAK_FENCE));
        GrowthcraftCoreBlocks.ropeKnotDarkOak = new BlockDefinition(
                new BlockRopeKnot("rope_knot_dark_oak", Blocks.DARK_OAK_FENCE));
        GrowthcraftCoreBlocks.ropeKnotAcacia = new BlockDefinition(
                new BlockRopeKnot("rope_knot_acacia", Blocks.ACACIA_FENCE));
        GrowthcraftCoreBlocks.ropeKnotSpruce = new BlockDefinition(
                new BlockRopeKnot("rope_knot_spruce", Blocks.SPRUCE_FENCE));
        GrowthcraftCoreBlocks.ropeKnotBirch = new BlockDefinition(
                new BlockRopeKnot("rope_knot_birch", Blocks.BIRCH_FENCE));
        GrowthcraftCoreBlocks.ropeKnotJungle = new BlockDefinition(
                new BlockRopeKnot("rope_knot_jungle", Blocks.JUNGLE_FENCE));
        GrowthcraftCoreBlocks.ropeKnotNetherBrick = new BlockDefinition(
                new BlockRopeKnot("rope_knot_nether_brick", Blocks.NETHER_BRICK_FENCE));
        // endregion
    }

    public static void registerBlockOres() {
        OreDictionary.registerOre("oreSalt", GrowthcraftCoreBlocks.salt_ore.getBlock());

        // region RopeKnotBlocks
        OreDictionary.registerOre(GrowthcraftCore.ORE_ROPE_KNOT_FENCE, GrowthcraftCoreBlocks.ropeKnotOak.getBlock());
        OreDictionary.registerOre(GrowthcraftCore.ORE_ROPE_KNOT_FENCE, GrowthcraftCoreBlocks.ropeKnotDarkOak.getBlock());
        OreDictionary.registerOre(GrowthcraftCore.ORE_ROPE_KNOT_FENCE, GrowthcraftCoreBlocks.ropeKnotAcacia.getBlock());
        OreDictionary.registerOre(GrowthcraftCore.ORE_ROPE_KNOT_FENCE, GrowthcraftCoreBlocks.ropeKnotSpruce.getBlock());
        OreDictionary.registerOre(GrowthcraftCore.ORE_ROPE_KNOT_FENCE, GrowthcraftCoreBlocks.ropeKnotBirch.getBlock());
        OreDictionary.registerOre(GrowthcraftCore.ORE_ROPE_KNOT_FENCE, GrowthcraftCoreBlocks.ropeKnotJungle.getBlock());
        OreDictionary.registerOre(GrowthcraftCore.ORE_ROPE_KNOT_FENCE, GrowthcraftCoreBlocks.ropeKnotNetherBrick.getBlock());
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
        GrowthcraftCoreBlocks.ropeKnotOak.registerBlock(registry);
        GrowthcraftCoreBlocks.ropeKnotDarkOak.registerBlock(registry);
        GrowthcraftCoreBlocks.ropeKnotAcacia.registerBlock(registry);
        GrowthcraftCoreBlocks.ropeKnotSpruce.registerBlock(registry);
        GrowthcraftCoreBlocks.ropeKnotBirch.registerBlock(registry);
        GrowthcraftCoreBlocks.ropeKnotJungle.registerBlock(registry);
        GrowthcraftCoreBlocks.ropeKnotNetherBrick.registerBlock(registry);
        // endregion
    }

    public static void registerBlockItems(IForgeRegistry<Item> registry) {
        GrowthcraftCoreBlocks.salt_block.registerBlockItem(registry);
        GrowthcraftCoreBlocks.salt_ore.registerBlockItem(registry);

        // region RopeKnotBlockItems
        GrowthcraftCoreBlocks.ropeKnotOak.registerBlockItem(registry);
        GrowthcraftCoreBlocks.ropeKnotDarkOak.registerBlockItem(registry);
        GrowthcraftCoreBlocks.ropeKnotAcacia.registerBlockItem(registry);
        GrowthcraftCoreBlocks.ropeKnotSpruce.registerBlockItem(registry);
        GrowthcraftCoreBlocks.ropeKnotBirch.registerBlockItem(registry);
        GrowthcraftCoreBlocks.ropeKnotJungle.registerBlockItem(registry);
        GrowthcraftCoreBlocks.ropeKnotNetherBrick.registerBlockItem(registry);
        // endregion
    }

    public static void registerBlockRenders() {
        GrowthcraftCoreBlocks.salt_block.registerItemRender();
        GrowthcraftCoreBlocks.salt_ore.registerItemRender();

        // region RopeKnotBlocks
        GrowthcraftCoreBlocks.ropeKnotOak.registerItemRender();
        GrowthcraftCoreBlocks.ropeKnotDarkOak.registerItemRender();
        GrowthcraftCoreBlocks.ropeKnotAcacia.registerItemRender();
        GrowthcraftCoreBlocks.ropeKnotSpruce.registerItemRender();
        GrowthcraftCoreBlocks.ropeKnotBirch.registerItemRender();
        GrowthcraftCoreBlocks.ropeKnotJungle.registerItemRender();
        GrowthcraftCoreBlocks.ropeKnotNetherBrick.registerItemRender();
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
        /* No longer used. Use JSON recipe instead. */
    }


}
