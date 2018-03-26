package growthcraft.bees.init;

import growthcraft.bees.GrowthcraftBees;
import growthcraft.bees.Reference;
import growthcraft.bees.api.BeesRegistry;
import growthcraft.bees.common.block.BlockBeeBox;
import growthcraft.bees.common.tileentity.TileEntityBeeBox;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.common.definition.BlockTypeDefinition;
import net.minecraft.init.Blocks;
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
	
	public static void initDefaults() {
		GrowthcraftBees.userBeesConfig.addDefault(GrowthcraftBeesItems.bee.asStack()).setComment("Growthcraft's default bee");
		BeesRegistry.instance().addHoneyComb(GrowthcraftBeesItems.honeyCombEmpty.asStack(), GrowthcraftBeesItems.honeyCombFilled.asStack());
		GrowthcraftBees.userFlowersConfig.addDefault(Blocks.RED_FLOWER);
		GrowthcraftBees.userFlowersConfig.addDefault(Blocks.YELLOW_FLOWER);
	}
}
