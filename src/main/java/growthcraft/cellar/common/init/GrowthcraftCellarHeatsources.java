package growthcraft.cellar.common.init;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.common.lib.processing.heatsource.user.UserHeatSourceEntry;
import net.minecraft.init.Blocks;

public class GrowthcraftCellarHeatsources {

	public static void init() {
	}

	public static void register() {
		GrowthcraftCellar.userApis.getUserHeatSources().addDefault("minecraft", "fire", UserHeatSourceEntry.newWildcardHeat(1.0f))
			.setComment("Fire!");
		GrowthcraftCellar.userApis.getUserHeatSources().addDefault("minecraft", "flowing_lava", UserHeatSourceEntry.newWildcardHeat(0.7f))
			.setComment("We need to register both states of lava, this when its flowing");
		GrowthcraftCellar.userApis.getUserHeatSources().addDefault("minecraft", "lava", UserHeatSourceEntry.newWildcardHeat(0.7f))
			.setComment("And when its a still pool.");
		GrowthcraftCellar.userApis.getUserHeatSources().addDefault(Blocks.MAGMA.getRegistryName(), UserHeatSourceEntry.newWildcardHeat(0.7f))
		    .setComment("Magma Block. Something like lava.");
	}
}
