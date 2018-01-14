package growthcraft.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import growthcraft.core.GrowthcraftCoreConfig;
import growthcraft.core.api.utils.NumUtils;
import growthcraft.core.api.utils.ObjectUtils;
import growthcraft.core.client.utils.ItemRenderUtils;
import growthcraft.core.common.block.GrowthcraftBlockFluid;
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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;

/**
 * A simple factory for creating generic fluid bottles, blocks etc..
 */
public class FluidFactory
{
	public static class FluidDetails
	{
		private FluidDefinition fluid;
		private GrowthcraftBlockFluidDefinition block;
		private ItemTypeDefinition<ItemBottleFluid> bottle;
		private ItemTypeDefinition<ItemFoodBottleFluid> foodBottle;
		private ItemStack bucketStack;
//		public ItemTypeDefinition<ItemBucketFluid> bucket;
		private int itemColor = 0xFFFFFF;

		public Fluid getFluid()
		{
			return fluid.getFluid();
		}
		
		@Override
		public FluidDetails clone() {
			FluidDetails copy = new FluidDetails();
			copy.block = block;
			copy.bottle = bottle;
			copy.bucketStack = bucketStack;
			copy.fluid = fluid;
			copy.foodBottle = foodBottle;
			copy.itemColor = itemColor;
			return copy;
		}
		
		public FluidDefinition getFluidDefinition() {
			return fluid;
		}
		
		public FluidStack asFluidStack(int size) {
			return fluid.asFluidStack(size);
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
			if( bucketStack == null )
				return null;
			ItemStack stack = bucketStack.copy();
			stack.setCount(size);
			return stack;
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
		
		public GrowthcraftBlockFluidDefinition getFluidBlockDefinition() {
			return this.block;
		}
		
		public ItemTypeDefinition<ItemBottleFluid> getBottle() {
			return bottle;
		}
		
		public ItemTypeDefinition<ItemFoodBottleFluid> getFoodBottle() {
			return foodBottle;
		}
		
/*		public FluidDetails setFluidBlockDefinition(GrowthcraftBlockFluidDefinition block) {
			this.block = block;
			return this;
		}
		
		public FluidDetails setBottle( ItemTypeDefinition<ItemBottleFluid> bottle ) {
			this.bottle = bottle;
			return this;
		}
		
		public FluidDetails setFoodBottle( ItemTypeDefinition<ItemFoodBottleFluid> foodBottle ) {
			this.foodBottle = foodBottle;
			return this;
		} */

		public FluidDetails registerObjects(String modID, String basename)
		{
			if (block != null)
			{
//				block.getBlock().setBlockName(modID + ".BlockFluid" + basename);
				block.register(new ResourceLocation(modID, "fluid_" + basename));
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
//OBSOLET		EventHandlerBucketFill.instance().register(block.getBlock(), bucket.getItem());
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
		
		public FluidDetails registerRenderer() {
			fluid.registerRenderer();
			if( bottle != null )
				bottle.registerRender();
			if( foodBottle != null )
				foodBottle.registerRender();
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
	
	public static class FluidDetailsBuilder {
		private final FluidDetails prototype = new FluidDetails();
		
		private final Fluid fluid;
		private final int defaultFeatures; 
		
		private Class<? extends GrowthcraftBlockFluid> fluidBlockClazz = null;
//		private Class<? extends ItemBottleFluid> bottleClazz = null;
//		private Class<? extends ItemBottleFluid> foodBottleClazz = null;

		public FluidDetailsBuilder(Fluid fluid) {
			this( fluid, FEATURE_ALL_NON_EDIBLE );
		}
		
		public FluidDetailsBuilder(Fluid fluid, int defaultFeatures) {
			this.fluid = fluid;
			this.defaultFeatures = defaultFeatures;
		}

		public FluidDetailsBuilder setFluidBlock( GrowthcraftBlockFluid block ) {
			this.prototype.block = new GrowthcraftBlockFluidDefinition( block );
			return this;
		}
		
		public FluidDetailsBuilder setFluidBlockClass( Class<? extends GrowthcraftBlockFluid> fluidBlockClazz ) {
			this.fluidBlockClazz = fluidBlockClazz;
			return this;
		}
		
		public FluidDetailsBuilder setBottle( ItemBottleFluid bottle ) {
			prototype.bottle = new ItemTypeDefinition<ItemBottleFluid>( bottle );
			return this;
		}
		
		public FluidDetailsBuilder setFoodBottle( ItemFoodBottleFluid foodBottle ) {
			prototype.foodBottle = new ItemTypeDefinition<ItemFoodBottleFluid>( foodBottle );
			return this;
		}
		
		public FluidDetails build() {
			FluidDetails details = prototype.clone();

			details.fluid = new FluidDefinition(fluid);
			if( !FluidRegistry.isFluidRegistered(fluid) )
				details.fluid.register(NumUtils.isFlagged(defaultFeatures, FEATURE_BUCKET));
			
			if( details.block == null && fluidBlockClazz != null ) {
				try {
					Constructor<? extends GrowthcraftBlockFluid> constr = fluidBlockClazz.getConstructor(Fluid.class);
					details.block = new GrowthcraftBlockFluidDefinition( constr.newInstance(fluid) );
				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new IllegalArgumentException("Failed to construct a fluid block. Details: " + e.getMessage(), e );
				}
			}
			
			if (details.block == null && NumUtils.isFlagged(defaultFeatures, FEATURE_BLOCK))
				details.block = GrowthcraftBlockFluidDefinition.create(fluid);

			if (details.bottle == null && NumUtils.isFlagged(defaultFeatures, FEATURE_BOTTLE))
				details.bottle = new ItemTypeDefinition<ItemBottleFluid>(new ItemBottleFluid(fluid));
			
			if (details.foodBottle == null && NumUtils.isFlagged(defaultFeatures, FEATURE_FOOD_BOTTLE))
				details.foodBottle = new ItemTypeDefinition<ItemFoodBottleFluid>(new ItemFoodBottleFluid(fluid));
	
			if (details.bucketStack == null && NumUtils.isFlagged(defaultFeatures, FEATURE_BUCKET))
				details.bucketStack = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluid);
	//			details.bucket = new ItemTypeDefinition<ItemBucketFluid>(new ItemBucketFluid(details.block != null ? details.block.getBlock() : null, fluid, null));

			details.refreshItemColor();
			details.refreshBlockColor();
			return details;
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
	
	public static FluidFactory instance()
	{
		return INSTANCE;
	}
}
