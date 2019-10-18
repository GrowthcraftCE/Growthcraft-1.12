package growthcraft.milk.common;

import growthcraft.cellar.shared.GrowthcraftCellarApis;
import growthcraft.cellar.shared.booze.BoozeRegistryHelper;
import growthcraft.cellar.shared.booze.BoozeTag;
import growthcraft.cellar.shared.booze.BoozeUtils;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.definition.BlockBoozeDefinition;
import growthcraft.cellar.shared.definition.BoozeDefinition;
import growthcraft.cellar.shared.init.GrowthcraftCellarPotions;
import growthcraft.cellar.shared.item.ItemBoozeBottle;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.cellar.shared.utils.BoozeHelper;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.GrowthcraftCoreApis;
import growthcraft.core.shared.client.render.utils.ItemRenderUtils;
import growthcraft.core.shared.compat.Compat;
import growthcraft.core.shared.config.GrowthcraftCoreConfig;
import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.definition.ItemTypeDefinition;
import growthcraft.core.shared.effect.*;
import growthcraft.core.shared.fluids.FluidFactory;
import growthcraft.core.shared.fluids.FluidFactory.FluidDetails;
import growthcraft.core.shared.fluids.FluidFactory.FluidDetailsBuilder;
import growthcraft.core.shared.fluids.FluidTag;
import growthcraft.core.shared.fluids.TaggedFluidStacks;
import growthcraft.core.shared.item.CommonItemStackComparator;
import growthcraft.core.shared.item.IItemStackComparator;
import growthcraft.core.shared.item.ItemFoodBottleFluid;
import growthcraft.core.shared.item.OreItemStacks;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import growthcraft.core.shared.utils.TickUtils;
import growthcraft.milk.GrowthcraftMilk;
import growthcraft.milk.client.render.RenderButterChurnPlunger;
import growthcraft.milk.client.render.RenderCheesePress;
import growthcraft.milk.client.render.RenderCheeseVat;
import growthcraft.milk.client.render.RenderPancheon;
import growthcraft.milk.common.block.*;
import growthcraft.milk.common.block.fluids.*;
import growthcraft.milk.common.fluids.*;
import growthcraft.milk.common.item.*;
import growthcraft.milk.common.lib.effect.EffectMilk;
import growthcraft.milk.common.lib.processing.DriedCurdsCheesePressRecipe;
import growthcraft.milk.common.tileentity.*;
import growthcraft.milk.shared.MilkRegistry;
import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.cheese.CheeseUtils;
import growthcraft.milk.shared.config.GrowthcraftMilkConfig;
import growthcraft.milk.shared.definition.*;
import growthcraft.milk.shared.fluids.MilkFluidTags;
import growthcraft.milk.shared.init.GrowthcraftMilkBlocks;
import growthcraft.milk.shared.init.GrowthcraftMilkFluids;
import growthcraft.milk.shared.init.GrowthcraftMilkItems;
import growthcraft.milk.shared.init.GrowthcraftMilkItems.*;
import growthcraft.milk.shared.utils.CheeseVatRecipeBuilder;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;
import java.util.Map;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;
import static growthcraft.milk.shared.init.GrowthcraftMilkFluids.kumisBooze;
import static net.minecraftforge.fml.common.registry.GameRegistry.registerTileEntity;

public class Init {
    private Init() {
    }

    ////////
    // Blocks
    ////////

    public static void preInitBlocks() {
        GrowthcraftMilkBlocks.thistle = new BlockDefinition(new BlockThistle("thistle"));
        GrowthcraftMilkBlocks.cheeseVat = new BlockDefinition(new BlockCheeseVat("cheese_vat"));
        if (GrowthcraftMilkFluids.milk != null)
            GrowthcraftMilkBlocks.blockFluidMilk = GrowthcraftMilkFluids.milk.getFluidBlockDefinition();
        GrowthcraftMilkBlocks.blockFluidRennet = GrowthcraftMilkFluids.rennet.getFluidBlockDefinition();
        GrowthcraftMilkBlocks.blockFluidButterMilk = GrowthcraftMilkFluids.butterMilk.getFluidBlockDefinition();
        GrowthcraftMilkBlocks.blockFluidCream = GrowthcraftMilkFluids.cream.getFluidBlockDefinition();
        GrowthcraftMilkBlocks.blockFluidMilkCurds = GrowthcraftMilkFluids.curds.getFluidBlockDefinition();
        GrowthcraftMilkBlocks.blockFluidPasteurizedMilk = GrowthcraftMilkFluids.pasteurizedMilk.getFluidBlockDefinition();
        GrowthcraftMilkBlocks.blockFluidSkimMilk = GrowthcraftMilkFluids.skimMilk.getFluidBlockDefinition();
        GrowthcraftMilkBlocks.blockFluidWhey = GrowthcraftMilkFluids.whey.getFluidBlockDefinition();

        GrowthcraftMilkBlocks.pancheon = new BlockDefinition(new BlockPancheon("pancheon"));
        GrowthcraftMilkBlocks.churn = new BlockDefinition(new BlockButterChurn("churn"));
        GrowthcraftMilkBlocks.churnPlunger = new BlockDefinition(new BlockButterChurnPlunger("churn_plunger"));
        GrowthcraftMilkBlocks.cheesePress = new BlockDefinition(new BlockCheesePress("cheese_press"));
        GrowthcraftMilkBlocks.agedCheeseBlock = new BlockDefinition(new BlockCheeseBlock());
        GrowthcraftMilkBlocks.waxedCheeseBlock = new BlockDefinition(new BlockCheeseBlock());
        GrowthcraftMilkBlocks.agedCheeseCurds = new BlockDefinition(new BlockHangingCurds());
        GrowthcraftMilkBlocks.waxedCheeseCurds = new BlockDefinition(new BlockHangingCurds());
        GrowthcraftMilkBlocks.simpleCheeseCurds = new BlockDefinition(new BlockHangingCurds());
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        GrowthcraftMilkBlocks.thistle.registerBlock(registry);
        GrowthcraftMilkBlocks.cheeseVat.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkBlocks.cheeseVat.registerBlock(registry);
        GrowthcraftMilkBlocks.pancheon.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkBlocks.pancheon.registerBlock(registry);
        GrowthcraftMilkBlocks.churn.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkBlocks.churn.registerBlock(registry);
        GrowthcraftMilkBlocks.churnPlunger.registerBlock(registry);
        GrowthcraftMilkBlocks.cheesePress.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkBlocks.cheesePress.registerBlock(registry);
        GrowthcraftMilkBlocks.agedCheeseBlock.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkBlocks.agedCheeseBlock.registerBlock(registry, new ResourceLocation(Reference.MODID, "cheese_aged"));
        GrowthcraftMilkBlocks.waxedCheeseBlock.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkBlocks.waxedCheeseBlock.registerBlock(registry, new ResourceLocation(Reference.MODID, "cheese_waxed"));
        GrowthcraftMilkBlocks.agedCheeseCurds.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkBlocks.agedCheeseCurds.registerBlock(registry, new ResourceLocation(Reference.MODID, "curds_aged"));
        GrowthcraftMilkBlocks.waxedCheeseCurds.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkBlocks.waxedCheeseCurds.registerBlock(registry, new ResourceLocation(Reference.MODID, "curds_waxed"));
        GrowthcraftMilkBlocks.simpleCheeseCurds.getBlock().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkBlocks.simpleCheeseCurds.registerBlock(registry, new ResourceLocation(Reference.MODID, "curds_simple"));
    }

    public static void registerBlockItems(IForgeRegistry<Item> registry) {
        GrowthcraftMilkBlocks.cheeseVat.registerBlockItem(registry);
        GrowthcraftMilkBlocks.pancheon.registerBlockItem(registry);
        GrowthcraftMilkBlocks.churn.registerBlockItem(registry);
        GrowthcraftMilkBlocks.cheesePress.registerBlockItem(registry);
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockRenders() {
        GrowthcraftMilkBlocks.thistle.registerItemRender();
        GrowthcraftMilkBlocks.cheeseVat.registerItemRender();
        GrowthcraftMilkBlocks.pancheon.registerItemRender();
        GrowthcraftMilkBlocks.churn.registerItemRender();
        GrowthcraftMilkBlocks.cheesePress.registerItemRender();
    }

    public static void registerTileEntities() {
        registerTileEntity(TileEntityPancheon.class, Reference.MODID + ":pancheon");
        registerTileEntity(TileEntityButterChurn.class, Reference.MODID + ":churn");
        registerTileEntity(TileEntityButterChurnPlunger.class, Reference.MODID + ":churn_plunger");
        registerTileEntity(TileEntityCheeseBlock.class, Reference.MODID + ":cheese_block");
        registerTileEntity(TileEntityHangingCurds.class, Reference.MODID + ":cheese_curds");
        registerTileEntity(TileEntityCheeseVat.class, Reference.MODID + ":cheese_vat");
        registerTileEntity(TileEntityCheesePress.class, Reference.MODID + ":cheese_press");
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockSpecialRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPancheon.class, new RenderPancheon());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCheeseVat.class, new RenderCheeseVat());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCheesePress.class, new RenderCheesePress());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityButterChurnPlunger.class, new RenderButterChurnPlunger());
    }

    ////////
    // Items
    ////////

    public static void preInitItems() {
        GrowthcraftMilkItems.thistle = new ItemDefinition(new ItemThistle("thistle"));
        GrowthcraftMilkItems.thistleSeed = new ItemDefinition(new ItemSeedThistle("thistle_seed"));
        GrowthcraftMilkItems.stomach = new ItemDefinition(new ItemStomach("stomach"));
        GrowthcraftMilkItems.cheeseCloth = new ItemDefinition(new ItemCheeseCloth("cheese_cloth"));
        GrowthcraftMilkItems.starterCulture = new ItemDefinition(new ItemStarterCulture("starter_culture"));
        GrowthcraftMilkItems.butter = new ItemDefinition(new ItemButter("butter", 2, 0.3F, false));
        GrowthcraftMilkItems.iceCream = new ItemDefinition(new ItemIceCream("ice_cream", 2, 0.3F, false));
        GrowthcraftMilkItems.yogurt = new ItemDefinition(new ItemYogurt("yogurt", 2, 0.3F, false));
        GrowthcraftMilkItems.knife = new ItemDefinition(new ItemKnife("knife"));
        GrowthcraftMilkItems.agedCheeseSlice = new ItemDefinition(new ItemAgedCheeseSlice("cheese_aged_slice", 3, 0.3F, false));
        GrowthcraftMilkItems.agedCheeseBlockItem = new ItemDefinition(new ItemBlockCheeseBlock<AgedCheeseTypes>(GrowthcraftMilkBlocks.agedCheeseBlock.getBlock(), AgedCheeseTypes.values()));
        GrowthcraftMilkItems.waxedCheeseBlockItem = new ItemDefinition(new ItemBlockCheeseBlock<WaxedCheeseTypes>(GrowthcraftMilkBlocks.waxedCheeseBlock.getBlock(), WaxedCheeseTypes.values()));
        GrowthcraftMilkItems.waxedCheeseSlice = new ItemDefinition(new ItemWaxedCheeseSlice("cheese_waxed_slice", 4, 0.3F, false));
        GrowthcraftMilkItems.simpleCheeseSlice = new ItemDefinition(new ItemCheeseBowl("cheese_simple_slice", 2, 0.3F, false));
        GrowthcraftMilkItems.agedCheeseCurdsItem = new ItemDefinition(new ItemBlockHangingCurds<AgedCheeseTypes>(GrowthcraftMilkBlocks.agedCheeseCurds.getBlock(), AgedCheeseTypes.values()));
        GrowthcraftMilkItems.waxedCheeseCurdsItem = new ItemDefinition(new ItemBlockHangingCurds<WaxedCheeseTypes>(GrowthcraftMilkBlocks.waxedCheeseCurds.getBlock(), WaxedCheeseTypes.values()));
        GrowthcraftMilkItems.simpleCheeseCurdsItem = new ItemDefinition(new ItemBlockHangingCurds<SimpleCheeseTypes>(GrowthcraftMilkBlocks.simpleCheeseCurds.getBlock(), SimpleCheeseTypes.values()));
        GrowthcraftMilkItems.kumisBottle = new ItemTypeDefinition<ItemBoozeBottle>(new ItemBoozeBottle());
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        GrowthcraftMilkItems.thistle.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkItems.thistle.registerItem(registry);
        GrowthcraftMilkItems.thistleSeed.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkItems.thistleSeed.registerItem(registry);
        MinecraftForge.addGrassSeed(GrowthcraftMilkItems.thistleSeed.asStack(), 1);
        GrowthcraftMilkItems.stomach.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkItems.stomach.registerItem(registry);
        GrowthcraftMilkItems.cheeseCloth.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkItems.cheeseCloth.registerItem(registry);
        GrowthcraftMilkItems.starterCulture.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkItems.starterCulture.registerItem(registry);
        GrowthcraftMilkItems.butter.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkItems.butter.registerItem(registry);
        GrowthcraftMilkItems.iceCream.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkItems.iceCream.registerItem(registry);
        GrowthcraftMilkItems.yogurt.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkItems.yogurt.registerItem(registry);
        GrowthcraftMilkItems.knife.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkItems.knife.registerItem(registry);
        GrowthcraftMilkItems.agedCheeseSlice.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkItems.agedCheeseSlice.registerItem(registry);
        GrowthcraftMilkItems.waxedCheeseSlice.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkItems.waxedCheeseSlice.registerItem(registry);
        GrowthcraftMilkItems.simpleCheeseSlice.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkItems.simpleCheeseSlice.registerItem(registry);
        GrowthcraftMilkItems.agedCheeseBlockItem.registerItem(registry, GrowthcraftMilkBlocks.agedCheeseBlock.getBlock().getRegistryName() /*new ResourceLocation(Reference.MODID, "cheese_aged")*/);
        GrowthcraftMilkItems.waxedCheeseBlockItem.registerItem(registry, GrowthcraftMilkBlocks.waxedCheeseBlock.getBlock().getRegistryName());
        GrowthcraftMilkItems.agedCheeseCurdsItem.registerItem(registry, GrowthcraftMilkBlocks.agedCheeseCurds.getBlock().getRegistryName());
        GrowthcraftMilkItems.waxedCheeseCurdsItem.registerItem(registry, GrowthcraftMilkBlocks.waxedCheeseCurds.getBlock().getRegistryName());
        GrowthcraftMilkItems.simpleCheeseCurdsItem.registerItem(registry, GrowthcraftMilkBlocks.simpleCheeseCurds.getBlock().getRegistryName());

        GrowthcraftMilkItems.kumisBottle.registerItem(registry, new ResourceLocation(Reference.MODID, "kumisbottle"));
        GrowthcraftMilkItems.kumisBottle.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftMilkItems.kumisBottle.getItem().setBoozes(kumisBooze);
    }

    public static void registerItemOres() {
        for (IceCreamTypes e : IceCreamTypes.values()) {
            OreDictionary.registerOre("foodIceCream", e.asStack());
        }

        for (ButterTypes e : ButterTypes.values()) {
            OreDictionary.registerOre("foodButter", e.asStack());
        }

        for (AgedCheeseTypes e : AgedCheeseTypes.values()) {
            OreDictionary.registerOre("foodCheese", e.getCheeseItems().asStack());
        }

        for (WaxedCheeseTypes e : WaxedCheeseTypes.values()) {
            OreDictionary.registerOre("foodCheese", e.getCheeseItems().asStack());
        }

        for (SimpleCheeseTypes e : SimpleCheeseTypes.values()) {
            OreDictionary.registerOre("foodCheese", e.getCheeseItems().asStack());
        }

        OreDictionary.registerOre("toolKnife", GrowthcraftMilkItems.knife.getItem());
        for (ItemStack stack : OreDictionary.getOres("itemFoodCutter"))
            OreDictionary.registerOre("toolKnife", stack);

        OreDictionary.registerOre("foodYogurt", GrowthcraftMilkItems.yogurt.asStack());
        OreDictionary.registerOre("materialStomach", GrowthcraftMilkItems.stomach.asStack());
        OreDictionary.registerOre("rennetSource", GrowthcraftMilkItems.thistle.getItem());
        OreDictionary.registerOre("rennetSource", GrowthcraftMilkItems.stomach.asStack());
        OreDictionary.registerOre("foodOffal", GrowthcraftMilkItems.stomach.asStack());
        OreDictionary.registerOre("materialStarterCulture", GrowthcraftMilkItems.starterCulture.asStack());
        OreDictionary.registerOre("materialCheeseCloth", GrowthcraftMilkItems.cheeseCloth.asStack());

        OreDictionary.registerOre("listAllsugar", Items.SUGAR);    // From Pam's Harvestcraft
        OreDictionary.registerOre("foodCocoa", new ItemStack(Items.DYE, 1, 3));
        OreDictionary.getOres("foodCocoapowder").forEach(stack -> OreDictionary.registerOre("foodCocoa", stack));

        if (GrowthcraftMilkItems.thistleSeed != null) {
            OreDictionary.registerOre("listAllseed", GrowthcraftMilkItems.thistleSeed.asStack());
        }

        OreDictionary.registerOre("foodMelon", Items.MELON);
        OreDictionary.registerOre("foodFruit", Items.MELON);
        OreDictionary.registerOre("foodChorusFruit", Items.CHORUS_FRUIT);
        OreDictionary.registerOre("foodFruit", Items.CHORUS_FRUIT);
    }

    @SideOnly(Side.CLIENT)
    private static <ET extends ICheeseType & IStringSerializable> void registerCheeseWheelItemRenders(ItemDefinition itemDef, ET[] types) {
        for (ET type : types) {
            ICheeseBlockStackFactory blockStackFactory = type.getCheeseBlocks();
            for (EnumCheeseStage stage : EnumCheeseStage.values()) {
                if (blockStackFactory.getInitialStage() != EnumCheeseStage.UNWAXED &&
                        stage == EnumCheeseStage.UNWAXED)
                    continue;    // ignore unwaxed stage for non waxable items

                if (stage == EnumCheeseStage.CUT) {
                    for (int i = 1; i <= CheeseUtils.MAX_CUTS; i++) {
                        int meta = CheeseUtils.getItemMetaFor(type, i, stage);
                        itemDef.registerRender(meta, type.getName() + "_" + stage.getName());
                    }
                } else {
                    int meta = CheeseUtils.getItemMetaFor(type, CheeseUtils.MAX_CUTS, stage);
                    itemDef.registerRender(meta, type.getName() + "_" + stage.getName());
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static <ET extends ICheeseType & IStringSerializable> void registerCheeseCurdsItemRenders(ItemDefinition itemDef, ET[] types) {
        for (ET type : types) {
            int meta = CheeseUtils.getItemMetaFor(type, 4, EnumCheeseStage.UNAGED);
            itemDef.registerRender(meta, type.getName() + "_curds");
        }
    }

    @SideOnly(Side.CLIENT)
    public
    static void registerItemRenders() {
        GrowthcraftMilkItems.thistle.registerRender();
        GrowthcraftMilkItems.thistleSeed.registerRender();
        GrowthcraftMilkItems.stomach.registerRender();
        GrowthcraftMilkItems.cheeseCloth.registerRender();
        GrowthcraftMilkItems.starterCulture.registerRender();

        GrowthcraftMilkItems.butter.registerRenders(ButterTypes.class);
        GrowthcraftMilkItems.iceCream.registerRenders(IceCreamTypes.class);
        GrowthcraftMilkItems.yogurt.registerRenders(YogurtTypes.class);
        GrowthcraftMilkItems.knife.registerRender();
        GrowthcraftMilkItems.agedCheeseSlice.registerRenders(AgedCheeseTypes.class);
        GrowthcraftMilkItems.waxedCheeseSlice.registerRenders(WaxedCheeseTypes.class);
        GrowthcraftMilkItems.simpleCheeseSlice.registerRenders(SimpleCheeseTypes.class);

        registerCheeseWheelItemRenders(GrowthcraftMilkItems.agedCheeseBlockItem, AgedCheeseTypes.values());
        registerCheeseWheelItemRenders(GrowthcraftMilkItems.waxedCheeseBlockItem, WaxedCheeseTypes.values());

        registerCheeseCurdsItemRenders(GrowthcraftMilkItems.agedCheeseCurdsItem, AgedCheeseTypes.values());
        registerCheeseCurdsItemRenders(GrowthcraftMilkItems.waxedCheeseCurdsItem, WaxedCheeseTypes.values());
        registerCheeseCurdsItemRenders(GrowthcraftMilkItems.simpleCheeseCurdsItem, SimpleCheeseTypes.values());

        GrowthcraftMilkItems.kumisBottle.registerRenders(KumisTypes.class);
    }

    @SideOnly(Side.CLIENT)
    public
    static void registerModelBakeryVariants() {
        GrowthcraftMilkItems.butter.registerModelBakeryVariants(ButterTypes.class);
        GrowthcraftMilkItems.kumisBottle.registerModelBakeryVariants(KumisTypes.class);
    }

    ////////
    // Fluids
    ////////

    // TODO: Move these to some kind of cheese registry helper.

    private static <E extends Enum<?> & ICheeseType & IStringSerializable> void preInitCheeseTypes(Map<E, FluidFactory.FluidDetails> fluidMap, Class<E> clazz) {
        E[] vals = clazz.getEnumConstants();
        for (E type : vals) {
            final String fluidName = "fluid_cheese_" + type.getName();
            Fluid cheeseFluid = new FluidCheese(fluidName).setColor(type.getColor());
            FluidDetails fluidFactory = new FluidDetailsBuilder(cheeseFluid, FluidFactory.FEATURE_NONE)
                    .setFluidBlockClass(BlockFluidCheese.class).build()
                    .setBlockColor(cheeseFluid.getColor())
                    .setItemColor(cheeseFluid.getColor());
            fluidMap.put(type, fluidFactory);
        }
    }

    private static <E extends Enum<?> & ICheeseType & IStringSerializable> void registerCheeseFluidBlocks(IForgeRegistry<Block> registry, Map<E, FluidFactory.FluidDetails> fluidMap) {
        for (Map.Entry<E, FluidDetails> entry : fluidMap.entrySet()) {
            entry.getValue().registerBlocks(registry, Reference.MODID, "cheese_" + entry.getKey().getName());
            CoreRegistry.instance().fluidDictionary().addFluidTags(entry.getValue().getFluid(), MilkFluidTags.CHEESE);
        }
    }

    private static <E extends Enum<?> & ICheeseType & IStringSerializable> void registerCheeseFluidItems(IForgeRegistry<Item> registry, Map<E, FluidFactory.FluidDetails> fluidMap) {
        for (Map.Entry<E, FluidDetails> entry : fluidMap.entrySet()) {
            entry.getValue().registerItems(registry, Reference.MODID, "cheese_" + entry.getKey().getName());
        }
    }

    @SideOnly(Side.CLIENT)
    private static <E extends Enum<?> & ICheeseType & IStringSerializable> void registerCheeseRenders(Map<E, FluidFactory.FluidDetails> fluidMap) {
        for (Map.Entry<E, FluidDetails> entry : fluidMap.entrySet()) {
            entry.getValue().registerRenderer();
        }
    }

    @SideOnly(Side.CLIENT)
    private static <E extends Enum<?> & ICheeseType & IStringSerializable> void registerCheeseColorHandlers(Map<E, FluidFactory.FluidDetails> fluidMap) {
        for (Map.Entry<E, FluidDetails> entry : fluidMap.entrySet()) {
            entry.getValue().registerColorHandlers();
        }
    }

    private static void preInitCheeseFluids() {
        preInitCheeseTypes(GrowthcraftMilkFluids.cheesesSimple, SimpleCheeseTypes.class);
        preInitCheeseTypes(GrowthcraftMilkFluids.cheesesAged, AgedCheeseTypes.class);
        preInitCheeseTypes(GrowthcraftMilkFluids.cheesesWaxed, WaxedCheeseTypes.class);
    }


    public static int roundToBottles(int fluidAmount) {
        int numBottles = fluidAmount / GrowthcraftCoreConfig.bottleCapacity;
        return numBottles * GrowthcraftCoreConfig.bottleCapacity;
    }

    public static void registerFluidOres() {
        if (GrowthcraftMilkFluids.milk != null) {
            OreDictionary.registerOre("bottleMilk", GrowthcraftMilkFluids.milk.getFoodBottle().asStack());
        }
        // Milk bucket is the vanilla milk bucket, derp
        OreDictionary.registerOre("bucketMilk", Items.MILK_BUCKET);
        if (GrowthcraftMilkFluids.skimMilk != null) {
            OreDictionary.registerOre("bottleSkimmilk", GrowthcraftMilkFluids.skimMilk.asFoodBottleItemStack());
            OreDictionary.registerOre("bucketSkimmilk", GrowthcraftMilkFluids.skimMilk.asBucketItemStack());
        }
        if (GrowthcraftMilkFluids.butterMilk != null) {
            OreDictionary.registerOre("bottleButtermilk", GrowthcraftMilkFluids.butterMilk.asFoodBottleItemStack());
            OreDictionary.registerOre("bucketButtermilk", GrowthcraftMilkFluids.butterMilk.asBucketItemStack());
        }
        if (GrowthcraftMilkFluids.whey != null) {
            OreDictionary.registerOre("bottleWhey", GrowthcraftMilkFluids.whey.asFoodBottleItemStack());
            OreDictionary.registerOre("bucketWhey", GrowthcraftMilkFluids.whey.asBucketItemStack());
            // https://github.com/GrowthcraftCE/Growthcraft-1.7/issues/419
            OreDictionary.registerOre("foodStock", GrowthcraftMilkFluids.whey.asFoodBottleItemStack());
        }
        if (GrowthcraftMilkFluids.cream != null) {
            OreDictionary.registerOre("bottleCream", GrowthcraftMilkFluids.cream.asBottleItemStack());
            OreDictionary.registerOre("bucketCream", GrowthcraftMilkFluids.cream.asBucketItemStack());
        }
    }

    public static void preInitFluids() {
        final IEffect milkEffect = EffectMilk.create(GrowthcraftCellarPotions.potionTipsy);
        if (GrowthcraftMilkConfig.milkEnabled) {
            FluidMilk fluidMilk = (FluidMilk) new FluidMilk("fluid_milk");
            ItemFoodBottleFluid foodBottleMilk = (ItemFoodBottleFluid) new ItemFoodBottleFluid(fluidMilk, 4, 0.3f, false);
            foodBottleMilk.setEffect(milkEffect).setAlwaysEdible();
            GrowthcraftMilkFluids.milk = new FluidDetailsBuilder(
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
            if (GrowthcraftMilkConfig.fantasyMilkEffects) {
                // Idea from here: http://www.altmedicine101.com/buttermilk
                list.add(new EffectExtinguish());
                list.add(EffectUtils.createAddPotionEffect(MobEffects.FIRE_RESISTANCE, TickUtils.seconds(15), 0));
            }
            foodBottleButterMilk.setEffect(list).setAlwaysEdible();
        }
        GrowthcraftMilkFluids.butterMilk = new FluidDetailsBuilder(fluidButterMilk, FluidFactory.FEATURE_ALL_EDIBLE)
                .setFluidBlockClass(BlockFluidButterMilk.class)
                .setFoodBottle(foodBottleButterMilk).build()
                .setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFEE7);

        FluidCream fluidCream = new FluidCream("fluid_cream");
        GrowthcraftMilkFluids.cream = new FluidDetailsBuilder(fluidCream)
                .setFluidBlockClass(BlockFluidCream.class).build()
                .setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFDD0);

        FluidMilkCurds fluidMilkCurds = new FluidMilkCurds("fluid_milk_curds");
        GrowthcraftMilkFluids.curds = new FluidDetailsBuilder(fluidMilkCurds)
                .setFluidBlockClass(BlockFluidMilkCurds.class).build()
                .setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFFF6);

        FluidRennet fluidRennet = new FluidRennet("fluid_rennet");
        GrowthcraftMilkFluids.rennet = new FluidDetailsBuilder(fluidRennet)
                .setFluidBlockClass(BlockFluidRennet.class).build()
                .setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0x877243);

        FluidSkimMilk fluidSkimMilk = new FluidSkimMilk("fluid_skim_milk");
        ItemFoodBottleFluid foodBottleSkimMilk = new ItemFoodBottleFluid(fluidSkimMilk, 2, 0.2f, false);
        {
            final EffectList list = new EffectList();
            list.add(milkEffect);
            if (GrowthcraftMilkConfig.fantasyMilkEffects) {
                list.add(EffectUtils.createAddPotionEffect(MobEffects.SPEED, TickUtils.seconds(15), 0));
            }
            foodBottleSkimMilk.setEffect(list).setAlwaysEdible();
        }
        GrowthcraftMilkFluids.skimMilk = new FluidDetailsBuilder(fluidSkimMilk, FluidFactory.FEATURE_ALL_EDIBLE)
                .setFluidBlockClass(BlockFluidSkimMilk.class)
                .setFoodBottle(foodBottleSkimMilk).build()
                .setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFFFA);

        FluidWhey fluidWhey = new FluidWhey("fluid_whey");
        ItemFoodBottleFluid foodBottleWhey = new ItemFoodBottleFluid(fluidWhey, 1, 0.1f, false);
        {
            final EffectList list = new EffectList();
            if (GrowthcraftMilkConfig.fantasyMilkEffects) {
                list.add(EffectUtils.createAddPotionEffect(MobEffects.STRENGTH, TickUtils.seconds(10), 0));
                list.add(EffectUtils.createAddPotionEffect(MobEffects.RESISTANCE, TickUtils.seconds(10), 0));
            }
            foodBottleWhey.setEffect(list).setAlwaysEdible();
        }
        GrowthcraftMilkFluids.whey = new FluidDetailsBuilder(fluidWhey, FluidFactory.FEATURE_ALL_EDIBLE)
                .setFluidBlockClass(BlockFluidWhey.class)
                .setFoodBottle(foodBottleWhey).build()
                .setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0x94a860);

        FluidPasteurizedMilk fluidPasteurizedMilk = new FluidPasteurizedMilk("fluid_pasteurized_milk");
        GrowthcraftMilkFluids.pasteurizedMilk = new FluidDetailsBuilder(fluidPasteurizedMilk)
                .setFluidBlockClass(BlockFluidPasteurizedMilk.class).build()
                .setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFFFA);

        /* Kumis */
        kumisBooze = new BoozeDefinition[KumisTypes.values().length];
        BoozeRegistryHelper.initializeAndRegisterBoozeFluids(kumisBooze, KumisTypes.class, "kumis");

        // Set the fluid color and density for each of the fluid sub-types
        kumisBooze[KumisTypes.KUMIS_FERMENTED.ordinal()]
                .getFluid()
                .setColor(GrowthcraftMilkConfig.kumisFermentedColor)
                .setDensity(1120);
        kumisBooze[KumisTypes.KUMIS_POTENT.ordinal()]
                .getFluid()
                .setColor(GrowthcraftMilkConfig.kumisPotentColor)
                .setDensity(1120);
        kumisBooze[KumisTypes.KUMIS_EXTENDED.ordinal()]
                .getFluid()
                .setColor(GrowthcraftMilkConfig.kumisExtendedColor)
                .setDensity(1120);
        kumisBooze[KumisTypes.KUMIS_HYPEREXTENDED.ordinal()]
                .getFluid()
                .setColor(GrowthcraftMilkConfig.kumisHyperExtendedColor)
                .setDensity(1120);
        kumisBooze[KumisTypes.KUMIS_POTENT_EXTENDED.ordinal()]
                .getFluid()
                .setColor(GrowthcraftMilkConfig.kumisPotentExtendedColor)
                .setDensity(1120);
        kumisBooze[KumisTypes.KUMIS_POTENT_HYPEREXTENDED.ordinal()]
                .getFluid()
                .setColor(GrowthcraftMilkConfig.kumisPotentHyperExtendedColor)
                .setDensity(1120);
        kumisBooze[KumisTypes.KUMIS_POISONED.ordinal()]
                .getFluid()
                .setColor(GrowthcraftMilkConfig.kumisPoisonedColor)
                .setDensity(1120);

        preInitCheeseFluids();
    }

    public static void initFluids() {
        int restCapRounded = roundToBottles(FluidContainerRegistry.BUCKET_VOLUME - 2 * GrowthcraftCoreConfig.bottleCapacity);

        final List<Fluid> milks = Compat.getMilkFluids();
        for (Fluid f : milks) {
            CoreRegistry.instance().fluidDictionary().addFluidTags(f, MilkFluidTags.MILK);

            MilkRegistry.instance().pancheon().addRecipe(
                    new FluidStack(f, 1000),
                    GrowthcraftMilkFluids.cream.asFluidStack(2 * GrowthcraftCoreConfig.bottleCapacity), GrowthcraftMilkFluids.skimMilk.asFluidStack(restCapRounded),
                    TickUtils.minutes(1));
        }
    }

    public static void registerFluidBlocks(IForgeRegistry<Block> registry) {
        if (GrowthcraftMilkFluids.milk != null) {
            GrowthcraftMilkFluids.milk.registerBlocks(registry, Reference.MODID, "milk");
            // ensure that we don't already have some variation of milk present
            if (FluidRegistry.getFluid("milk") == null) {
                FluidContainerRegistry.registerFluidContainer(GrowthcraftMilkFluids.milk.getFluid(), new ItemStack(Items.MILK_BUCKET, 1), new ItemStack(Items.BUCKET, 1));
//OBSOLET				EventHandlerBucketFill.instance().register(milk.getFluidBlock(), new ItemStack(Items.milk_bucket, 1));
            }
        }
        GrowthcraftMilkFluids.butterMilk.registerBlocks(registry, Reference.MODID, "butter_milk");
        GrowthcraftMilkFluids.cream.registerBlocks(registry, Reference.MODID, "cream");
        GrowthcraftMilkFluids.curds.registerBlocks(registry, Reference.MODID, "milk_curds");
        GrowthcraftMilkFluids.rennet.registerBlocks(registry, Reference.MODID, "rennet");
        GrowthcraftMilkFluids.skimMilk.registerBlocks(registry, Reference.MODID, "skim_milk");
        GrowthcraftMilkFluids.whey.registerBlocks(registry, Reference.MODID, "whey");
        GrowthcraftMilkFluids.pasteurizedMilk.registerBlocks(registry, Reference.MODID, "pasteurized_milk");

        CoreRegistry.instance().fluidDictionary().addFluidTags(GrowthcraftMilkFluids.cream.getFluid(), MilkFluidTags.CREAM);
        CoreRegistry.instance().fluidDictionary().addFluidTags(GrowthcraftMilkFluids.curds.getFluid(), MilkFluidTags.MILK_CURDS);
        CoreRegistry.instance().fluidDictionary().addFluidTags(GrowthcraftMilkFluids.rennet.getFluid(), MilkFluidTags.RENNET);
        CoreRegistry.instance().fluidDictionary().addFluidTags(GrowthcraftMilkFluids.whey.getFluid(), MilkFluidTags.WHEY);

        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftMilkFluids.rennet.getFluid())
                .brewsFrom(new FluidStack(FluidRegistry.WATER, 1000), new OreItemStacks("rennetSource"), false, TickUtils.minutes(1), null);

        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftMilkFluids.pasteurizedMilk.getFluid())
                .brewsFrom(GrowthcraftMilkFluids.skimMilk.asFluidStack(250), new ItemStack(Items.SUGAR), false, TickUtils.minutes(1), new Residue(GrowthcraftMilkItems.starterCulture.asStack(1), 1.0f));

        GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftMilkFluids.skimMilk.getFluid())
                .culturesTo(250, GrowthcraftMilkItems.starterCulture.asStack(), 0.6f, TickUtils.seconds(12));

        // TODO: Move to recipes
        GrowthcraftMilk.userApis.churnRecipes.addDefault(
                GrowthcraftMilkFluids.cream.asFluidStack(roundToBottles(1000)),
                GrowthcraftMilkFluids.butterMilk.asFluidStack(roundToBottles(500)),
                GrowthcraftMilkItems.butter.asStack(2),
                16);

        registerCheeseFluidBlocks(registry, GrowthcraftMilkFluids.cheesesSimple);
        registerCheeseFluidBlocks(registry, GrowthcraftMilkFluids.cheesesAged);
        registerCheeseFluidBlocks(registry, GrowthcraftMilkFluids.cheesesWaxed);

        // Init the Kumis fluid blocks
        GrowthcraftMilkBlocks.kumisFluidBlocks = new BlockBoozeDefinition[kumisBooze.length];
        BoozeRegistryHelper.initializeBooze(kumisBooze, GrowthcraftMilkBlocks.kumisFluidBlocks);
        BoozeRegistryHelper.setBoozeFoodStats(kumisBooze, 1, -0.3F);
        BoozeRegistryHelper.registerBoozeBlocks(registry, kumisBooze, GrowthcraftMilkBlocks.kumisFluidBlocks, Reference.MODID, "kumisbooze", KumisTypes.class);

    }

    public static void initBoozes() {
        BoozeRegistryHelper.initBoozeContainers(kumisBooze, GrowthcraftMilkItems.kumisBottle,
                Reference.MODID, "kumisbooze", KumisTypes.class);
        registerFermentations();
    }

    private static void registerFermentations() {

        final int fermentTime = GrowthcraftCellarConfig.fermentTime;

        final FluidStack[] fs = BoozeHelper.boozeDefintionsToFluidStacks(kumisBooze);
        final FluidStack[] spoilInputFs = BoozeHelper.boozeDefintionsToFluidStacks(kumisBooze, 200);

        // FERMENTABLE

        for (KumisTypes kumisType : KumisTypes.values()) {
            Fluid outputFluid;
            FluidStack inputFluid;
            FluidTag[] fluidTags;
            Item inputItem;
            Float fermentOffset = 1.00F;
            Float alcoholRate = 0.045F;
            int tipsyDuration = 60;
            Potion potionEffect = MobEffects.RESISTANCE;
            int potionDuration = 1;

            switch (kumisType) {
                case KUMIS_FERMENTED:
                    outputFluid = kumisBooze[KumisTypes.KUMIS_FERMENTED.ordinal()].getFluid();
                    inputFluid = new FluidStack(FluidRegistry.getFluid("fluid_milk"), 200);
                    fluidTags = new FluidTag[]{BoozeTag.KUMIS, BoozeTag.FERMENTED};
                    inputItem = Items.NETHER_WART;
                    fermentOffset = 0.66F;
                    registerFermentation(outputFluid, inputFluid, fluidTags, inputItem, fermentTime * fermentOffset, alcoholRate, tipsyDuration, potionEffect, potionDuration);
                    // Mod-Compat: Forestry Milk
                    inputFluid = new FluidStack(FluidRegistry.getFluid("milk"), 200);
                    registerFermentation(outputFluid, inputFluid, fluidTags, inputItem, fermentTime * fermentOffset, alcoholRate, tipsyDuration, potionEffect, potionDuration);
                    break;
                case KUMIS_POTENT:
                    outputFluid = kumisBooze[KumisTypes.KUMIS_POTENT.ordinal()].getFluid();
                    inputFluid = fs[KumisTypes.KUMIS_FERMENTED.ordinal()];
                    fluidTags = new FluidTag[]{BoozeTag.KUMIS, BoozeTag.FERMENTED, BoozeTag.POTENT};
                    inputItem = Items.GLOWSTONE_DUST;
                    alcoholRate = 0.09F;
                    registerFermentation(outputFluid, inputFluid, fluidTags, inputItem, fermentTime * fermentOffset, alcoholRate, tipsyDuration, potionEffect, potionDuration);
                    break;
                case KUMIS_EXTENDED:
                    outputFluid = kumisBooze[KumisTypes.KUMIS_EXTENDED.ordinal()].getFluid();
                    inputFluid = fs[KumisTypes.KUMIS_FERMENTED.ordinal()];
                    fluidTags = new FluidTag[]{BoozeTag.KUMIS, BoozeTag.FERMENTED, BoozeTag.EXTENDED};
                    inputItem = Items.REDSTONE;
                    tipsyDuration = 180;
                    potionDuration = 3;
                    registerFermentation(outputFluid, inputFluid, fluidTags, inputItem, fermentTime * fermentOffset, alcoholRate, tipsyDuration, potionEffect, potionDuration);
                    break;
                case KUMIS_HYPEREXTENDED:
                    outputFluid = kumisBooze[KumisTypes.KUMIS_HYPEREXTENDED.ordinal()].getFluid();
                    inputFluid = fs[KumisTypes.KUMIS_EXTENDED.ordinal()];
                    fluidTags = new FluidTag[]{BoozeTag.KUMIS, BoozeTag.FERMENTED, BoozeTag.HYPER_EXTENDED};
                    inputItem = Items.REDSTONE;
                    tipsyDuration = 360;
                    potionDuration = 6;
                    registerFermentation(outputFluid, inputFluid, fluidTags, inputItem, fermentTime * fermentOffset, alcoholRate, tipsyDuration, potionEffect, potionDuration);
                    break;
                case KUMIS_POTENT_EXTENDED:
                    outputFluid = kumisBooze[KumisTypes.KUMIS_POTENT_EXTENDED.ordinal()].getFluid();
                    inputFluid = fs[KumisTypes.KUMIS_POTENT.ordinal()];
                    fluidTags = new FluidTag[]{BoozeTag.KUMIS, BoozeTag.FERMENTED, BoozeTag.POTENT, BoozeTag.EXTENDED};
                    inputItem = Items.REDSTONE;
                    alcoholRate = 0.09F;
                    tipsyDuration = 180;
                    potionDuration = 3;
                    registerFermentation(outputFluid, inputFluid, fluidTags, inputItem, fermentTime * fermentOffset, alcoholRate, tipsyDuration, potionEffect, potionDuration);

                    inputFluid = fs[KumisTypes.KUMIS_POTENT.ordinal()];
                    inputItem = Items.GLOWSTONE_DUST;
                    registerFermentation(outputFluid, inputFluid, fluidTags, inputItem, fermentTime * fermentOffset, alcoholRate, tipsyDuration, potionEffect, potionDuration);

                    break;
                case KUMIS_POTENT_HYPEREXTENDED:
                    outputFluid = kumisBooze[KumisTypes.KUMIS_POTENT_HYPEREXTENDED.ordinal()].getFluid();
                    inputFluid = fs[KumisTypes.KUMIS_POTENT_EXTENDED.ordinal()];
                    fluidTags = new FluidTag[]{BoozeTag.KUMIS, BoozeTag.FERMENTED, BoozeTag.POTENT, BoozeTag.HYPER_EXTENDED};
                    inputItem = Items.REDSTONE;
                    alcoholRate = 0.09F;
                    tipsyDuration = 360;
                    potionDuration = 6;
                    registerFermentation(outputFluid, inputFluid, fluidTags, inputItem, fermentTime * fermentOffset, alcoholRate, tipsyDuration, potionEffect, potionDuration);

                    inputFluid = fs[KumisTypes.KUMIS_HYPEREXTENDED.ordinal()];
                    inputItem = Items.GLOWSTONE_DUST;
                    registerFermentation(outputFluid, inputFluid, fluidTags, inputItem, fermentTime * fermentOffset, alcoholRate, tipsyDuration, potionEffect, potionDuration);

                    break;
                default:
                    // If it is POISONED for not listed above, skip to the next ENUM value.
                    continue;
            }

        }

        // POSIONED

        // TODO: Implement recipe for KUMIS_POISONED

    }

    public static void registerFermentation(
            Fluid outputFluid,
            FluidStack inputFluid,
            FluidTag[] fluidTags,
            Item inputItem,
            float fermentTime,
            Float alcoholRate,
            int tipsyDuration,
            Potion potionEffect,
            int potionDuration) {
        try {
            GrowthcraftCellarApis.boozeBuilderFactory.create(outputFluid)
                    .tags(fluidTags)
                    .fermentsFrom(
                            inputFluid,
                            new ItemStack(inputItem),
                            (int) (fermentTime))
                    .getEffect()
                    .setTipsy(BoozeUtils.alcoholToTipsy(alcoholRate), TickUtils.seconds(tipsyDuration))
                    .addPotionEntry(potionEffect, TickUtils.minutes(potionDuration), 0);
        } catch (Exception ex) {

        }
    }

    public static void registerFluidItems(IForgeRegistry<Item> registry) {
        if (GrowthcraftMilkFluids.milk != null)
            GrowthcraftMilkFluids.milk.registerItems(registry, Reference.MODID, "milk");

        GrowthcraftMilkFluids.butterMilk.registerItems(registry, Reference.MODID, "butter_milk");
        GrowthcraftMilkFluids.cream.registerItems(registry, Reference.MODID, "cream");
        GrowthcraftMilkFluids.curds.registerItems(registry, Reference.MODID, "milk_curds");
        GrowthcraftMilkFluids.rennet.registerItems(registry, Reference.MODID, "rennet");
        GrowthcraftMilkFluids.skimMilk.registerItems(registry, Reference.MODID, "skim_milk");
        GrowthcraftMilkFluids.whey.registerItems(registry, Reference.MODID, "whey");
        GrowthcraftMilkFluids.pasteurizedMilk.registerItems(registry, Reference.MODID, "pasteurized_milk");

        registerCheeseFluidItems(registry, GrowthcraftMilkFluids.cheesesSimple);
        registerCheeseFluidItems(registry, GrowthcraftMilkFluids.cheesesAged);
        registerCheeseFluidItems(registry, GrowthcraftMilkFluids.cheesesWaxed);

    }

    @SideOnly(Side.CLIENT)
    public static void registerFluidRenders() {
        if (GrowthcraftMilkFluids.milk != null)
            GrowthcraftMilkFluids.milk.registerRenderer();
        GrowthcraftMilkFluids.butterMilk.registerRenderer();
        GrowthcraftMilkFluids.cream.registerRenderer();
        GrowthcraftMilkFluids.curds.registerRenderer();
        GrowthcraftMilkFluids.rennet.registerRenderer();
        GrowthcraftMilkFluids.skimMilk.registerRenderer();
        GrowthcraftMilkFluids.whey.registerRenderer();
        GrowthcraftMilkFluids.pasteurizedMilk.registerRenderer();
        registerCheeseRenders(GrowthcraftMilkFluids.cheesesSimple);
        registerCheeseRenders(GrowthcraftMilkFluids.cheesesAged);
        registerCheeseRenders(GrowthcraftMilkFluids.cheesesWaxed);

        BoozeRegistryHelper.registerBoozeRenderers(kumisBooze, GrowthcraftMilkBlocks.kumisFluidBlocks);

    }

    @SideOnly(Side.CLIENT)
    public static void registerFluidColorHandlers() {
        if (GrowthcraftMilkFluids.milk != null)
            GrowthcraftMilkFluids.milk.registerColorHandlers();
        GrowthcraftMilkFluids.butterMilk.registerColorHandlers();
        GrowthcraftMilkFluids.cream.registerColorHandlers();
        GrowthcraftMilkFluids.curds.registerColorHandlers();
        GrowthcraftMilkFluids.rennet.registerColorHandlers();
        GrowthcraftMilkFluids.skimMilk.registerColorHandlers();
        GrowthcraftMilkFluids.whey.registerColorHandlers();
        GrowthcraftMilkFluids.pasteurizedMilk.registerColorHandlers();
        registerCheeseColorHandlers(GrowthcraftMilkFluids.cheesesSimple);
        registerCheeseColorHandlers(GrowthcraftMilkFluids.cheesesAged);
        registerCheeseColorHandlers(GrowthcraftMilkFluids.cheesesWaxed);
        ItemRenderUtils.registerItemColorHandler(GrowthcraftMilkItems.kumisBottle.getItem());

    }

    ////////
    // Cheeese
    ////////

    public static void perInitCheese() {
        MilkRegistry.instance().cheese().registerCheeses(AgedCheeseTypes.class);
        MilkRegistry.instance().cheese().registerCheeses(SimpleCheeseTypes.class);
        MilkRegistry.instance().cheese().registerCheeses(WaxedCheeseTypes.class);
    }

    ////////
    // Effects
    ////////

    public static void preInitEffects() {
        final EffectRegistry reg = CoreRegistry.instance().getEffectsRegistry();
        reg.register("booze_milk", EffectMilk.class);
//TODO:		reg.register("evil_booze_milk", EffectEvilBoozeMilk.class);
    }

    ////////
    // Recipes
    ////////

    private static void registerCheeseVatRecipes() {
        final String[] saltOres = {"salt", "itemSalt", "dustSalt", "foodSalt", "listAllSalt", "ingredientSalt", "pinchSalt", "portionSalt", "lumpSalt", "materialSalt"};

        for (String saltOre : saltOres) {
            CheeseVatRecipeBuilder.buildRecipe("CHEDDAR Orange Dye Recipe")
                    .outputFluids(WaxedCheeseTypes.CHEDDAR.getFluids().asFluidStack(5000))
                    .inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
                    .inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("dyeOrange", 1))
                    .register();

            CheeseVatRecipeBuilder.buildRecipe("CHEDDAR Pumpkin Recipe")
                    .outputFluids(WaxedCheeseTypes.CHEDDAR.getFluids().asFluidStack(5000))
                    .inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
                    .inputItems(new OreItemStacks(saltOre, 1), new ItemStack(Blocks.PUMPKIN))
                    .register();

            CheeseVatRecipeBuilder.buildRecipe("GORGONZOLA Recipe")
                    .outputFluids(AgedCheeseTypes.GORGONZOLA.getFluids().asFluidStack(5000))
                    .inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
                    .inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("foodFruit", 1))
                    .register();

            CheeseVatRecipeBuilder.buildRecipe("EMMENTALER Recipe")
                    .outputFluids(AgedCheeseTypes.EMMENTALER.getFluids().asFluidStack(5000))
                    .inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
                    .inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("cropWheat", 1))
                    .register();

            CheeseVatRecipeBuilder.buildRecipe("APPENZELLER Wine Recipe")
                    .outputFluids(AgedCheeseTypes.APPENZELLER.getFluids().asFluidStack(5000))
                    .inputFluids(new TaggedFluidStacks(5000, "milk_curds"), new TaggedFluidStacks(1000, "wine"))
                    .inputItems(new OreItemStacks(saltOre, 1))
                    .register();

            CheeseVatRecipeBuilder.buildRecipe("APPENZELLER Cider Recipe")
                    .outputFluids(AgedCheeseTypes.APPENZELLER.getFluids().asFluidStack(5000))
                    .inputFluids(new TaggedFluidStacks(5000, "milk_curds"), new TaggedFluidStacks(1000, "cider"))
                    .inputItems(new OreItemStacks(saltOre, 1))
                    .register();

            CheeseVatRecipeBuilder.buildRecipe("ASIAGO Recipe")
                    .outputFluids(AgedCheeseTypes.ASIAGO.getFluids().asFluidStack(5000))
                    .inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
                    .inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks(saltOre, 1), new OreItemStacks("dyeYellow", 1))
                    .register();

            CheeseVatRecipeBuilder.buildRecipe("PARMESAN Recipe")
                    .outputFluids(AgedCheeseTypes.PARMESAN.getFluids().asFluidStack(5000))
                    .inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
                    .inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("dyeWhite", 1))
                    .register();

            CheeseVatRecipeBuilder.buildRecipe("MONTEREY Recipe")
                    .outputFluids(WaxedCheeseTypes.MONTEREY.getFluids().asFluidStack(5000))
                    .inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
                    .inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("dyeRed", 1))
                    .register();

            CheeseVatRecipeBuilder.buildRecipe("PROVOLONE Yellow Dye Recipe")
                    .outputFluids(WaxedCheeseTypes.PROVOLONE.getFluids().asFluidStack(5000))
                    .inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
                    .inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("dyeYellow", 1))
                    .register();

            CheeseVatRecipeBuilder.buildRecipe("GOUDA Recipe")
                    .outputFluids(WaxedCheeseTypes.GOUDA.getFluids().asFluidStack(5000))
                    .inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
                    .inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("dyeGray", 1))
                    .register();

            CheeseVatRecipeBuilder.buildRecipe("CASU MARZU Recipe")
                    .outputFluids(AgedCheeseTypes.CASU_MARZU.getFluids().asFluidStack(5000))
                    .inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
                    .inputItems(new OreItemStacks(saltOre, 1), new ItemStack(Items.ROTTEN_FLESH))
                    .register();
        }
    }

    public static void registerCraftingRecipes(IForgeRegistry<IRecipe> registry) {
        // TODO: Register non-standard Cheese Vat recipes.
        //			- Cheese

        // TODO: Register non-standard Pancheon recipes.
        //			- Cream recipe
        //			- Skim Milk recipe

    }

    private static <E extends Enum<?> & ICheeseType & IStringSerializable> void registerCheesePressRecipesFor(Class<E> enumType) {
        E[] values = enumType.getEnumConstants();
        for (ICheeseType type : values) {
            ICheeseBlockStackFactory blockFactory = type.getCheeseBlocks();
            ICheeseCurdStackFactory curdsFactory = type.getCurdBlocks();
            if (blockFactory != null && curdsFactory != null) {
                MilkRegistry.instance().cheesePress().addRecipe(new DriedCurdsCheesePressRecipe(curdsFactory.asStack(), blockFactory.asStackForStage(4, blockFactory.getInitialStage()), 200));
            }
        }
    }

    private static ResourceLocation toRegName(String name) {
        return new ResourceLocation(Reference.MODID, name);
    }

    private static void registerCheesePressRecipes() {
        registerCheesePressRecipesFor(AgedCheeseTypes.class);
        registerCheesePressRecipesFor(WaxedCheeseTypes.class);
    }


    public static void initRecipes() {
        registerCheeseVatRecipes();
        registerCheesePressRecipes();
    }

    //

    public static class DriedCurdComparator implements IItemStackComparator {
        private CommonItemStackComparator common = new CommonItemStackComparator();

        public boolean equals(ItemStack expected, ItemStack actual) {
            if (expected.getItem() instanceof ItemBlockHangingCurds) {
                if (actual.getItem() instanceof ItemBlockHangingCurds) {
                    final ItemBlockHangingCurds<?> actualCurd = (ItemBlockHangingCurds<?>) actual.getItem();
                    final ItemBlockHangingCurds<?> expectedCurd = (ItemBlockHangingCurds<?>) expected.getItem();
                    if (expectedCurd.getCheeseType(expected) == actualCurd.getCheeseType(actual)) {
                        if (actualCurd.isDried(actual)) return true;
                    }
                }
                return false;
            } else {
                return common.equals(expected, actual);
            }
        }
    }

}
