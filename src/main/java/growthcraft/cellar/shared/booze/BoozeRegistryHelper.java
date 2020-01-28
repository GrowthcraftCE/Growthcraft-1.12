package growthcraft.cellar.shared.booze;

import java.util.ArrayList;
import java.util.List;

import growthcraft.cellar.shared.block.BlockFluidBooze;
import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.fluids.Booze;
import growthcraft.cellar.shared.definition.BlockBoozeDefinition;
import growthcraft.cellar.shared.definition.BoozeDefinition;
import growthcraft.cellar.shared.item.ItemBoozeBottle;
import growthcraft.core.shared.config.GrowthcraftCoreConfig;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.definition.IObjectVariant;
import growthcraft.core.shared.fluids.FluidDictionary;
import growthcraft.core.shared.definition.ItemTypeDefinition;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.registries.IForgeRegistry;

public class BoozeRegistryHelper {
    private BoozeRegistryHelper() {
    }

    public static <ET extends Enum<?> & IStringSerializable> void initializeAndRegisterBoozeFluids(BoozeDefinition[] boozes, Class<ET> boozeTypeEnum, String basename) {
        ET[] values = boozeTypeEnum.getEnumConstants();

        for (int i = 0; i < boozes.length; ++i) {
            String boozeName = values[i].getName();
            if (basename != null && !basename.isEmpty())
                boozeName = basename + "_" + boozeName;
            boozes[i] = new BoozeDefinition(new Booze(boozeName));
            boozes[i].register();
            CellarRegistry.instance().booze().registerBooze(boozes[i].getFluid());
        }
    }

    public static void initializeBooze(BoozeDefinition[] boozes, BlockBoozeDefinition[] grapeWineFluids) {
        for (int i = 0; i < boozes.length; ++i) {
            final BlockFluidBooze boozeBlock = new BlockFluidBooze(boozes[i].getFluid());
            grapeWineFluids[i] = new BlockBoozeDefinition(boozeBlock);
        }
    }

    public static void setBoozeFoodStats(BoozeDefinition booze, int heal, float saturation) {
        final BoozeEntry entry = CellarRegistry.instance().booze().getBoozeEntry(booze.getFluid());
        if (entry != null) {
            entry.setFoodStats(heal, saturation);
        }
    }

    public static void setBoozeFoodStats(BoozeDefinition[] boozes, int heal, float saturation) {
        for (BoozeDefinition booze : boozes) {
            setBoozeFoodStats(booze, heal, saturation);
        }
    }

    public static List<BoozeEffect> getBoozeEffects(Fluid[] boozes) {
        final BoozeRegistry reg = CellarRegistry.instance().booze();
        final FluidDictionary dict = CoreRegistry.instance().fluidDictionary();
        final List<BoozeEffect> effects = new ArrayList<BoozeEffect>();
        for (int i = 0; i < boozes.length; ++i) {
            if (dict.hasFluidTags(boozes[i], BoozeTag.FERMENTED)) {
                effects.add(reg.getEffect(boozes[i]));
            }
        }
        return effects;
    }

    public static <ET extends Enum<?> & IObjectVariant & IStringSerializable> void registerBoozeBlocks(IForgeRegistry<Block> registry, BoozeDefinition[] boozes, BlockBoozeDefinition[] fluidBlocks, String modid, String basename, Class<ET> boozeTypeEnum) {
        ET[] values = boozeTypeEnum.getEnumConstants();
        for (int i = 0; i < boozes.length; ++i) {
            String boozeName = values[i].getName();
            fluidBlocks[i].registerBlock(registry, new ResourceLocation(modid, "fluid_" + basename + "_" + boozeName));
        }
    }

    public static <ET extends Enum<?> & IObjectVariant & IStringSerializable> void initBoozeContainers(BoozeDefinition[] boozes, ItemTypeDefinition<ItemBoozeBottle> bottle, String modid, String basename, Class<ET> boozeTypeEnum) {
        ET[] values = boozeTypeEnum.getEnumConstants();
        for (int i = 0; i < boozes.length; ++i) {
            boozes[i].registerBucketItem();
            final ItemStack bucket = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, boozes[i].getFluid());

            int bottleVariantID = values[i].getVariantID();
            final FluidStack fluidStack = boozes[i].asFluidStack(GrowthcraftCoreConfig.BOTTLE_CAPACITY);
            FluidContainerRegistry.registerFluidContainer(fluidStack, bottle.asStack(1, bottleVariantID), FluidContainerRegistry.EMPTY_BOTTLE);

//OFF			GameRegistry.addShapelessRecipe(bottle.asStack(3, bottleVariantID), bucket, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE);
        }
    }


    public static void registerBoozeRenderers(BoozeDefinition[] boozes, BlockBoozeDefinition[] fluidBlocks) {
        for (int i = 0; i < boozes.length; ++i) {
            boozes[i].registerRenderer();
        }
    }

}
