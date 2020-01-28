package growthcraft.hops.common;

import growthcraft.cellar.shared.GrowthcraftCellarApis;
import growthcraft.cellar.shared.booze.BoozeRegistryHelper;
import growthcraft.cellar.shared.booze.BoozeTag;
import growthcraft.cellar.shared.booze.BoozeUtils;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.definition.BlockBoozeDefinition;
import growthcraft.cellar.shared.definition.BoozeDefinition;
import growthcraft.cellar.shared.item.ItemBoozeBottle;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.shared.GrowthcraftCoreApis;
import growthcraft.core.shared.client.render.utils.ItemRenderUtils;
import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.definition.ItemTypeDefinition;
import growthcraft.core.shared.effect.EffectAddPotionEffect;
import growthcraft.core.shared.effect.EffectWeightedRandomList;
import growthcraft.core.shared.effect.SimplePotionEffectFactory;
import growthcraft.core.shared.item.OreItemStacks;
import growthcraft.core.shared.utils.TickUtils;
import growthcraft.hops.common.block.BlockHops;
import growthcraft.hops.common.item.ItemHops;
import growthcraft.hops.common.item.ItemSeedHops;
import growthcraft.hops.shared.Reference;
import growthcraft.hops.shared.config.GrowthcraftHopsConfig;
import growthcraft.hops.shared.init.GrowthcraftHopsBlocks;
import growthcraft.hops.shared.init.GrowthcraftHopsFluids;
import growthcraft.hops.shared.init.GrowthcraftHopsItems;
import growthcraft.hops.shared.init.GrowthcraftHopsItems.HopAleTypes;
import growthcraft.hops.shared.init.GrowthcraftHopsItems.LagerTypes;
import growthcraft.hops.shared.init.GrowthcraftHopsLootTables;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class Init {
    private Init() {
    }

    ////////
    // Blocks
    ////////

    public static void preInitBlocks() {
        GrowthcraftHopsBlocks.hops = new BlockDefinition(new BlockHops("hops"));

        GrowthcraftHopsBlocks.hopAleFluidBlocks = null; // Initialized for GrowthcraftHopsFluids
        GrowthcraftHopsBlocks.lagerFluidBlocks = null; // Initialized for GrowthcraftHopsFluids
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        GrowthcraftHopsBlocks.hops.registerBlock(registry);
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockRenders() {
    }

    ////////
    // Items
    ////////

    public static void preInitItems() {
        GrowthcraftHopsItems.hops = new ItemDefinition(new ItemHops("hops"));
        GrowthcraftHopsItems.hop_seeds = new ItemDefinition(new ItemSeedHops("hop_seeds"));
        GrowthcraftHopsItems.lagerBottle = new ItemTypeDefinition<ItemBoozeBottle>(new ItemBoozeBottle());
        GrowthcraftHopsItems.hopAleBottle = new ItemTypeDefinition<ItemBoozeBottle>(new ItemBoozeBottle());
    }

    public static void registerItemOres() {
        OreDictionary.registerOre("cropHops", GrowthcraftHopsItems.hops.getItem());
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        GrowthcraftHopsItems.hops.getItem().setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft);
        GrowthcraftHopsItems.hops.registerItem(registry);
        GrowthcraftHopsItems.hop_seeds.getItem().setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft);
        GrowthcraftHopsItems.hop_seeds.registerItem(registry);

        GrowthcraftHopsItems.lagerBottle.getItem().setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft);
        GrowthcraftHopsItems.lagerBottle.getItem().setBoozes(GrowthcraftHopsFluids.lagerBooze);
        GrowthcraftHopsItems.lagerBottle.registerItem(registry, new ResourceLocation(Reference.MODID, "lager"));

        GrowthcraftHopsItems.hopAleBottle.getItem().setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft);
        GrowthcraftHopsItems.hopAleBottle.getItem().setBoozes(GrowthcraftHopsFluids.hopAleBooze);
        GrowthcraftHopsItems.hopAleBottle.registerItem(registry, new ResourceLocation(Reference.MODID, "hopale"));
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemRenders() {
        GrowthcraftHopsItems.hops.registerRender();
        GrowthcraftHopsItems.hop_seeds.registerRender();
        GrowthcraftHopsItems.lagerBottle.registerRenders(LagerTypes.class);
        GrowthcraftHopsItems.hopAleBottle.registerRenders(HopAleTypes.class);
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemColorHandlers() {
        ItemRenderUtils.registerItemColorHandler(GrowthcraftHopsItems.lagerBottle.getItem());
        ItemRenderUtils.registerItemColorHandler(GrowthcraftHopsItems.hopAleBottle.getItem());
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemVariants() {
        GrowthcraftHopsItems.lagerBottle.registerModelBakeryVariants(LagerTypes.class);
        GrowthcraftHopsItems.hopAleBottle.registerModelBakeryVariants(HopAleTypes.class);
    }

    ////////
    // Fluids
    ////////

    public static void preInitFluids() {
        GrowthcraftHopsFluids.lagerBooze = new BoozeDefinition[LagerTypes.values().length];
        BoozeRegistryHelper.initializeAndRegisterBoozeFluids(GrowthcraftHopsFluids.lagerBooze, LagerTypes.class, "lager");
        for (BoozeDefinition booze : GrowthcraftHopsFluids.lagerBooze) {
            booze.getFluid().setColor(GrowthcraftHopsConfig.lagerColor).setDensity(1080);
        }

        GrowthcraftHopsFluids.hopAleBooze = new BoozeDefinition[HopAleTypes.values().length];
        BoozeRegistryHelper.initializeAndRegisterBoozeFluids(GrowthcraftHopsFluids.hopAleBooze, HopAleTypes.class, "hopale");
        for (BoozeDefinition booze : GrowthcraftHopsFluids.hopAleBooze) {
            booze.getFluid().setColor(GrowthcraftHopsConfig.hopAleColor).setDensity(1080);
        }
    }

    public static void registerFluidBlocks(IForgeRegistry<Block> registry) {
        GrowthcraftHopsBlocks.lagerFluidBlocks = new BlockBoozeDefinition[GrowthcraftHopsFluids.lagerBooze.length];
        BoozeRegistryHelper.initializeBooze(GrowthcraftHopsFluids.lagerBooze, GrowthcraftHopsBlocks.lagerFluidBlocks);
        BoozeRegistryHelper.setBoozeFoodStats(GrowthcraftHopsFluids.lagerBooze, 1, -0.6f);
        BoozeRegistryHelper.setBoozeFoodStats(GrowthcraftHopsFluids.lagerBooze[LagerTypes.LAGER_YOUNG.ordinal()], 1, 0.3f);
        BoozeRegistryHelper.registerBoozeBlocks(registry, GrowthcraftHopsFluids.lagerBooze, GrowthcraftHopsBlocks.lagerFluidBlocks, Reference.MODID, "lagerbooze", LagerTypes.class);

        GrowthcraftHopsBlocks.hopAleFluidBlocks = new BlockBoozeDefinition[GrowthcraftHopsFluids.hopAleBooze.length];
        BoozeRegistryHelper.initializeBooze(GrowthcraftHopsFluids.hopAleBooze, GrowthcraftHopsBlocks.hopAleFluidBlocks);
        BoozeRegistryHelper.setBoozeFoodStats(GrowthcraftHopsFluids.hopAleBooze, 1, -0.6f);
        BoozeRegistryHelper.setBoozeFoodStats(GrowthcraftHopsFluids.hopAleBooze[HopAleTypes.ALE_UNHOPPED.ordinal()], 1, 0.3f);
        BoozeRegistryHelper.setBoozeFoodStats(GrowthcraftHopsFluids.hopAleBooze[HopAleTypes.ALE_YOUNG.ordinal()], 1, 0.3f);
        BoozeRegistryHelper.registerBoozeBlocks(registry, GrowthcraftHopsFluids.hopAleBooze, GrowthcraftHopsBlocks.hopAleFluidBlocks, Reference.MODID, "hopalebooze", HopAleTypes.class);
    }

    public static void initBoozes() {
        BoozeRegistryHelper.initBoozeContainers(GrowthcraftHopsFluids.hopAleBooze, GrowthcraftHopsItems.hopAleBottle, Reference.MODID, "hopalebooze", HopAleTypes.class);
        BoozeRegistryHelper.initBoozeContainers(GrowthcraftHopsFluids.lagerBooze, GrowthcraftHopsItems.lagerBottle, Reference.MODID, "lagerbooze", LagerTypes.class);
        registerFermentations();
    }

    private static void registerLagerFermentations() {
        // TODO: Add configuration for brewing time and yielding amount, like in grapes module

        final int fermentTime = GrowthcraftCellarConfig.fermentTime;
        final FluidStack[] fs = new FluidStack[GrowthcraftHopsFluids.lagerBooze.length];
        for (int i = 0; i < GrowthcraftHopsFluids.lagerBooze.length; ++i) {
            fs[i] = GrowthcraftHopsFluids.lagerBooze[i].asFluidStack();
        }
        final FluidStack[] spoilInputFs = new FluidStack[GrowthcraftHopsFluids.lagerBooze.length];
        for (int i = 0; i < GrowthcraftHopsFluids.lagerBooze.length; ++i) {
            spoilInputFs[i] = GrowthcraftHopsFluids.lagerBooze[i].asFluidStack(40);
        }


        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.lagerBooze[LagerTypes.LAGER_YOUNG.ordinal()].getFluid())
                .tags(BoozeTag.YOUNG, BoozeTag.CHILLED)
                .brewsFrom(
                        new FluidStack(GrowthcraftHopsFluids.hopAleBooze[HopAleTypes.ALE_YOUNG.ordinal()].getFluid(), 40),
                        new OreItemStacks("yeastLager"),
                        false,
                        TickUtils.minutes(1),
                        Residue.newDefault(0.0F));

        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.lagerBooze[LagerTypes.LAGER_NORMAL.ordinal()].getFluid())
                .tags(BoozeTag.FERMENTED, BoozeTag.CHILLED)
                .fermentsFrom(fs[LagerTypes.LAGER_YOUNG.ordinal()], new OreItemStacks("yeastBrewers"), fermentTime)
                .fermentsFrom(fs[LagerTypes.LAGER_YOUNG.ordinal()], new ItemStack(Items.NETHER_WART), (int) (fermentTime * 0.66))
                .getEffect()
                .setTipsy(BoozeUtils.alcoholToTipsy(0.0419f), TickUtils.seconds(45))
                .addPotionEntry(MobEffects.SPEED, TickUtils.seconds(90), 0)
                .addPotionEntry(MobEffects.HASTE, TickUtils.seconds(90), 0);

        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.lagerBooze[LagerTypes.LAGER_POTENT.ordinal()].getFluid())
                .tags(BoozeTag.FERMENTED, BoozeTag.CHILLED, BoozeTag.POTENT)
                .fermentsFrom(fs[LagerTypes.LAGER_NORMAL.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
                .fermentsFrom(fs[LagerTypes.LAGER_EXTENDED.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
                .getEffect()
                .setTipsy(BoozeUtils.alcoholToTipsy(0.0419f), TickUtils.seconds(45))
                .addPotionEntry(MobEffects.SPEED, TickUtils.seconds(90), 0)
                .addPotionEntry(MobEffects.HASTE, TickUtils.seconds(90), 0);

        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.lagerBooze[LagerTypes.LAGER_EXTENDED.ordinal()].getFluid())
                .tags(BoozeTag.FERMENTED, BoozeTag.CHILLED, BoozeTag.EXTENDED)
                .fermentsFrom(fs[LagerTypes.LAGER_NORMAL.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
                .fermentsFrom(fs[LagerTypes.LAGER_POTENT.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
                .getEffect()
                .setTipsy(BoozeUtils.alcoholToTipsy(0.0419f), TickUtils.seconds(45))
                .addPotionEntry(MobEffects.SPEED, TickUtils.seconds(90), 0)
                .addPotionEntry(MobEffects.HASTE, TickUtils.seconds(90), 0);

        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.lagerBooze[LagerTypes.LAGER_HYPEREXTENDED.ordinal()].getFluid())
                .tags(BoozeTag.FERMENTED, BoozeTag.CHILLED, BoozeTag.HYPER_EXTENDED)
                .fermentsFrom(fs[LagerTypes.LAGER_POTENT.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
                .fermentsFrom(fs[LagerTypes.LAGER_EXTENDED.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
                .getEffect()
                .setTipsy(BoozeUtils.alcoholToTipsy(0.0419f), TickUtils.seconds(45))
                .addPotionEntry(MobEffects.SPEED, TickUtils.seconds(90), 0)
                .addPotionEntry(MobEffects.HASTE, TickUtils.seconds(90), 0);

        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.lagerBooze[LagerTypes.LAGER_INTOXICATED.ordinal()].getFluid())
                .tags(BoozeTag.FERMENTED, BoozeTag.CHILLED, BoozeTag.INTOXICATED)
                .fermentsFrom(fs[LagerTypes.LAGER_POTENT.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
                .fermentsFrom(fs[LagerTypes.LAGER_EXTENDED.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
                .getEffect()
                .setTipsy(BoozeUtils.alcoholToTipsy(0.091f), TickUtils.seconds(45))
                .addEffect(new EffectWeightedRandomList()
                        .add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.HASTE, TickUtils.minutes(3), 2)))
                        .add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.MINING_FATIGUE, TickUtils.minutes(3), 2))))
                .addEffect(new EffectWeightedRandomList()
                        .add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.SPEED, TickUtils.minutes(3), 2)))
                        .add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.SLOWNESS, TickUtils.minutes(3), 2))));

        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.lagerBooze[LagerTypes.LAGER_POISONED.ordinal()].getFluid())
                .tags(BoozeTag.FERMENTED, BoozeTag.CHILLED, BoozeTag.POISONED)
//			.fermentsFrom(fs[LagerTypes.LAGER_YOUNG.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[LagerTypes.LAGER_YOUNG.ordinal()], fermentTime)
//				.brewsFromFallback(spoilInputFs[LagerTypes.LAGER_YOUNG.ordinal()], TickUtils.minutes(1), null)
                .fermentsFrom(fs[LagerTypes.LAGER_NORMAL.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[LagerTypes.LAGER_NORMAL.ordinal()], fermentTime)
                .brewsFromFallback(spoilInputFs[LagerTypes.LAGER_NORMAL.ordinal()], TickUtils.minutes(1), null)
                .fermentsFrom(fs[LagerTypes.LAGER_POTENT.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[LagerTypes.LAGER_POTENT.ordinal()], fermentTime)
                .brewsFromFallback(spoilInputFs[LagerTypes.LAGER_POTENT.ordinal()], TickUtils.minutes(1), null)
                .fermentsFrom(fs[LagerTypes.LAGER_EXTENDED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[LagerTypes.LAGER_EXTENDED.ordinal()], fermentTime)
                .brewsFromFallback(spoilInputFs[LagerTypes.LAGER_EXTENDED.ordinal()], TickUtils.minutes(1), null)
                .fermentsFrom(fs[LagerTypes.LAGER_HYPEREXTENDED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[LagerTypes.LAGER_HYPEREXTENDED.ordinal()], fermentTime)
                .brewsFromFallback(spoilInputFs[LagerTypes.LAGER_HYPEREXTENDED.ordinal()], TickUtils.minutes(1), null)
                .fermentsFrom(fs[LagerTypes.LAGER_INTOXICATED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[LagerTypes.LAGER_INTOXICATED.ordinal()], fermentTime)
                .brewsFromFallback(spoilInputFs[LagerTypes.LAGER_INTOXICATED.ordinal()], TickUtils.minutes(1), null)
                .fermentsFromFallback(fs[LagerTypes.LAGER_POISONED.ordinal()], fermentTime)
                .brewsFromFallback(spoilInputFs[LagerTypes.LAGER_POISONED.ordinal()], TickUtils.minutes(1), null)
                .getEffect()
                .setTipsy(BoozeUtils.alcoholToTipsy(0.0419f), TickUtils.seconds(45))
                .createPotionEntry(MobEffects.POISON, TickUtils.seconds(90), 0).toggleDescription(!GrowthcraftCore.config.getHidePoisonedBooze());
    }

    private static void registerHopAleFermentations() {
        // TODO: Add configuration for brewing time and yielding amount, like in grapes module

        final int fermentTime = GrowthcraftCellarConfig.fermentTime;
        final FluidStack[] fs = new FluidStack[GrowthcraftHopsFluids.hopAleBooze.length];
        for (int i = 0; i < GrowthcraftHopsFluids.hopAleBooze.length; ++i) {
            fs[i] = GrowthcraftHopsFluids.hopAleBooze[i].asFluidStack();
        }
        final FluidStack[] spoilInputFs = new FluidStack[GrowthcraftHopsFluids.hopAleBooze.length];
        for (int i = 0; i < GrowthcraftHopsFluids.hopAleBooze.length; ++i) {
            spoilInputFs[i] = GrowthcraftHopsFluids.hopAleBooze[i].asFluidStack(40);
        }

        // Unhopped
        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.hopAleBooze[HopAleTypes.ALE_YOUNG.ordinal()].getFluid())
                .tags(BoozeTag.YOUNG)
                .brewsFrom(
                        new FluidStack(FluidRegistry.WATER, 40),
                        new OreItemStacks("cropWheat"),
                        false,
                        TickUtils.minutes(1),
                        Residue.newDefault(0.3F));

        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.hopAleBooze[HopAleTypes.ALE_UNHOPPED.ordinal()].getFluid())
                .tags(BoozeTag.YOUNG, BoozeTag.HOPPED)
                .brewsFrom(
                        new FluidStack(GrowthcraftHopsFluids.hopAleBooze[HopAleTypes.ALE_YOUNG.ordinal()].getFluid(), 40),
                        new OreItemStacks("cropHops"),
                        false,
                        TickUtils.minutes(1),
                        Residue.newDefault(0.0F));

        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.hopAleBooze[HopAleTypes.ALE_NORMAL.ordinal()].getFluid())
                .tags(BoozeTag.FERMENTED)
                .fermentsFrom(fs[HopAleTypes.ALE_UNHOPPED.ordinal()], new OreItemStacks("yeastBrewers"), fermentTime)
                .fermentsFrom(fs[HopAleTypes.ALE_UNHOPPED.ordinal()], new ItemStack(Items.NETHER_WART), (int) (fermentTime * 0.66))
                .getEffect()
                .setTipsy(BoozeUtils.alcoholToTipsy(0.10f), TickUtils.seconds(45))
                .addPotionEntry(MobEffects.HASTE, TickUtils.minutes(3), 0);

        // Glowstone
        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.hopAleBooze[HopAleTypes.ALE_POTENT.ordinal()].getFluid())
                .tags(BoozeTag.HOPPED, BoozeTag.FERMENTED, BoozeTag.POTENT)
                .fermentsFrom(fs[HopAleTypes.ALE_NORMAL.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
                .fermentsFrom(fs[HopAleTypes.ALE_EXTENDED.ordinal()], new OreItemStacks("dustGlowstone"), fermentTime)
                .getEffect()
                .setTipsy(BoozeUtils.alcoholToTipsy(0.12f), TickUtils.seconds(45))
                .addPotionEntry(MobEffects.HASTE, TickUtils.minutes(3), 0);

        // Redstone
        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.hopAleBooze[HopAleTypes.ALE_EXTENDED.ordinal()].getFluid())
                .tags(BoozeTag.HOPPED, BoozeTag.FERMENTED, BoozeTag.EXTENDED)
                .fermentsFrom(fs[HopAleTypes.ALE_NORMAL.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
                .fermentsFrom(fs[HopAleTypes.ALE_POTENT.ordinal()], new OreItemStacks("dustRedstone"), fermentTime)
                .getEffect()
                .setTipsy(BoozeUtils.alcoholToTipsy(0.10f), TickUtils.seconds(45))
                .addPotionEntry(MobEffects.HASTE, TickUtils.minutes(3), 0);

        // Ethereal Yeast
        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.hopAleBooze[HopAleTypes.ALE_HYPEREXTENDED.ordinal()].getFluid())
                .tags(BoozeTag.HOPPED, BoozeTag.FERMENTED, BoozeTag.HYPER_EXTENDED)
                .fermentsFrom(fs[HopAleTypes.ALE_POTENT.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
                .fermentsFrom(fs[HopAleTypes.ALE_EXTENDED.ordinal()], new OreItemStacks("yeastEthereal"), fermentTime)
                .getEffect()
                .setTipsy(BoozeUtils.alcoholToTipsy(0.10f), TickUtils.seconds(45))
                .addPotionEntry(MobEffects.HASTE, TickUtils.minutes(3), 0);

        // Lager Yeast
        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.hopAleBooze[HopAleTypes.ALE_LAGER.ordinal()].getFluid())
                .tags(BoozeTag.HOPPED, BoozeTag.FERMENTED, BoozeTag.CHILLED)
                .fermentsFrom(fs[HopAleTypes.ALE_HYPEREXTENDED.ordinal()], new OreItemStacks("yeastLager"), fermentTime)
                .getEffect()
                .setTipsy(BoozeUtils.alcoholToTipsy(0.071f), TickUtils.seconds(45))
                .addPotionEntry(MobEffects.HASTE, TickUtils.minutes(10), 2);

        // Intoxicated - Origin
        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.hopAleBooze[HopAleTypes.ALE_INTOXICATED.ordinal()].getFluid())
                .tags(BoozeTag.HOPPED, BoozeTag.FERMENTED, BoozeTag.INTOXICATED)
                .fermentsFrom(fs[HopAleTypes.ALE_POTENT.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
                .fermentsFrom(fs[HopAleTypes.ALE_EXTENDED.ordinal()], new OreItemStacks("yeastOrigin"), fermentTime)
                .getEffect()
                .setTipsy(BoozeUtils.alcoholToTipsy(0.20f), TickUtils.seconds(45))
                .addEffect(new EffectWeightedRandomList()
                        .add(8, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.HASTE, TickUtils.minutes(3), 2)))
                        .add(2, new EffectAddPotionEffect(new SimplePotionEffectFactory(MobEffects.MINING_FATIGUE, TickUtils.minutes(3), 2))));

        // Poisoned - Netherrash
        // Regardless of what you brewed it with, it will kill the hops in the
        // booze and poison it.
        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftHopsFluids.hopAleBooze[HopAleTypes.ALE_POISONED.ordinal()].getFluid())
                .tags(BoozeTag.FERMENTED, BoozeTag.POISONED)
//			.fermentsFromFallback(fs[HopAleTypes.ALE_YOUNG.ordinal()], fermentTime)
//				.brewsFromFallback(spoilInputFs[HopAleTypes.ALE_YOUNG.ordinal()], TickUtils.minutes(1), null)
//			.fermentsFromFallback(fs[HopAleTypes.ALE_UNHOPPED.ordinal()], fermentTime)
//				.brewsFromFallback(spoilInputFs[HopAleTypes.ALE_UNHOPPED.ordinal()], TickUtils.minutes(1), null)
                .fermentsFrom(fs[HopAleTypes.ALE_NORMAL.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[HopAleTypes.ALE_NORMAL.ordinal()], fermentTime)
                .brewsFromFallback(spoilInputFs[HopAleTypes.ALE_NORMAL.ordinal()], TickUtils.minutes(1), null)
                .fermentsFrom(fs[HopAleTypes.ALE_POTENT.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[HopAleTypes.ALE_POTENT.ordinal()], fermentTime)
                .brewsFromFallback(spoilInputFs[HopAleTypes.ALE_POTENT.ordinal()], TickUtils.minutes(1), null)
                .fermentsFrom(fs[HopAleTypes.ALE_EXTENDED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[HopAleTypes.ALE_EXTENDED.ordinal()], fermentTime)
                .brewsFromFallback(spoilInputFs[HopAleTypes.ALE_EXTENDED.ordinal()], TickUtils.minutes(1), null)
                .fermentsFrom(fs[HopAleTypes.ALE_HYPEREXTENDED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[HopAleTypes.ALE_HYPEREXTENDED.ordinal()], fermentTime)
                .brewsFromFallback(spoilInputFs[HopAleTypes.ALE_HYPEREXTENDED.ordinal()], TickUtils.minutes(1), null)
                .fermentsFrom(fs[HopAleTypes.ALE_LAGER.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[HopAleTypes.ALE_LAGER.ordinal()], fermentTime)
                .brewsFromFallback(spoilInputFs[HopAleTypes.ALE_LAGER.ordinal()], TickUtils.minutes(1), null)
                .fermentsFrom(fs[HopAleTypes.ALE_INTOXICATED.ordinal()], new OreItemStacks("yeastPoison"), fermentTime).fermentsFromFallback(fs[HopAleTypes.ALE_INTOXICATED.ordinal()], fermentTime)
                .brewsFromFallback(spoilInputFs[HopAleTypes.ALE_INTOXICATED.ordinal()], TickUtils.minutes(1), null)
                .fermentsFromFallback(fs[HopAleTypes.ALE_POISONED.ordinal()], fermentTime)
                .brewsFromFallback(spoilInputFs[HopAleTypes.ALE_POISONED.ordinal()], TickUtils.minutes(1), null)
                .getEffect()
                .setTipsy(BoozeUtils.alcoholToTipsy(0.10f), TickUtils.seconds(45))
                .createPotionEntry(MobEffects.POISON, TickUtils.seconds(90), 0).toggleDescription(!GrowthcraftCore.config.getHidePoisonedBooze());
    }

    private static void registerFermentations() {
        registerHopAleFermentations();
        registerLagerFermentations();
    }

    @SideOnly(Side.CLIENT)
    public static void registerFluidRenders() {
        BoozeRegistryHelper.registerBoozeRenderers(GrowthcraftHopsFluids.lagerBooze, GrowthcraftHopsBlocks.lagerFluidBlocks);
        BoozeRegistryHelper.registerBoozeRenderers(GrowthcraftHopsFluids.hopAleBooze, GrowthcraftHopsBlocks.hopAleFluidBlocks);
    }


    ////////
    // Recipes
    ////////

    public static void registerRecipes() {
        registerCraftingRecipes();
    }

    private static void registerCraftingRecipes() {
        // TODO: RECIPE_REGISTER!

//		GameRegistry.addShapelessRecipe(GrowthcraftHopsItems.hop_seeds.asStack(1), GrowthcraftHopsItems.hops.asStack(1) );
    }

    // Loot
    ////////


    /**
     * Loot tables need to be in the preInit but after the items and blocks.
     */
    public static void preInitLootTables() {
        GrowthcraftHopsLootTables.registerLootTables();
    }

}
