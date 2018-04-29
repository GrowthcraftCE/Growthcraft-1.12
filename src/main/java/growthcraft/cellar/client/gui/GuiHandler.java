package growthcraft.cellar.client.gui;

import growthcraft.core.shared.client.gui.GrowthcraftGuiProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler extends GrowthcraftGuiProvider implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return super.getServerGuiElement(ID, player, world, x, y, z);
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return super.getClientGuiElement(ID, player, world, x, y, z);
    }
}
