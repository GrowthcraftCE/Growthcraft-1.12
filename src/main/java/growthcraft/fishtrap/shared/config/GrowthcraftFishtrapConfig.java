package growthcraft.fishtrap.shared.config;

import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.fishtrap.shared.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GrowthcraftFishtrapConfig {

    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_FISHTRAP = "fishtrap";
    public static boolean isDebug = false;
    public static String logLevel = "info";
    public static boolean baitRequired = false;
    public static boolean strictBait = false;
    public static List<String> FISHTRAP_BAIT_TABLE = new ArrayList<String>() {{
        add("minecraft:rotten_flesh");
        add("minecraft:fish");
    }};
    private static Configuration configuration;
    private static boolean enableFishtrapSound = true;
    private static int fishtrapSoundRange = 3;

    private GrowthcraftFishtrapConfig() {
    }

    public static void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        configuration = new Configuration(new File(directory.getPath(), "growthcraft/growthcraft-fistrap.cfg"));
        readConfig();
    }

    public static void readConfig() {
        try {
            configuration.load();
            initGeneralConfig();
            initFishtrapConfig();
            initDebugConfig();
        } catch (Exception e) {
            GrowthcraftLogger.getLogger(Reference.MODID).log(Level.ERROR, "Unable to load configuration files for Growthcraft Fishtrap!", e);
        } finally {
            if (configuration.hasChanged()) {
                configuration.save();
            }
        }
    }

    private static void initGeneralConfig() {
        logLevel = configuration.getString(
                "log_level",
                CATEGORY_GENERAL,
                logLevel,
                "Log level specific to GrowthcraftFishtrap."
        );
    }

    private static void initFishtrapConfig() {
        baitRequired = configuration.getBoolean(
                "baitRequired",
                CATEGORY_FISHTRAP,
                baitRequired,
                "Is bait required in order to catch any fish?"
        );
        strictBait = configuration.getBoolean(
                "enforceBaitTable",
                CATEGORY_FISHTRAP,
                strictBait,
                "Only bait in the authorizedFishBait list will be allowed."
        );

        enableFishtrapSound = configuration.getBoolean(
                "enableFishtrapSound",
                CATEGORY_FISHTRAP,
                enableFishtrapSound,
                "Play a sound notifying nearby players that the fishtrap caught something"
        );

        fishtrapSoundRange = configuration.getInt(
                "fishtrapSoundRange",
                CATEGORY_FISHTRAP,
                fishtrapSoundRange,
                1, 10,
                "Set the range that the fishtrap sounds can be heard. This is relative to the fishtrap BlockPos."
        );

        FISHTRAP_BAIT_TABLE = Arrays.asList(configuration.getStringList(
                "fishtrap_bait_list",
                CATEGORY_FISHTRAP,
                FISHTRAP_BAIT_TABLE.toArray(new String[0]),
                "Add the item's registry name on a new line for each item you want the fishtrap to use it as bait."
        ));

    }

    private static void initDebugConfig() {
        logLevel = configuration.getString("logLevel", CATEGORY_GENERAL, logLevel, "Set standard logging levels. (INFO, ERROR, DEBUG)");
        if (logLevel.equalsIgnoreCase("debug")) {
            isDebug = true;
        }
    }

    public static boolean isFishtrapSoundEnabled() {
        return enableFishtrapSound;
    }

    public static int getFishtrapSoundRange() {
        return fishtrapSoundRange;
    }

}
