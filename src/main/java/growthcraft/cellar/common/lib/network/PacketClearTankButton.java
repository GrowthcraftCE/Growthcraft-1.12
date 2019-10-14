package growthcraft.cellar.common.lib.network;

import growthcraft.cellar.common.tileentity.TileEntityCellarDevice;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PacketClearTankButton extends AbstractPacketButton {
    public PacketClearTankButton() {
    }

    public PacketClearTankButton(BlockPos pos) {
        super(pos);
    }

    @Override
    public void handleClientSide(EntityPlayer player) {

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        final World world = player.world;
        final TileEntity te = world.getTileEntity(coord);

        if (te instanceof TileEntityCellarDevice) {
            ((TileEntityCellarDevice) te).clearTank(0);
        }
    }
}
