package growthcraft.milk.common.tileentity;

import java.io.IOException;

import growthcraft.core.shared.io.nbt.INBTItemSerializable;
import growthcraft.core.shared.client.utils.FXHelper;
import growthcraft.core.shared.utils.Pair;
import growthcraft.core.shared.utils.PulseStepper;
import growthcraft.core.shared.utils.SpatialRandom;
import growthcraft.core.shared.utils.TickUtils;
import growthcraft.core.shared.tileentity.GrowthcraftTileBase;
import growthcraft.core.shared.tileentity.event.TileEventHandler;
import growthcraft.core.shared.tileentity.feature.IFluidTankOperable;
import growthcraft.milk.common.item.ItemBlockHangingCurds;
import growthcraft.milk.common.tileentity.struct.CheeseCurd;
import growthcraft.milk.shared.definition.ICheeseType;
import growthcraft.milk.shared.init.GrowthcraftMilkFluids;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityHangingCurds extends GrowthcraftTileBase implements ITickable, INBTItemSerializable
{
	// SpatialRandom instance
	@SideOnly(Side.CLIENT)
	private SpatialRandom sprand = new SpatialRandom();
	
	// Pulsar instance
	private PulseStepper wheyPulsar = new PulseStepper(TickUtils.seconds(15), 10);

	// the following variables are responsible for step tracking
	/// This pulse stepper is used to control the 'drip' animation
	@SideOnly(Side.CLIENT)
	private PulseStepper animPulsar = new PulseStepper(10, 4);

	/// The server will increment this value whenever it does a drip step
	private int serverStep;

	/// Clients will set this value to the serverStep value and proceed with the drip animation
	@SideOnly(Side.CLIENT)
	private int clientStep;

	private CheeseCurd cheeseCurd = new CheeseCurd();

	private IPancheonTile getPancheonTile()
	{
		for (int i = 1; i < 3; ++i)
		{
			BlockPos ofsPos = getPos().down(i);
			final TileEntity te = world.getTileEntity(ofsPos);
			if (te instanceof IPancheonTile)
			{
				return (IPancheonTile)te;
			}
			else
			{
				if (!world.isAirBlock(ofsPos)) break;
			}
		}
		return null;
	}
	
	public ICheeseType getCheeseType() {
		return cheeseCurd.getType();
	}

	public int getRenderColor()
	{
		return cheeseCurd.getRenderColor();
	}

	public float getProgress()
	{
		return cheeseCurd.getAgeProgress();
	}

	public boolean isDried()
	{
		return cheeseCurd.isDried();
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			if (cheeseCurd.needClientUpdate)
			{
				cheeseCurd.needClientUpdate = false;
				markForUpdate();
			}
			cheeseCurd.update();
			if (wheyPulsar.update() == PulseStepper.State.PULSE)
			{
				if( !isDried() ) 
				{
					final IPancheonTile pancheonTile = getPancheonTile();
					// When a pancheon is present, try filling it with Whey
					if (pancheonTile != null)
					{
						final IFluidTankOperable fh = pancheonTile.getPancheonFluidHandler();
						final FluidStack stack = GrowthcraftMilkFluids.whey.asFluidStack(100);
						if (fh.canFill(EnumFacing.UP, stack.getFluid()))
						{
							fh.fill(EnumFacing.UP, stack, true);
						}
					}
					// regardless of a pancheon being present, the curd SHOULD drip
					serverStep++;
					markDirtyAndUpdate();
				}
			}
		}
		else
		{
			if (clientStep != serverStep)
			{
				this.clientStep = serverStep;
				animPulsar.reset();
			}

			if (animPulsar.update() == PulseStepper.State.PULSE)
			{
				if( !isDried() ) {
					final Pair<Double, Double> p = sprand.nextCenteredD2();
					final double px = (double)pos.getX() + 0.5 + p.left * 0.5;
					final double py = (double)pos.getY() + 2.0/16.0;
					final double pz = (double)pos.getZ() + 0.5 + p.right * 0.5;
					FXHelper.dropParticle(world, px, py, pz, GrowthcraftMilkFluids.whey.getItemColor());
				}
			}
		}
	}

	protected void readCheeseCurdFromNBT(NBTTagCompound nbt)
	{
		cheeseCurd.readFromNBT(nbt);
	}

	protected void readWheyPulsarFromNBT(NBTTagCompound nbt)
	{
		wheyPulsar.readFromNBT(nbt, "whey_pulsar");
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readCheeseCurdFromNBT(nbt);
		readWheyPulsarFromNBT(nbt);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_READ)
	public void readFromNBT_HangingCurds(NBTTagCompound nbt)
	{
		readCheeseCurdFromNBT(nbt);
		readWheyPulsarFromNBT(nbt);
	}

	protected void writeCheeseCurdToNBT(NBTTagCompound nbt)
	{
		cheeseCurd.writeToNBT(nbt);
	}

	protected void writeWheyPulsarToNBT(NBTTagCompound nbt)
	{
		wheyPulsar.writeToNBT(nbt, "whey_pulsar");
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeCheeseCurdToNBT(nbt);
		writeWheyPulsarToNBT(nbt);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_WRITE)
	public void writeToNBT_HangingCurds(NBTTagCompound nbt)
	{
		writeCheeseCurdToNBT(nbt);
		writeWheyPulsarToNBT(nbt);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_HangingCurds(ByteBuf stream) throws IOException
	{
		cheeseCurd.readFromStream(stream);
		wheyPulsar.readFromStream(stream);
		this.serverStep = stream.readInt();
		return true;
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_HangingCurds(ByteBuf stream) throws IOException
	{
		cheeseCurd.writeToStream(stream);
		wheyPulsar.writeToStream(stream);
		stream.writeInt(serverStep);
		return true;
	}

	public ItemStack asItemStack()
	{
		ICheeseType type = cheeseCurd.getType();
		final ItemStack stack = type.getCurdBlocks().asStack();
		final NBTTagCompound tag = ItemBlockHangingCurds.openNBT(stack);
		writeToNBTForItem(tag);
		return stack;
	}
}
