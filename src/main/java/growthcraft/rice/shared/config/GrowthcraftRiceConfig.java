package growthcraft.rice.shared.config;

import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

import java.io.File;

public class GrowthcraftRiceConfig extends Configuration {

    private static Configuration configuration;

    // Categories
    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_CELLAR = "growthcraft-cellar-interop";


    private GrowthcraftRiceConfig() { }


    // VALUES

    public static boolean isDebug = false;
    public static String logLevel = "info";

    // GUI
    public static boolean enableDiscardButton = true;

    // Fermenation
    public static int fermentTime = 24000;

    public static int brewTime = 1;

    // Fluid Colors
    public static int sakeMashColor = 0xE9EFF7;
    public static int sakePotentColor = 0xE9EFF7;
    public static int sakeExtendedColor = 0xE9EFF7;
    public static int sakeYoungColor = 0xE9EFF7;
    public static int sakeHyperExtendedColor = 0xE9EFF7;
    public static int sakeIntoxicatedColor = 0xE9EFF7;
    public static int sakePoisonedColor = 0xE9EFF7;

    public static void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        configuration = new Configuration(new File(directory.getPath(), "growthcraft/growthcraft-rice.cfg"));
        readConfig();
    }

    public static void readConfig() {
        try {
            configuration.load();
            initGeneralConfig();
            initDebugConfig();
        } catch (Exception e) {
            GrowthcraftLogger.getLogger(Reference.MODID).log(Level.ERROR, "Unable to load configuration files for Growthcraft Rice!", e);
        } finally {
            if ( configuration.hasChanged() ) {
                configuration.save();
            }
        }
    }

    private static void initGeneralConfig() {
        fermentTime = configuration.getInt("fermentTime", CATEGORY_GENERAL, fermentTime, 100, 48000, "General fermentation time for Growthcraft Rice Booze.");
        enableDiscardButton = configuration.getBoolean("enableDiscardButton", CATEGORY_GENERAL, enableDiscardButton, "Allow users to be able to dump the fluid in the fermentor.");
        brewTime = configuration.getInt("brewTime", CATEGORY_CELLAR, brewTime, 1, 10, "Time in minutes for cooking rice in the brew kettle.");
    }

    private static void initDebugConfig() {
        logLevel = configuration.getString("logLevel", CATEGORY_GENERAL, logLevel, "Set standard logging levels. (INFO, ERROR, DEBUG)");
        if ( logLevel.equalsIgnoreCase("debug")) { isDebug = true; }
    }

}
