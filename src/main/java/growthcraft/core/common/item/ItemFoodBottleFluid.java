package growthcraft.core.common.item;

import javax.annotation.Nullable;

import growthcraft.cellar.common.item.IFluidItem;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Generic fluid bottle for growthcraft fluids that are edible
 */
public class ItemFoodBottleFluid extends GrowthcraftItemFoodBase implements IFluidItem, IItemColored
{
	private Fluid defaultFluid;
	// Used to override the fluid color
	private int color = -1;

/*	@SideOnly(Side.CLIENT)
	private IIcon bottle;
	@SideOnly(Side.CLIENT)
	private IIcon contents; */

	public ItemFoodBottleFluid(@Nullable Fluid defaultFluid, int healAmount, float saturation, boolean isWolfFavouriteFood)
	{
		super(healAmount, saturation, isWolfFavouriteFood);
		setItemUseAction(EnumAction.DRINK);
		setContainerItem(Items.GLASS_BOTTLE);
		if( defaultFluid == null )
			defaultFluid = FluidRegistry.WATER;
		this.defaultFluid = defaultFluid;
	}

	public ItemFoodBottleFluid(@Nullable Fluid defaultFluid, int healAmount, float saturation)
	{
		this(defaultFluid, healAmount, saturation, false);
	}

	public ItemFoodBottleFluid(@Nullable Fluid defaultFluid, int healAmount)
	{
		this(defaultFluid, healAmount, 0.0f);
	}

	public ItemFoodBottleFluid(@Nullable Fluid defaultFluid)
	{
		this(defaultFluid, 0);
	}

	@Override
	public Fluid getFluid(ItemStack stack)
	{
		return defaultFluid;
	}

	public ItemFoodBottleFluid setColor(int c)
	{
		this.color = c;
		return this;
	}

	@Override
	public int getColor(ItemStack stack)
	{
		if (color != -1) return color;
		return getFluid(stack).getColor();
	}
	
/*
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		this.bottle = reg.registerIcon("minecraft:potion_bottle_empty");
		this.contents = reg.registerIcon("minecraft:potion_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int _damage, int pass)
	{
		return pass == 0 ? this.contents : this.bottle;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		return pass == 0 ? getColor(stack) : 0xFFFFFF;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	*/
}
