package growthcraft.core;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GrowthcraftCoreConfig {

    private static Configuration config = null;

    public static final String CATEGORY_NAME_LOGGING = "logging";

    public static String loggingLevel;

    public static void preInit() {
        File configFile = new File(Loader.instance().getConfigDir(), "growthcraft/growthcraft-core.cfg");
        config = new Configuration(configFile);
        syncFromFiles();
    }

    public static Configuration getConfig() {
        return config;
    }

    public static void clientPreInit() {
        MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());

    }

    public static void syncFromFiles() {
        syncConfig(true, true);
    }

    public static void syncFromGui() {
        syncConfig(false, true );
    }

    public static void syncFromFields() {
        syncConfig(false, false );
    }

    private static void syncConfig(boolean loadFromConfigFile, boolean readFieldsFromConfig) {
        if ( loadFromConfigFile ) {
            config.load();
        }

        Property propertyLogLevel = config.get(CATEGORY_NAME_LOGGING, "log_level", "info");
        propertyLogLevel.setLanguageKey("gui.config.logging.log_level");
        propertyLogLevel.setComment(I18n.format("gui.config.logging.log_level.comment"));

        List<String> propteryOrderBlocks = new ArrayList<String>();
        propteryOrderBlocks.add(propertyLogLevel.getName());
        config.setCategoryPropertyOrder(CATEGORY_NAME_LOGGING, propteryOrderBlocks);

        if ( readFieldsFromConfig ) {
            loggingLevel = propertyLogLevel.getString();
        }

        propertyLogLevel.set(loggingLevel);

        if(config.hasChanged())
            config.save();
    }

    public static class ConfigEventHandler {
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public void onEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(Reference.MODID)) {
                syncFromGui();
            }

        }
    }
}
