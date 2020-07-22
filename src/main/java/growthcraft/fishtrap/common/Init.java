package growthcraft.fishtrap.common;

import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.fishtrap.common.block.BlockFishtrap;
import growthcraft.fishtrap.common.tileentity.TileEntityFishtrap;
import growthcraft.fishtrap.shared.Reference;
import growthcraft.fishtrap.shared.config.GrowthcraftFishtrapConfig;
import growthcraft.fishtrap.shared.init.GrowthcraftFishtrapBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;

public class Init {
    private Init() {
    }

    //////////
    // Blocks
    //////////

    public static void initBlocks() {
        GrowthcraftFishtrapBlocks.fishtrap = new BlockDefinition(new BlockFishtrap("fishtrap"));

        GrowthcraftFishtrapBlocks.fishtrap_oak = new BlockDefinition(new BlockFishtrap("fishtrap_oak"));
        GrowthcraftFishtrapBlocks.fishtrap_darkoak = new BlockDefinition(new BlockFishtrap("fishtrap_darkoak"));
        GrowthcraftFishtrapBlocks.fishtrap_birch = new BlockDefinition(new BlockFishtrap("fishtrap_birch"));
        GrowthcraftFishtrapBlocks.fishtrap_spruce = new BlockDefinition(new BlockFishtrap("fishtrap_spruce"));
        GrowthcraftFishtrapBlocks.fishtrap_acacia = new BlockDefinition(new BlockFishtrap("fishtrap_acacia"));
        GrowthcraftFishtrapBlocks.fishtrap_jungle = new BlockDefinition(new BlockFishtrap("fishtrap_jungle"));

        GrowthcraftFishtrapBlocks.fishtrap_apple = new BlockDefinition(new BlockFishtrap("fishtrap_apple"));
        GrowthcraftFishtrapBlocks.fishtrap_bamboo = new BlockDefinition(new BlockFishtrap("fishtrap_bamboo"));
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        GrowthcraftFishtrapBlocks.fishtrap.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftFishtrapBlocks.fishtrap.registerBlock(registry);

        GrowthcraftFishtrapBlocks.fishtrap_oak.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftFishtrapBlocks.fishtrap_oak.registerBlock(registry);
        GrowthcraftFishtrapBlocks.fishtrap_darkoak.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftFishtrapBlocks.fishtrap_darkoak.registerBlock(registry);
        GrowthcraftFishtrapBlocks.fishtrap_birch.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftFishtrapBlocks.fishtrap_birch.registerBlock(registry);
        GrowthcraftFishtrapBlocks.fishtrap_spruce.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftFishtrapBlocks.fishtrap_spruce.registerBlock(registry);
        GrowthcraftFishtrapBlocks.fishtrap_acacia.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftFishtrapBlocks.fishtrap_acacia.registerBlock(registry);
        GrowthcraftFishtrapBlocks.fishtrap_jungle.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftFishtrapBlocks.fishtrap_jungle.registerBlock(registry);
        GrowthcraftFishtrapBlocks.fishtrap_apple.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftFishtrapBlocks.fishtrap_apple.registerBlock(registry);
        GrowthcraftFishtrapBlocks.fishtrap_bamboo.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftFishtrapBlocks.fishtrap_bamboo.registerBlock(registry);


    }

    public static void registerBlockItems(IForgeRegistry<Item> registry) {
        GrowthcraftFishtrapBlocks.fishtrap.registerBlockItem(registry);

        GrowthcraftFishtrapBlocks.fishtrap_oak.registerBlockItem(registry);
        GrowthcraftFishtrapBlocks.fishtrap_darkoak.registerBlockItem(registry);
        GrowthcraftFishtrapBlocks.fishtrap_birch.registerBlockItem(registry);
        GrowthcraftFishtrapBlocks.fishtrap_spruce.registerBlockItem(registry);
        GrowthcraftFishtrapBlocks.fishtrap_acacia.registerBlockItem(registry);
        GrowthcraftFishtrapBlocks.fishtrap_jungle.registerBlockItem(registry);

        GrowthcraftFishtrapBlocks.fishtrap_apple.registerBlockItem(registry);
        GrowthcraftFishtrapBlocks.fishtrap_bamboo.registerBlockItem(registry);

    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockRenders() {
        GrowthcraftFishtrapBlocks.fishtrap.registerItemRender();

        GrowthcraftFishtrapBlocks.fishtrap_oak.registerItemRender();
        GrowthcraftFishtrapBlocks.fishtrap_darkoak.registerItemRender();
        GrowthcraftFishtrapBlocks.fishtrap_birch.registerItemRender();
        GrowthcraftFishtrapBlocks.fishtrap_spruce.registerItemRender();
        GrowthcraftFishtrapBlocks.fishtrap_acacia.registerItemRender();
        GrowthcraftFishtrapBlocks.fishtrap_jungle.registerItemRender();

        GrowthcraftFishtrapBlocks.fishtrap_apple.registerItemRender();
        GrowthcraftFishtrapBlocks.fishtrap_bamboo.registerItemRender();

    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityFishtrap.class, Reference.MODID + ":fishtrap");

        GameRegistry.registerTileEntity(TileEntityFishtrap.class, Reference.MODID + ":fishtrap_oak");
        GameRegistry.registerTileEntity(TileEntityFishtrap.class, Reference.MODID + ":fishtrap_darkoak");
        GameRegistry.registerTileEntity(TileEntityFishtrap.class, Reference.MODID + ":fishtrap_birch");
        GameRegistry.registerTileEntity(TileEntityFishtrap.class, Reference.MODID + ":fishtrap_spruce");
        GameRegistry.registerTileEntity(TileEntityFishtrap.class, Reference.MODID + ":fishtrap_acacia");
        GameRegistry.registerTileEntity(TileEntityFishtrap.class, Reference.MODID + ":fishtrap_jungle");

        GameRegistry.registerTileEntity(TileEntityFishtrap.class, Reference.MODID + ":fishtrap_apple");
        GameRegistry.registerTileEntity(TileEntityFishtrap.class, Reference.MODID + ":fishtrap_bamboo");

    }

    //////////
    // Items
    //////////
    public static void registerItemOres() {
        if (GrowthcraftFishtrapConfig.FISHTRAP_BAIT_TABLE.size() > 0) {
            GrowthcraftFishtrapConfig.FISHTRAP_BAIT_TABLE.forEach(
                    name -> OreDictionary.registerOre("baitFishing", Item.REGISTRY.getObject(new ResourceLocation(name)))
            );
        }
    }

    //////////
    // Recipes
    //////////

    private static void registerCraftingRecipes() {

    }

    private static void registerSmeltingRecipes() {

    }

    public static void registerRecipes() {
        registerCraftingRecipes();
        registerSmeltingRecipes();
    }
}
