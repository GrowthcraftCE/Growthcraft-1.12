package growthcraft.core.shared.config;

import growthcraft.core.shared.Reference;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import net.minecraft.util.text.translation.I18n;
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
    // TODO: Keep either this class or GrowthcraftCoreConfiguration

    // TODO: Need to make a super class so that sub-modules can extend.

    private static Configuration config = null;

    public static final String CATEGORY_NAME_GENERAL = "general";
    public static boolean hidePoisonedBooze = true;


    public static final String CATEGORY_NAME_LOGGING = "logging";
    public static String loggingLevel;

    public static final String CATEGORY_NAME_WORLDGEN = "worldgen";
    public static boolean blockSaltOreWorldGen = true;
    public static int blockSaltOreMinHeight = 10;
    public static int blockSaltOreMaxHeight = 64;
    public static int blockSaltOreChanceToSpawn = 5;


    public static int bottleCapacity = FluidContainerRegistry.BOTTLE_VOLUME;


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
        syncConfig(false, true);
    }

    public static void syncFromFields() {
        syncConfig(false, false);
    }

    private static void syncConfig(boolean loadFromConfigFile, boolean readFieldsFromConfig) {
        if (loadFromConfigFile) {
            config.load();
        }

        /* Configuration: Logging Settings */
        Property propertyLogLevel = config.get(CATEGORY_NAME_LOGGING, "log_level", "info");
        propertyLogLevel.setLanguageKey("config.logging.log_level");
        propertyLogLevel.setComment(I18n.translateToLocal("config.logging.log_level.comment"));

        List<String> propertyOrderLoggingBlocks = new ArrayList<String>();
        propertyOrderLoggingBlocks.add(propertyLogLevel.getName());

        config.setCategoryPropertyOrder(CATEGORY_NAME_LOGGING, propertyOrderLoggingBlocks);

        /* Configuration: World Generation */
        Property propertyWorldGEnBlocksaltOre = config.get(CATEGORY_NAME_WORLDGEN, "blockSaltOreWorldGen", true);
        propertyWorldGEnBlocksaltOre.setLanguageKey("config.worldgen.worldgenBlockSaltOre");
        propertyWorldGEnBlocksaltOre.setComment(I18n.translateToLocal("config.worldgen.worldgenBlockSaltOre.comment"));

        Property propertyBlockSaltOreMinHeight = config.get(CATEGORY_NAME_WORLDGEN, "blockSaltOreMinHeight", 10);
        propertyBlockSaltOreMinHeight.setLanguageKey("config.worldgen.blockSaltOreMinHeight");
        propertyBlockSaltOreMinHeight.setComment(I18n.translateToLocal("config.worldgen.blockSaltOreMinHeight.comment"));

        Property propertyBlockSaltOreMaxHeight = config.get(CATEGORY_NAME_WORLDGEN, "blockSaltOreMaxHeight", 64);
        propertyBlockSaltOreMaxHeight.setLanguageKey("config.worldgen.blockSaltOreMaxHeight");
        propertyBlockSaltOreMaxHeight.setComment(I18n.translateToLocal("config.worldgen.blockSaltOreMaxHeight.comment"));

        Property propertyBlockSaltOreChanceToSpawn = config.get(CATEGORY_NAME_WORLDGEN, "blockSaltOreChanceToSpawn", 5);
        propertyBlockSaltOreChanceToSpawn.setLanguageKey("config.worldgen.blockSaltOreChanceToSpawn");
        propertyBlockSaltOreChanceToSpawn.setComment(I18n.translateToLocal("config.worldgen.blockSaltOreChanceToSpawn.comment"));

        List<String> propertyOrderWorldGenBlocks = new ArrayList<String>();
        propertyOrderWorldGenBlocks.add(propertyWorldGEnBlocksaltOre.getName());
        propertyOrderWorldGenBlocks.add(propertyBlockSaltOreMinHeight.getName());
        propertyOrderWorldGenBlocks.add(propertyBlockSaltOreMaxHeight.getName());
        propertyOrderWorldGenBlocks.add(propertyBlockSaltOreChanceToSpawn.getName());

        config.setCategoryPropertyOrder(CATEGORY_NAME_WORLDGEN, propertyOrderWorldGenBlocks);

        /* Configuration: General / Everything Else */
        Property propertyHidePoisonedBooze = config.get(CATEGORY_NAME_GENERAL, "hidePoisonedBooze", true);
        propertyHidePoisonedBooze.setLanguageKey("config.general.hidePoisonedBooze");
        propertyHidePoisonedBooze.setComment(I18n.translateToLocal("config.general.hidePoisonedBooze.comment"));

        List<String> propertyOrderGeneralBlocks = new ArrayList<String>();
        propertyOrderGeneralBlocks.add(propertyHidePoisonedBooze.getName());

        config.setCategoryPropertyOrder(CATEGORY_NAME_GENERAL, propertyOrderGeneralBlocks);

        if (readFieldsFromConfig) {
            loggingLevel = propertyLogLevel.getString();

            blockSaltOreWorldGen = propertyWorldGEnBlocksaltOre.getBoolean();
            blockSaltOreMinHeight = propertyBlockSaltOreMinHeight.getInt();
            blockSaltOreMaxHeight = propertyBlockSaltOreMaxHeight.getInt();
            blockSaltOreChanceToSpawn = propertyBlockSaltOreChanceToSpawn.getInt();

            hidePoisonedBooze = propertyHidePoisonedBooze.getBoolean();
        }

        propertyLogLevel.set(loggingLevel);

        if (config.hasChanged())
            config.save();

    }

    public static class ConfigEventHandler {
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public void onEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Reference.MODID)) {
                syncFromGui();
            }
        }
    }

}
