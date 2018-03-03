package growthcraft.bees.init;

import growthcraft.bees.Reference;
import growthcraft.bees.common.block.BlockBeeBox;
import growthcraft.bees.common.tileentity.TileEntityBeeBox;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.common.definition.BlockTypeDefinition;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrowthcraftBeesBlocks {
	public static BlockTypeDefinition<? extends BlockBeeBox> beeBox;

	public static void preInit() {
		beeBox = new BlockTypeDefinition<BlockBeeBox>(new BlockBeeBox("beebox"));
	}
	
	public static void register() {
		beeBox.getBlock().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
		beeBox.register(true);
	}
	
	public static void registerRender() {
		beeBox.registerItemRender();
	}

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityBeeBox.class, Reference.MODID + ":bee_box");
	}
}
