package growthcraft.milk.utils;

import java.io.File;

import growthcraft.milk.api.processing.churn.user.UserChurnRecipesConfig;

public class GrowthcraftMilkUserApis
{
	public final UserChurnRecipesConfig churnRecipes;

	public GrowthcraftMilkUserApis()
	{
		super();
		this.churnRecipes = new UserChurnRecipesConfig();
//		add(churnRecipes);
	}

	public void setConfigDirectory(File dir)
	{
		churnRecipes.setConfigFile(dir, "growthcraft/milk/churn_recipes.json");
	}
	
	public void preInit() {
		this.churnRecipes.preInit();
	}
	
	public void register() {
		this.churnRecipes.register();
	}

	public void init() {
		this.churnRecipes.init();
	}

	public void postInit() {
		this.churnRecipes.postInit();
	}

	public void loadConfigs()
	{
/*		for (IModule module : this)
		{
			if (module instanceof AbstractUserJSONConfig)
			{
				((AbstractUserJSONConfig)module).loadUserConfig();
			}
		}*/
		this.churnRecipes.loadUserConfig();
	}
}
