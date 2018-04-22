package growthcraft.bees.common.lib.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.bees.GrowthcraftBees;
import growthcraft.bees.shared.Reference;
import growthcraft.core.shared.item.ItemKey;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.block.BlockKey;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BeesRegistry
{
	private static final BeesRegistry INSTANCE = new BeesRegistry();

	private final List<ItemKey> beesList = new ArrayList<ItemKey>();
	private final Map<ItemKey, ItemStack> emptyToFullHoneyComb = new HashMap<ItemKey, ItemStack>();
	private final Map<ItemKey, ItemStack> fullToEmptyHoneyComb = new HashMap<ItemKey, ItemStack>();
	private final Map<BlockKey, IFlowerBlockEntry> flowerEntries = new HashMap<BlockKey, IFlowerBlockEntry>();

	public static final BeesRegistry instance()
	{
		return INSTANCE;
	}

	private ItemKey stackToKey(@Nonnull ItemStack itemstack)
	{
		return new ItemKey(itemstack);
	}

	///////////////////////////////////////////////////////////////////////
	// BEES ///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	/**
	 * addBee()
	 * Adds a custom bee the mod.
	 * NOTE: This is not meta-sensitive.
	 *
	 * @param bee - The Item/Block to be registered.
	 */
	public void addBee(@Nonnull Item bee, int meta)
	{
		GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding Bee {%s}:%d", bee, meta);
		beesList.add(new ItemKey(bee, meta));
	}

	public void addBee(@Nonnull Block bee, int meta)
	{
		addBee(Item.getItemFromBlock(bee), meta);
	}
	
	public void addBee(@Nonnull IBlockState beeState)
	{
		Block block = beeState.getBlock();
		addBee(Item.getItemFromBlock(block), block.getMetaFromState(beeState));
	}

	public void addBee(@Nonnull ItemStack stack)
	{
		final ItemKey key = stackToKey(stack);
		GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding Bee {%s}", key);
		beesList.add(key);
	}

	public void addBee(@Nonnull Item bee)
	{
		addBee(bee, ItemKey.WILDCARD_VALUE);
	}

	public void addBee(@Nonnull Block bee)
	{
		addBee(Item.getItemFromBlock(bee), ItemKey.WILDCARD_VALUE);
	}

	protected void addHoneyCombMapping(@Nonnull ItemStack empty, @Nonnull ItemStack full)
	{
		GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding Honey Comb mapping {%s} - {%s}", empty, full);
		emptyToFullHoneyComb.put(stackToKey(empty), full);
		fullToEmptyHoneyComb.put(stackToKey(full), empty);
	}

	public void addHoneyComb(@Nonnull ItemStack empty, @Nonnull ItemStack full)
	{
		addHoneyCombMapping(empty, full);
	}

	public ItemStack getFilledHoneyComb(@Nonnull ItemStack itemstack)
	{
		return emptyToFullHoneyComb.get(stackToKey(itemstack));
	}

	public ItemStack getEmptyHoneyComb(@Nonnull ItemStack itemstack)
	{
		return fullToEmptyHoneyComb.get(stackToKey(itemstack));
	}

	protected boolean isItemFilledHoneyComb(@Nonnull ItemKey key)
	{
		return fullToEmptyHoneyComb.containsKey(key);
	}

	public boolean isItemFilledHoneyComb(@Nonnull ItemStack itemstack)
	{
		return isItemFilledHoneyComb(stackToKey(itemstack));
	}

	protected boolean isItemEmptyHoneyComb(@Nonnull ItemKey key)
	{
		return emptyToFullHoneyComb.containsKey(key);
	}

	public boolean isItemEmptyHoneyComb(@Nonnull ItemStack itemstack)
	{
		return isItemEmptyHoneyComb(stackToKey(itemstack));
	}

	public boolean isItemHoneyComb(@Nonnull ItemStack itemstack)
	{
		final ItemKey key = stackToKey(itemstack);
		return isItemFilledHoneyComb(key) || isItemEmptyHoneyComb(key);
	}

	public void addFlower(@Nonnull BlockKey key, @Nonnull IFlowerBlockEntry entry)
	{
		GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding Flower {%s}:{%s}", key, entry);
		flowerEntries.put(key, entry);
	}

	public void addFlower(@Nonnull IFlowerBlockEntry entry)
	{
		addFlower(new BlockKey(entry.getBlockState()), entry);
	}

	/**
	 * Adds a custom flower the mod.
	 * NOTE: This is meta-sensitive.
	 *
	 * @param flower - Block to be registered.
	 * @param meta   - Metadata of the block to be registered.
	 */
	public void addFlower(@Nonnull Block flower, int meta)
	{
		addFlower(new BlockKey(flower, meta), new GenericFlowerBlockEntry(flower, meta));
	}

	/**
	 * Flower wildcard
	 *
	 * @param flower - Block to be registered.
	 */
	public void addFlower(@Nonnull Block flower)
	{
		addFlower(flower, ItemKey.WILDCARD_VALUE);
	}
	
	/**
	 * Adds a custom flower the mod.
	 * NOTE: This is meta-sensitive.
	 *
	 * @param flower - Block to be registered.
	 * @param meta   - Metadata of the block to be registered.
	 */
	public void addFlower(@Nonnull IBlockState flower)
	{
		addFlower(new BlockKey(flower), new GenericFlowerBlockEntry(flower));
	}

	/**
	 * @param itemstack - an itemstack to check
	 * @return Does the provided itemstack contain any known bees?
	 */
	public boolean isItemBee(@Nullable ItemStack itemstack)
	{
		if (itemstack == null) return false;
		final Item item = itemstack.getItem();
		final int meta = itemstack.getItemDamage();
		for (ItemKey key : beesList)
		{
			if (item == key.item)
			{
				if (key.meta == ItemKey.WILDCARD_VALUE || key.meta == meta)
				{
					return true;
				}
			}
		}
		return false;
	}

	public IFlowerBlockEntry getFlowerBlockEntry(@Nonnull BlockKey key)
	{
		return flowerEntries.get(key);
	}

	public IFlowerBlockEntry getFlowerBlockEntry(@Nullable Block block, int meta)
	{
		if (block == null) return null;
		final IFlowerBlockEntry entry = getFlowerBlockEntry(new BlockKey(block, meta));
		if (entry != null) return entry;
		return getFlowerBlockEntry(new BlockKey(block, ItemKey.WILDCARD_VALUE));
	}
	
	public IFlowerBlockEntry getFlowerBlockEntry(@Nullable IBlockState blockState) {
		if( blockState == null )
			return null;
		Block block = blockState.getBlock();
		return getFlowerBlockEntry(block, block.getMetaFromState(blockState));
	}

	public boolean isBlockFlower(@Nullable IBlockState blockState)
	{
		return flowerEntries.containsKey(new BlockKey(blockState)) ||
			flowerEntries.containsKey(new BlockKey(blockState.getBlock(), ItemKey.WILDCARD_VALUE));
	}
}
