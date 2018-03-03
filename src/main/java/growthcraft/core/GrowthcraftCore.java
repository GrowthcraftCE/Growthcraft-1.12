package growthcraft.core;

import net.minecraftforge.oredict.RecipeSorter.Category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import growthcraft.core.api.item.recipes.ShapelessItemComparableRecipe;
import growthcraft.core.api.item.recipes.ShapelessMultiRecipe;
import growthcraft.core.creativetabs.TabGrowthcraft;
import growthcraft.core.init.GrowthcraftCoreBlocks;
import growthcraft.core.init.GrowthcraftCoreItems;
import growthcraft.core.init.GrowthcraftCoreRecipes;
import growthcraft.core.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.RecipeSorter;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftCore {

    public static final CreativeTabs tabGrowthcraft = new TabGrowthcraft();

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftCore instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;
    
    // REVISE_TEAM
    public static Logger logger = LogManager.getLogger(Reference.MODID);

	public static ItemStack EMPTY_BOTTLE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GrowthcraftCoreBlocks.preInit();
        GrowthcraftCoreBlocks.register();
        GrowthcraftCoreItems.preInit();
        GrowthcraftCoreItems.register();
        
        EMPTY_BOTTLE = new ItemStack(Items.GLASS_BOTTLE);
        
        proxy.registerRenders();
        proxy.registerTitleEntities();
        
        RecipeSorter.register("minecraft:shapeless_comparator", ShapelessItemComparableRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("minecraft:shapeless_multi", ShapelessMultiRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        proxy.registerSpecialRenders();
        proxy.registerModelBakeryVariants();
        GrowthcraftCoreRecipes.registerRecipes();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

}
