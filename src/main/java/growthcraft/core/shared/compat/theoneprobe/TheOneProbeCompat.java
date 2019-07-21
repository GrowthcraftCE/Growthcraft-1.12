package growthcraft.core.shared.compat.theoneprobe;

import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.Reference;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import javax.annotation.Nullable;
import java.util.function.Function;

public class TheOneProbeCompat {
    private static boolean registered;

    public static void register() {
        // prevent this class from being registered again.
        if ( !registered ) {
            FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "growthcraft.core.shared.compat.theoneprobe.TheOneProbeCompat$GetTheOneProbe");
            registered = true;
        }
    }

    public static class GetTheOneProbe implements Function<ITheOneProbe, Void> {
        public static ITheOneProbe probe;

        @Nullable
        @Override
        public Void apply(ITheOneProbe iTheOneProbe) {
            probe = iTheOneProbe;
            GrowthcraftLogger.getLogger(Reference.MODID).info("Enabled supprt for TheOneProbe");
            probe.registerProvider(new IProbeInfoProvider() {
                @Override
                public String getID() {
                    return Reference.MODID + ":default";
                }

                @Override
                public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
                    if ( blockState.getBlock() instanceof ITheOneProbeInfoProvider) {
                        ITheOneProbeInfoProvider provider = (ITheOneProbeInfoProvider) blockState.getBlock();
                        provider.addProbeInfo(mode, probeInfo, player, world, blockState, data);
                    }
                }
            });
            return null;
        }
    }
}
