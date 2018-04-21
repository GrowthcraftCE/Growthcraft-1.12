package growthcraft.cellar.shared;

import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.booze.BoozeEntry;
import growthcraft.cellar.shared.booze.BoozeRegistry;
import growthcraft.cellar.shared.processing.brewing.BrewingRegistry;
import growthcraft.cellar.shared.processing.culturing.CulturingRegistry;
import growthcraft.cellar.shared.processing.fermenting.FermentingRegistry;
import growthcraft.cellar.shared.processing.heatsource.HeatSourceRegistry;
import growthcraft.cellar.shared.processing.pressing.PressingRegistry;
import growthcraft.cellar.shared.processing.yeast.YeastRegistry;

public class CellarRegistry {
	// REVISE_ME 0
	
	private static final CellarRegistry INSTANCE = new CellarRegistry();

	private final BoozeRegistry boozeRegistry = new BoozeRegistry();
	private final BrewingRegistry brewingRegistry = new BrewingRegistry();
	private final CulturingRegistry culturingRegistry = new CulturingRegistry();
	private final FermentingRegistry fermentingRegistry = new FermentingRegistry();
	private final HeatSourceRegistry heatSourceRegistry = new HeatSourceRegistry();
	private final PressingRegistry pressingRegistry = new PressingRegistry();
	private final YeastRegistry yeastRegistry = new YeastRegistry();
	
	/**
	 * @return current instrance of the CellarRegistry
	 */
	public static final CellarRegistry instance()
	{
		return INSTANCE;
	}
	
//	private CellarRegistry initialize()
//	{
//		GrowthcraftCellarEffects.init();
//		return this;
//	}
	
	/**
	 * @return instance of the BoozeRegistry
	 */
	public BoozeRegistry booze()
	{
		return boozeRegistry;
	}
	
	/**
	 * @return instance of the BrewingRegistry
	 */
	public BrewingRegistry brewing()
	{
		return brewingRegistry;
	}
	
	/**
	 * @return instance of the CulturingRegistry
	 */
	public CulturingRegistry culturing()
	{
		return culturingRegistry;
	}
	
	/**
	 * @return instance of the PressingRegistry
	 */
	public PressingRegistry pressing()
	{
		return pressingRegistry;
	}
	
	/**
	 * @return instance of the FermentingRegistry
	 */
	public FermentingRegistry fermenting()
	{
		return fermentingRegistry;
	}
	
	/**
	 * @return instance of the HeatSourceRegistry
	 */
	public HeatSourceRegistry heatSource()
	{
		return heatSourceRegistry;
	}
	
	/**
	 * @return instance of the YeastRegistry
	 */
	public YeastRegistry yeast()
	{
		return yeastRegistry;
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
