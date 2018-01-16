package growthcraft.milk.common.tileentity;

import java.io.IOException;
import java.util.List;

import growthcraft.core.api.nbt.INBTItemSerializable;
import growthcraft.core.common.tileentity.GrowthcraftTileBase;
import growthcraft.core.common.tileentity.event.TileEventHandler;
import growthcraft.core.common.tileentity.feature.IAltItemHandler;
import growthcraft.core.utils.ItemUtils;
import growthcraft.milk.api.definition.ICheeseBlockStackFactory;
import growthcraft.milk.common.item.ItemBlockCheeseBlock;
import growthcraft.milk.common.struct.Cheese;
import growthcraft.milk.init.GrowthcraftMilkBlocks;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntityCheeseBlock extends GrowthcraftTileBase implements ITickable, IAltItemHandler, INBTItemSerializable
{
	private Cheese cheese = new Cheese();

	public List<ItemStack> populateDrops(List<ItemStack> list)
	{
		ItemStack stack = null;
		boolean bHasAllSlices = cheese.getSlicesMax() == cheese.getSlices();
		if( cheese.isAged() ) {
			if( !bHasAllSlices )
				stack = cheese.asFullStack();
			else
				stack = cheese.getType().getCheeseBlocks().asStackForStage(cheese.getStage());
		}
		else if( bHasAllSlices )
			stack = cheese.getType().getCheeseBlocks().asStackForStage(cheese.getStage());
		
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
		final ItemStack stack = blockStackFactory.asStackForStage(blockStackFactory.getInitialStage());  // GrowthcraftMilkBlocks.cheeseBlock.asStack();
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

	@Override
	public boolean tryPlaceItem(IAltItemHandler.Action action, EntityPlayer player, ItemStack onHand)
	{
		if (IAltItemHandler.Action.RIGHT != action) return false;
		if( cheese.tryWaxing(onHand) ) {
			if( !player.capabilities.isCreativeMode ) {
				if( onHand.getCount() > 1 ) {
					onHand.shrink(1);
					player.inventory.setInventorySlotContents(player.inventory.currentItem, onHand);
				}
				else
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);				
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean tryTakeItem(IAltItemHandler.Action action, EntityPlayer player, ItemStack onHand)
	{
		if (IAltItemHandler.Action.RIGHT != action) return false;
		if (cheese.isAged())
		{
			final ItemStack stack = cheese.yankSlices(1, true);
			if (stack != null)
			{
				ItemUtils.addStackToPlayer(stack, player, false);
			}
			cheese.needClientUpdate |= true;
			return true;
		}
		return false;
	}
}
