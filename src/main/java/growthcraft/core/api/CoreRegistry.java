package growthcraft.core.api;

import growthcraft.core.api.fluids.FluidDictionary;
import growthcraft.core.api.fluids.FluidTagsRegistry;

public class CoreRegistry {
	// OPEN_ADHOC
	// REVISE_ME 0
	// INITIALIZE
	
	private static final CoreRegistry instance = new CoreRegistry();

	private final FluidDictionary fluidDictionary = new FluidDictionary();
	private final FluidTagsRegistry fluidTagsRegistry = new FluidTagsRegistry();
	private final EffectRegistry effectRegistry = new EffectRegistry().initialize();
	private final PotionEffectFactoryRegistry potionEffectFactoryRegistry = new PotionEffectFactoryRegistry();
	
	public static final CoreRegistry instance()
	{
		return instance;
	}

	public EffectRegistry getEffectsRegistry()
	{
		return effectRegistry;
	}
	
	public PotionEffectFactoryRegistry getPotionEffectFactoryRegistry()
	{
		return potionEffectFactoryRegistry;
	}
	
	/**
	 * @return instance of the FluidTagsRegistry
	 */
	public FluidTagsRegistry fluidTags()
	{
		return fluidTagsRegistry;
	}
	
	public FluidDictionary fluidDictionary()
	{
		return fluidDictionary;
	}
}
