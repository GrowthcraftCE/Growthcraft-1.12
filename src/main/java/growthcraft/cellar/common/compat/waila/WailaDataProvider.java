package growthcraft.cellar.common.compat.waila;

import growthcraft.cellar.common.tileentity.TileEntityFermentBarrel;
import growthcraft.core.shared.tileentity.GrowthcraftTileDeviceBase;
import growthcraft.core.shared.tileentity.feature.ITileProgressiveDevice;
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
        registrar.registerBodyProvider(new WailaDataProvider(), GrowthcraftTileDeviceBase.class);
    }

    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        final TileEntity tileEntity = accessor.getTileEntity();
        // TODO: Sync TileEntity server object with client when getDeviceProgressScaled is called client side. Otherwise client has to opne the GUI to get an update.
        if(tileEntity instanceof ITileProgressiveDevice){
            float progress = ((ITileProgressiveDevice) tileEntity).getDeviceProgressScaled(100);
            if(progress > 0){
                tooltip.add(TextFormatting.GREEN + "Progress: " + progress + "%");
            }
        }

        if (tileEntity instanceof TileEntityFermentBarrel) {
            // TODO: Sync TileEntityFermentBarrel server object with client. Otherwise client has to opne the GUI to get an update.
            FluidTank fluidTank = ((TileEntityFermentBarrel) tileEntity).getFluidTank(0);

            if (fluidTank.getFluidAmount() > 0) {
                tooltip.add(TextFormatting.GREEN + String.format("%dmb %s", fluidTank.getFluidAmount(), fluidTank.getFluid().getLocalizedName()));
            } else {
                tooltip.add("Empty");
            }
        }

        return tooltip;
    }

}
