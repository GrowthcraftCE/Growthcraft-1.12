package growthcraft.fishtrap.common;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;

import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.fishtrap.common.block.BlockFishtrap;
import growthcraft.fishtrap.shared.init.GrowthcraftFishtrapBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

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
	
	//////////
	// Items
	//////////

	
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
