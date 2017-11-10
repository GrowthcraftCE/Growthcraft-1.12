package growthcraft.apples.init;

import growthcraft.apples.Reference;
import growthcraft.apples.items.ItemAppleDoor;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftApplesItems {
    public static ItemAppleDoor itemAppleDoor;

    public static void preInit() {
        itemAppleDoor = new ItemAppleDoor("apple_door_item", GrowthcraftApplesBlocks.blockAppleDoor);
        register();
    }

    public static void register() {
        registerItem(itemAppleDoor);
    }

    public static void registerRenders() {
        registerRender(itemAppleDoor);
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
