package growthcraft.core.utils;

import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * Utility class for item handling.
 */
public class ItemUtils
{
	private ItemUtils() {}

	public static NBTTagCompound openTagCompound(ItemStack stack)
	{
		if (!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		return stack.getTagCompound();
	}

	public static ItemStack[] clearInventorySlots(ItemStack[] invSlots, int expectedSize)
	{
		// avoid reallocating an existing ItemStack array
		if (invSlots == null || invSlots.length != expectedSize)
		{
			return new ItemStack[expectedSize];
		}
		else
		{
			// clear it instead
			for (int i = 0; i < invSlots.length; ++i)
			{
				invSlots[i] = null;
			}
		}
		return invSlots;
	}

	public static ItemStack increaseStack(ItemStack itemstack, int amount)
	{
		ItemStack containerItem = getContainerItem(itemstack);
		
		itemstack.setCount( MathHelper.clamp(itemstack.getCount() + amount, 0, itemstack.getMaxStackSize()) );
		if (itemstack.isEmpty())
		{
/*			final Item item = itemstack.getItem();
			if (item.hasContainerItem(itemstack))
			{
				return item.getContainerItem(itemstack);
			}
			else
			{
				return null;
			}*/
			return containerItem;
		}
		return itemstack;
	}
	
	public static ItemStack getContainerItem(ItemStack itemstack) {
		final Item item = itemstack.getItem();
		if (item.hasContainerItem(itemstack))
		{
			return item.getContainerItem(itemstack);
		}
		else
		{
			return new ItemStack(Items.AIR, 1, 0);
		}
	}

	public static ItemStack increaseStack(ItemStack itemstack)
	{
		return increaseStack(itemstack, 1);
	}

	public static ItemStack consumeStack(ItemStack itemstack, int amount)
	{
		return increaseStack(itemstack, -amount);
	}

	public static ItemStack consumeStack(ItemStack itemstack)
	{
		return consumeStack(itemstack, 1);
	}

	/**
	 * Destructive version of mergeStacks, may mutate both item stacks
	 *
	 * @param a - primary stack to merge to
	 *   If the secondary stack is null, then this stack is returned as is
	 * @param b - secondary stack to merge from
	 *   If the primary stack is null, then a copy of this stack is returned and
	 *   the original's stackSize reduced to 0
	 * @return new ItemStack - the resulting ItemStack of null
	 */
	public static ItemStack mergeStacksBang(ItemStack a, ItemStack b)
	{
		if (a == null && b == null)
		{
			return null;
		}
		else if (a == null && b != null)
		{
			final ItemStack result = b.copy();
			b.setCount(0);
			return result;
		}
		else if (a != null && b == null)
		{
			return a;
		}
		else
		{
			if (a.isItemEqual(b))
			{
				final int newSize = MathHelper.clamp(a.getCount() + b.getCount(), 0, a.getMaxStackSize());
				b.setCount( b.getCount() - newSize - a.getCount() );
				a.setCount(newSize);
				return a;
			}
		}
		return null;
	}

	/**
	 * Less-destructive version of mergeStacksBang
	 *
	 * @param a - destination stack, may be modified
	 * @param b - source stack, will not be modified
	 * @return new ItemStack
	 */
	public static ItemStack mergeStacks(ItemStack a, ItemStack b)
	{
		return mergeStacksBang(a, b != null ? b.copy() : b);
	}

	public static void replacePlayerCurrentItem(@Nonnull EntityPlayer player, ItemStack stack)
	{
		player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
	}

	/**
	 * Uses one item on the
	 */
	public static ItemStack consumeStackOnPlayer(ItemStack itemstack, @Nonnull EntityPlayer player)
	{
		if (player.capabilities.isCreativeMode) return itemstack;
		final ItemStack result = consumeStack(itemstack);
		replacePlayerCurrentItem(player, result);
		return result;
	}

	public static ItemStack decrPlayerInventorySlot(@Nonnull EntityPlayer player, int slot, int amount)
	{
		return player.inventory.decrStackSize(slot, amount);
	}

	public static ItemStack decrPlayerCurrentInventorySlot(@Nonnull EntityPlayer player, int amount)
	{
		return player.inventory.decrStackSize(player.inventory.currentItem, amount);
	}

	public static void addStackToPlayer(ItemStack itemstack, @Nonnull EntityPlayer player, World world, BlockPos pos, boolean checkCreative)
	{
		final boolean flag = checkCreative ? !player.capabilities.isCreativeMode : true;
		if (flag)
		{
			if (!player.inventory.addItemStackToInventory(itemstack))
			{
				player.dropItem(itemstack, false);
			}
			else if (player instanceof EntityPlayerMP)
			{
				((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
			}
		}
	}

	public static void addStackToPlayer(ItemStack itemstack, EntityPlayer player, World world, boolean checkCreative)
	{
		BlockPos pos = new BlockPos((int)player.posX, (int)player.posY, (int)player.posZ);
		addStackToPlayer(itemstack, player, world, pos, checkCreative);
	}

	public static void addStackToPlayer(ItemStack itemstack, EntityPlayer player, boolean checkCreative)
	{
		addStackToPlayer(itemstack, player, player.world, checkCreative);
	}

	public static void spawnItemStack(World world, double x, double y, double z, ItemStack stack, Random random)
	{
		if (stack != null)
		{
			final double f = random.nextDouble() * 0.8D + 0.1D;
			final double f1 = random.nextDouble() * 0.8D + 0.1D;
			final double f2 = random.nextDouble() * 0.8D + 0.1D;

			final EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, stack);
			final float f3 = 0.05F;
			entityitem.motionX = random.nextGaussian() * f3;
			entityitem.motionY = random.nextGaussian() * f3 + 0.2F;
			entityitem.motionZ = random.nextGaussian() * f3;
			world.spawnEntity(entityitem);
		}
	}
	
	public static void spawnItemStack(World world, BlockPos pos, ItemStack stack, Random random) {
		spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack, random);
	}

	public static void spawnBrokenItemStack(World world, BlockPos pos, ItemStack stack, Random random)
	{
		if (stack != null)
		{
			while (stack.getCount() > 0)
			{
				int k1 = random.nextInt(21) + 10;

				if (k1 > stack.getCount())
				{
					k1 = stack.getCount();
				}

				final ItemStack entityStack = stack.splitStack(k1);

				spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), entityStack, random);
			}
		}
	}

	public static void spawnItemStackAtEntity(ItemStack stack, Entity entity, Random random)
	{
		spawnItemStack(entity.world, entity.posX, entity.posY, entity.posZ, stack, random);
	}

	public static void spawnItemStackAtTile(ItemStack stack, TileEntity tile, Random random)
	{
		BlockPos pos = tile.getPos();
		spawnItemStack(tile.getWorld(), pos.getX(), pos.getY(), pos.getZ(), stack, random);
	}

	/**
	 * NOTICE: This method was copied from ForestryMC ItemStackUtil
	 *
	 * @param stack - item stack to retrieve block from
	 * @return block
	 */
	public static Block getBlock(ItemStack stack)
	{
		if (stack == null) return null;
		final Item item = stack.getItem();
		if (item instanceof ItemBlock) return ((ItemBlock)item).getBlock();
		return null;
	}

	/**
	 * NOTICE: This method was copied from ForestryMC ItemStackUtil, and modified.
	 *
	 * @param block - block to check
	 * @param stack - item stack to check
	 * @return true the block matches the provided item stack
	 */
	public static boolean equals(Block block, ItemStack stack)
	{
		if (stack == null) return false;
		return block == getBlock(stack);
	}

	/**
	 * NOTICE: This method was copied from ForestryMC ItemStackUtil
	 *
	 * @param block - block to check
	 * @param meta - block's metadata
	 * @param stack - item stack to check
	 * @return true the block matches the provided item stack
	 */
	public static boolean equals(Block block, int meta, ItemStack stack)
	{
		if (stack == null) return false;
		return block == getBlock(stack) && meta == stack.getItemDamage();
	}

	/**
	 * Determines if item is an IToolWrench
	 *
	 * @param item - an item to check
	 * @return true if the item is a IToolWrench, false otherwise
	 */
	public static boolean isIToolWrench(ItemStack item)
	{
		// OPEN_ADHOC
		return false;
		
//TODO		if (item == null) return false;
//TODO		return item.getItem() instanceof IToolWrench;
	}

	/**
	 * Checks if the provided item is a Wrench
	 *
	 * @param item - an item to check
	 * @return true if the item is a wrench, false otherwise
	 */
	public static boolean isWrench(ItemStack item)
	{
		if (item == null) return false;
		return isIToolWrench(item);
	}

	public static boolean canWrench(ItemStack item, EntityPlayer player, BlockPos pos)
	{
		// OPEN_ADHOC
		
//TODO:		if (isIToolWrench(item))
//TODO:		{
//TODO:			return ((IToolWrench)item.getItem()).canWrench(player, x, y, z);
//TODO:		}
		return false;
	}

	public static void wrenchUsed(ItemStack item, EntityPlayer player, BlockPos pos)
	{
		// OPEN_ADHOC
		
		if (item == null) return;
//TODO:		if (isIToolWrench(item))
//TODO:		{
//TODO:			((IToolWrench)item.getItem()).wrenchUsed(player, x, y, z);
//TODO:		}
	}
}
