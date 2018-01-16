package growthcraft.milk.init;

import growthcraft.milk.api.MilkRegistry;
import growthcraft.milk.handlers.EnumHandler.AgedCheeseTypes;
import growthcraft.milk.handlers.EnumHandler.SimpleCheeseTypes;
import growthcraft.milk.handlers.EnumHandler.WaxedCheeseTypes;

public class GrowthcraftMilkCheeses {
	
    public static void perInit() {
    	MilkRegistry.instance().cheese().registerCheeses(AgedCheeseTypes.class);
    	MilkRegistry.instance().cheese().registerCheeses(SimpleCheeseTypes.class);
    	MilkRegistry.instance().cheese().registerCheeses(WaxedCheeseTypes.class);
    }

}
