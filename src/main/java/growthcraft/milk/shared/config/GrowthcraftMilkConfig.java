package growthcraft.milk.shared.config;

import growthcraft.core.shared.legacy.FluidContainerRegistry;

public class GrowthcraftMilkConfig {
    private GrowthcraftMilkConfig() {
    }

    public static boolean milkEnabled = true;
    public static boolean fantasyMilkEffects = true;
    public static int cheeseMaxAge = 1200;
    public static int cheeseItemPerBlockSlice = 4;
    public static int cheeseVatPrimaryTankCapacity = 5000;
    public static int cheeseVatRennetTankCapacity = FluidContainerRegistry.BOTTLE_VOLUME;
    public static int cheeseVatWasteTankCapacity = 1000;
    public static int cheeseVatRecipeTankCapacity = 1000;
    public static int cheeseVatMilkToCurdsWheyAmount = 1000;
    public static int cheeseVatWheyToRicottaWheyAmount = 1000;
    public static int cheeseVatCheeseTime = 2400;
    public static int cheeseVatCurdTime = 2400;
    public static int cheeseVatWheyTime = 2400;
    public static boolean cheesePressHandOperated = true;
    public static boolean cheesePressRedstoneOperated = true;

    /* Kumis Colors */
    public static int kumisFermentedColor = 0xF9F9F9;
    public static int kumisPotentColor = 0xF9F9F9;
    public static int kumisExtendedColor = 0xF9F9F9;
    public static int kumisHyperExtendedColor = 0xF9F9F9;
    public static int kumisPotentExtendedColor = 0xF9F9F9;
    public static int kumisPotentHyperExtendedColor = 0xF9F9F9;
    public static int kumisPoisonedColor = 0xF9F9F9;

}
