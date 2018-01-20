package growthcraft.milk.common.item;

import growthcraft.core.api.definition.IObjectVariant;
import growthcraft.core.api.nbt.NBTHelper;
import growthcraft.core.common.item.IItemTileBlock;
import growthcraft.milk.GrowthcraftMilk;
import growthcraft.milk.Reference;
import growthcraft.milk.api.cheese.CheeseIO;
import growthcraft.milk.api.cheese.CheeseUtils;
import growthcraft.milk.api.definition.EnumCheeseStage;
import growthcraft.milk.api.definition.ICheeseBlockStackFactory;
import growthcraft.milk.api.definition.ICheeseType;
import growthcraft.milk.handlers.EnumHandler;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockCheeseBlock<T extends ICheeseType & IObjectVariant> extends ItemBlockCheeseBase<T> implements IItemTileBlock
{
//	private final T[] typeLookup;
	
	public ItemBlockCheeseBlock(Block block, T[] typeLookup)
	{
		super(block, typeLookup);
		this.maxStackSize = 1;
//		this.typeLookup = typeLookup;
	}
/*	
	@SuppressWarnings("unchecked")
	private T getTypeForVariantID(int variantID) {
		// Maybe the types are ordered in table by ID s beginning with 0 and incrementing by 1
		if( variantID >= 0 && variantID < typeLookup.length && typeLookup[variantID].getVariantID() ==  variantID )
			return typeLookup[variantID];
		
		// Need to search ...
		for( T type : typeLookup ) {
			if( type.getVariantID() == variantID )
				return type;
		}
		
		// Otherwise return a fallback case cheese
		return (T)EnumHandler.WaxedCheeseTypes.CHEDDAR;
	}
*/
	private NBTTagCompound getTileTagCompoundABS(ItemStack stack)
	{
		final NBTTagCompound tag = NBTHelper.openItemStackTag(stack);
		if (!tag.hasKey("te_cheese_block"))
		{
			final NBTTagCompound cheeseTag = new NBTTagCompound();
			final ICheeseType cheese = getTypeForVariantID(CheeseUtils.getVariantIDFromMeta(stack.getItemDamage())); // EnumCheeseType.getSafeById(EnumCheeseType.getTypeFromMeta(stack.getItemDamage()));
			final EnumCheeseStage stage = CheeseUtils.getStageFromMeta(stack.getItemDamage());
			final int slices = CheeseUtils.getSlicesFromMeta(stack.getItemDamage());
			CheeseIO.writeToNBT(cheeseTag, cheese);
			stage.writeToNBT(cheeseTag);
			cheeseTag.setInteger("slices", slices);
			tag.setTag("te_cheese_block", cheeseTag);
		}
		return tag.getCompoundTag("te_cheese_block");
	}

	@Override
	public void setTileTagCompound(ItemStack stack, NBTTagCompound tileTag)
	{
		final NBTTagCompound tag = NBTHelper.openItemStackTag(stack);
		tag.setTag("te_cheese_block", tileTag);
	}

	@Override
	public NBTTagCompound getTileTagCompound(ItemStack stack)
	{
		final NBTTagCompound tag = getTileTagCompoundABS(stack);
		return tag;
	}

	public ICheeseType getCheeseType(ItemStack stack)
	{
		final NBTTagCompound tag = getTileTagCompoundABS(stack);
		return CheeseIO.loadFromNBT(tag);
	}

	public EnumCheeseStage getCheeseStage(ItemStack stack)
	{
		final NBTTagCompound tag = getTileTagCompoundABS(stack);
		return EnumCheeseStage.loadFromNBT(tag);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) +
			"." + getCheeseType(stack).getRegistryName().getResourcePath() +
			"." + getCheeseStage(stack).getName();
	}

	public int getSlices(ItemStack stack)
	{
		return getTileTagCompound(stack).getInteger("slices");
	}

	public int getSlicesMax(ItemStack stack)
	{
		return getTileTagCompound(stack).getInteger("slices_max");
	}
	
	@Override
    public int getMetadata(int damage)
    {
        int numSlices = CheeseUtils.getSlicesFromMeta(damage);
        if( numSlices <= 0 ) {
        	GrowthcraftMilk.logger.warn("Cheese item meta returned slicescount=0.");
        	numSlices = 1;
        }
        return (numSlices - 1) << 2;
    }

	public static NBTTagCompound openNBT(ItemStack stack)
	{
		final Item item = stack.getItem();
		if (item instanceof ItemBlockCheeseBlock)
		{
			return ((ItemBlockCheeseBlock<?>)item).getTileTagCompound(stack);
		}
		else
		{
			// throw error
		}
		return null;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
    	for( T type : getAllVariants() ) {
    		ICheeseBlockStackFactory blockStackFactory = type.getCheeseBlocks();
    		ItemStack stack = blockStackFactory.asStackForStage(4, EnumCheeseStage.AGED /*blockStackFactory.getInitialStage()*/);
    		subItems.add(stack);
    	}
    }
}
