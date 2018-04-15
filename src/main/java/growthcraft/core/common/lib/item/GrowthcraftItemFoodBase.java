package growthcraft.core.common.lib.item;

import java.util.ArrayList;
import java.util.List;

import growthcraft.core.client.lib.GrowthcraftCoreState;
import growthcraft.core.common.lib.effect.IEffect;
import growthcraft.core.common.lib.utils.ItemUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrowthcraftItemFoodBase extends ItemFood
{
	private IEffect effect;
	private EnumAction action = EnumAction.EAT;

	public GrowthcraftItemFoodBase(int hunger, float saturation, boolean isWolfFav)
	{
		super(hunger, saturation, isWolfFav);
	}

	public GrowthcraftItemFoodBase(int hunger, boolean isWolfFav)
	{
		super(hunger, isWolfFav);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return action;
	}

	public GrowthcraftItemFoodBase setItemUseAction(EnumAction act)
	{
		this.action = act;
		return this;
	}

	public GrowthcraftItemFoodBase setEffect(IEffect ef)
	{
		this.effect = ef;
		return this;
	}

	public IEffect getEffect()
	{
		return effect;
	}

	protected void applyIEffects(ItemStack itemStack, World world, EntityPlayer player)
	{
		if (effect != null)
		{
			effect.apply(world, player, world.rand, itemStack);
		}
	}

	@Override
	protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player)
	{
		super.onFoodEaten(itemStack, world, player);
		if (!world.isRemote)
		{
			applyIEffects(itemStack, world, player);
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		if( entityLiving instanceof EntityPlayer ) {
			EntityPlayer player = (EntityPlayer)entityLiving;
			
			if (!player.capabilities.isCreativeMode)
			{
				if (!worldIn.isRemote)
				{
					stack = stack.copy();
					final ItemStack result = ItemUtils.consumeStack(stack.splitStack(1));
					ItemUtils.addStackToPlayer(result, player, worldIn, false);
				}
			}

			player.getFoodStats().addStats(this, stack);
			worldIn.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
			this.onFoodEaten(stack, worldIn, player);
		}

//		return result.getCount() <= 0 ? null : result;
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean showAdvanced)
	{
		super.addInformation(stack, player, list, showAdvanced);
		GrowthcraftItemBase.addDescription(this, stack, player, list, showAdvanced);
		if (effect != null)
		{
			final List<String> tempList = new ArrayList<String>();
			effect.getDescription(tempList);
			if (tempList.size() > 0)
			{
				if (GrowthcraftCoreState.showDetailedInformation())
				{
					list.addAll((List)tempList);
				}
				else
				{
					list.add(TextFormatting.GRAY +
							I18n.format("grc.tooltip.detailed_information",
								TextFormatting.WHITE + GrowthcraftCoreState.detailedKey + TextFormatting.GRAY));
				}
			}
		}
	}
}
