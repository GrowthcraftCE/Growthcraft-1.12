package growthcraft.grapes.init;

import growthcraft.core.GrowthcraftCore;
import growthcraft.grapes.Reference;
import growthcraft.grapes.handlers.EnumHandler;
import growthcraft.grapes.items.ItemGrape;
import growthcraft.grapes.items.ItemGrapeSeed;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrowthcraftGrapesItems {
    public static Item grape;
    public static Item grape_seed;

    public static void init() {
        grape = new ItemGrape("grape", 1, 1, false);
        grape_seed = new ItemGrapeSeed("grape_seed");
    }

    public static void register() {
        registerItem(grape);
        registerItem(grape_seed);
    }

    public static void registerRenders() {

        registerRender(grape_seed);

        for ( int i = 0; i < EnumHandler.GrapeTypes.values().length; i++ ) {
           EnumHandler.GrapeTypes type = EnumHandler.GrapeTypes.values()[i];
           registerRender(grape, type.getVariantID(), "grape_" + type.getName());
        }
    }

    public static void registerItem(Item item) {
        item.setCreativeTab(GrowthcraftCore.tabGrowthcraft);
        GameRegistry.register(item);
    }

    public static void  registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));

    }

    public static void registerRender(Item item, int meta, String fileName) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }
}
