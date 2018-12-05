package growthcraft.cellar.common.compat.thaumcraft;

import growthcraft.cellar.shared.init.GrowthcraftCellarItems;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class CellarAspectRegistry {

	public static void register() {
		// From Sonconures suggestion. See discord dev-chatter channel entry 03.06.2018
		ThaumcraftApi.registerObjectTag((GrowthcraftCellarItems.EnumYeast.BREWERS.asStack()), new AspectList().add(Aspect.LIFE, 2).add(Aspect.CRAFT, 2));
		ThaumcraftApi.registerObjectTag((GrowthcraftCellarItems.EnumYeast.BAYANUS.asStack()), new AspectList().add(Aspect.LIFE, 2).add(Aspect.DESIRE, 2));
		ThaumcraftApi.registerObjectTag((GrowthcraftCellarItems.EnumYeast.ORIGIN.asStack()), new AspectList().add(Aspect.LIFE, 2).add(Aspect.ALCHEMY, 2));
		ThaumcraftApi.registerObjectTag((GrowthcraftCellarItems.EnumYeast.ETHEREAL.asStack()), new AspectList().add(Aspect.LIFE, 2).add(Aspect.MAGIC, 2));
		ThaumcraftApi.registerObjectTag((GrowthcraftCellarItems.EnumYeast.LAGER.asStack()), new AspectList().add(Aspect.LIFE, 2).add(Aspect.COLD, 2));		
	}

}
