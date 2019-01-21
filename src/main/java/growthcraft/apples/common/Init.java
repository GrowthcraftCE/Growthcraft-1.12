package growthcraft.apples.common;

import growthcraft.apples.common.block.BlockApple;
import growthcraft.apples.common.block.BlockAppleDoor;
import growthcraft.apples.common.block.BlockAppleFence;
import growthcraft.apples.common.block.BlockAppleFenceGate;
import growthcraft.apples.common.block.BlockAppleLeaves;
import growthcraft.apples.common.block.BlockAppleLog;
import growthcraft.apples.common.block.BlockApplePlanks;
import growthcraft.apples.common.block.BlockAppleSapling;
import growthcraft.apples.common.block.BlockAppleSlabDouble;
import growthcraft.apples.common.block.BlockAppleSlabHalf;
import growthcraft.apples.common.block.BlockAppleStairs;
import growthcraft.apples.common.item.ItemAppleDoor;
import growthcraft.apples.common.item.ItemAppleLeaves;
import growthcraft.apples.shared.Reference;
import growthcraft.apples.shared.config.GrowthcraftApplesConfig;
import growthcraft.apples.shared.init.GrowthcraftApplesBlocks;
import growthcraft.apples.shared.init.GrowthcraftApplesItems;
import growthcraft.apples.shared.init.GrowthcraftApplesItems.AppleCiderTypes;
import growthcraft.cellar.shared.GrowthcraftCellarApis;
import growthcraft.cellar.shared.booze.BoozeRegistryHelper;
import growthcraft.cellar.shared.booze.BoozeTag;
import growthcraft.cellar.shared.booze.BoozeUtils;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.definition.BlockBoozeDefinition;
import growthcraft.cellar.shared.definition.BoozeDefinition;
import growthcraft.cellar.shared.item.ItemBoozeBottle;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.core.shared.client.render.utils.ItemRenderUtils;
import growthcraft.core.shared.config.GrowthcraftCoreConfig;
import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.core.shared.definition.BlockTypeDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.definition.ItemTypeDefinition;
import growthcraft.core.shared.effect.EffectAddPotionEffect;
import growthcraft.core.shared.effect.EffectRandomList;
import growthcraft.core.shared.effect.EffectWeightedRandomList;
import growthcraft.core.shared.effect.SimplePotionEffectFactory;
import growthcraft.core.shared.item.OreItemStacks;
import growthcraft.core.shared.utils.TickUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;
import static growthcraft.grapes.shared.init.GrowthcraftGrapesFluids.grapeWineBooze;
import static growthcraft.apples.shared.init.GrowthcraftApplesFluids.appleCiderBooze;

public class Init {
	private Init() {}
	
	////////
	// Blocks
	////////
	
    public static void preInitBlocks() {
    	GrowthcraftApplesBlocks.blockApple = new BlockDefinition( new BlockApple("apple_crop") );
    	GrowthcraftApplesBlocks.blockAppleDoor = new BlockDefinition( new BlockAppleDoor("apple_door") );
    	GrowthcraftApplesBlocks.blockAppleFence = new BlockDefinition( new BlockAppleFence("apple_fence") );
    	GrowthcraftApplesBlocks.blockAppleFenceGate = new BlockDefinition( new BlockAppleFenceGate("apple_fence_gate") );
    	GrowthcraftApplesBlocks.blockAppleLeaves = new BlockTypeDefinition<BlockAppleLeaves>( new BlockAppleLeaves("apple_leaves") );
    	GrowthcraftApplesBlocks.blockAppleLog = new BlockDefinition( new BlockAppleLog("apple_log") );
    	GrowthcraftApplesBlocks.blockApplePlanks = new BlockDefinition( new BlockApplePlanks("apple_planks") );
    	GrowthcraftApplesBlocks.blockAppleSapling = new BlockDefinition( new BlockAppleSapling("apple_sapling") );
    	GrowthcraftApplesBlocks.blockAppleSlabHalf = new BlockTypeDefinition<BlockSlab>( new BlockAppleSlabHalf("apple_slab_half") );
    	GrowthcraftApplesBlocks.blockAppleSlabDouble = new BlockTypeDefinition<BlockSlab>( new BlockAppleSlabDouble("apple_slab_double") );
    	GrowthcraftApplesBlocks.blockAppleStairs = new BlockDefinition( new BlockAppleStairs("apple_stairs", GrowthcraftApplesBlocks.blockApplePlanks.getDefaultState()) );
    }
    
	public static void registerBlockOres()
	{
		OreDictionary.registerOre("plankWood", GrowthcraftApplesBlocks.blockApplePlanks.getItem());
	}

    public static void registerBlocks(IForgeRegistry<Block> registry) {
    	GrowthcraftApplesBlocks.blockApple.registerBlock(registry);
    	GrowthcraftApplesBlocks.blockAppleDoor.registerBlock(registry);
    	GrowthcraftApplesBlocks.blockAppleFence.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockAppleFence.registerBlock(registry);
    	GrowthcraftApplesBlocks.blockAppleFenceGate.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockAppleFenceGate.registerBlock(registry);
    	GrowthcraftApplesBlocks.blockAppleLeaves.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockAppleLeaves.registerBlock(registry);
    	GrowthcraftApplesBlocks.blockAppleLog.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockAppleLog.registerBlock(registry);
    	GrowthcraftApplesBlocks.blockApplePlanks.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockApplePlanks.registerBlock(registry);
    	GrowthcraftApplesBlocks.blockAppleSapling.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockAppleSapling.registerBlock(registry);
    	GrowthcraftApplesBlocks.blockAppleSlabHalf.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockAppleSlabHalf.registerBlock(registry);
    	GrowthcraftApplesBlocks.blockAppleSlabDouble.registerBlock(registry);
    	GrowthcraftApplesBlocks.blockAppleStairs.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesBlocks.blockAppleStairs.registerBlock(registry);
    }
    
    public static void registerBlockItems(IForgeRegistry<Item> registry) {
    	GrowthcraftApplesBlocks.blockAppleFence.registerBlockItem(registry);
    	GrowthcraftApplesBlocks.blockAppleFenceGate.registerBlockItem(registry);
    	GrowthcraftApplesBlocks.blockAppleLeaves.registerBlockItem(registry,
    			new ItemAppleLeaves(GrowthcraftApplesBlocks.blockAppleLeaves.getBlock()));
    	GrowthcraftApplesBlocks.blockAppleLog.registerBlockItem(registry);
    	GrowthcraftApplesBlocks.blockApplePlanks.registerBlockItem(registry);
    	GrowthcraftApplesBlocks.blockAppleSapling.registerBlockItem(registry);
    	GrowthcraftApplesBlocks.blockAppleSlabHalf.registerBlockItem(registry,
    			new ItemSlab(GrowthcraftApplesBlocks.blockAppleSlabHalf.getBlock(),
    			             GrowthcraftApplesBlocks.blockAppleSlabHalf.getBlock(),
    			             GrowthcraftApplesBlocks.blockAppleSlabDouble.getBlock()));
    	GrowthcraftApplesBlocks.blockAppleStairs.registerBlockItem(registry);

    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockRenders() {
    	GrowthcraftApplesBlocks.blockApple.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleDoor.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleFence.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleFenceGate.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleLeaves.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleLog.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleSapling.registerItemRender();
    	GrowthcraftApplesBlocks.blockApplePlanks.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleStairs.registerItemRender();
    	GrowthcraftApplesBlocks.blockAppleSlabHalf.registerItemRender();
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockColorHandlers() {
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
    	blockColors.registerBlockColorHandler(
        		(state, worldIn, pos, tintindex) -> {
        			return worldIn != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic();
        		},
        		GrowthcraftApplesBlocks.blockAppleLeaves.getBlock());
    }

    /*
     * Credit to CJMinecraft for identifying how to ignore properties.
     */
    @SideOnly(Side.CLIENT)
    public static void setCustomBlockStateMappers() {
        ModelLoader.setCustomStateMapper(GrowthcraftApplesBlocks.blockAppleLeaves.getBlock(), (new StateMap.Builder().ignore(BlockAppleLeaves.DECAYABLE, BlockAppleLeaves.CHECK_DECAY)).build());
        ModelLoader.setCustomStateMapper(GrowthcraftApplesBlocks.blockAppleFenceGate.getBlock(), (new StateMap.Builder().ignore(BlockFenceGate.POWERED)).build());
    }


	////////
	// Items
	////////
	
    public static void preInitItems() {
    	GrowthcraftApplesItems.itemAppleDoor = new ItemDefinition( new ItemAppleDoor("apple_door_item", GrowthcraftApplesBlocks.blockAppleDoor.getBlock()) );
    	GrowthcraftApplesItems.appleCider = new ItemTypeDefinition<ItemBoozeBottle>( new ItemBoozeBottle() );
    }
    
	public static void registerItemOres()
	{
		OreDictionary.registerOre("foodApple", Items.APPLE);
		OreDictionary.registerOre("foodFruit", Items.APPLE);
		OreDictionary.registerOre("foodApplejuice", GrowthcraftApplesItems.appleCider.asStack(1, 0));
	}

    public static void registerItems(IForgeRegistry<Item> registry) {
    	GrowthcraftApplesItems.itemAppleDoor.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesItems.itemAppleDoor.registerItem(registry);
    	
    	GrowthcraftApplesItems.appleCider.registerItem(registry, new ResourceLocation(Reference.MODID, "applecider"));
    	GrowthcraftApplesItems.appleCider.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftApplesItems.appleCider.getItem().setBoozes(appleCiderBooze);
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemRenders() {
    	GrowthcraftApplesItems.itemAppleDoor.registerRender();
    	GrowthcraftApplesItems.appleCider.registerRenders(AppleCiderTypes.class);
    }
    
    @SideOnly(Side.CLIENT)
	public static void registerItemColorHandlers() {
		ItemRenderUtils.registerItemColorHandler(GrowthcraftApplesItems.appleCider.getItem());
		
		// TODO: Move to core utils
		BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
		ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
		itemColors.registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
                IBlockState iblockstate = ((ItemBlock)stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
                return blockColors.colorMultiplier(iblockstate, (IBlockAccess)null, (BlockPos)null, tintIndex);
            }
        }, GrowthcraftApplesBlocks.blockAppleLeaves.getBlock());
	}
    
    @SideOnly(Side.CLIENT)
	public static void registerItemVariants() {
    	GrowthcraftApplesItems.appleCider.registerModelBakeryVariants(AppleCiderTypes.class);
    }
    
	////////////
	// Fluids
	////////////
    
	public static void preInitFluids() {
		appleCiderBooze = new BoozeDefinition[AppleCiderTypes.values().length];
		BoozeRegistryHelper.initializeAndRegisterBoozeFluids(appleCiderBooze, AppleCiderTypes.class, "");
		
		// 0 = young
		// 1 = cider: fermented
		// 2 = cider: fermented, potent
		// 3 = cider: fermented, extended
		// 4 = cider: ethereal
		// 5 = cider: intoxicated
		// 6 = cider: poisoned
		appleCiderBooze[AppleCiderTypes.APPLE_JUICE.ordinal()].getFluid().setColor(GrowthcraftApplesConfig.appleCiderColor).setDensity(1010);
		appleCiderBooze[AppleCiderTypes.APPLE_CIDER_FERMENTED.ordinal()].getFluid().setColor(GrowthcraftApplesConfig.appleCiderColor).setDensity(1010);
		appleCiderBooze[AppleCiderTypes.APPLE_CIDER_POTENT.ordinal()].getFluid().setColor(GrowthcraftApplesConfig.appleCiderColor).setDensity(1010);
		appleCiderBooze[AppleCiderTypes.APPLE_CIDER_EXTENDED.ordinal()].getFluid().setColor(GrowthcraftApplesConfig.appleCiderColor).setDensity(1010);
		appleCiderBooze[AppleCiderTypes.APPLE_CIDER_ETHEREAL.ordinal()].getFluid().setColor(GrowthcraftApplesConfig.silkenNectarColor).setDensity(1010);
		appleCiderBooze[AppleCiderTypes.APPLE_CIDER_INTOXICATED.ordinal()].getFluid().setColor(GrowthcraftApplesConfig.appleCiderColor).setDensity(1010);
		appleCiderBooze[AppleCiderTypes.APPLE_CIDER_POISONED.ordinal()].getFluid().setColor(GrowthcraftApplesConfig.appleCiderColor).setDensity(1010);
		
		/*
    	APPLE_JUICE(0, "apple_juice"),
    	APPLE_CIDER_FERMENTED(1, "apple_cider_fermented"),
    	APPLE_CIDER_POTENT(2, "apple_cider_potent"),
    	APPLE_CIDER_EXTENDED(3, "apple_cider_extended"),
    	APPLE_CIDER_ETHEREAL(4, "apple_cider_ethereal"),
    	APPLE_CIDER_INTOXICATED(5, "apple_cider_intoxicated"),
    	APPLE_CIDER_POISONED(6, "apple_cider_poisoned");
		 *
		 */
	}	
    
	public static void registerFluidBlocks(IForgeRegistry<Block> registry) {
		GrowthcraftApplesBlocks.appleCiderFluidBlocks = new BlockBoozeDefinition[appleCiderBooze.length];
		BoozeRegistryHelper.initializeBooze(appleCiderBooze, GrowthcraftApplesBlocks.appleCiderFluidBlocks);
		// TODO: Refactor me: Food stats need to be initialized in preInitFluids.
		BoozeRegistryHelper.setBoozeFoodStats(appleCiderBooze, 1, -0.3f);
		BoozeRegistryHelper.setBoozeFoodStats(appleCiderBooze[AppleCiderTypes.APPLE_JUICE.ordinal()], 1, 0.3f);
		
		BoozeRegistryHelper.registerBoozeBlocks(registry, appleCiderBooze, GrowthcraftApplesBlocks.appleCiderFluidBlocks, Reference.MODID, "applecider", AppleCiderTypes.class);
	}
	
	public static void initBoozes() {
		BoozeRegistryHelper.initBoozeContainers(appleCiderBooze, GrowthcraftApplesItems.appleCider, Reference.MODID, "applecider", AppleCiderTypes.class);
		registerFermentations();
	}
	
    @SideOnly(Side.CLIENT)
	public static void registerFluidRenders() {
    	BoozeRegistryHelper.registerBoozeRenderers(appleCiderBooze, GrowthcraftApplesBlocks.appleCiderFluidBlocks);
    }
	
    private static void registerFermentations() {
    	// TODO: Add configuration for brewing time and yielding amount, like in grapes module
    	
    	final int fermentTime = GrowthcraftCellarConfig.fermentTime;
		final FluidStack[] fs = new FluidStack[appleCiderBooze.length];
		for (int i = 0; i < appleCiderBooze.length; ++i)
		{
			fs[i] = appleCiderBooze[i].asFluidStack();
		}
		final FluidStack[] spoilInputFs = new FluidStack[appleCiderBooze.length];
		for (int i = 0; i < appleCiderBooze.length; ++i)
		{
			spoilInputFs[i] = appleCiderBooze[i].asFluidStack(40);
		}

		
		GrowthcraftCellarApis.boozeBuilderFactory.create(appleCiderBooze[AppleCiderTypes.APPLE_JUICE.ordinal()].getFluid())
			.tags(BoozeTag.YOUNG)
			.pressesFrom(
				new OreItemStacks("foodApple"),
				TickUtils.seconds(2),
				40,
				Residue.newDefault(0.3F)
			);

		GrowthcraftCellarApis.boozeBuilderFactory.create(appleCiderBooze[AppleCiderTypes.APPLE_CIDER_FERMENTED.ordinal()].getFluid())
			.tags(BoozeTag.CIDER, BoozeTag.FERMENTED)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_JUICE.ordinal()], new OreItemStacks("yeastBrewers"), fermentTime)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_JUICE.ordinal()], new ItemStack(Items.NETHER_WART), (int)(fermentTime * 0.66))
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.045f), TickUtils.seconds(45))
				.addPotionEntry(MobEffects.ABSORPTION, TickUtils.seconds(90), 0);
		
		GrowthcraftCellarApis.boozeBuilderFactory.create(appleCiderBooze[AppleCiderTypes.APPLE_CIDER_POTENT.ordinal()].getFluid())
			.tags(BoozeTag.CIDER, BoozeTag.FERMENTED, BoozeTag.POTENT)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_CIDER_FERMENTED.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_CIDER_EXTENDED.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.080f), TickUtils.seconds(45))
				.addPotionEntry(MobEffects.ABSORPTION, TickUtils.seconds(90), 0);

		GrowthcraftCellarApis.boozeBuilderFactory.create(appleCiderBooze[AppleCiderTypes.APPLE_CIDER_EXTENDED.ordinal()].getFluid())
			.tags(BoozeTag.CIDER, BoozeTag.FERMENTED, BoozeTag.EXTENDED)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_CIDER_FERMENTED.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_CIDER_POTENT.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.045f), TickUtils.seconds(45))
				.addPotionEntry(MobEffects.ABSORPTION, TickUtils.seconds(90), 0);
		
		// Silken Nectar - ETHEREAL
		GrowthcraftCellarApis.boozeBuilderFactory.create(appleCiderBooze[AppleCiderTypes.APPLE_CIDER_ETHEREAL.ordinal()].getFluid())
			.tags(BoozeTag.CIDER, BoozeTag.FERMENTED, BoozeTag.MAGICAL)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_CIDER_POTENT.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_CIDER_EXTENDED.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.045f), TickUtils.seconds(45))
				.addEffect(new EffectRandomList()
					// This is terrifying, thank heavens for a decent text editor...
					.add(new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.SPEED, TickUtils.minutes(10), 0)))
					.add(new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.HASTE, TickUtils.minutes(10), 0)))
					.add(new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.STRENGTH, TickUtils.minutes(10), 0)))
					.add(new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.HEALTH_BOOST, TickUtils.minutes(10), 0)))
					.add(new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.JUMP_BOOST, TickUtils.minutes(10), 0)))
					.add(new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.REGENERATION, TickUtils.minutes(10), 0)))
					.add(new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.RESISTANCE, TickUtils.minutes(10), 0)))
					.add(new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.FIRE_RESISTANCE, TickUtils.minutes(10), 0)))
					.add(new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.WATER_BREATHING, TickUtils.minutes(10), 0)))
					.add(new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.INVISIBILITY, TickUtils.minutes(10), 0)))
					.add(new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.NIGHT_VISION, TickUtils.minutes(10), 0)))
//					.add(new EffectAddPotionEffect(new SimplePotionEffectFactory(Potion.field_76434_w.id, TickUtils.minutes(10), 0)))
					.add(new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.ABSORPTION, TickUtils.minutes(10), 0)))
//					.add(new EffectAddPotionEffect(new SimplePotionEffectFactory(Potion.field_76443_y.id, TickUtils.minutes(10), 0)))
				);
		
		// Intoxicated - Origin Yeast
		GrowthcraftCellarApis.boozeBuilderFactory.create(appleCiderBooze[AppleCiderTypes.APPLE_CIDER_INTOXICATED.ordinal()].getFluid())
			.tags(BoozeTag.CIDER, BoozeTag.FERMENTED, BoozeTag.INTOXICATED)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_CIDER_POTENT.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_CIDER_EXTENDED.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.045f * 2.5f), TickUtils.seconds(45))
				.addEffect(new EffectWeightedRandomList()
					.add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.ABSORPTION, TickUtils.seconds(90), 2)))
					.add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.WITHER, TickUtils.seconds(90), 2)))
				);
		
		// Poisoned - created from netherrash,
		// the booze looses all its benefits and effectively becomes poisoned
		GrowthcraftCellarApis.boozeBuilderFactory.create(appleCiderBooze[AppleCiderTypes.APPLE_CIDER_POISONED.ordinal()].getFluid())
			.tags(BoozeTag.CIDER, BoozeTag.FERMENTED, BoozeTag.POISONED)
//			.fermentsFrom(fs[AppleCiderTypes.APPLE_JUICE.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[AppleCiderTypes.APPLE_JUICE.ordinal()], fermentTime)
//				.brewsFromFallback(spoilInputFs[AppleCiderTypes.APPLE_JUICE.ordinal()], TickUtils.minutes(1), null)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_CIDER_FERMENTED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[AppleCiderTypes.APPLE_CIDER_FERMENTED.ordinal()], fermentTime)
				.brewsFromFallback(spoilInputFs[AppleCiderTypes.APPLE_CIDER_FERMENTED.ordinal()], TickUtils.minutes(1), null)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_CIDER_POTENT.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[AppleCiderTypes.APPLE_CIDER_POTENT.ordinal()], fermentTime)
				.brewsFromFallback(spoilInputFs[AppleCiderTypes.APPLE_CIDER_POTENT.ordinal()], TickUtils.minutes(1), null)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_CIDER_EXTENDED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[AppleCiderTypes.APPLE_CIDER_EXTENDED.ordinal()], fermentTime)
				.brewsFromFallback(spoilInputFs[AppleCiderTypes.APPLE_CIDER_EXTENDED.ordinal()], TickUtils.minutes(1), null)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_CIDER_ETHEREAL.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[AppleCiderTypes.APPLE_CIDER_ETHEREAL.ordinal()], fermentTime)
				.brewsFromFallback(spoilInputFs[AppleCiderTypes.APPLE_CIDER_ETHEREAL.ordinal()], TickUtils.minutes(1), null)
			.fermentsFrom(fs[AppleCiderTypes.APPLE_CIDER_INTOXICATED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[AppleCiderTypes.APPLE_CIDER_INTOXICATED.ordinal()], fermentTime)
				.brewsFromFallback(spoilInputFs[AppleCiderTypes.APPLE_CIDER_INTOXICATED.ordinal()], TickUtils.minutes(1), null)
			.fermentsFromFallback(fs[AppleCiderTypes.APPLE_CIDER_POISONED.ordinal()], fermentTime)
				.brewsFromFallback(spoilInputFs[AppleCiderTypes.APPLE_CIDER_POISONED.ordinal()], TickUtils.minutes(1), null)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.045f), TickUtils.seconds(45))
				.createPotionEntry(MobEffects.POISON, TickUtils.seconds(90), 0).toggleDescription(!GrowthcraftCoreConfig.hidePoisonedBooze);
    }
    
	////////
	// Recipes
	////////
    
    public static void registerRecipes() {
        registerCraftingRecipes();
    }

    private static void registerCraftingRecipes() {
    	// TODO: RECIPE_REGISTER!
/*
        GameRegistry.addRecipe(
                GrowthcraftApplesBlocks.blockApplePlanks.asStack(4),
                "W",
                'W', GrowthcraftApplesBlocks.blockAppleLog.getBlock());

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesBlocks.blockAppleStairs.asStack(4),
                "  P", " PP", "PPP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock());

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesBlocks.blockAppleStairs.asStack(4),
                "P  ", "PP ", "PPP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock());

        GameRegistry.addRecipe(
        		GrowthcraftApplesBlocks.blockAppleSlabHalf.asStack(4),
                "PPP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock());

        GameRegistry.addShapedRecipe(
                new ItemStack(Blocks.CHEST, 1),
                "PPP", "P P", "PPP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock());

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesBlocks.blockAppleFence.asStack(3),
                "PSP", "PSP",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock(),
                'S', new ItemStack(Items.STICK, 1));

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesBlocks.blockAppleFenceGate.asStack(1),
                "SPS", "SPS",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock(),
                'S', new ItemStack(Items.STICK, 1));

        GameRegistry.addShapedRecipe(
                new ItemStack(Items.STICK, 4),
                "P", "P",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock() );

        GameRegistry.addShapedRecipe(
        		GrowthcraftApplesItems.itemAppleDoor.asStack(1),
                "PP ", "PP ", "PP ",
                'P', GrowthcraftApplesBlocks.blockApplePlanks.getBlock()
        );

        GameRegistry.addShapelessRecipe(
        		GrowthcraftApplesBlocks.blockAppleSapling.asStack(1),
                new ItemStack(Items.APPLE, 1),
                new ItemStack(Blocks.SAPLING, 1),
                new ItemStack(Items.WOODEN_SWORD, 1) );
*/
    }


}
