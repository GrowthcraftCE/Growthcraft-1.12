package growthcraft.hops.shared.config;

import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

import java.io.File;

public class GrowthcraftHopsConfig extends Configuration {

	public static  Configuration configuration;

	// Non-configurable settings.
	public static int lagerColor = 0x9F7851;
	public static int hopAleColor = 0xD0AF4E;

	// Configurable settings.
	public static int hopVineMaxYield = 2;
	public static float hopVineGrowthRate = 25.0f;
	public static float hopVineFlowerSpawnRate = 40.0f;

	/* CATEGORIES */
	private static final String CATEGORY_GENERAL = "general";

	public static void preInit(FMLPreInitializationEvent e) {
		File directory = e.getModConfigurationDirectory();
		configuration = new Configuration(new File(directory.getPath(), "growthcraft/growthcraft-hops.cfg"));
		readConfig();
	}

	public static void readConfig() {
		try {
			configuration.load();
			initGeneralConfig();
		} catch (Exception e) {
			GrowthcraftLogger.getLogger(Reference.MODID).log(Level.ERROR, "Unable to load configuration files for Growthcraft Hops!", e);
		} finally {
			if ( configuration.hasChanged() ) {
				configuration.save();
			}
		}
	}

	private static void initGeneralConfig() {
		hopVineMaxYield = configuration.getInt(
				"maxHopYield",
				CATEGORY_GENERAL,
				hopVineMaxYield,
				0, 10,
				"Set the maximum number of Hops yielded from hop vines."
		);

		hopVineGrowthRate = configuration.getFloat(
				"hopGrowthRate",
				CATEGORY_GENERAL,
				hopVineGrowthRate,
				1.0F, 100.0F,
				"Chance that a hop vine will grow up the rope."
		);

		hopVineFlowerSpawnRate = configuration.getFloat(
				"hopFlowerRate",
				CATEGORY_GENERAL,
				hopVineFlowerSpawnRate,
				1.0F, 100.0F,
				"Chance that a hop vine will advance in growth age."
		);
	}

	private static void initDebugConfig() {
		// TODO: [Growhtcraft Hops] Implement dynamic logging at the sub-module level.
	}

}
