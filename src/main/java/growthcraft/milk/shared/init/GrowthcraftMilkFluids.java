package growthcraft.milk.shared.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.cellar.shared.GrowthcraftCellarApis;
import growthcraft.cellar.shared.init.GrowthcraftCellarPotions;
import growthcraft.core.shared.config.GrowthcraftCoreConfig;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.GrowthcraftCoreApis;
import growthcraft.core.shared.effect.EffectExtinguish;
import growthcraft.core.shared.effect.EffectList;
import growthcraft.core.shared.effect.EffectUtils;
import growthcraft.core.shared.effect.IEffect;
import growthcraft.core.shared.item.OreItemStacks;
import growthcraft.core.shared.utils.TickUtils;
import growthcraft.core.shared.item.ItemFoodBottleFluid;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import growthcraft.core.shared.fluids.FluidFactory;
import growthcraft.core.shared.fluids.FluidFactory.FluidDetails;
import growthcraft.core.shared.fluids.FluidFactory.FluidDetailsBuilder;
import growthcraft.milk.GrowthcraftMilk;
import growthcraft.milk.common.block.fluids.BlockFluidButterMilk;
import growthcraft.milk.common.block.fluids.BlockFluidCheese;
import growthcraft.milk.common.block.fluids.BlockFluidCream;
import growthcraft.milk.common.block.fluids.BlockFluidMilk;
import growthcraft.milk.common.block.fluids.BlockFluidMilkCurds;
import growthcraft.milk.common.block.fluids.BlockFluidPasteurizedMilk;
import growthcraft.milk.common.block.fluids.BlockFluidRennet;
import growthcraft.milk.common.block.fluids.BlockFluidSkimMilk;
import growthcraft.milk.common.block.fluids.BlockFluidWhey;
import growthcraft.milk.common.fluids.FluidButterMilk;
import growthcraft.milk.common.fluids.FluidCheese;
import growthcraft.milk.common.fluids.FluidCream;
import growthcraft.milk.common.fluids.FluidMilk;
import growthcraft.milk.common.fluids.FluidMilkCurds;
import growthcraft.milk.common.fluids.FluidPasteurizedMilk;
import growthcraft.milk.common.fluids.FluidRennet;
import growthcraft.milk.common.fluids.FluidSkimMilk;
import growthcraft.milk.common.fluids.FluidWhey;
import growthcraft.milk.common.handlers.EnumHandler.AgedCheeseTypes;
import growthcraft.milk.common.handlers.EnumHandler.SimpleCheeseTypes;
import growthcraft.milk.common.handlers.EnumHandler.WaxedCheeseTypes;
import growthcraft.milk.common.lib.effect.EffectMilk;
import growthcraft.milk.shared.MilkRegistry;
import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.config.GrowthcraftMilkConfig;
import growthcraft.milk.shared.definition.ICheeseType;
import growthcraft.milk.shared.fluids.MilkFluidTags;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class GrowthcraftMilkFluids {
	
	public static FluidDetails butterMilk;
	public static FluidDetails cream;
	public static FluidDetails milk;
	public static FluidDetails curds;
	public static FluidDetails rennet;
	public static FluidDetails skimMilk;
	public static FluidDetails whey;
	public static FluidDetails pasteurizedMilk;
	public static Map<SimpleCheeseTypes, FluidFactory.FluidDetails> cheesesSimple = new HashMap<SimpleCheeseTypes, FluidFactory.FluidDetails>();
	public static Map<AgedCheeseTypes, FluidFactory.FluidDetails> cheesesAged = new HashMap<AgedCheeseTypes, FluidFactory.FluidDetails>();
	public static Map<WaxedCheeseTypes, FluidFactory.FluidDetails> cheesesWaxed = new HashMap<WaxedCheeseTypes, FluidFactory.FluidDetails>();
	
	// TODO: Move these to some kind of cheese registry helper.
	
	private static <E extends Enum<?> & ICheeseType & IStringSerializable> void preInitCheeseTypes( Map<E, FluidFactory.FluidDetails> fluidMap, Class<E> clazz ) {
		E[] vals = clazz.getEnumConstants();
		for( E type : vals ) {
			final String fluidName = "fluid_cheese_" + type.getName();
			Fluid cheeseFluid = new FluidCheese(fluidName).setColor(type.getColor());
			FluidDetails fluidFactory = new FluidDetailsBuilder(cheeseFluid, FluidFactory.FEATURE_NONE)
						.setFluidBlockClass(BlockFluidCheese.class).build()
							.setBlockColor(cheeseFluid.getColor())
							.setItemColor(cheeseFluid.getColor());
			fluidMap.put(type, fluidFactory);
		}
	}
	
	private static <E extends Enum<?> & ICheeseType & IStringSerializable> void registerCheeseTypes( Map<E, FluidFactory.FluidDetails> fluidMap ) {
		for( Map.Entry<E, FluidDetails> entry : fluidMap.entrySet() ) {
			entry.getValue().registerObjects(Reference.MODID, "cheese_" + entry.getKey().getName() );
			CoreRegistry.instance().fluidDictionary().addFluidTags(entry.getValue().getFluid(), MilkFluidTags.CHEESE);
		}	
	}
	
	private static <E extends Enum<?> & ICheeseType & IStringSerializable> void registerCheeseRenders( Map<E, FluidFactory.FluidDetails> fluidMap ) {
		for( Map.Entry<E, FluidDetails> entry : fluidMap.entrySet() ) {
			entry.getValue().registerRenderer();
		}
	}

	private static <E extends Enum<?> & ICheeseType & IStringSerializable> void registerCheeseColorHandlers( Map<E, FluidFactory.FluidDetails> fluidMap ) {
		for( Map.Entry<E, FluidDetails> entry : fluidMap.entrySet() ) {
			entry.getValue().registerColorHandlers();
		}
	}
	
	private static void preInitCheeseFluids() {
		preInitCheeseTypes(cheesesSimple, SimpleCheeseTypes.class);
		preInitCheeseTypes(cheesesAged, AgedCheeseTypes.class);
		preInitCheeseTypes(cheesesWaxed, WaxedCheeseTypes.class);
	}
	
    private static void preInitFluids() {
		final IEffect milkEffect = EffectMilk.create(GrowthcraftCellarPotions.potionTipsy);
		if (GrowthcraftMilkConfig.milkEnabled)
		{
			FluidMilk fluidMilk = (FluidMilk)new FluidMilk("fluid_milk");
			ItemFoodBottleFluid foodBottleMilk = (ItemFoodBottleFluid)new ItemFoodBottleFluid(fluidMilk, 4, 0.3f, false);
			foodBottleMilk.setEffect(milkEffect).setAlwaysEdible();
			milk = new FluidDetailsBuilder(
				fluidMilk,
				FluidFactory.FEATURE_FOOD_BOTTLE | FluidFactory.FEATURE_BLOCK)
					.setFluidBlockClass(BlockFluidMilk.class)
					.setFoodBottle(foodBottleMilk).build()
						.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFFFF);
		}
		
		FluidButterMilk fluidButterMilk = new FluidButterMilk("fluid_butter_milk");
		ItemFoodBottleFluid foodBottleButterMilk = new ItemFoodBottleFluid(fluidButterMilk, 6, 0.4f, false);
		{
			final EffectList list = new EffectList();
			list.add(milkEffect);
			if (GrowthcraftMilkConfig.fantasyMilkEffects)
			{
				// Idea from here: http://www.altmedicine101.com/buttermilk
				list.add(new EffectExtinguish());
				list.add(EffectUtils.createAddPotionEffect(MobEffects.FIRE_RESISTANCE, TickUtils.seconds(15), 0));
			}
			foodBottleButterMilk.setEffect(list).setAlwaysEdible();
		}
		butterMilk = new FluidDetailsBuilder(fluidButterMilk, FluidFactory.FEATURE_ALL_EDIBLE)
				.setFluidBlockClass(BlockFluidButterMilk.class)
				.setFoodBottle(foodBottleButterMilk).build()
					.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFEE7);

        FluidCream fluidCream = new FluidCream("fluid_cream");
		cream = new FluidDetailsBuilder(fluidCream)
				.setFluidBlockClass(BlockFluidCream.class).build()
					.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFDD0);

        FluidMilkCurds fluidMilkCurds = new FluidMilkCurds("fluid_milk_curds");
		curds = new FluidDetailsBuilder(fluidMilkCurds)
				.setFluidBlockClass(BlockFluidMilkCurds.class).build()
					.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFFF6);
        
        FluidRennet fluidRennet = new FluidRennet("fluid_rennet");
        rennet = new FluidDetailsBuilder(fluidRennet)
				.setFluidBlockClass(BlockFluidRennet.class).build()
					.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0x877243);
        
        FluidSkimMilk fluidSkimMilk = new FluidSkimMilk("fluid_skim_milk");
        ItemFoodBottleFluid foodBottleSkimMilk = new ItemFoodBottleFluid(fluidSkimMilk, 2, 0.2f, false);
		{
			final EffectList list = new EffectList();
			list.add(milkEffect);
			if (GrowthcraftMilkConfig.fantasyMilkEffects)
			{
				list.add(EffectUtils.createAddPotionEffect(MobEffects.SPEED, TickUtils.seconds(15), 0));
			}
			foodBottleSkimMilk.setEffect(list).setAlwaysEdible();
		}        
        skimMilk = new FluidDetailsBuilder(fluidSkimMilk, FluidFactory.FEATURE_ALL_EDIBLE)
        		.setFluidBlockClass(BlockFluidSkimMilk.class)
        		.setFoodBottle(foodBottleSkimMilk).build()
        			.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFFFA);
		
        FluidWhey fluidWhey = new FluidWhey("fluid_whey");
		ItemFoodBottleFluid foodBottleWhey = new ItemFoodBottleFluid(fluidWhey, 1, 0.1f, false);
		{
			final EffectList list = new EffectList();
			if (GrowthcraftMilkConfig.fantasyMilkEffects)
			{
				list.add(EffectUtils.createAddPotionEffect(MobEffects.STRENGTH, TickUtils.seconds(10), 0));
				list.add(EffectUtils.createAddPotionEffect(MobEffects.RESISTANCE, TickUtils.seconds(10), 0));
			}
			foodBottleWhey.setEffect(list).setAlwaysEdible();
		}
		whey = new FluidDetailsBuilder(fluidWhey, FluidFactory.FEATURE_ALL_EDIBLE)
				.setFluidBlockClass(BlockFluidWhey.class)
				.setFoodBottle(foodBottleWhey).build()
					.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0x94a860);
        
        FluidPasteurizedMilk fluidPasteurizedMilk = new FluidPasteurizedMilk("fluid_pasteurized_milk");
		pasteurizedMilk = new FluidDetailsBuilder(fluidPasteurizedMilk)
				.setFluidBlockClass(BlockFluidPasteurizedMilk.class).build()
					.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFFFA);
		
		preInitCheeseFluids();
    }
    
    public static void preInit() {
    	preInitFluids();
    }
    
	public static List<Fluid> getMilkFluids()
	{
		final List<Fluid> milks = new ArrayList<Fluid>();
		if (milk != null)
			milks.add(milk.getFluid());
//		if (ForestryFluids.MILK.exists()) milks.add(ForestryFluids.MILK.getFluid());
		// Automagy Milk
		final Fluid fluidmilk = FluidRegistry.getFluid("fluidmilk");
		if (fluidmilk != null)
			milks.add(fluidmilk);
		return milks;
	}
    
	public static int roundToBottles(int fluidAmount) {
		int numBottles = fluidAmount / GrowthcraftCoreConfig.bottleCapacity;
		return numBottles * GrowthcraftCoreConfig.bottleCapacity;
	}
	
    public static void init() {
		int restCapRounded = roundToBottles(FluidContainerRegistry.BUCKET_VOLUME - 2*GrowthcraftCoreConfig.bottleCapacity);
    	
    	final List<Fluid> milks = getMilkFluids();
		for (Fluid f : milks)
		{
			CoreRegistry.instance().fluidDictionary().addFluidTags(f, MilkFluidTags.MILK);
			
			MilkRegistry.instance().pancheon().addRecipe(
				new FluidStack(f, 1000),
				cream.asFluidStack(2*GrowthcraftCoreConfig.bottleCapacity), skimMilk.asFluidStack(restCapRounded),
				TickUtils.minutes(1));
		}
	}
    
	private static void registerOres()
	{
		if (milk != null)
		{
			OreDictionary.registerOre("bottleMilk", milk.getFoodBottle().asStack());
		}
		// Milk bucket is the vanilla milk bucket, derp
		OreDictionary.registerOre("bucketMilk", Items.MILK_BUCKET);
		if (skimMilk != null)
		{
			OreDictionary.registerOre("bottleSkimmilk", skimMilk.asFoodBottleItemStack());
			OreDictionary.registerOre("bucketSkimmilk", skimMilk.asBucketItemStack());
		}
		if (butterMilk != null)
		{
			OreDictionary.registerOre("bottleButtermilk", butterMilk.asFoodBottleItemStack());
			OreDictionary.registerOre("bucketButtermilk", butterMilk.asBucketItemStack());
		}
		if (whey != null)
		{
			OreDictionary.registerOre("bottleWhey", whey.asFoodBottleItemStack());
			OreDictionary.registerOre("bucketWhey", whey.asBucketItemStack());
			// https://github.com/GrowthcraftCE/Growthcraft-1.7/issues/419
			OreDictionary.registerOre("foodStock", whey.asFoodBottleItemStack());
		}
		if (cream != null)
		{
			OreDictionary.registerOre("bottleCream", cream.asBottleItemStack());
			OreDictionary.registerOre("bucketCream", cream.asBucketItemStack());
		}
	}

    public static void register() {
		if (milk != null)
		{
			milk.registerObjects(Reference.MODID, "milk");
			// ensure that we don't already have some variation of milk present
			if (FluidRegistry.getFluid("milk") == null)
			{
				FluidContainerRegistry.registerFluidContainer(milk.getFluid(), new ItemStack(Items.MILK_BUCKET, 1), new ItemStack(Items.BUCKET, 1));
//OBSOLET				EventHandlerBucketFill.instance().register(milk.getFluidBlock(), new ItemStack(Items.milk_bucket, 1));
			}
		}
		butterMilk.registerObjects(Reference.MODID, "butter_milk");
		cream.registerObjects(Reference.MODID, "cream");
		curds.registerObjects(Reference.MODID, "milk_curds");
		rennet.registerObjects(Reference.MODID, "rennet");
		skimMilk.registerObjects(Reference.MODID, "skim_milk");
		whey.registerObjects(Reference.MODID, "whey");
		pasteurizedMilk.registerObjects(Reference.MODID, "pasteurized_milk");

		CoreRegistry.instance().fluidDictionary().addFluidTags(cream.getFluid(), MilkFluidTags.CREAM);
		CoreRegistry.instance().fluidDictionary().addFluidTags(curds.getFluid(), MilkFluidTags.MILK_CURDS);
		CoreRegistry.instance().fluidDictionary().addFluidTags(rennet.getFluid(), MilkFluidTags.RENNET);
		CoreRegistry.instance().fluidDictionary().addFluidTags(whey.getFluid(), MilkFluidTags.WHEY);

		GrowthcraftCellarApis.boozeBuilderFactory.create(rennet.getFluid())
			.brewsFrom(new FluidStack(FluidRegistry.WATER, 1000), new OreItemStacks("rennetSource"), TickUtils.minutes(1), null);

		GrowthcraftCellarApis.boozeBuilderFactory.create(pasteurizedMilk.getFluid())
			.brewsFrom(skimMilk.asFluidStack(250), new ItemStack(Items.SUGAR), TickUtils.minutes(1), new Residue(GrowthcraftMilkItems.starterCulture.asStack(1), 1.0f));

		GrowthcraftCellarApis.boozeBuilderFactory.create(skimMilk.getFluid())
			.culturesTo(250, GrowthcraftMilkItems.starterCulture.asStack(), 0.6f, TickUtils.seconds(12));

		GrowthcraftMilk.userApis.churnRecipes.addDefault(
				cream.asFluidStack(roundToBottles(1000)),
				butterMilk.asFluidStack(roundToBottles(500)),
				GrowthcraftMilkItems.butter.asStack(2),
				16);
		
		registerCheeseTypes(cheesesSimple);
		registerCheeseTypes(cheesesAged);
		registerCheeseTypes(cheesesWaxed);
		
		registerOres();
    }

    public static void registerRenders() {
    	if( milk != null )
    		milk.registerRenderer();
		butterMilk.registerRenderer();
		cream.registerRenderer();
		curds.registerRenderer();
		rennet.registerRenderer();
		skimMilk.registerRenderer();
		whey.registerRenderer();
		pasteurizedMilk.registerRenderer();
		registerCheeseRenders(cheesesSimple);
		registerCheeseRenders(cheesesAged);
		registerCheeseRenders(cheesesWaxed);
    }
    
    public static void registerColorHandlers() {
    	if (milk != null)
    		milk.registerColorHandlers();
		butterMilk.registerColorHandlers();
		cream.registerColorHandlers();
		curds.registerColorHandlers();
		rennet.registerColorHandlers();
		skimMilk.registerColorHandlers();
		whey.registerColorHandlers();
		pasteurizedMilk.registerColorHandlers();
		registerCheeseColorHandlers(cheesesSimple);
		registerCheeseColorHandlers(cheesesAged);
		registerCheeseColorHandlers(cheesesWaxed);
    }
}
