package growthcraft.rice.shared.config;

import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GrowthcraftRiceConfig extends Configuration {

    private static Configuration configuration;

    // Categories
    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_CELLAR = "growthcraft-cellar-interop";


    private GrowthcraftRiceConfig() {
    }


    // VALUES

    public static boolean isDebug = false;
    public static String logLevel = "info";

    public static int riceGrowthRate = 25;
    public static int riceGrowthMinLight = 5;
    public static boolean boneMealIgnoreLight = false;

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

    // Loot tables
    public static int CULTIVATOR_HARVEST_TABLE_CHANCE = 10;
    public static List<String> CULTIVATOR_HARVEST_TABLE = new ArrayList<String>() {{
        add("growthcraft_rice:rice");
    }};

    public static List<String> ADD_GRASS_DROPS = new ArrayList<String>() {{
        add("growthcraft_rice:rice");
    }};

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
            if (configuration.hasChanged()) {
                configuration.save();
            }
        }
    }

    private static void initGeneralConfig() {

        boneMealIgnoreLight = configuration.getBoolean(
                "boneMealIgnoreLight",
                CATEGORY_GENERAL,
                boneMealIgnoreLight,
                "Allow for bonemeal to force growth regardless of light level."
        );

        riceGrowthMinLight = configuration.getInt(
                "riceGrowthMinLight",
                CATEGORY_GENERAL,
                riceGrowthMinLight,
                1, 15,
                "Minimum light level for rice to grow. Midnight (4), Sunlight/Thunderstorms (8), Sunlight (12)."
        );

        riceGrowthRate = configuration.getInt(
                "riceGrowthRate",
                CATEGORY_GENERAL,
                riceGrowthRate,
                1, 100,
                "Percentage chance of rice growing."
        );

        fermentTime = configuration.getInt(
                "fermentTime",
                CATEGORY_GENERAL,
                fermentTime,
                100, 4000,
                "General fermentation time for Growthcraft Rice Booze.");

        enableDiscardButton = configuration.getBoolean(
                "enableDiscardButton",
                CATEGORY_GENERAL,
                enableDiscardButton,
                "Allow users to be able to dump the fluid in the fermentor.");

        brewTime = configuration.getInt(
                "brewTime",
                CATEGORY_CELLAR,
                brewTime,
                1, 10,
                "Time in minutes for cooking rice in the brew kettle.");

        CULTIVATOR_HARVEST_TABLE = Arrays.asList(configuration.getStringList(
                "cultivator_harvest_table",
                CATEGORY_GENERAL,
                CULTIVATOR_HARVEST_TABLE.toArray(new String[0]),
                "Add the item's registry name on a new line for each item you want the culivator to drop when it cultivates."));

        CULTIVATOR_HARVEST_TABLE_CHANCE = configuration.getInt(
                "cultivator_harvest_table_chance",
                CATEGORY_GENERAL,
                CULTIVATOR_HARVEST_TABLE_CHANCE, 1, 100,
                "1 in X chances something will drop while using the cultivator. Larger the int the more likely it will drop.");

        ADD_GRASS_DROPS = Arrays.asList(configuration.getStringList(
                "add_grass_drops",
                CATEGORY_GENERAL,
                ADD_GRASS_DROPS.toArray(new String[0]),
                "Add a new with the registry name of the item you want to add to the grassSeed drop table."));

    }

    private static void initDebugConfig() {
        logLevel = configuration.getString("logLevel", CATEGORY_GENERAL, logLevel, "Set standard logging levels. (INFO, ERROR, DEBUG)");
        if (logLevel.equalsIgnoreCase("debug")) {
            isDebug = true;
        }
    }

}
