package growthcraft.grapes.shared.init;

import growthcraft.cellar.shared.definition.BlockBoozeDefinition;
import growthcraft.core.shared.definition.BlockTypeDefinition;
import growthcraft.grapes.common.blocks.BlockGrapeFruit;
import growthcraft.grapes.common.blocks.BlockGrapeLeaves;
import growthcraft.grapes.common.blocks.BlockGrapeVine0;
import growthcraft.grapes.common.blocks.BlockGrapeVine1;
import growthcraft.grapes.common.handlers.EnumHandler.GrapeTypes;
import growthcraft.grapes.shared.Reference;
import net.minecraft.util.ResourceLocation;

public class GrowthcraftGrapesBlocks {
	
	private GrowthcraftGrapesBlocks() {}

	public static BlockBoozeDefinition[] grapeWineFluidBlocks;
	public static BlockTypeDefinition<BlockGrapeVine0> grapeVine0;
	public static BlockTypeDefinition<BlockGrapeVine1> grapeVine1;
	public static BlockTypeDefinition<BlockGrapeLeaves> grapeLeaves;
	public static BlockTypeDefinition<BlockGrapeFruit> grapeFruit;
	
}
