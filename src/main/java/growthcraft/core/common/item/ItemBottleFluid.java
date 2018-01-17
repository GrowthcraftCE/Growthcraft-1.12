package growthcraft.core.common.item;

import growthcraft.cellar.common.item.IFluidItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

/**
 * Generic fluid bottle for growthcraft fluids
 */
public class ItemBottleFluid extends GrowthcraftItemBase implements IFluidItem, IItemColored
{
	private Fluid fluid;
	// Used to override the fluid color
	private int color = -1;

/*	@SideOnly(Side.CLIENT)
	private IIcon bottle;
	@SideOnly(Side.CLIENT)
	private IIcon contents; */

	public ItemBottleFluid(Fluid flu)
	{
		super();
		setContainerItem(Items.GLASS_BOTTLE);
		this.fluid = flu;
	}

	@Override
	public Fluid getFluid(ItemStack stack)
	{
		return fluid;
	}

	public ItemBottleFluid setColor(int c)
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