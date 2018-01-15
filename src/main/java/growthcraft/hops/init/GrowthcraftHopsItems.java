package growthcraft.hops.init;

import growthcraft.core.GrowthcraftCore;
import growthcraft.core.common.definition.ItemDefinition;
import growthcraft.hops.Reference;
import growthcraft.hops.items.ItemHops;
import growthcraft.hops.items.ItemSeedHops;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrowthcraftHopsItems {

    public static ItemDefinition hops;
    public static ItemDefinition hop_seeds;

    public static void init() {
        hops = new ItemDefinition( new ItemHops("hops") );
        hop_seeds = new ItemDefinition( new ItemSeedHops("hop_seeds") );
    }

    public static void register() {
        // registerItem(hops);
    	hops.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
    	hops.register();
        // registerItem(hop_seeds);
    	hop_seeds.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
    	hop_seeds.register();
    }

    public static void registerRenders() {
        // registerRender(hops);
    	hops.registerRender();
        // registerRender(hop_seeds);
    	hop_seeds.registerRender();
    }
/*
    public static void registerItem(Item item) {
        item.setCreativeTab(GrowthcraftCore.tabGrowthcraft);
        GameRegistry.register(item);
    }

    public static void  registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));

    }

    public static void registerRender(Item item, int meta, String fileName) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    } */
}
