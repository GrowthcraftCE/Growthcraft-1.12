package growthcraft.core.shared.compat.rustic;

import growthcraft.core.shared.compat.Compat;
import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.core.shared.definition.FluidDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class InitRustic {
	// TODO: Read crushing and fermenting recipes.
	//       Be careful with double entries due to multiple references to same mod including indirect references
    
	////////////
	// Blocks
	////////////
	
	public static void preInitBlocks() {
		RusticModBlocks.windThistle = new BlockDefinition(Block.getBlockFromName( Compat.MODID_RUSTIC + ":wind_thistle"));
	}
	
	public static void registerBlockOres() {
		OreDictionary.registerOre("rennetSource", RusticModBlocks.windThistle.getItem());
	}
	
	////////////
    // Items
	////////////
    
    public static void preInitItems() {
    	RusticModItems.rusticGrape = new ItemDefinition(Item.getByNameOrId(Compat.MODID_RUSTIC + ":grapes"));
    }
    
	////////////
	// Fluids
	////////////

	public static void preInitFluids() {
		RusticModFluids.rusticGrapeJuice = new FluidDefinition(FluidRegistry.getFluid("grapejuice"));
		RusticModFluids.rusticGrapeWine = new FluidDefinition(FluidRegistry.getFluid("wine"));
		
		RusticModFluids.rusticAppleJuice = new FluidDefinition(FluidRegistry.getFluid("applejuice"));
		RusticModFluids.rusticAppleCider = new FluidDefinition(FluidRegistry.getFluid("cider"));
	}
	
}
