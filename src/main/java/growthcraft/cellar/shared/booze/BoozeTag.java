package growthcraft.cellar.shared.booze;

import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.fluids.FluidTag;

public class BoozeTag {
	// REVISE_ME 0
	
	// the booze is young and probably has no effects
	public static final FluidTag YOUNG = CoreRegistry.instance().fluidTags().createTag("young");
	// the booze is fermented
	public static final FluidTag FERMENTED = CoreRegistry.instance().fluidTags().createTag("fermented");
	// the booze has its effect time extended
	public static final FluidTag EXTENDED = CoreRegistry.instance().fluidTags().createTag("extended");
	// the booze has a stronger effect, but limited time
	public static final FluidTag POTENT = CoreRegistry.instance().fluidTags().createTag("potent");
	// the booze has the extended + potent effect
	public static final FluidTag HYPER_EXTENDED = CoreRegistry.instance().fluidTags().createTag("hyper_extended");
	// the booze WILL kill the player on overdose
	public static final FluidTag DEADLY = CoreRegistry.instance().fluidTags().createTag("deadly");
	// the booze is poisoned, most likely by using netherrash
	public static final FluidTag POISONED = CoreRegistry.instance().fluidTags().createTag("poisoned");
	// the booze was fermented using Lager yeast
	public static final FluidTag CHILLED = CoreRegistry.instance().fluidTags().createTag("chilled");
	// the booze is heavily intoxicating
	public static final FluidTag INTOXICATED = CoreRegistry.instance().fluidTags().createTag("intoxicated");
	// the booze has a base in magic
	public static final FluidTag MAGICAL = CoreRegistry.instance().fluidTags().createTag("magical");
	// the booze has been reinforced, (a stronger version of potent)
	public static final FluidTag FORTIFIED = CoreRegistry.instance().fluidTags().createTag("fortified");
	// the booze has been brewed with hops
	public static final FluidTag HOPPED = CoreRegistry.instance().fluidTags().createTag("hopped");
	// the booze is some form of wine
	public static final FluidTag WINE = CoreRegistry.instance().fluidTags().createTag("wine");
	// the booze is some form of cider
	public static final FluidTag CIDER = CoreRegistry.instance().fluidTags().createTag("cider");

	private BoozeTag() {}

}
