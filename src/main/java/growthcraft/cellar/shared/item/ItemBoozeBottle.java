package growthcraft.cellar.shared.item;

import java.util.List;

import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.fluids.Booze;
import growthcraft.cellar.shared.booze.BoozeEntry;
import growthcraft.cellar.shared.definition.BoozeDefinition;
import growthcraft.cellar.shared.booze.BoozeUtils;
import growthcraft.core.shared.definition.FluidDefinition;
import growthcraft.core.shared.item.IFluidContainerItem;
import growthcraft.core.shared.item.ItemFoodBottleFluid;
import growthcraft.core.shared.client.GrowthcraftCoreState;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBoozeBottle extends ItemFoodBottleFluid implements IFluidContainerItem
{
	private Booze[] boozes;

	public ItemBoozeBottle()
	{
		super(null, 0, 0.0f, false);
		this.setAlwaysEdible();
		this.setMaxStackSize(4);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
//		this.setContainerItem(Items.GLASS_BOTTLE);
	}
	
	public ItemBoozeBottle setBoozes(BoozeDefinition[] boozeAry) {
		this.boozes = FluidDefinition.convertArray(boozeAry, Booze.class);
		return this;
	}

	public Booze[] getFluidArray()
	{
		return this.boozes;
	}

	public Booze getFluidByIndex(int i)
	{
		if( boozes == null )
			return null;	// Boozes not initialized, yet. Fallback
		return (i < 0 || i >= boozes.length) ? boozes[0] : boozes[i];
	}

	public Booze getFluid(ItemStack stack) {
		if (stack == null) return null;
		return getFluidByIndex(stack.getItemDamage());
	}
	
	@Override
	public FluidStack getFluidStack(ItemStack stack)
	{
		Booze fluid = getFluid(stack);
		if( fluid == null )
			return null;
		return new FluidStack(fluid, FluidContainerRegistry.BOTTLE_VOLUME);
	}

	public BoozeEntry getBoozeEntry(ItemStack stack)
	{
		final Fluid fluid = getFluid(stack);
		if (fluid != null)
		{
			return CellarRegistry.instance().booze().getBoozeEntry(fluid);
		}
		return null;
	}

	@Override
	public int getHealAmount(ItemStack stack)
	{
		final BoozeEntry entry = getBoozeEntry(stack);
		if (entry != null)
		{
			return entry.getHealAmount();
		}
		return 0;
	}

	@Override
	public float getSaturationModifier(ItemStack stack)
	{
		final BoozeEntry entry = getBoozeEntry(stack);
		if (entry != null)
		{
			return entry.getSaturation();
		}
		return 0.0f;
	}

	@Override
	public int getColor(ItemStack stack)
	{
		final Fluid booze = getFluid(stack);
		if (booze != null) return booze.getColor();
		return 0xFFFFFF;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if (stack.getItemDamage() >= getFluidArray().length)
		{
			stack.setItemDamage(0);
		}
	}

	@Override
	protected void applyIEffects(ItemStack itemStack, World world, EntityPlayer player)
	{
		super.applyIEffects(itemStack, world, player);
		BoozeUtils.addEffects(getFluid(itemStack), itemStack, world, player);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		super.addInformation(stack, player, list, bool);
		final boolean showDetailed = GrowthcraftCoreState.showDetailedInformation();
		BoozeUtils.addBottleInformation(getFluid(stack), stack, player, list, bool, showDetailed);
		if (!showDetailed)
		{
			list.add(TextFormatting.GRAY +
					I18n.format("grc.tooltip.detailed_information",
						TextFormatting.WHITE + GrowthcraftCoreState.detailedKey + TextFormatting.GRAY));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return BoozeUtils.hasEffect(getFluid(stack));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		Booze booze = getFluid(stack);
		return super.getUnlocalizedName() + "_" + booze.getName().substring(12);  // skipping "fluid_booze_" part
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.DRINK;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		final Fluid booze = getFluid(stack);
		if (booze != null)
		{
			return I18n.format(booze.getUnlocalizedName());
		}
		return super.getItemStackDisplayName(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		for (int i = 0; i < getFluidArray().length; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
}