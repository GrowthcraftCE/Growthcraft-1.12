package growthcraft.rice.common;

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
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.definition.ItemTypeDefinition;
import growthcraft.core.shared.utils.TickUtils;
import growthcraft.rice.common.block.BlockPaddy;
import growthcraft.rice.common.block.BlockRiceCrop;
import growthcraft.rice.common.item.ItemCultivator;
import growthcraft.rice.common.item.ItemFoodRice;
import growthcraft.rice.common.item.ItemRice;
import growthcraft.rice.common.item.ItemRiceBall;
import growthcraft.rice.shared.Reference;
import growthcraft.rice.shared.config.GrowthcraftRiceConfig;
import growthcraft.rice.shared.init.GrowthcraftRiceBlocks;
import growthcraft.rice.shared.init.GrowthcraftRiceFluids;
import growthcraft.rice.shared.init.GrowthcraftRiceItems;
import growthcraft.rice.shared.init.GrowthcraftRiceItems.SakeTypes;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Iterator;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;
import static growthcraft.rice.shared.init.GrowthcraftRiceFluids.sakeBooze;

public class Init {
    private Init() {
        // Nothing to do.
    }

    ////////
    // Blocks
    ////////

    public static void preInitBlocks() {
        GrowthcraftRiceBlocks.ricePaddy = new BlockDefinition(new BlockPaddy("ricePaddy"));
        GrowthcraftRiceBlocks.riceCrop = new BlockDefinition(new BlockRiceCrop("riceCrop"));
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        GrowthcraftRiceBlocks.ricePaddy.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftRiceBlocks.ricePaddy.registerBlock(registry);

        GrowthcraftRiceBlocks.riceCrop.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftRiceBlocks.riceCrop.registerBlock(registry);
    }

    public static void registerBlockItems(IForgeRegistry<Item> registry) {
        GrowthcraftRiceBlocks.ricePaddy.registerBlockItem(registry);
        GrowthcraftRiceBlocks.riceCrop.registerBlockItem(registry);
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockRenders() {
        GrowthcraftRiceBlocks.ricePaddy.registerItemRender();
        GrowthcraftRiceBlocks.riceCrop.registerItemRender();
    }

    public static void  registerTileEntities() {
        // TODO: register tile entities.
    }

    ////////////
    // Items
    ////////////

    public static void preInitItems() {
        GrowthcraftRiceItems.cultivator = new ItemDefinition( new ItemCultivator("cultivator"));
        GrowthcraftRiceItems.rice = new ItemDefinition( new ItemRice("rice"));
        GrowthcraftRiceItems.rice_ball = new ItemDefinition( new ItemRiceBall("rice_ball"));
        GrowthcraftRiceItems.rice_cooked = new ItemDefinition( new ItemFoodRice("rice_cooked"));
        GrowthcraftRiceItems.sakeBottle = new ItemTypeDefinition<ItemBoozeBottle>( new ItemBoozeBottle() );
    }

    public static void registerItemOres() {
        // TODO: register ore dictionaries.
        OreDictionary.registerOre("foodRice", GrowthcraftRiceItems.rice_cooked.asStack());
        OreDictionary.registerOre("seedRice", GrowthcraftRiceItems.rice.asStack());
        OreDictionary.registerOre("foodRice", GrowthcraftRiceItems.rice_ball.asStack());
    }

    public static void registerItems(IForgeRegistry<Item> registry) {

        GrowthcraftRiceItems.cultivator.registerItem(registry);
        GrowthcraftRiceItems.cultivator.getItem().setCreativeTab(tabGrowthcraft);

        GrowthcraftRiceItems.sakeBottle.registerItem(registry, new ResourceLocation(Reference.MODID, "sakebottle"));
        GrowthcraftRiceItems.sakeBottle.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftRiceItems.sakeBottle.getItem().setBoozes(sakeBooze);

        GrowthcraftRiceItems.rice_cooked.registerItem(registry);
        GrowthcraftRiceItems.rice_cooked.getItem().setCreativeTab(tabGrowthcraft);

        GrowthcraftRiceItems.rice.registerItem(registry);
        GrowthcraftRiceItems.rice.getItem().setCreativeTab(tabGrowthcraft);
        MinecraftForge.addGrassSeed(GrowthcraftRiceItems.rice.asStack(), 1);


        GrowthcraftRiceItems.rice_ball.registerItem(registry);
        GrowthcraftRiceItems.rice_ball.getItem().setCreativeTab(tabGrowthcraft);

        Iterator iterator = GrowthcraftRiceConfig.ADD_GRASS_DROPS.iterator();
        while (iterator.hasNext()) {
            String itemRegistryName = (String)iterator.next();
            Item item = Item.REGISTRY.getObject(new ResourceLocation(itemRegistryName));
            if ( item != null ) {
                ItemStack itemStack = new ItemStack(item);
                MinecraftForge.addGrassSeed(itemStack, 1);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemRenders() {
        GrowthcraftRiceItems.cultivator.registerRender();
        GrowthcraftRiceItems.sakeBottle.registerRenders(SakeTypes.class);
        GrowthcraftRiceItems.rice_cooked.registerRender();
        GrowthcraftRiceItems.rice.registerRender();
        GrowthcraftRiceItems.rice_ball.registerRender();
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemColorHandlers() {
        ItemRenderUtils.registerItemColorHandler(GrowthcraftRiceItems.sakeBottle.getItem());
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemVariants() {
        GrowthcraftRiceItems.sakeBottle.registerModelBakeryVariants(SakeTypes.class);
    }

    ////////////
    // Fluids
    ////////////

    public static void preInitFluids() {
        // Initialize sakeBooze
        sakeBooze = new BoozeDefinition[SakeTypes.values().length];
        BoozeRegistryHelper.initializeAndRegisterBoozeFluids(sakeBooze, SakeTypes.class, "");

        // Set the fluid color and density for each of the fluid sub-types.
        sakeBooze[SakeTypes.SAKE_MASH.ordinal()].getFluid().setColor(GrowthcraftRiceConfig.sakeMashColor).setDensity(1120);
        sakeBooze[SakeTypes.SAKE_POTENT.ordinal()].getFluid().setColor(GrowthcraftRiceConfig.sakePotentColor).setDensity(1120);
        sakeBooze[SakeTypes.SAKE_EXTENDED.ordinal()].getFluid().setColor(GrowthcraftRiceConfig.sakeExtendedColor).setDensity(1120);
        sakeBooze[SakeTypes.SAKE_FERMENTED.ordinal()].getFluid().setColor(GrowthcraftRiceConfig.sakeYoungColor).setDensity(1120);
        sakeBooze[SakeTypes.SAKE_HYPEREXTENDED.ordinal()].getFluid().setColor(GrowthcraftRiceConfig.sakeHyperExtendedColor).setDensity(1120);
        sakeBooze[SakeTypes.SAKE_POISONED.ordinal()].getFluid().setColor(GrowthcraftRiceConfig.sakePoisonedColor).setDensity(1120);

    }

    public static void registerFluidBlocks(IForgeRegistry<Block> registry) {

        // Initialize the Sake fluid blocks
        GrowthcraftRiceBlocks.sakeFluidBlocks = new BlockBoozeDefinition[sakeBooze.length];
        BoozeRegistryHelper.initializeBooze(sakeBooze, GrowthcraftRiceBlocks.sakeFluidBlocks);

        // Set the default booze food stats.
        BoozeRegistryHelper.setBoozeFoodStats(sakeBooze, 1, -0.3f);
        // Override any booze food stats.
        BoozeRegistryHelper.setBoozeFoodStats(sakeBooze[SakeTypes.SAKE_FERMENTED.ordinal()], 1, 0.3f);

        BoozeRegistryHelper.registerBoozeBlocks(registry, sakeBooze, GrowthcraftRiceBlocks.sakeFluidBlocks, Reference.MODID, "sakebooze", SakeTypes.class);

    }

    public static void initBoozes() {
        BoozeRegistryHelper.initBoozeContainers(sakeBooze, GrowthcraftRiceItems.sakeBottle, Reference.MODID, "sakebooze", SakeTypes.class);

        registerFermentations();
    }

    @SideOnly(Side.CLIENT)
    public static void registerFluidRenders() {
        BoozeRegistryHelper.registerBoozeRenderers(sakeBooze, GrowthcraftRiceBlocks.sakeFluidBlocks);
    }

    private static void registerFermentations() {
        // TODO: Implement boozeBuidlerFactory for Sake
    	// TODO: Add configuration for brewing yield amount as it is done in grapes module
        final int fermentTime = GrowthcraftCellarConfig.fermentTime;

        final FluidStack[] fs = new FluidStack[sakeBooze.length];
        for ( int i = 0; i < sakeBooze.length; ++i ) {
            fs[i] = sakeBooze[i].asFluidStack();
        }
        
        final FluidStack[] spoilInputFs = new FluidStack[sakeBooze.length];
        for ( int i = 0; i < sakeBooze.length; ++i ) {
        	spoilInputFs[i] = sakeBooze[i].asFluidStack(200);
        }

        /**
         * Water + Rice = SAKE_WATER (Rice Water)
         * Rice Water + Koji = SAKE_MASH
         * Rice Water + Sake Yeast = SAKE_MASH
         * SAKE_MASH + Nether Wort = SAKE_FERMENTED
         *
         */

        // SAKE_WATER, Water and Rice in the Brew Kettle
        GrowthcraftCellarApis.boozeBuilderFactory.create(sakeBooze[SakeTypes.SAKE_WATER.ordinal()].getFluid())
            .tags(BoozeTag.SAKE)
            .brewsFrom(
                new FluidStack(FluidRegistry.WATER, 200),
                GrowthcraftRiceItems.rice.asStack(),
                true,
                TickUtils.minutes(GrowthcraftRiceConfig.brewTime),
                new Residue(GrowthcraftRiceItems.rice_cooked.asStack(), 1.0F));

        // TODO: Implement Moto Sake Mash made from Sake Yeast.
        // SAKE_MASH, SAKE_WATER and Mushrooms (Koji) or Sake Yeast (Moto, Not Implemented)
        GrowthcraftCellarApis.boozeBuilderFactory.create(sakeBooze[SakeTypes.SAKE_MASH.ordinal()].getFluid())
            .tags(BoozeTag.SAKE, BoozeTag.MASH)
            .brewsFrom(
                new FluidStack(GrowthcraftRiceFluids.sakeBooze[SakeTypes.SAKE_WATER.ordinal()].getFluid(), 250),
                new ItemStack(Item.getItemFromBlock(Blocks.BROWN_MUSHROOM)),
                false,
                TickUtils.minutes(1),
                Residue.newDefault(0.0F))
            .brewsFrom(
                new FluidStack(GrowthcraftRiceFluids.sakeBooze[SakeTypes.SAKE_WATER.ordinal()].getFluid(), 250),
                new ItemStack(Item.getItemFromBlock(Blocks.RED_MUSHROOM)),
                false,
                TickUtils.minutes(1),
                Residue.newDefault(0.0F));

        // SAKE_FERMENTED, Sake Mash and Nether Wort in Fermentation Barrel
        GrowthcraftCellarApis.boozeBuilderFactory.create(sakeBooze[SakeTypes.SAKE_FERMENTED.ordinal()].getFluid())
            .tags(BoozeTag.SAKE, BoozeTag.FERMENTED)
            .fermentsFrom(
                fs[SakeTypes.SAKE_MASH.ordinal()],
                new ItemStack(Items.NETHER_WART),
                (int)(fermentTime * 0.66) )
            .getEffect()
            .setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
            .addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(1), 0);

        // SAKE_POTENT, SAKE_FERMENTED and Glowstone in Fermentation Barrel
        GrowthcraftCellarApis.boozeBuilderFactory.create((sakeBooze[SakeTypes.SAKE_POTENT.ordinal()].getFluid()))
            .tags(BoozeTag.SAKE, BoozeTag.FERMENTED, BoozeTag.POTENT)
            .fermentsFrom(
                fs[SakeTypes.SAKE_FERMENTED.ordinal()],
                new ItemStack(Items.GLOWSTONE_DUST),
                fermentTime)
            .getEffect()
            .setTipsy(BoozeUtils.alcoholToTipsy(0.10f), TickUtils.seconds(90))
            .addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(1), 1);

        // TODO: SAKE_EXTENDED, SAKE_FERMENTED + Redstone
        GrowthcraftCellarApis.boozeBuilderFactory.create(sakeBooze[SakeTypes.SAKE_EXTENDED.ordinal()].getFluid())
            .tags(BoozeTag.SAKE, BoozeTag.FERMENTED, BoozeTag.EXTENDED)
            .fermentsFrom(
                fs[SakeTypes.SAKE_FERMENTED.ordinal()],
                new ItemStack(Items.GLOWSTONE_DUST),
                fermentTime)
            .getEffect()
            .setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
            .addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 0);
            
        // TODO: SAKE_HYPEREXTENDED, SAKE_EXTENDED + Redstone
        GrowthcraftCellarApis.boozeBuilderFactory.create(sakeBooze[SakeTypes.SAKE_HYPEREXTENDED.ordinal()].getFluid())
            .tags(BoozeTag.SAKE, BoozeTag.FERMENTED, BoozeTag.HYPER_EXTENDED)
            .fermentsFrom(
                fs[SakeTypes.SAKE_FERMENTED.ordinal()],
                new ItemStack(Items.GLOWSTONE_DUST),
                fermentTime)
            .getEffect()
            .setTipsy(BoozeUtils.alcoholToTipsy(0.05f), TickUtils.seconds(90))
            .addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(8), 0);
        
        // TODO: SAKE_POTENT_EXTENDED, SAKE_POTENT + Redstone
        GrowthcraftCellarApis.boozeBuilderFactory.create((sakeBooze[SakeTypes.SAKE_POTENT_EXTENDED.ordinal()].getFluid()))
            .tags(BoozeTag.SAKE, BoozeTag.FERMENTED, BoozeTag.POTENT, BoozeTag.EXTENDED)
            .fermentsFrom(
                fs[SakeTypes.SAKE_POTENT.ordinal()],
                new ItemStack(Items.REDSTONE),
                fermentTime)
            .getEffect()
            .setTipsy(BoozeUtils.alcoholToTipsy(0.10f), TickUtils.seconds(90))
            .addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(3), 1);
            
        // TODO: SAKE_POTENT_HYPEREXTENDED, SAKE_POTENT_EXTENDED + Redstone
        GrowthcraftCellarApis.boozeBuilderFactory.create((sakeBooze[SakeTypes.SAKE_POTENT_HYPEREXTENDED.ordinal()].getFluid()))
            .tags(BoozeTag.SAKE, BoozeTag.FERMENTED, BoozeTag.POTENT, BoozeTag.HYPER_EXTENDED)
            .fermentsFrom(
                fs[SakeTypes.SAKE_POTENT_EXTENDED.ordinal()],
                new ItemStack(Items.REDSTONE),
                fermentTime)
            .getEffect()
            .setTipsy(BoozeUtils.alcoholToTipsy(0.10f), TickUtils.seconds(90))
            .addPotionEntry(MobEffects.RESISTANCE, TickUtils.minutes(8), 1);
        
        // TODO: SAKE_POISONED
        GrowthcraftCellarApis.boozeBuilderFactory.create((sakeBooze[SakeTypes.SAKE_POISONED.ordinal()].getFluid()))
        	.tags(BoozeTag.SAKE, BoozeTag.FERMENTED, BoozeTag.POISONED)
//        	.fermentsFromFallback(fs[SakeTypes.SAKE_WATER.ordinal()], fermentTime)
//        		.brewsFromFallback(spoilInputFs[SakeTypes.SAKE_WATER.ordinal()], GrowthcraftRiceConfig.brewTime, null)		// Disabled, because it would appear strange if non alcoholic drink would turn to alcoholic drinks
//        	.fermentsFromFallback(fs[SakeTypes.SAKE_MASH.ordinal()], fermentTime)
//        		.brewsFromFallback(spoilInputFs[SakeTypes.SAKE_MASH.ordinal()], GrowthcraftRiceConfig.brewTime, null)
        	.fermentsFromFallback(fs[SakeTypes.SAKE_FERMENTED.ordinal()], fermentTime)
        		.brewsFromFallback(spoilInputFs[SakeTypes.SAKE_FERMENTED.ordinal()], GrowthcraftRiceConfig.brewTime, null)
        	.fermentsFromFallback(fs[SakeTypes.SAKE_POTENT.ordinal()], fermentTime)
        		.brewsFromFallback(spoilInputFs[SakeTypes.SAKE_POTENT.ordinal()], GrowthcraftRiceConfig.brewTime, null)
        	.fermentsFromFallback(fs[SakeTypes.SAKE_EXTENDED.ordinal()], fermentTime)
        		.brewsFromFallback(spoilInputFs[SakeTypes.SAKE_EXTENDED.ordinal()], GrowthcraftRiceConfig.brewTime, null)
        	.fermentsFromFallback(fs[SakeTypes.SAKE_HYPEREXTENDED.ordinal()], fermentTime)
        		.brewsFromFallback(spoilInputFs[SakeTypes.SAKE_HYPEREXTENDED.ordinal()], GrowthcraftRiceConfig.brewTime, null)
        	.fermentsFromFallback(fs[SakeTypes.SAKE_POTENT_EXTENDED.ordinal()], fermentTime)
        		.brewsFromFallback(spoilInputFs[SakeTypes.SAKE_POTENT_EXTENDED.ordinal()], GrowthcraftRiceConfig.brewTime, null)
        	.fermentsFromFallback(fs[SakeTypes.SAKE_POTENT_HYPEREXTENDED.ordinal()], fermentTime)
        		.brewsFromFallback(spoilInputFs[SakeTypes.SAKE_POTENT_HYPEREXTENDED.ordinal()], GrowthcraftRiceConfig.brewTime, null)
        	.fermentsFromFallback(fs[SakeTypes.SAKE_POISONED.ordinal()], fermentTime)
        		.brewsFromFallback(spoilInputFs[SakeTypes.SAKE_POISONED.ordinal()], GrowthcraftRiceConfig.brewTime, null)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.10f), TickUtils.seconds(90))
				.createPotionEntry(MobEffects.POISON, TickUtils.seconds(90), 0).toggleDescription(!GrowthcraftCoreConfig.hidePoisonedBooze);


    }

    ////////////
    // Recipes
    ////////////

    public static void initRecipes() {
        // No thing to do
    }

    public static void registerRecipes() {
        registerCraftingRecipes();
    }

    private static void registerCraftingRecipes() {
        // TODO: RECIPE_REGISTER!
    }

    ////////////
    // Loot
    ////////////

    public static void lootLoad(LootTableLoadEvent event) {
        // TODO: add items to the dungeon loot table.
    }

}