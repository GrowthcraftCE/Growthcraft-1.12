package growthcraft.cellar.common.compat.waila;

import growthcraft.cellar.common.block.BlockFermentBarrel;
import growthcraft.cellar.common.tileentity.TileEntityFermentBarrel;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import java.util.List;

public class WailaDataProvider implements IWailaDataProvider {

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new WailaDataProvider(), BlockFermentBarrel.class);
    }

    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        final TileEntity tileEntity = accessor.getTileEntity();

        if (tileEntity instanceof TileEntityFermentBarrel) {
            // TODO: Sync TileEntityFermentBarrel server object with client. Otherwise client has to opne the GUI to get an update.

            float progress = ((TileEntityFermentBarrel) tileEntity).getDeviceProgressScaled(100);
            FluidTank fluidTank = ((TileEntityFermentBarrel) tileEntity).getFluidTank(0);

            if (fluidTank.getFluidAmount() > 0) {
                tooltip.add(TextFormatting.GREEN + String.format("%dmb %s", fluidTank.getFluidAmount(), fluidTank.getFluid().getLocalizedName()));
                tooltip.add(TextFormatting.GREEN + "Progress: " + progress + "%");
            } else {
                tooltip.add("Empty");
            }
        }

        return tooltip;
    }

}
