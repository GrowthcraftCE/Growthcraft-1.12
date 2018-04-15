package growthcraft.cellar.network;

import growthcraft.cellar.common.tileentity.PREVTileEntityBrewKettle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PacketSwitchTankButton extends AbstractPacketButton
{
	public PacketSwitchTankButton() {}

	public PacketSwitchTankButton(BlockPos pos)
	{
		super(pos);
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{

	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		final World world = player.world;
		final TileEntity te = world.getTileEntity(coord);

		if (te instanceof PREVTileEntityBrewKettle)
		{
			((PREVTileEntityBrewKettle)te).switchTanks();
		}
	}
}
