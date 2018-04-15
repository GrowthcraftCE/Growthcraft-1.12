package growthcraft.cellar.common.tileentity;

import java.io.IOException;

import growthcraft.cellar.common.block.BlockFruitPresser;
import growthcraft.cellar.common.block.BlockFruitPresser.PressState;
import growthcraft.cellar.init.GrowthcraftCellarBlocks;
import growthcraft.core.common.tileentity.GrowthcraftTileBase;
import growthcraft.core.common.tileentity.event.TileEventHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityFruitPresser extends GrowthcraftTileBase implements ITickable
{
	// INITIALIZE
	
	public float trans;
	public float transPrev;

	private float transSpd = 0.21875F;
	private float transMin;
	private float transMax = 0.4375F;

	@Override
	public void update()
	{
		if (GrowthcraftCellarBlocks.fruitPresser.getBlock() != getBlockType())
		{
			invalidate();
			return;
		}

		IBlockState state = world.getBlockState(getPos());
		final PressState pressed = state.getValue(BlockFruitPresser.TYPE_PRESSED);
		this.transPrev = this.trans;

		if (pressed == PressState.UNPRESSED && this.trans > this.transMin)
		{
			this.trans -= this.transSpd;
		}
		else if (pressed == PressState.PRESSED && this.trans < this.transMax)
		{
			this.trans += this.transSpd;
		}
	}

	public float getTranslation()
	{
		return this.trans;
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_READ)
	public void readFromNBT_FruitPresser(NBTTagCompound nbt)
	{
		this.trans = nbt.getFloat("trans");
		this.transPrev = nbt.getFloat("transprev");
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_WRITE)
	public void writeToNBT_FruitPresser(NBTTagCompound nbt)
	{
		nbt.setFloat("trans", trans);
		nbt.setFloat("transprev", transPrev);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_FruitPresser(ByteBuf stream) throws IOException
	{
		this.trans = stream.readFloat();
		this.transPrev = stream.readFloat();
		return true;
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_FruitPresser(ByteBuf stream) throws IOException
	{
		stream.writeFloat(trans);
		stream.writeFloat(transPrev);
		return true;
	}
}
