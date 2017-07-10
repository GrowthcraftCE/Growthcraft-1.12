package growthcraft.core.utils;

import growthcraft.core.Reference;
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
}
