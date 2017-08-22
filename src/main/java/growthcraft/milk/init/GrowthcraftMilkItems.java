package growthcraft.milk.init;

import growthcraft.milk.Reference;
import growthcraft.milk.items.ItemSeedThistle;
import growthcraft.milk.items.ItemThistle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftMilkItems {
    public static Item thistle_seed;
    public static Item thistle;

    public static void init() {
        thistle = new ItemThistle("thistle");
        thistle_seed = new ItemSeedThistle("thistle_seed");
    }

    public static void register() {
        registerItem(thistle);
        registerItem(thistle_seed);
    }

    public static void registerRenders() {
        registerRender(thistle);
        registerRender(thistle_seed);
    }

    public static void registerItem(Item item) {
        item.setCreativeTab(tabGrowthcraft);
        GameRegistry.register(item);
    }

    public static void  registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));

    }

    public static void registerRender(Item item, int meta, String fileName) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }



}
