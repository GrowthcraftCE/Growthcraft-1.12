package growthcraft.cellar.api;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.GrowthcraftCellarConfig;
import growthcraft.cellar.api.booze.BoozeEntry;
import growthcraft.cellar.api.booze.BoozeRegistry;
import growthcraft.cellar.init.CellarEffects;

public class CellarRegistry {
	// REVISE_ME 0
	// OPEN_ADHOC
	
	private static final CellarRegistry INSTANCE = new CellarRegistry().initialize();

	private final BoozeRegistry boozeRegistry = new BoozeRegistry();

	/**
	 * @return current instrance of the CellarRegistry
	 */
	public static final CellarRegistry instance()
	{
		return INSTANCE;
	}
	
	private CellarRegistry initialize()
	{
		CellarEffects.init();
		return this;
	}
	
	/**
	 * @return instance of the BoozeRegistry
	 */
	public BoozeRegistry booze()
	{
		return boozeRegistry;
	}
	
	public static void onPostInit() {
		if (!GrowthcraftCellarConfig.boozeEffectsEnabled)
		{
			GrowthcraftCellar.logger.debug("Stripping ALL booze effects except tipsy");
			for (BoozeEntry entry : CellarRegistry.instance().booze().getBoozeEntries())
			{
				entry.getEffect().clearEffects();
			}
		}
	}
}
