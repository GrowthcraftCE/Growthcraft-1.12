package growthcraft.bamboo;

import growthcraft.core.utils.GrowthcraftLogger;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

import java.io.File;

public class GrowthcraftBambooConfig {

    public static Configuration configuration;

    // Categories
    private static final String CATEGORY_GENERAL = "general";

    // Values
    private static boolean isDebug = false;
    private static String logLevel = "info";

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        configuration = new Configuration(new File(directory.getPath(), "modtut.cfg"));
        GrowthcraftBambooConfig.readConfig();
    }

    public static void readConfig() {
        Configuration configuration = GrowthcraftBamboo.configuration;
        try {
            configuration.load();
            initDebugConfig(configuration);
        } catch (Exception e) {
            GrowthcraftLogger.getLogger().log(Level.ERROR, "Unable to load configuration files for Growthcraft Bamboo!", e);
        } finally {
            if ( configuration.hasChanged() ) {
                configuration.save();
            }
        }
    }

    private static void initDebugConfig(Configuration configuration) {
        logLevel = configuration.getString("logLevel", CATEGORY_GENERAL, logLevel, "Set standard logging levels. (INFO, ERROR, DEBUG)");
    }

}
