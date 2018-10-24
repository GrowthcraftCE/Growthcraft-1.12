package growthcraft.milk.shared.config;

import growthcraft.core.shared.legacy.FluidContainerRegistry;

public class GrowthcraftMilkConfig {
	private GrowthcraftMilkConfig() { }

	public static boolean milkEnabled = true;
	public static boolean fantasyMilkEffects = true;
	public static int cheeseMaxAge = 1200;
	public static int cheeseItemPerBlockSlice = 32;
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
	public static int ricottaBowlCount = 4;
}
