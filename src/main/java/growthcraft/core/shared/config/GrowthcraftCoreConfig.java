package growthcraft.core.shared.config;

import growthcraft.core.shared.legacy.FluidContainerRegistry;

public class GrowthcraftCoreConfig extends GrowthcraftConfiguration {

    public static final String CATEGORY_NAME_WORLDGEN = "worldgen";
    private boolean blockSaltOreWorldGen = true;
    private int blockSaltOreMinHeight = 10;
    private int blockSaltOreMaxHeight = 64;
    private int blockSaltOreChanceToSpawn = 5;

    private static final String CATEGORY_BOOZE = "Booze/Effects";
    private boolean hidePoisonedBooze = true;

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

        blockSaltOreChanceToSpawn = getConfiguration().getInt(
                "blockSaltOreChanceToSpawn",
                CATEGORY_NAME_WORLDGEN,
                blockSaltOreChanceToSpawn,
                0, 10,
                "Chance for Rock Salt Ore to generate in the world."
        );

        blockSaltOreMaxHeight = getConfiguration().getInt(
                "blockSaltOreMaxHeight",
                CATEGORY_NAME_WORLDGEN,
                blockSaltOreMaxHeight,
                0, 256,
                "Maximum height to generate rock salt ore. This must be higher than blockSaltOreMinHeight. "
        );

        blockSaltOreMinHeight = getConfiguration().getInt(
                "blockSaltOreMinHeight",
                CATEGORY_NAME_WORLDGEN,
                blockSaltOreMinHeight,
                0, 256,
                "Minimum height to generate rock salt ore. This must be lower then blockSaltOreMinHeight."
        );

        blockSaltOreWorldGen = getConfiguration().getBoolean(
                "blockSaltOreWorldGen",
                CATEGORY_NAME_WORLDGEN,
                blockSaltOreWorldGen,
                "Set to false to disable worldgen of rock salt ores."
        );


    }

    public boolean getHidePoisonedBooze() {
        return hidePoisonedBooze;
    }

    public boolean getBlockSaltOreWorldGen() { return blockSaltOreWorldGen; }

    public int getBlockSaltOreMinHeight() { return blockSaltOreMinHeight; }

    public int getBlockSaltOreMaxHeight() { return blockSaltOreMaxHeight; }

    public int getBlockSaltOreChanceToSpawn() { return blockSaltOreChanceToSpawn; }




}
