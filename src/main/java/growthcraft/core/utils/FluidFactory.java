package growthcraft.core.utils;

import growthcraft.core.GrowthcraftCoreConfig;
import growthcraft.core.api.utils.NumUtils;
import growthcraft.core.api.utils.ObjectUtils;
import growthcraft.core.client.utils.ItemRenderUtils;
import growthcraft.core.common.definition.FluidDefinition;
import growthcraft.core.common.definition.GrowthcraftBlockFluidDefinition;
import growthcraft.core.common.definition.ItemTypeDefinition;
import growthcraft.core.common.item.IItemColored;
import growthcraft.core.common.item.ItemBottleFluid;
import growthcraft.core.common.item.ItemFoodBottleFluid;
import growthcraft.core.lib.legacy.FluidContainerRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;

/**
 * A simple factory for creating generic fluid bottles, blocks etc..
 */
public class FluidFactory
{
	public static class FluidDetails
	{
		public FluidDefinition fluid;
		public GrowthcraftBlockFluidDefinition block;
		public ItemTypeDefinition<ItemBottleFluid> bottle;
		public ItemTypeDefinition<ItemFoodBottleFluid> foodBottle;
		public ItemStack bucketStack;
//		public ItemTypeDefinition<ItemBucketFluid> bucket;
		private int itemColor = 0xFFFFFF;

		public Fluid getFluid()
		{
			return fluid.getFluid();
		}

		public Block getFluidBlock()
		{
			return block != null ? block.getBlock() : null;
		}

		public ItemStack asFluidBlockItemStack(int size)
		{
			return block != null ? block.asStack(size) : null;
		}

		public ItemStack asFluidBlockItemStack()
		{
			return asFluidBlockItemStack(1);
		}

		public ItemStack asBucketItemStack(int size)
		{
			return bucketStack; 
//			return bucket != null ? bucket.asStack(size) : null;
		}

		public ItemStack asBucketItemStack()
		{
			return asBucketItemStack(1);
		}

		public ItemStack asFoodBottleItemStack(int size)
		{
			return foodBottle != null ? foodBottle.asStack(size) : null;
		}

		public ItemStack asFoodBottleItemStack()
		{
			return asFoodBottleItemStack(1);
		}

		public ItemStack asGenericBottleItemStack(int size)
		{
			return bottle != null ? bottle.asStack(size) : null;
		}

		public ItemStack asGenericBottleItemStack()
		{
			return asGenericBottleItemStack(1);
		}

		public ItemStack asBottleItemStack(int size)
		{
			return ObjectUtils.<ItemStack>maybe(asFoodBottleItemStack(size), asGenericBottleItemStack(size));
		}

		public ItemStack asBottleItemStack()
		{
			return asBottleItemStack(1);
		}

		public FluidDetails registerObjects(String modID, String basename)
		{
			if (block != null)
			{
//				block.getBlock().setBlockName(modID + ".BlockFluid" + basename);
				block.register(new ResourceLocation(modID, "blockfluid_" + basename));
			}
			if (bottle != null)
			{
//				bottle.getItem().setUnlocalizedName(modID + "bottlefluid_" + basename);
				bottle.register(new ResourceLocation(modID, "bottlefluid_" + basename));
				final FluidStack fluidStack = fluid.asFluidStack(GrowthcraftCoreConfig.bottleCapacity);
				FluidContainerRegistry.registerFluidContainer(fluidStack, bottle.asStack(1), new ItemStack(Items.GLASS_BOTTLE, 1));
			}
			if (foodBottle != null)
			{
//				foodBottle.getItem().setUnlocalizedName(modID + ".FoodBottleFluid" + basename);
				foodBottle.register(new ResourceLocation(modID, "bottlefluid_" + basename));
				final FluidStack fluidStack = fluid.asFluidStack(GrowthcraftCoreConfig.bottleCapacity);
				FluidContainerRegistry.registerFluidContainer(fluidStack, foodBottle.asStack(1), new ItemStack(Items.GLASS_BOTTLE, 1));
			}
/*			if (bucket != null)
			{
				bucket.getItem().setUnlocalizedName(prefix + ".BucketFluid" + basename);
				bucket.register(prefix + ".BucketFluid" + basename);
				final FluidStack boozeStack = fluid.asFluidStack(FluidContainerRegistry.BUCKET_VOLUME);
				FluidContainerRegistry.registerFluidContainer(boozeStack, bucket.asStack(), FluidContainerRegistry.EMPTY_BUCKET);
			}
			if (block != null && bucket != null)
			{
				EventHandlerBucketFill.instance().register(block.getBlock(), bucket.getItem());
			} */

			return this;
		}
		
		public FluidDetails registerColorHandlers() {
			if (bottle != null) {
				ItemRenderUtils.registerItemColorHandler(bottle.getItem());
			}
			if (foodBottle != null) {
				ItemRenderUtils.registerItemColorHandler(foodBottle.getItem());
			}
			
			return this;
		}

		public FluidDetails setCreativeTab(CreativeTabs tab)
		{
			if (block != null) block.getBlock().setCreativeTab(tab);
			if (bottle != null) bottle.getItem().setCreativeTab(tab);
			if (foodBottle != null) foodBottle.getItem().setCreativeTab(tab);
//			if (bucket != null) bucket.getItem().setCreativeTab(tab);
			return this;
		}

		public FluidDetails setItemColor(int color)
		{
			this.itemColor = color;
			if (bottle != null) bottle.getItem().setColor(color);
			if (foodBottle != null) foodBottle.getItem().setColor(color);
//			if (bucket != null) bucket.getItem().setColor(color);
			return this;
		}

		public FluidDetails setBlockColor(int color)
		{
			if (block != null) block.getBlock().setColor(color);
			return this;
		}

		public FluidDetails refreshItemColor()
		{
			return setItemColor(fluid.getFluid().getColor());
		}

		public FluidDetails refreshBlockColor()
		{
			return setBlockColor(fluid.getFluid().getColor());
		}

		public int getItemColor()
		{
			return itemColor;
		}
	}

	public static final int FEATURE_BLOCK = 1;
	public static final int FEATURE_BOTTLE = 2;
	public static final int FEATURE_FOOD_BOTTLE = 4;
	public static final int FEATURE_BUCKET = 8;
	public static final int FEATURE_NONE = 0;
	public static final int FEATURE_ALL_NON_EDIBLE = FEATURE_BLOCK | FEATURE_BOTTLE | FEATURE_BUCKET;
	public static final int FEATURE_ALL_EDIBLE = FEATURE_BLOCK | FEATURE_FOOD_BOTTLE | FEATURE_BUCKET;
	private static FluidFactory INSTANCE = new FluidFactory();

	public FluidFactory() {}

	public FluidDetails create(Fluid fluid, int features)
	{
		final FluidDetails details = new FluidDetails();
		details.fluid = new FluidDefinition(fluid);
		details.fluid.register(NumUtils.isFlagged(features, FEATURE_BUCKET));
		if (NumUtils.isFlagged(features, FEATURE_BLOCK))
			details.block = GrowthcraftBlockFluidDefinition.create(details.fluid);

		if (NumUtils.isFlagged(features, FEATURE_BOTTLE))
			details.bottle = new ItemTypeDefinition<ItemBottleFluid>(new ItemBottleFluid(fluid));

		if (NumUtils.isFlagged(features, FEATURE_BUCKET))
			details.bucketStack = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, details.fluid.getFluid());
//			details.bucket = new ItemTypeDefinition<ItemBucketFluid>(new ItemBucketFluid(details.block != null ? details.block.getBlock() : null, fluid, null));

		details.refreshItemColor();
		details.refreshBlockColor();
		return details;
	}

	public FluidDetails create(Fluid fluid)
	{
		return create(fluid, FEATURE_ALL_NON_EDIBLE);
	}

	public static FluidFactory instance()
	{
		return INSTANCE;
	}
}
