package growthcraft.core.shared;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

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

    public static void setLevel(Logger logger, Level level) {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();

        LoggerConfig loggerConfig = config.getLoggerConfig(logger.getName());
        LoggerConfig specificConfig = loggerConfig;

        // We need a specific configuration for this logger,
        // otherwise we would change the level of all other loggers
        // having the original configuration as parent as well

        if (!loggerConfig.getName().equals(logger.getName())) {
            specificConfig = new LoggerConfig(logger.getName(), level, true);
            specificConfig.setParent(loggerConfig);
            config.addLogger(logger.getName(), specificConfig);
        }
        specificConfig.setLevel(level);
        ctx.updateLoggers();
    }
}
