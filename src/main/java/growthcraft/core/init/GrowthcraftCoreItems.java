package growthcraft.core.init;

import growthcraft.core.Reference;
import growthcraft.core.common.definition.ItemDefinition;
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

    public static ItemDefinition crowbar;
    public static ItemDefinition salt;
    public static ItemDefinition rope;

    public static void init() {
        crowbar = new ItemDefinition( new ItemCrowbar("crowbar") );
        salt = new ItemDefinition( new ItemSalt("salt") );
        rope = new ItemDefinition( new ItemRope("rope") );
    }

    public static void register() {
        // registerItem(crowbar);
    	crowbar.getItem().setCreativeTab(tabGrowthcraft);
    	crowbar.register();
        // registerItem(salt);
    	salt.getItem().setCreativeTab(tabGrowthcraft);
        salt.register();
    	// registerItem(rope);
        rope.getItem().setCreativeTab(tabGrowthcraft);
        rope.register();
    }

    public static void registerRenders() {
        // registerRender(salt);
    	salt.registerRender();
        //registerRender(rope);
    	rope.registerRender();
//        for ( int i = 0; i < EnumHandler.CrowbarTypes.values().length; i++ ) {
//            // registerRender(crowbar, i, "crowbar_" + EnumHandler.CrowbarTypes.values()[i].getName());
//        	crowbar.registerRender(i, "crowbar_" + EnumHandler.CrowbarTypes.values()[i].getName());
//        }
    	crowbar.registerRenders(EnumHandler.CrowbarTypes.values());
    }
/*
    public static void registerItem(Item item) {
        item.setCreativeTab(tabGrowthcraft);
        GameRegistry.register(item);
    }

    public static void  registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void registerRender(Item item, int meta, String fileName) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    } */

}
