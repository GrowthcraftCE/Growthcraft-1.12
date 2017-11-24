package growthcraft.milk.init;

import growthcraft.milk.Reference;
import growthcraft.milk.items.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftMilkItems {

    public static Item itemStomach;
    public static Item thistle_seed;
    public static Item thistle;
    public static Item itemCheeseCloth;
    // TODO: ItemButter
    // TODO: ItemCheeseSlice w/ meta
    // TODO: ItemIceCream w/ meta 
    // TODO: ItemYogurt w/ meta
    public static Item itemStarterCulture;

    public static void init() {
        thistle = new ItemThistle("thistle");
        thistle_seed = new ItemSeedThistle("thistle_seed");
        itemStomach = new ItemStomach("stomach");
        itemCheeseCloth = new ItemCheeseCloth("cheese_cloth");
        itemStarterCulture = new ItemStarterCulture("starter_culture");
    }

    public static void register() {
        registerItem(thistle);
        registerItem(thistle_seed);
        registerItem(itemStomach);
        registerItem(itemCheeseCloth);
        registerItem(itemStarterCulture);
    }

    public static void registerRenders() {
        registerRender(thistle);
        registerRender(thistle_seed);
        registerRender(itemStomach);
        registerRender(itemCheeseCloth);
        registerRender(itemStarterCulture);
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
