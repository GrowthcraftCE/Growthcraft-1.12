package growthcraft.hops.init;

import growthcraft.core.GrowthcraftCore;
import growthcraft.hops.Reference;
import growthcraft.hops.items.ItemHops;
import growthcraft.hops.items.ItemSeedHops;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrowthcraftHopsItems {

    public static Item hops;
    public static Item hop_seeds;

    public static void init() {
        hops = new ItemHops("hops");
        hop_seeds = new ItemSeedHops("hop_seeds");
    }

    public static void register() {
        registerItem(hops);
        registerItem(hop_seeds);
    }

    public static void registerRenders() {
        registerRender(hops);
        registerRender(hop_seeds);
    }

    public static void registerItem(Item item) {
        item.setCreativeTab(GrowthcraftCore.tabGrowthcraft);
        GameRegistry.register(item);
    }

    public static void  registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));

    }

    public static void registerRender(Item item, int meta, String fileName) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }
}
