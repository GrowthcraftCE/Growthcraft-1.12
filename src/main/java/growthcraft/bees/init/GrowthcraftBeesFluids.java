package growthcraft.bees.init;

import growthcraft.bees.GrowthcraftBeesConfig;
import growthcraft.bees.Reference;
import growthcraft.bees.common.fluids.FluidHoney;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.common.item.ItemFoodBottleFluid;
import growthcraft.core.utils.FluidFactory;
import growthcraft.core.utils.FluidFactory.FluidDetails;
import growthcraft.core.utils.FluidFactory.FluidDetailsBuilder;
import net.minecraftforge.oredict.OreDictionary;

public class GrowthcraftBeesFluids {
	public static FluidDetails honey;
	
	public void preInit() {
		if (GrowthcraftBeesConfig.honeyEnabled) {
			FluidHoney fluidHoney = new FluidHoney("fluid_honey");
			ItemFoodBottleFluid foodBottleHoney = new ItemFoodBottleFluid(fluidHoney, 2, 0.2f, false);
			honey = new FluidDetailsBuilder(fluidHoney, FluidFactory.FEATURE_ALL_EDIBLE)
					.setFoodBottle(foodBottleHoney).build()
						.setCreativeTab(GrowthcraftCore.tabGrowthcraft); //.setItemColor(0xFFAC01);
			honey.refreshItemColor();
		}
	}
	
	public static void registerOres() {
		if (honey != null)
		{
			OreDictionary.registerOre("bottleHoney", honey.asBottleItemStack());
//			OreDictionary.registerOre("bucketHoney", honey.asBucketItemStack());
			OreDictionary.registerOre("honeyDrop", honey.asBucketItemStack());
			OreDictionary.registerOre("dropHoney", honey.asBucketItemStack());
		}
	}
	
	public static void register() {
		if( honey != null ) {
			honey.registerObjects(Reference.MODID, "honey");
		}
	}
}
