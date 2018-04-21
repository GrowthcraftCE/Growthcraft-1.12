package growthcraft.grapes.common.init;

import growthcraft.cellar.shared.definition.BlockBoozeDefinition;
import growthcraft.core.shared.definition.BlockTypeDefinition;
import growthcraft.grapes.Reference;
import growthcraft.grapes.common.blocks.BlockGrapeFruit;
import growthcraft.grapes.common.blocks.BlockGrapeLeaves;
import growthcraft.grapes.common.blocks.BlockGrapeVine0;
import growthcraft.grapes.common.blocks.BlockGrapeVine1;
import growthcraft.grapes.common.handlers.EnumHandler.GrapeTypes;
import net.minecraft.util.ResourceLocation;

public class GrowthcraftGrapesBlocks {

	public static BlockBoozeDefinition[] grapeWineFluidBlocks;
	public static BlockTypeDefinition<BlockGrapeVine0> grapeVine0;
	public static BlockTypeDefinition<BlockGrapeVine1> grapeVine1;
	public static BlockTypeDefinition<BlockGrapeLeaves> grapeLeaves;
	public static BlockTypeDefinition<BlockGrapeFruit> grapeFruit;
	
    public static void preInit() {
    	
		grapeFruit  = new BlockTypeDefinition<BlockGrapeFruit>(new BlockGrapeFruit(GrapeTypes.values()));
		grapeLeaves = new BlockTypeDefinition<BlockGrapeLeaves>(new BlockGrapeLeaves(GrapeTypes.values(), grapeFruit.getBlock()));
		grapeVine1  = new BlockTypeDefinition<BlockGrapeVine1>(new BlockGrapeVine1(grapeLeaves.getBlock()));
		grapeVine0  = new BlockTypeDefinition<BlockGrapeVine0>(new BlockGrapeVine0(GrapeTypes.values(), grapeVine1.getBlock()));
    	
        grapeWineFluidBlocks = null; // Is initialized in GrowthcraftGrapesFluids
    }

    public static void register() {
    	grapeVine0.register(new ResourceLocation(Reference.MODID, "native_grape_vine0"), false);
    	grapeVine1.register(new ResourceLocation(Reference.MODID, "native_grape_vine1"), false);
    	grapeLeaves.register(new ResourceLocation(Reference.MODID, "native_grape_vine_leaves"), false);
    	grapeFruit.register(new ResourceLocation(Reference.MODID, "native_grape_fruit"), false);
    }

    public static void  registerRenders() {

    }
}
