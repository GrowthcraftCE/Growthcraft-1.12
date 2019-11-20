package growthcraft.bees.shared.config;

import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

import java.io.File;

public class GrowthcraftBeesConfig {

    private static Configuration configuration;

    // Categories
    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_BEEBOX = "beebox";
    private static final String CATEGORY_BEEHIVE = "beehive";


    private GrowthcraftBeesConfig() {
    }

    public static boolean isDebug = false;
    public static String logLevel = "info";


    public static boolean honeyEnabled = true;

    public static float beeBoxHoneyCombSpawnRate = 18.75f;
    public static float beeBoxHoneySpawnRate = 6.25f;
    public static float beeBoxBeeSpawnRate = 6.25f;
    public static float beeBoxFlowerSpawnRate = 6.25f;
    public static int beeBoxFlowerRadius = 5;
    public static float beeBoxBonusMultiplier = 2.5f;

    public static int honeyMeadColor = 0xA3610C;

    public static boolean worldgenBeeHive = true;
    public static int worldgenBeeHiveRarity = 10;

    public static void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        configuration = new Configuration(new File(directory.getPath(), "growthcraft/growthcraft-bees.cfg"));
        readConfig();
    }

    public static void readConfig() {
        try {
            configuration.load();
            initGeneralConfig();
            initDebugConfig();
        } catch (Exception e) {
            GrowthcraftLogger.getLogger(Reference.MODID).log(Level.ERROR, "Unable to load configuration files for Growthcraft Bees!", e);
        } finally {
            if (configuration.hasChanged()) {
                configuration.save();
            }
        }
    }

    private static void initGeneralConfig() {
        // CATEGORY_GENERAL
        honeyEnabled = configuration.getBoolean(
                "enableHoney",
                CATEGORY_GENERAL,
                honeyEnabled,
                "Enable Growthcraft Fluid Honey."
        );

        // CATEGORY_BEEBOX
        beeBoxHoneyCombSpawnRate = configuration.getFloat(
                "honeyCombGenerationRate",
                CATEGORY_BEEBOX,
                beeBoxHoneyCombSpawnRate,
                0.0F,
                100.0F,
                "The rate that honey comb is generated in the bee box."
        );

        beeBoxHoneySpawnRate = configuration.getFloat(
                "honeyGenerationRate",
                CATEGORY_BEEBOX,
                beeBoxHoneySpawnRate,
                0.0F,
                100.0F,
                "The rate that honey is generated with honey combs in the bee box."
        );

        beeBoxBeeSpawnRate = configuration.getFloat(
                "beeGenerationRate",
                CATEGORY_BEEBOX,
                beeBoxBeeSpawnRate,
                0.0F,
                100.0F,
                "The rate that bees are generated in the bee box."
        );

        beeBoxFlowerSpawnRate = configuration.getFloat(
                "flowerGenerationRate",
                CATEGORY_BEEBOX,
                beeBoxFlowerSpawnRate,
                0.0F,
                100.0F,
                "The rate that flowers are generated around the bee box."
        );

        beeBoxFlowerRadius = configuration.getInt(
                "flowerGenerationRadius",
                CATEGORY_BEEBOX,
                beeBoxFlowerRadius,
                1,
                10,
                "Radius of flower generation."
        );

        beeBoxBonusMultiplier = configuration.getFloat(
                "flowerGenerationRate",
                CATEGORY_BEEBOX,
                beeBoxBonusMultiplier,
                0.0F,
                10.0F,
                "The rate that flowers are generated around the bee box."
        );

        // CATEGORY_BEEHIVE
        worldgenBeeHive = configuration.getBoolean(
                "generateBeeHives",
                CATEGORY_BEEHIVE,
                worldgenBeeHive,
                "Generate BeeHives in the world."
        );

        worldgenBeeHiveRarity = configuration.getInt(
                "beeHiveRarity",
                CATEGORY_BEEHIVE,
                worldgenBeeHiveRarity,
                0,
                100,
                "Rarity of BeeHive generation. Higher the value, more likely it will generate in chunk."
        );

    }

    private static void initDebugConfig() {
        logLevel = configuration.getString("logLevel", CATEGORY_GENERAL, logLevel, "Set standard logging levels. (INFO, ERROR, DEBUG)");
        if (logLevel.equalsIgnoreCase("debug")) {
            isDebug = true;
        }
    }
}
