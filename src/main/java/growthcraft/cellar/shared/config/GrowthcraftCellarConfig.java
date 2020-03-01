package growthcraft.cellar.shared.config;

import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.GrowthcraftLogger;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

import java.io.File;

public class GrowthcraftCellarConfig {
    // OPEN

    /* CATEGORIES */
    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_PRODUCTION = "production";
    private static final String CATEGORY_CORKTREE = "cork_tree";
    public static Configuration configuration;

    // region General Configuration Settiings
    public static boolean enableDiscardButton = true;
    public static boolean boozeEffectsEnabled = true;
    public static boolean boozeIsWater = false;
    public static boolean setFireToFallenLivingEntities = true;
    public static boolean dropItemsInBrewKettle = true;
    public static int brewKettleRainFillPerUnit = 10;
    public static boolean brewKettleFillsWithRain = true;

    // endregion

    // region Production Configuration Settings
    public static int fermentBarrelMaxCap = 4000;
    public static int cultureJarTimeMax = 1200;
    public static int cultureJarConsumption = 1000 / 4;
    public static int cultureJarMaxCap = 1000;
    public static int fruitPressMaxCap = 1000;
    public static boolean fermentBarrelUseCachedRecipe = true;
    public static int fermentTime = 24000;
    public static int brewKettleMaxCap = 1000;
    public static boolean allowFallbackRecipes = true;

    // endregion

    // region Cork Tree Configuration Settings
    public static int corkBarkGrowthChance = 50;
    // endregion


    public static void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        configuration = new Configuration(new File(directory.getPath(), "growthcraft/growthcraft-cellar.cfg"));
        readConfig();
    }

    public static void readConfig() {
        try {
            configuration.load();
            initGeneralConfig();
        } catch (Exception e) {
            GrowthcraftLogger.getLogger(Reference.MODID).log(Level.ERROR, "Unable to load configuration files for Growthcraft Cellar!", e);
        } finally {
            if (configuration.hasChanged()) {
                configuration.save();
            }
        }
    }

    private static void initGeneralConfig() {
        // General Category
        enableDiscardButton = configuration.getBoolean(
                "enableDiscardButton",
                CATEGORY_GENERAL,
                enableDiscardButton,
                "Provides a button in the GUI to 'dump' the fluid contents in the FluidTank."
        );
        boozeEffectsEnabled = configuration.getBoolean(
                "boozeEffectsEnabled",
                CATEGORY_GENERAL,
                boozeEffectsEnabled,
                "Toggle the availability of booze effects."
        );
        boozeIsWater = configuration.getBoolean(
                "boozeIsWater",
                CATEGORY_GENERAL,
                boozeIsWater,
                "Treat booze as water."
        );
        dropItemsInBrewKettle = configuration.getBoolean(
                "dropItemsInBrewKettle",
                CATEGORY_GENERAL,
                dropItemsInBrewKettle,
                "When a brew kettle is destroyed, should the contains be dumped into the world."
        );
        brewKettleRainFillPerUnit = configuration.getInt(
                "brewKettleRainFillPerUnit",
                CATEGORY_GENERAL,
                brewKettleRainFillPerUnit,
                10, 100,
                "How much mb per rain does the kettle fill with water."
        );
        brewKettleFillsWithRain = configuration.getBoolean(
                "brewKettleFillsWithRain",
                CATEGORY_GENERAL,
                brewKettleFillsWithRain,
                "Allow the kettle to fill with water when exposed to rain."
        );

        // Production Category
        allowFallbackRecipes = configuration.getBoolean(
                "allowFallbackRecipes",
                CATEGORY_PRODUCTION, true,
                "If true then fallback recipes are activated. E.g. booze spoiling when using invalid ingredient.");
        fermentBarrelMaxCap = configuration.getInt(
                "fermentBarrelMaxCap",
                CATEGORY_PRODUCTION,
                fermentBarrelMaxCap,
                1000, 10000,
                "Set the maximum capacity of the fermentation barrel."
        );
        cultureJarTimeMax = configuration.getInt(
                "cultureJarTimeMax",
                CATEGORY_PRODUCTION,
                cultureJarTimeMax,
                100, 2400,
                "Maximum time it takes to process something in the culture jar."
        );
        cultureJarConsumption = configuration.getInt(
                "cultureJarConsumption",
                CATEGORY_PRODUCTION,
                cultureJarConsumption,
                100, 1000,
                "Set the consumption rate of fluids in the culture jar."
        );
        cultureJarMaxCap = configuration.getInt(
                "cultureJarMaxCap",
                CATEGORY_PRODUCTION,
                cultureJarMaxCap,
                250, 2000,
                "Maximum fluid capacity of the culture jar."
        );
        fruitPressMaxCap = configuration.getInt(
                "fruitPressMaxCap",
                CATEGORY_PRODUCTION,
                fruitPressMaxCap,
                250, 4000,
                "Maximum fluid capacity of the fruit press."
        );
        fermentTime = configuration.getInt(
                "fermentTime",
                CATEGORY_PRODUCTION,
                fermentTime,
                100, 48000,
                "How many ticks to ferment fluid in the fermentation barrel."
        );
        brewKettleMaxCap = configuration.getInt(
                "brewKettleMaxCap",
                CATEGORY_PRODUCTION,
                brewKettleMaxCap,
                250, 4000,
                "Maximum fluid capacity of the brew kettle."
        );

        // Cork Tree Category Settings
        corkBarkGrowthChance = configuration.getInt(
                "corkBarkGrowthChance",
                CATEGORY_CORKTREE,
                corkBarkGrowthChance,
                0,
                100,
                "Chance per ticket that stripped cork log will regrow cork bark."
        );

    }
}
