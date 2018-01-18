package growthcraft.apples.init;

import growthcraft.apples.items.ItemAppleDoor;
import growthcraft.core.common.definition.ItemDefinition;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.OreDictionary;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftApplesItems {
    public static ItemDefinition itemAppleDoor;

    public static void preInit() {
        itemAppleDoor = new ItemDefinition( new ItemAppleDoor("apple_door_item", GrowthcraftApplesBlocks.blockAppleDoor.getBlock()) );
        register();
    }
    
	private static void registerOres()
	{
		OreDictionary.registerOre("foodFruit", Items.APPLE);
	}

    public static void register() {
    	itemAppleDoor.getItem().setCreativeTab(tabGrowthcraft);
    	itemAppleDoor.register();
    	
    	registerOres();
    }

    public static void registerRenders() {
    	itemAppleDoor.registerRender();
    }
}
