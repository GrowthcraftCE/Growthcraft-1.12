package growthcraft.core.shared.config;

import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

import java.io.File;

public class GrowthcraftConfiguration extends Configuration {

    public Configuration configuration;

    /* Categories */
    private final String CATEGORY_GENERAL = "general";

    /* Default Values */
    private Level logLevel = Level.INFO;

    public GrowthcraftConfiguration() { /* Nothing to do */ }

     protected void preInit(FMLPreInitializationEvent event, String configFilePath) {
        File directory = event.getModConfigurationDirectory();
        configuration = new Configuration(
                new File(directory.getPath(), configFilePath)
        );
        readConfig(configFilePath);
    }

    protected void readConfig(String configFilePath) {
        try {
            configuration.load();
            initGeneralConfig();
        } catch (Exception e) {
            GrowthcraftLogger.getLogger(Reference.MODID).log(
                    Level.ERROR,
                    String.format("Unable to read configuration file at: %s", configFilePath)
            );
        } finally {
            if (configuration.hasChanged()) {
                configuration.save();
            }
        }
    }

    /**
     * Initialize the general configuration. By default this will also initialize the debug config
     * and set the log level for mod that is using this configuration.
     * You should override this method and append your custom configurations.
     */
    protected void initGeneralConfig() {
        // Always call the initialization of the debug configuration as we need it for logging.
        initDebugConfig();
    }

    /**
     * Initialize the log level for the module. This function is private as it should not be allowed to the overriden unless the caller decides not call super on initGeneralConfig.
     */
    private void initDebugConfig() {
        logLevel = Level.getLevel(
                configuration.getString(
                        "logLevel",
                        CATEGORY_GENERAL,
                        logLevel.toString(),
                        "Set standard logging levels, such as INFO, DEBUG, ERROR"
                )
        );
    }

    /**
     * Sub-modules should call this to check the verbosity of logging.
     * @return
     */
    public Level getLogLevel() {
        return logLevel;
    }
}
