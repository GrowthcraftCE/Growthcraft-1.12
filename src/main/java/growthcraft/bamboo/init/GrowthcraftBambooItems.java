package growthcraft.bamboo.init;

import growthcraft.bamboo.Reference;
import growthcraft.bamboo.items.ItemBambooCoal;
import growthcraft.bamboo.items.ItemBambooStick;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftBambooItems {

    public static ItemBambooCoal bambooCoal;
    public static ItemBambooStick bambooStick;

    public static void init() {
        bambooStick = new ItemBambooStick("bamboo_stick");
        bambooCoal = new ItemBambooCoal("bamboo_coal");
    }

    public static void register() {
        registerItem(bambooStick);
        registerItem(bambooCoal);
    }

    public static void registerRenders() {
        registerRender(bambooStick);
        registerRender(bambooCoal);
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
