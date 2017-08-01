package growthcraft.milk.init;

import growthcraft.core.utils.GrowthcraftLogger;
import growthcraft.milk.Reference;
import growthcraft.milk.items.ItemSeedThistle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftMilkItems {

    public static Item thistle;

    public static void init() {
        thistle = new ItemSeeds(GrowthcraftMilkBlocks.thistle, Blocks.FARMLAND)
                .setUnlocalizedName("thistle_seed")
                .setRegistryName(new ResourceLocation(Reference.MODID, "thistle_seed"));
    }

    public static void register() {
        registerItem(thistle);
    }

    public static void registerRenders() {
        registerRender(thistle);
    }

    public static void registerItem(Item item) {
        item.setCreativeTab(tabGrowthcraft);
        GameRegistry.register(item);
    }

    public static void  registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
        GrowthcraftLogger.getLogger().info("Registering Render for " + item.getUnlocalizedName());
    }

    public static void registerRender(Item item, int meta, String fileName) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }



}
