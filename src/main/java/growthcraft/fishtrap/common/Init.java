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
	private Init() {}
	
	//////////
	// Blocks
	//////////

    public static void initBlocks() {
    	GrowthcraftFishtrapBlocks.fishtrap = new BlockDefinition( new BlockFishtrap("fishtrap") );

    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
    	GrowthcraftFishtrapBlocks.fishtrap.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftFishtrapBlocks.fishtrap.registerBlock(registry);
    }
    
    public static void registerBlockItems(IForgeRegistry<Item> registry) {
    	GrowthcraftFishtrapBlocks.fishtrap.registerBlockItem(registry);
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockRenders() {
    	GrowthcraftFishtrapBlocks.fishtrap.registerItemRender();
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityFishtrap.class, Reference.MODID + ":fishtrap");
    }

        //////////
	// Items
	//////////
    public static void registerItemOres() {
	    if (GrowthcraftFishtrapConfig.FISHTRAP_BAIT_TABLE.size() > 0) {
            GrowthcraftFishtrapConfig.FISHTRAP_BAIT_TABLE.forEach(
                    name -> OreDictionary.registerOre("baitFishing", Item.REGISTRY.getObject( new ResourceLocation(name)))
                    );
        }
    }
	
	//////////
	// Recipes
	//////////

    private static void registerCraftingRecipes() {
    	// TODO: RECIPE_REGISTER!
/*
        GameRegistry.addRecipe(GrowthcraftFishtrapBlocks.fishtrap.asStack(1),
                "ACA", "CBC", "ACA",
                'A', Blocks.PLANKS,
                'B', Items.LEAD,
                'C', Items.STRING
        );
        */
    }

    private static void registerSmeltingRecipes() {

    }
    
    public static void registerRecipes() {
    	registerCraftingRecipes();
    	registerSmeltingRecipes();
    }	
}
