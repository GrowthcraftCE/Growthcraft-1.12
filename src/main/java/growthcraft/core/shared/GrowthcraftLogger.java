package growthcraft.core.shared;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GrowthcraftLogger {

    private static Logger logger;

    public static Logger getLogger(String modID) {
        if (logger == null) {
            logger = LogManager.getLogger(modID);
        }
        return logger;
    }

    public static void debugDumpLog(String modID, World worldIn, BlockPos pos) {
        if (logger == null) {
            logger = LogManager.getLogger(modID);
        }

        boolean isRemote = worldIn.isRemote;

        logger.debug("World isRemote = " + isRemote);
        logger.debug("Position = " + pos.toString());

    }
}
