package growthcraft.core.common.lib;

import growthcraft.core.common.lib.effect.EffectRegistry;
import growthcraft.core.common.lib.effect.PotionEffectFactoryRegistry;
import growthcraft.core.common.lib.fluids.FluidDictionary;
import growthcraft.core.common.lib.fluids.FluidTagsRegistry;

public class CoreRegistry {
	// OPEN_ADHOC
	// REVISE_ME 0
	
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
