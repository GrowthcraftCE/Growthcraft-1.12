package growthcraft.milk.shared.config;

import growthcraft.core.shared.config.GrowthcraftConfiguration;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


public class GrowthcraftMilkConfig extends GrowthcraftConfiguration {

    public static final boolean milkEnabled = true;
    public static final boolean fantasyMilkEffects = true;
    public static final int cheeseMaxAge = 1200;
    public static final int cheeseItemPerBlockSlice = 4;
    public static final int cheeseVatPrimaryTankCapacity = 5000;
    public static final int cheeseVatRennetTankCapacity = FluidContainerRegistry.BOTTLE_VOLUME;
    public static final int cheeseVatWasteTankCapacity = 1000;
    public static final int cheeseVatRecipeTankCapacity = 1000;
    public static final int cheeseVatMilkToCurdsWheyAmount = 1000;
    public static final int cheeseVatWheyToRicottaWheyAmount = 1000;
    public static final int cheeseVatCheeseTime = 2400;
    public static final int cheeseVatCurdTime = 2400;
    public static final int cheeseVatWheyTime = 2400;
    public static final boolean cheesePressHandOperated = true;
    public static final boolean cheesePressRedstoneOperated = true;

    /* Kumis Colors */
    public static int kumisFermentedColor = 0xF9F9F9;
    public static int kumisPotentColor = 0xF9F9F9;
    public static int kumisExtendedColor = 0xF9F9F9;
    public static int kumisHyperExtendedColor = 0xF9F9F9;
    public static int kumisPotentExtendedColor = 0xF9F9F9;
    public static int kumisPotentHyperExtendedColor = 0xF9F9F9;
    public static int kumisPoisonedColor = 0xF9F9F9;

    private int thistleDropChance = 2;

    private int stomachDropChance = 50;
    private boolean babyCowDropStomachOnly = true;

    public GrowthcraftMilkConfig() {
        super();
    }

    @Override
    public void preInit(FMLPreInitializationEvent event, String configFilePath) {
        super.preInit(event, configFilePath);
    }

    @Override
    protected void initGeneralConfig() {
        super.initGeneralConfig();

        thistleDropChance = configuration.getInt(
                "thistleDropChance",
                Configuration.CATEGORY_GENERAL,
                thistleDropChance,
                0, 100,
                "Percentage that thistle will drop when harvesting tall grass."
        );

        stomachDropChance = configuration.getInt(
                "enableDropStomach",
                Configuration.CATEGORY_GENERAL,
                stomachDropChance,
                -1, 100,
                "Percentage that cow stomach will drop when a cow is killed. Set to -1 to disable. "
        );

        babyCowDropStomachOnly = configuration.getBoolean(
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
