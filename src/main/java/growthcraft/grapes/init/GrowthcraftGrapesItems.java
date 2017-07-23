package growthcraft.grapes.init;

import growthcraft.core.GrowthcraftCore;
import growthcraft.core.utils.GrowthcraftLogger;
import growthcraft.grapes.Reference;
import growthcraft.grapes.handlers.EnumHandler;
import growthcraft.grapes.items.ItemGrape;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrowthcraftGrapesItems {
    public static Item grape;

    public static void init() {
        grape = new ItemGrape("grape", 1, 1, false);
    }

    public static void register() {
        registerItem(grape);
    }

    public static void registerRenders() {
        for ( int i = 0; i < EnumHandler.GrapeTypes.values().length; i++ ) {
           registerRender(grape, i, "grape_" + EnumHandler.GrapeTypes.values()[i].getName());
        }
    }

    public static void registerItem(Item item) {
        item.setCreativeTab(GrowthcraftCore.tabGrowthcraft);
        GameRegistry.register(item);
    }

    public static void  registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
        GrowthcraftLogger.getLogger().info("Registering non-metadata render for " + item.getUnlocalizedName());
    }

    public static void registerRender(Item item, int meta, String fileName) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
        GrowthcraftLogger.getLogger().info("Registering metadata render for " + item.getUnlocalizedName() + " [" + meta + "] filename = " + fileName);
    }
}
