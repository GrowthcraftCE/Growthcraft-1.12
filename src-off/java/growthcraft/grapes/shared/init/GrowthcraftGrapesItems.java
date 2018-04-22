package growthcraft.grapes.shared.init;

import growthcraft.cellar.shared.item.ItemBoozeBottle;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.definition.ItemTypeDefinition;
import growthcraft.core.shared.client.render.utils.ItemRenderUtils;
import growthcraft.grapes.common.handlers.EnumHandler.GrapeTypes;
import growthcraft.grapes.common.handlers.EnumHandler.WineTypes;
import growthcraft.grapes.common.items.ItemGrape;
import growthcraft.grapes.common.items.ItemGrapeSeed;
import growthcraft.grapes.shared.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;

public class GrowthcraftGrapesItems {
	
	private GrowthcraftGrapesItems() {}
	
    public static ItemDefinition grape;
    public static ItemDefinition grape_seed;
	public static ItemTypeDefinition<ItemBoozeBottle> grapeWine;
}
