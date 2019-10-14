package growthcraft.bamboo.shared.config;

import growthcraft.bamboo.shared.Reference;
import growthcraft.core.shared.GrowthcraftLogger;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

import java.io.File;

public class GrowthcraftBambooConfig {

    private static Configuration configuration;
    // Categories
    public static final String CATEGORY_GENERAL = "general";

    // Values
    public static boolean isDebug = false;
    public static String logLevel = "info";
    public static boolean enableGrowthcraftBamboo = true;

    private GrowthcraftBambooConfig() {
    }


    public static void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        configuration = new Configuration(new File(directory.getPath(), "growthcraft/growthcraft-bamboo.cfg"));
        readConfig();
    }

    public static void readConfig() {
        try {
            configuration.load();
            initGeneralConfig();
            initDebugConfig();
        } catch (Exception e) {
            GrowthcraftLogger.getLogger(Reference.MODID).log(Level.ERROR, "Unable to load configuration files for Growthcraft Bamboo!", e);
        } finally {
            if (configuration.hasChanged()) {
                configuration.save();
            }
        }
    }

    private static void initGeneralConfig() {
        enableGrowthcraftBamboo = configuration.getBoolean(
                "enableGrowthcraftBamboo",
                CATEGORY_GENERAL,
                enableGrowthcraftBamboo,
                "Enable the usage of Growthcraft Bamboo."
        );
    }

    private static void initDebugConfig() {
        logLevel = configuration.getString("logLevel", CATEGORY_GENERAL, logLevel, "Set standard logging levels. (INFO, ERROR, DEBUG)");
        if (logLevel.equalsIgnoreCase("debug")) {
            isDebug = true;
        }
    }


}
