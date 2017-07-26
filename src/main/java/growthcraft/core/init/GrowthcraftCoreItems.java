package growthcraft.core.init;

import growthcraft.core.Reference;
import growthcraft.core.handlers.EnumHandler;
import growthcraft.core.items.ItemCrowbar;
import growthcraft.core.items.ItemRope;
import growthcraft.core.items.ItemSalt;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftCoreItems {

    public static Item crowbar;
    public static Item salt;
    public static Item rope;

    public static void init() {
        crowbar = new ItemCrowbar("crowbar");
        salt = new ItemSalt("salt");
        rope = new ItemRope("rope");
    }

    public static void register() {
        registerItem(crowbar);
        registerItem(salt);
        registerItem(rope);
    }

    public static void registerRenders() {
        registerRender(salt);
        registerRender(rope);
        for ( int i = 0; i < EnumHandler.CrowbarTypes.values().length; i++ ) {
            registerRender(crowbar, i, "crowbar_" + EnumHandler.CrowbarTypes.values()[i].getName());
        }
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
