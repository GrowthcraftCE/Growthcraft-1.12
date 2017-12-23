package growthcraft.cellar.util;

import java.util.ArrayList;
import java.util.List;

import growthcraft.cellar.api.CellarRegistry;
import growthcraft.cellar.api.booze.Booze;
import growthcraft.cellar.api.booze.BoozeEffect;
import growthcraft.cellar.api.booze.BoozeEntry;
import growthcraft.cellar.api.booze.BoozeRegistry;
import growthcraft.cellar.api.booze.BoozeTag;
import growthcraft.cellar.common.block.BlockFluidBooze;
import growthcraft.cellar.common.definition.BlockBoozeDefinition;
import growthcraft.cellar.common.definition.BoozeDefinition;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.GrowthcraftCoreConfig;
import growthcraft.core.api.CoreRegistry;
import growthcraft.core.api.fluids.FluidDictionary;
import growthcraft.core.common.definition.ItemDefinition;
import growthcraft.core.lib.legacy.FluidContainerRegistry;
import growthcraft.grapes.Reference;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BoozeRegistryHelper {
	// OPEN_ADHOC
	
	private BoozeRegistryHelper() {}

	public static void initializeAndRegisterBoozeFluids(String basename, BoozeDefinition[] boozes)
	{
		for (int i = 0; i < boozes.length; ++i)
		{
			boozes[i] = new BoozeDefinition( new Booze(basename + i) );
			boozes[i].register(true);
			CellarRegistry.instance().booze().registerBooze(boozes[i].getFluid());
		}
	}

	public static void initializeBooze(BoozeDefinition[] boozes, BlockBoozeDefinition[] grapeWineFluids) {
		// OPEN
		for (int i = 0; i < boozes.length; ++i)
		{
			final BlockFluidBooze boozeBlock = new BlockFluidBooze(boozes[i].getFluid());
			grapeWineFluids[i] = new BlockBoozeDefinition(boozeBlock);
		}		
	}
	
	public static void setBoozeFoodStats(BoozeDefinition booze, int heal, float saturation)
	{
		final BoozeEntry entry = CellarRegistry.instance().booze().getBoozeEntry(booze.getFluid());
		if (entry != null)
		{
			entry.setFoodStats(heal, saturation);
		}
	}
	
	public static void setBoozeFoodStats(BoozeDefinition[] boozes, int heal, float saturation)
	{
		for (BoozeDefinition booze : boozes)
		{
			setBoozeFoodStats(booze, heal, saturation);
		}
	}
	
	public static List<BoozeEffect> getBoozeEffects(Fluid[] boozes)
	{
		final BoozeRegistry reg = CellarRegistry.instance().booze();
		final FluidDictionary dict = CoreRegistry.instance().fluidDictionary();
		final List<BoozeEffect> effects = new ArrayList<BoozeEffect>();
		for (int i = 0; i < boozes.length; ++i)
		{
			if (dict.hasFluidTags(boozes[i], BoozeTag.FERMENTED))
			{
				effects.add(reg.getEffect(boozes[i]));
			}
		}
		return effects;
	}
	
	public static void registerBooze(BoozeDefinition[] boozes, BlockBoozeDefinition[] fluidBlocks, ItemDefinition bottle, String basename)
	{
		for (int i = 0; i < boozes.length; ++i)
		{
//			buckets[i].register(basename + "Bucket." + i);
			fluidBlocks[i].register(new ResourceLocation(Reference.MODID, "fluid_" + basename + i ));

//			EventHandlerBucketFill.instance().register(fluidBlocks[i].getBlock(), buckets[i].getItem());

//			final FluidStack boozeStack = boozes[i].asFluidStack( FluidContainerRegistry.BUCKET_VOLUME );
//			FluidContainerRegistry.registerFluidContainer(boozeStack, buckets[i].asStack(), FluidContainerRegistry.EMPTY_BUCKET);
			final ItemStack bucket = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, boozes[i].getFluid());

			final FluidStack fluidStack = boozes[i].asFluidStack( GrowthcraftCoreConfig.bottleCapacity );
			FluidContainerRegistry.registerFluidContainer(fluidStack, bottle.asStack(1, i), GrowthcraftCore.EMPTY_BOTTLE);

			GameRegistry.addShapelessRecipe(bottle.asStack(3, i), bucket, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE);
		}
	}
	
	public static void registerBoozeRenderers(BoozeDefinition[] boozes, BlockBoozeDefinition[] fluidBlocks) {
		for (int i = 0; i < boozes.length; ++i)
		{
			boozes[i].registerRenderer();
//			fluidBlocks[i].registerRender();
		}
		
	}

}
