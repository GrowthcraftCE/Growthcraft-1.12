package growthcraft.core.shared.fluids;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import growthcraft.core.shared.config.GrowthcraftCoreConfig;
import growthcraft.core.shared.client.render.utils.ItemRenderUtils;
import growthcraft.core.shared.block.GrowthcraftBlockFluid;
import growthcraft.core.shared.definition.FluidDefinition;
import growthcraft.core.shared.definition.GrowthcraftBlockFluidDefinition;
import growthcraft.core.shared.definition.ItemTypeDefinition;
import growthcraft.core.shared.item.ItemBottleFluid;
import growthcraft.core.shared.item.ItemFoodBottleFluid;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import growthcraft.core.shared.utils.NumUtils;
import growthcraft.core.shared.utils.ObjectUtils;
import net.minecraft.block.Block;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

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
		private boolean hasBucket = false;
		private ItemStack bucketStack;
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
		
		public FluidDetails registerBlocks(IForgeRegistry<Block> registry, String modID, String basename)
		{
			if (block != null)
			{
				block.register(registry, new ResourceLocation(modID, "fluid_" + basename));
			}

			return this;
		}
		
		public FluidDetails registerItems(IForgeRegistry<Item> registry, String modID, String basename)
		{
			if (bottle != null)
			{
				bottle.registerItem(registry, new ResourceLocation(modID, "bottlefluid_" + basename));
				final FluidStack fluidStack = fluid.asFluidStack(GrowthcraftCoreConfig.bottleCapacity);
				FluidContainerRegistry.registerFluidContainer(fluidStack, bottle.asStack(1), new ItemStack(Items.GLASS_BOTTLE, 1));
			}
			if (foodBottle != null)
			{
				foodBottle.registerItem(registry, new ResourceLocation(modID, "bottlefluid_" + basename));
				final FluidStack fluidStack = fluid.asFluidStack(GrowthcraftCoreConfig.bottleCapacity);
				FluidContainerRegistry.registerFluidContainer(fluidStack, foodBottle.asStack(1), new ItemStack(Items.GLASS_BOTTLE, 1));
			}
			
			if( hasBucket )
			{
				fluid.registerBucketItem();
				if (bucketStack == null )
					bucketStack = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluid.getFluid());
			}
			
			return this;
		}
		
		@SideOnly(Side.CLIENT)
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
			return this;
		}

		public FluidDetails setItemColor(int color)
		{
			this.itemColor = color;
			if (bottle != null) bottle.getItem().setColor(color);
			if (foodBottle != null) foodBottle.getItem().setColor(color);
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
				details.fluid.register();
			if( NumUtils.isFlagged(defaultFeatures, FEATURE_BUCKET) )
				details.hasBucket = true;
			
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
