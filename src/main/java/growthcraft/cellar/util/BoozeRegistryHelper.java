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
import growthcraft.cellar.common.item.ItemBoozeBottle;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.GrowthcraftCoreConfig;
import growthcraft.core.api.CoreRegistry;
import growthcraft.core.api.definition.IObjectVariant;
import growthcraft.core.api.fluids.FluidDictionary;
import growthcraft.core.common.definition.ItemDefinition;
import growthcraft.core.common.definition.ItemTypeDefinition;
import growthcraft.core.lib.legacy.FluidContainerRegistry;
import growthcraft.grapes.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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

	public static <ET extends Enum<?> & IStringSerializable> void initializeAndRegisterBoozeFluids(BoozeDefinition[] boozes, Class<ET> boozeTypeEnum)
	{
		ET[] values = boozeTypeEnum.getEnumConstants();
		
		for (int i = 0; i < boozes.length; ++i)
		{
			String boozeName = values[i].getName();
			boozes[i] = new BoozeDefinition( new Booze(boozeName) );
			boozes[i].register(true);
			CellarRegistry.instance().booze().registerBooze(boozes[i].getFluid());
		}
	}

	public static void initializeBooze(BoozeDefinition[] boozes, BlockBoozeDefinition[] grapeWineFluids) {
		for (int i = 0; i < boozes.length; ++i)
		{
			final BlockFluidBooze boozeBlock = new BlockFluidBooze(boozes[i].getFluid());
//TODO			boozeBlock.setCreativeTab(GrowthcraftCore.tabGrowthcraft);
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
	
	public static <ET extends Enum<?> & IObjectVariant & IStringSerializable> void registerBooze(BoozeDefinition[] boozes, BlockBoozeDefinition[] fluidBlocks, ItemTypeDefinition<ItemBoozeBottle> bottle, String basename, Class<ET> boozeTypeEnum )
	{
		ET[] values = boozeTypeEnum.getEnumConstants();
		for (int i = 0; i < boozes.length; ++i)
		{
			String boozeName = values[i].getName();
			int bottleVariantID = values[i].getVariantID();
			fluidBlocks[i].register(new ResourceLocation(Reference.MODID, "fluid_" + basename + "_" + boozeName ), false);

//			EventHandlerBucketFill.instance().register(fluidBlocks[i].getBlock(), buckets[i].getItem());

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
//			fluidBlocks[i].registerRender();
		}
		
	}
	
	public static void registerBoozeColorHandler(ItemBoozeBottle bottle) {
		ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
		itemColors.registerItemColorHandler(new IItemColor() {

			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				if( tintIndex != 0 )
					return -1;
				Item item = stack.getItem();
				if( !(item instanceof ItemBoozeBottle) )
					return -1;
				ItemBoozeBottle boozeBottle = (ItemBoozeBottle)item;
				int value = boozeBottle.getColor(stack);
				
				return value;
			}
			
		}, bottle);
	}

}
