package growthcraft.core.shared.compat.theoneprobe;

import net.minecraftforge.fml.common.Loader;

public class TheOneProbeHandler {
    public static void register() {
        if (Loader.isModLoaded("theoneprobe")) {
            TheOneProbeCompat.register();
        }
    }
}
