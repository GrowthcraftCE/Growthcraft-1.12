package growthcraft.cellar.util;

import growthcraft.cellar.api.processing.brewing.user.UserBrewingRecipesConfig;
import growthcraft.cellar.api.processing.culturing.user.UserCultureRecipesConfig;
import growthcraft.cellar.api.processing.fermenting.user.UserFermentingRecipesConfig;
import growthcraft.cellar.api.processing.heatsource.user.UserHeatSourcesConfig;
import growthcraft.cellar.api.processing.pressing.user.UserPressingRecipesConfig;
import growthcraft.cellar.api.processing.yeast.user.UserYeastEntriesConfig;

public class UserApis
{
	// INITIALIZE
	
	private UserBrewingRecipesConfig userBrewingRecipes;
	private UserCultureRecipesConfig userCultureRecipes;
	private UserFermentingRecipesConfig userFermentingRecipes;
	private UserHeatSourcesConfig userHeatSources;
	private UserPressingRecipesConfig userPressingRecipes;
	private UserYeastEntriesConfig userYeastEntries;

	public UserApis()
	{
		this.userBrewingRecipes = new UserBrewingRecipesConfig();
		this.userCultureRecipes = new UserCultureRecipesConfig();
		this.userFermentingRecipes = new UserFermentingRecipesConfig();
		this.userHeatSources = new UserHeatSourcesConfig();
		this.userPressingRecipes = new UserPressingRecipesConfig();
		this.userYeastEntries = new UserYeastEntriesConfig();
	}

	public UserBrewingRecipesConfig getUserBrewingRecipes()
	{
		return this.userBrewingRecipes;
	}

	public UserCultureRecipesConfig getUserCultureRecipes()
	{
		return this.userCultureRecipes;
	}

	public UserFermentingRecipesConfig getUserFermentingRecipes()
	{
		return this.userFermentingRecipes;
	}

	public UserHeatSourcesConfig getUserHeatSources()
	{
		return this.userHeatSources;
	}

	public UserPressingRecipesConfig getUserPressingRecipes()
	{
		return this.userPressingRecipes;
	}

	public UserYeastEntriesConfig getUserYeastEntries()
	{
		return this.userYeastEntries;
	}

	public void preInit()
	{
		this.userBrewingRecipes.preInit();
		this.userCultureRecipes.preInit();
		this.userFermentingRecipes.preInit();
		this.userHeatSources.preInit();
		this.userPressingRecipes.preInit();
		this.userYeastEntries.preInit();
	}

	public void register()
	{
		this.userBrewingRecipes.register();
		this.userCultureRecipes.register();
		this.userFermentingRecipes.register();
		this.userHeatSources.register();
		this.userPressingRecipes.register();
		this.userYeastEntries.register();
	}

	public void init()
	{
		this.userBrewingRecipes.init();
		this.userCultureRecipes.init();
		this.userFermentingRecipes.init();
		this.userHeatSources.init();
		this.userPressingRecipes.init();
		this.userYeastEntries.init();
	}

	public void postInit()
	{
		this.userBrewingRecipes.postInit();
		this.userCultureRecipes.postInit();
		this.userFermentingRecipes.postInit();
		this.userHeatSources.postInit();
		this.userPressingRecipes.postInit();
		this.userYeastEntries.postInit();
	}

	public void loadConfigs()
	{
		this.userBrewingRecipes.loadUserConfig();
		this.userCultureRecipes.loadUserConfig();
		this.userFermentingRecipes.loadUserConfig();
		this.userHeatSources.loadUserConfig();
		this.userPressingRecipes.loadUserConfig();
		this.userYeastEntries.loadUserConfig();
	}
}
