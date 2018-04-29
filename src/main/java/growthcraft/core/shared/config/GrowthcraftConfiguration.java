package growthcraft.core.shared.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

import org.apache.logging.log4j.Level;

import growthcraft.core.shared.Reference;
import growthcraft.core.shared.GrowthcraftLogger;

public class GrowthcraftConfiguration extends Configuration {
	// REVISE_TEAM 0: Reused pattern from GrowthcraftBambooConfig, correct?
	// REVISE_ME 0: Remove cellar dependency.
	// INITIALIZE
	
    private static Configuration configuration;

    // Categories
    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_BOOZE = "Booze/Effects";

    // Values
    public static boolean isDebug = false;
    public static String logLevel = "info";
    public static boolean hidePoisonedBooze = true;

/*    public GrowthcraftConfiguration(File configDirectory, String name) {
        configuration = new Configuration(new File(configDirectory.getPath(), "growthcraft/" + name + ".cfg"));
    } */

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        configuration = new Configuration(new File(directory.getPath(), "modtut.cfg"));
        readConfig();
    }

    public static void readConfig() {
        try {
            configuration.load();
            initDebugConfig();
            initBoozeConfig();
        } catch (Exception e) {
            GrowthcraftLogger.getLogger(Reference.MODID).log(Level.ERROR, "Unable to load configuration files for Growthcraft Bamboo!", e);
        } finally {
            if ( configuration.hasChanged() ) {
                configuration.save();
            }
        }
    }

	private static void initDebugConfig() {
        logLevel = configuration.getString("logLevel", CATEGORY_GENERAL, logLevel, "Set standard logging levels. (INFO, ERROR, DEBUG)");
    }

    private static void initBoozeConfig() {
    	hidePoisonedBooze = configuration.getBoolean("hidePoisoned", CATEGORY_BOOZE, hidePoisonedBooze, "Should purposely poisoned booze have its effect hidden?");
	}
}
