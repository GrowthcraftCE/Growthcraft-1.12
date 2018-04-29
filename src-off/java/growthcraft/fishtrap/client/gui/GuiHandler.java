package growthcraft.fishtrap.client.gui;

import growthcraft.fishtrap.common.container.ContainerFishtrap;
import growthcraft.fishtrap.common.tileentity.TileEntityFishtrap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    public static final int FISHTRAP = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == FISHTRAP) {
            return new ContainerFishtrap(player.inventory, (TileEntityFishtrap)world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == FISHTRAP) {
            return new GuiFishtrap(player.inventory, (TileEntityFishtrap)world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}
