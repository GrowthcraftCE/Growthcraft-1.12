package growthcraft.milk.common.tileentity;

import java.io.IOException;
import java.util.List;

import growthcraft.core.shared.io.nbt.INBTItemSerializable;
import growthcraft.core.shared.tileentity.GrowthcraftTileBase;
import growthcraft.core.shared.tileentity.event.TileEventHandler;
import growthcraft.core.shared.tileentity.feature.IItemOperable;
import growthcraft.core.shared.item.ItemUtils;
import growthcraft.milk.common.item.ItemBlockCheeseBlock;
import growthcraft.milk.common.tileentity.struct.Cheese;
import growthcraft.milk.shared.definition.ICheeseBlockStackFactory;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntityCheeseBlock extends GrowthcraftTileBase implements ITickable, IItemOperable, INBTItemSerializable
{
	private Cheese cheese = new Cheese();

	public List<ItemStack> populateDrops(List<ItemStack> list)
	{
		ItemStack stack = null;
		boolean bHasAllSlices = cheese.getSlicesMax() == cheese.getSlices();
		boolean bHasAnySlices = cheese.getSlices() > 0;
		if( cheese.isAged() && bHasAnySlices ) {
/*			if( !bHasAllSlices )
				stack = cheese.asFullStack();
			else*/
				stack = cheese.getType().getCheeseBlocks().asStackForStage(cheese.getSlices(), cheese.getStage());
		}
		else if( bHasAllSlices )
			stack = cheese.getType().getCheeseBlocks().asStackForStage(cheese.getSlices(), cheese.getStage());
		
		if (stack != null)
			list.add(stack);
		
/*		if (cheese.isAged())
		{
			final ItemStack stack = cheese.asFullStack();
			if (stack != null) list.add(stack);
		} */
		return list;
	}

	public Cheese getCheese()
	{
		return cheese;
	}

	public int getCheeseId()
	{
		return getCheese().getId();
	}

	public int getCheeseStageId()
	{
		return getCheese().getStageId();
	}

	protected void readCheeseFromNBT(NBTTagCompound nbt)
	{
		cheese.readFromNBT(nbt);
	}

	/**
	 * When the tileentity is reloaded from an ItemStack
	 *
	 * @param nbt  tag compound to read
	 */
	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readCheeseFromNBT(nbt);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_READ)
	public void readFromNBT_CheeseBlock(NBTTagCompound nbt)
	{
		readCheeseFromNBT(nbt);
	}

	protected void writeCheeseToNBT(NBTTagCompound nbt)
	{
		cheese.writeToNBT(nbt);
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeCheeseToNBT(nbt);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_WRITE)
	public void writeToNBT_CheeseBlock(NBTTagCompound nbt)
	{
		writeCheeseToNBT(nbt);
	}

	public ItemStack asItemStack()
	{
		final ICheeseBlockStackFactory blockStackFactory = cheese.getType().getCheeseBlocks();
		final ItemStack stack = blockStackFactory.asStackForStage(cheese.getSlices(), blockStackFactory.getInitialStage());  // GrowthcraftMilkBlocks.cheeseBlock.asStack();
		final NBTTagCompound tag = ItemBlockCheeseBlock.openNBT(stack);
		writeToNBTForItem(tag);
		return stack;
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_CheeseBlock(ByteBuf stream) throws IOException
	{
		cheese.readFromStream(stream);
		return true;
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_CheeseBlock(ByteBuf stream) throws IOException
	{
		cheese.writeToStream(stream);
		return true;
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			cheese.update();
			if (cheese.needClientUpdate)
			{
				cheese.needClientUpdate = false;
				if (cheese.hasSlices())
				{
					markForUpdate();
				}
				else
				{
					world.setBlockToAir(getPos());
				}
			}
		}
	}
	
/*
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		// return super.shouldRefresh(world, pos, oldState, newSate);
		return false;
	}
*/
	
	@Override
	public boolean tryPlaceItem(IItemOperable.Action action, EntityPlayer player, ItemStack onHand)
	{
		if (IItemOperable.Action.RIGHT != action) return false;
		if( cheese.tryWaxing(onHand) ) {
			if( !player.capabilities.isCreativeMode ) {
				if( onHand.getCount() > 1 ) {
					onHand.shrink(1);
					player.inventory.setInventorySlotContents(player.inventory.currentItem, onHand);
				}
				else
					player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);				
			}
			markDirtyAndUpdate(); // Test, if correct
			return true;
		}
		return false;
	}

	@Override
	public boolean tryTakeItem(IItemOperable.Action action, EntityPlayer player, ItemStack onHand)
	{
		if (IItemOperable.Action.RIGHT != action) return false;
		if (cheese.isAged())
		{
			final ItemStack stack = cheese.yankSlices(1, true);
			if (!ItemUtils.isEmpty(stack))
			{
				ItemUtils.addStackToPlayer(stack, player, false);
			}
			markDirtyAndUpdate();
			cheese.needClientUpdate |= true;
			return true;
		}
		return false;
	}
}
