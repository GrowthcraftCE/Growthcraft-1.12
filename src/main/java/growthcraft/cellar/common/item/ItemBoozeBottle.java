package growthcraft.cellar.common.item;

import java.util.List;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.Reference;
import growthcraft.cellar.api.CellarRegistry;
import growthcraft.cellar.api.booze.Booze;
import growthcraft.cellar.api.booze.BoozeEntry;
import growthcraft.cellar.common.definition.BoozeDefinition;
import growthcraft.cellar.util.BoozeUtils;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.common.definition.FluidDefinition;
import growthcraft.core.common.item.GrowthcraftItemFoodBase;
import growthcraft.core.lib.GrowthcraftCoreState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBoozeBottle extends GrowthcraftItemFoodBase implements IFluidItem
{
	private Booze[] boozes;

/*	@SideOnly(Side.CLIENT)
	private IIcon bottle;
	@SideOnly(Side.CLIENT)
	private IIcon contents; */

	public ItemBoozeBottle(BoozeDefinition[] boozeAry)
	{
		super(0, 0.0f, false);
		this.setAlwaysEdible();
		this.setMaxStackSize(4);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setContainerItem(Items.GLASS_BOTTLE);
		this.setCreativeTab(GrowthcraftCore.tabGrowthcraft);

		this.boozes = FluidDefinition.convertArray(boozeAry);
	}

	public Fluid[] getFluidArray()
	{
		return this.boozes;
	}

	public Fluid getFluidByIndex(int i)
	{
		return (i < 0 || i >= boozes.length) ? boozes[0] : boozes[i];
	}

	@Override
	public Fluid getFluid(ItemStack stack)
	{
		if (stack == null) return null;
		return getFluidByIndex(stack.getItemDamage());
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

/*
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		this.bottle = reg.registerIcon("grccellar:booze");
		this.contents = reg.registerIcon("grccellar:booze_contents");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int pass)
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack/*, int pass*/)
	{
		return BoozeUtils.hasEffect(getFluid(stack));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName();
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
//TODO		playerIn.setItemInUse(stack, this.getMaxItemUseDuration(stack));
//		return stack;
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