package growthcraft.milk.shared.init;

import growthcraft.milk.common.handlers.EnumHandler.AgedCheeseTypes;
import growthcraft.milk.common.handlers.EnumHandler.SimpleCheeseTypes;
import growthcraft.milk.common.handlers.EnumHandler.WaxedCheeseTypes;
import growthcraft.milk.shared.MilkRegistry;

public class GrowthcraftMilkCheeses {
	
    public static void perInit() {
    	MilkRegistry.instance().cheese().registerCheeses(AgedCheeseTypes.class);
    	MilkRegistry.instance().cheese().registerCheeses(SimpleCheeseTypes.class);
    	MilkRegistry.instance().cheese().registerCheeses(WaxedCheeseTypes.class);
    }

}
