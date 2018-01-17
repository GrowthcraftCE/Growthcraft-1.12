package growthcraft.bamboo.init;

import growthcraft.bamboo.Reference;
import growthcraft.bamboo.items.ItemBambooCoal;
import growthcraft.bamboo.items.ItemBambooDoor;
import growthcraft.bamboo.items.ItemBambooStick;
import growthcraft.core.common.definition.ItemDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftBambooItems {

    public static ItemDefinition bambooCoal;
    public static ItemDefinition bambooStick;
    public static ItemDefinition itemBambooDoor;

    public static void init() {
        bambooStick = new ItemDefinition( new ItemBambooStick("bamboo_stick") );
        bambooCoal = new ItemDefinition( new ItemBambooCoal("bamboo_coal") );
        itemBambooDoor = new ItemDefinition( new ItemBambooDoor("bamboo_door_item", GrowthcraftBambooBlocks.blockBambooDoor.getBlock()) );
    }

    public static void register() {
        // registerItem(bambooStick);
    	bambooStick.getItem().setCreativeTab(tabGrowthcraft);
    	bambooStick.register();
        // registerItem(bambooCoal);
    	bambooCoal.getItem().setCreativeTab(tabGrowthcraft);
    	bambooCoal.register();
        // registerItem(itemBambooDoor);
    	itemBambooDoor.getItem().setCreativeTab(tabGrowthcraft);
    	itemBambooDoor.register();
    }

    public static void registerRenders() {
        // registerRender(bambooStick);
    	bambooStick.registerRender();
        // registerRender(bambooCoal);
    	bambooCoal.registerRender();
        // registerRender(itemBambooDoor);
    	itemBambooDoor.registerRender();
    }
/*
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
    } */
}
