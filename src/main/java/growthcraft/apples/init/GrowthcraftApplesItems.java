package growthcraft.apples.init;

import growthcraft.apples.Reference;
import growthcraft.apples.items.ItemAppleDoor;
import growthcraft.core.common.definition.ItemDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftApplesItems {
    public static ItemDefinition itemAppleDoor;

    public static void preInit() {
        itemAppleDoor = new ItemDefinition( new ItemAppleDoor("apple_door_item", GrowthcraftApplesBlocks.blockAppleDoor.getBlock()) );
        register();
    }

    public static void register() {
        // registerItem(itemAppleDoor);
    	itemAppleDoor.getItem().setCreativeTab(tabGrowthcraft);
    	itemAppleDoor.register();
    }

    public static void registerRenders() {
        // registerRender(itemAppleDoor);
    	itemAppleDoor.registerRender();
    }

/*    public static void registerItem(Item item) {
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
    } */
}
