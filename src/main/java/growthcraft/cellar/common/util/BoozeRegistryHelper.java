package growthcraft.cellar.common.util;

import java.util.ArrayList;
import java.util.List;

import growthcraft.cellar.common.block.BlockFluidBooze;
import growthcraft.cellar.common.definition.BlockBoozeDefinition;
import growthcraft.cellar.common.definition.BoozeDefinition;
import growthcraft.cellar.common.lib.CellarRegistry;
import growthcraft.cellar.common.lib.booze.Booze;
import growthcraft.cellar.common.lib.booze.BoozeEffect;
import growthcraft.cellar.common.lib.booze.BoozeEntry;
import growthcraft.cellar.common.lib.booze.BoozeRegistry;
import growthcraft.cellar.common.lib.booze.BoozeTag;
import growthcraft.cellar.common.lib.item.ItemBoozeBottle;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.GrowthcraftCoreConfig;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.definition.IObjectVariant;
import growthcraft.core.shared.fluids.FluidDictionary;
import growthcraft.core.shared.definition.ItemTypeDefinition;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BoozeRegistryHelper {
	private BoozeRegistryHelper() {}

	public static <ET extends Enum<?> & IStringSerializable> void initializeAndRegisterBoozeFluids(BoozeDefinition[] boozes, Class<ET> boozeTypeEnum, String basename)
	{
		ET[] values = boozeTypeEnum.getEnumConstants();
		
		for (int i = 0; i < boozes.length; ++i)
		{
			String boozeName = values[i].getName();
			if( basename != null && !basename.isEmpty() )
				boozeName = basename + "_" + boozeName;
			boozes[i] = new BoozeDefinition( new Booze(boozeName) );
			boozes[i].register(true);
			CellarRegistry.instance().booze().registerBooze(boozes[i].getFluid());
		}
	}

	public static void initializeBooze(BoozeDefinition[] boozes, BlockBoozeDefinition[] grapeWineFluids) {
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
	
	public static <ET extends Enum<?> & IObjectVariant & IStringSerializable> void registerBooze(BoozeDefinition[] boozes, BlockBoozeDefinition[] fluidBlocks, ItemTypeDefinition<ItemBoozeBottle> bottle, String modid, String basename, Class<ET> boozeTypeEnum )
	{
		ET[] values = boozeTypeEnum.getEnumConstants();
		for (int i = 0; i < boozes.length; ++i)
		{
			String boozeName = values[i].getName();
			int bottleVariantID = values[i].getVariantID();
			fluidBlocks[i].register(new ResourceLocation(modid, "fluid_" + basename + "_" + boozeName ), false);

//OBSOLET	EventHandlerBucketFill.instance().register(fluidBlocks[i].getBlock(), buckets[i].getItem());
			
			final ItemStack bucket = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, boozes[i].getFluid());
			
			final FluidStack fluidStack = boozes[i].asFluidStack( GrowthcraftCoreConfig.bottleCapacity );
			FluidContainerRegistry.registerFluidContainer(fluidStack, bottle.asStack(1, bottleVariantID), GrowthcraftCore.EMPTY_BOTTLE);

			GameRegistry.addShapelessRecipe(bottle.asStack(3, bottleVariantID), bucket, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE);
		}
	}
	
	public static void registerBoozeRenderers(BoozeDefinition[] boozes, BlockBoozeDefinition[] fluidBlocks) {
		for (int i = 0; i < boozes.length; ++i)
		{
			boozes[i].registerRenderer();
		}
	}

}
