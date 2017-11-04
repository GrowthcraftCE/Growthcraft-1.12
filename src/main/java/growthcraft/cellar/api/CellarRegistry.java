package growthcraft.cellar.api;

import growthcraft.cellar.api.booze.BoozeRegistry;
import growthcraft.cellar.init.CellarEffects;

public class CellarRegistry {
	// REVISE_ME 0
	// OPEN_ADHOC
	// INITIALIZE
	
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
}
