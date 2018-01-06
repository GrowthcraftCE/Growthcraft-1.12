package growthcraft.cellar.network;

import growthcraft.cellar.common.tileentity.PREVTileEntityBrewKettle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PacketSwitchTankButton extends AbstractPacketButton
{
	public PacketSwitchTankButton() {}

	public PacketSwitchTankButton(int x, int y, int z)
	{
		super(x, y, z);
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{

	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		final World world = player.world;
		final TileEntity te = world.getTileEntity(new BlockPos(xCoord, yCoord, zCoord));

		if (te instanceof PREVTileEntityBrewKettle)
		{
			((PREVTileEntityBrewKettle)te).switchTanks();
		}
	}
}
