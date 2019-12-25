package growthcraft.core.shared.config;

import growthcraft.core.shared.legacy.FluidContainerRegistry;

public class GrowthcraftCoreConfig extends GrowthcraftConfiguration {

    public static final String CATEGORY_NAME_WORLDGEN = "worldgen";
    public static final boolean BLOCK_SALT_ORE_WORLD_GEN = true;
    public static final int BLOCK_SALT_ORE_MIN_HEIGHT = 10;
    public static final int BLOCK_SALT_ORE_MAX_HEIGHT = 64;
    public static final int BLOCK_SALT_ORE_CHANCE_TO_SPAWN = 5;

    private static final String CATEGORY_BOOZE = "Booze/Effects";
    public static boolean hidePoisonedBooze = true;

    public static final int BOTTLE_CAPACITY = FluidContainerRegistry.BOTTLE_VOLUME;

    public GrowthcraftCoreConfig() {
        super();
    }

    @Override
    protected void initGeneralConfig() {
        super.initGeneralConfig();
        initWorldGenCategory();
        initBoozeCategory();
    }

    private void initBoozeCategory() {
        hidePoisonedBooze = getConfiguration().getBoolean(
                "hidePoisoned",
                CATEGORY_BOOZE,
                hidePoisonedBooze,
                "Should purposely poisoned booze have its effect hidden?"
        );
    }

    private void initWorldGenCategory() {

    }

    public boolean getHidePoisonedBooze() {
        return hidePoisonedBooze;
    }
}
