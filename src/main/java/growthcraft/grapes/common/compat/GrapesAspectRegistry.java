package growthcraft.grapes.common.compat;

import growthcraft.grapes.shared.init.GrowthcraftGrapesItems;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

/**
 * Created by Joseph on 5/30/2018.
 */
public class GrapesAspectRegistry {

	public static void register() {
		ThaumcraftApi.registerObjectTag((GrowthcraftGrapesItems.GrapeTypes.PURPLE.asStack()), new AspectList().add(Aspect.PLANT, 4).add(Aspect.DESIRE, 4));
	}
}
