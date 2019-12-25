package growthcraft.milk.shared.config;

import growthcraft.core.shared.config.GrowthcraftConfiguration;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import net.minecraftforge.common.config.Configuration;

public class GrowthcraftMilkConfig extends GrowthcraftConfiguration {

    public static final boolean MILK_ENABLED = true;
    public static final boolean FANTASY_MILK_EFFECTS = true;
    public static final int CHEESE_MAX_AGE = 1200;
    public static final int CHEESE_ITEM_PER_BLOCK_SLICE = 4;
    public static final int CHEESE_VAT_PRIMARY_TANK_CAPACITY = 5000;
    public static final int CHEESE_VAT_RENNET_TANK_CAPACITY = FluidContainerRegistry.BOTTLE_VOLUME;
    public static final int CHEESE_VAT_WASTE_TANK_CAPACITY = 1000;
    public static final int CHEESE_VAT_RECIPE_TANK_CAPACITY = 1000;
    public static final int CHEESE_VAT_MILK_TO_CURDS_WHEY_AMOUNT = 1000;
    public static final int CHEESE_VAT_WHEY_TO_RICOTTA_WHEY_AMOUNT = 1000;
    public static final int CHEESE_VAT_CHEESE_TIME = 2400;
    public static final int CHEESE_VAT_CURD_TIME = 2400;
    public static final int CHEESE_VAT_WHEY_TIME = 2400;
    public static final boolean CHEESE_PRESS_HAND_OPERATED = true;
    public static final boolean CHEESE_PRESS_REDSTONE_OPERATED = true;

    /* Kumis Colors */
    public static final int KUMIS_FERMENTED_COLOR = 0xF9F9F9;
    public static final int KUMIS_POTENT_COLOR = 0xF9F9F9;
    public static final int KUMIS_EXTENDED_COLOR = 0xF9F9F9;
    public static final int KUMIS_HYPER_EXTENDED_COLOR = 0xF9F9F9;
    public static final int KUMIS_POTENT_EXTENDED_COLOR = 0xF9F9F9;
    public static final int KUMIS_POTENT_HYPER_EXTENDED_COLOR = 0xF9F9F9;
    public static final int KUMIS_POISONED_COLOR = 0xF9F9F9;

    private int thistleDropChance = 2;

    private int stomachDropChance = 50;
    private boolean babyCowDropStomachOnly = true;

    public GrowthcraftMilkConfig() {
        super();
    }

    @Override
    protected void initGeneralConfig() {
        super.initGeneralConfig();

        thistleDropChance = getConfiguration().getInt(
                "thistleDropChance",
                Configuration.CATEGORY_GENERAL,
                thistleDropChance,
                0, 100,
                "Percentage that thistle will drop when harvesting tall grass."
        );

        stomachDropChance = getConfiguration().getInt(
                "enableDropStomach",
                Configuration.CATEGORY_GENERAL,
                stomachDropChance,
                -1, 100,
                "Percentage that cow stomach will drop when a cow is killed. Set to -1 to disable. "
        );

        babyCowDropStomachOnly = getConfiguration().getBoolean(
                "babyCowDropStomachOnly",
                Configuration.CATEGORY_GENERAL,
                babyCowDropStomachOnly,
                "Set to true to only drop stomach from baby cows."
        );

    }

    public int getThistleDropChance() {
        return thistleDropChance;
    }

    public int getStomachDropChance() {
        return stomachDropChance;
    }

    public boolean getBabyCowsOnlyDropStomach() {
        return babyCowDropStomachOnly;
    }

}
