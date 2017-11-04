package growthcraft.cellar.init;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

import growthcraft.cellar.Reference;
import growthcraft.cellar.items.ItemChievDummy;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrowthcraftCellarItems {
	// REVISE_ME 0
	// INITIALIZE
	
	public static ItemChievDummy chievItemDummy;

    public static void init() {
    	chievItemDummy = new ItemChievDummy("bamboo_stick");
    }

    public static void register() {
        registerItem(chievItemDummy);
    }

    public static void registerRenders() {
        registerRender(chievItemDummy);
    }

    public static void registerItem(Item item) {
        item.setCreativeTab(tabGrowthcraft);
        GameRegistry.register(item);
    }

    public static void  registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void registerRender(Item item, int meta, String fileName) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }
}
