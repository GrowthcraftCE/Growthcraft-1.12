package growthcraft.cellar.client.gui;

import growthcraft.cellar.container.ContainerBrewKettle;
import growthcraft.cellar.tileentity.TileEntityBrewKettle;
import growthcraft.core.GrowthcraftGuiProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler extends GrowthcraftGuiProvider implements IGuiHandler {

    public static final int BREW_KETTLE = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == BREW_KETTLE) {
            return new ContainerBrewKettle(player.inventory, (TileEntityBrewKettle) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return super.getServerGuiElement(ID, player, world, x, y, z);
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == BREW_KETTLE) {
            return new GuiBrewKettle(player.inventory, (TileEntityBrewKettle) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return super.getClientGuiElement(ID, player, world, x, y, z);
    }
}
