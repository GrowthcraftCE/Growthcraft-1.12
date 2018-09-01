package growthcraft.rice.common;

import growthcraft.cellar.shared.booze.BoozeRegistryHelper;
import growthcraft.cellar.shared.definition.BlockBoozeDefinition;
import growthcraft.cellar.shared.definition.BoozeDefinition;
import growthcraft.cellar.shared.item.ItemBoozeBottle;
import growthcraft.core.shared.client.render.utils.ItemRenderUtils;
import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.definition.ItemTypeDefinition;
import growthcraft.rice.common.block.BlockPaddy;
import growthcraft.rice.common.block.BlockRiceCrop;
import growthcraft.rice.common.item.ItemRice;
import growthcraft.rice.common.item.ItemRiceBall;
import growthcraft.rice.shared.Reference;
import growthcraft.rice.shared.config.GrowthcraftRiceConfig;
import growthcraft.rice.shared.init.GrowthcraftRiceBlocks;
import growthcraft.rice.shared.init.GrowthcraftRiceItems;
import growthcraft.rice.shared.init.GrowthcraftRiceItems.SakeTypes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

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
    }

    public static void registerBlockItems(IForgeRegistry<Item> registry) {
        GrowthcraftRiceBlocks.ricePaddy.registerBlockItem(registry);
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockRenders() {
        GrowthcraftRiceBlocks.ricePaddy.registerItemRender();
    }

    public static void  registerTileEntities() {
        // TODO: register tile entities.
    }

    ////////////
    // Items
    ////////////

    public static void preInitItems() {
        GrowthcraftRiceItems.rice = new ItemDefinition( new ItemRice("rice"));
        GrowthcraftRiceItems.riceBall = new ItemDefinition( new ItemRiceBall("rice_ball"));
        GrowthcraftRiceItems.sakeBottle = new ItemTypeDefinition<ItemBoozeBottle>( new ItemBoozeBottle() );
    }

    public static void registerItemOres() {
        // TODO: register ore dictionaries.
    }

    public static void registerItems(IForgeRegistry<Item> registry) {

        GrowthcraftRiceItems.sakeBottle.registerItem(registry, new ResourceLocation(Reference.MODID, "sakebottle"));
        GrowthcraftRiceItems.sakeBottle.getItem().setCreativeTab(tabGrowthcraft);
        GrowthcraftRiceItems.sakeBottle.getItem().setBoozes(sakeBooze);

    }

    @SideOnly(Side.CLIENT)
    public static void registerItemRenders() {
        GrowthcraftRiceItems.sakeBottle.registerRenders(SakeTypes.class);
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
        sakeBooze[SakeTypes.SAKE_YOUNG.ordinal()].getFluid().setColor(GrowthcraftRiceConfig.sakeYoungColor).setDensity(1120);
        sakeBooze[SakeTypes.SAKE_HYPEREXTENDED.ordinal()].getFluid().setColor(GrowthcraftRiceConfig.sakeHyperExtendedColor).setDensity(1120);
        sakeBooze[SakeTypes.SAKE_INTOXICATED.ordinal()].getFluid().setColor(GrowthcraftRiceConfig.sakeIntoxicatedColor).setDensity(1120);
        sakeBooze[SakeTypes.SAKE_POISONED.ordinal()].getFluid().setColor(GrowthcraftRiceConfig.sakePoisonedColor).setDensity(1120);

    }

    public static void registerFluidBlocks(IForgeRegistry<Block> registry) {

        // Initialize the Sake fluid blocks
        GrowthcraftRiceBlocks.sakeFluidBlocks = new BlockBoozeDefinition[sakeBooze.length];
        BoozeRegistryHelper.initializeBooze(sakeBooze, GrowthcraftRiceBlocks.sakeFluidBlocks);

        // Set the default booze food stats.
        BoozeRegistryHelper.setBoozeFoodStats(sakeBooze, 1, -0.3f);
        // Override any booze food stats.
        BoozeRegistryHelper.setBoozeFoodStats(sakeBooze[SakeTypes.SAKE_YOUNG.ordinal()], 1, 0.3f);

        BoozeRegistryHelper.registerBoozeBlocks(registry, sakeBooze, GrowthcraftRiceBlocks.sakeFluidBlocks, Reference.MODID, "sakebooze", SakeTypes.class);

    }

    public static void initBoozes() {
        BoozeRegistryHelper.initBoozeContainers(sakeBooze, GrowthcraftRiceItems.sakeBottle, Reference.MODID, "sakebooze", SakeTypes.class);
    }

    @SideOnly(Side.CLIENT)
    public static void registerFluidRenders() {
        BoozeRegistryHelper.registerBoozeRenderers(sakeBooze, GrowthcraftRiceBlocks.sakeFluidBlocks);
    }

    private static void registerFermentations() {
        // TODO: Implement boozeBuidlerFactory for Sake
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