package growthcraft.core.utils;

import growthcraft.core.Reference;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GrowthcraftLogger {

    private static Logger logger;

    public static Logger getLogger() {
        if ( logger == null ) {
            logger = LogManager.getLogger(Reference.MODID);
        }
        return logger;
    }

    public static void debugDumpLog(World worldIn, BlockPos pos) {
        if ( logger == null ) {
            logger = LogManager.getLogger(Reference.MODID);
        }

        boolean isRemote = worldIn.isRemote;

        logger.debug("World isRemote = " + isRemote);
        logger.debug("Position = " + pos.toString());

    }
}
