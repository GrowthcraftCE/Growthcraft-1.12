package growthcraft.cellar.common.lib.definition;

import growthcraft.cellar.common.lib.booze.Booze;

public interface IObjectBooze {

	default Booze getFluid() {
		return getFluidDefinition().getFluid();
	}
	
	BoozeDefinition getFluidDefinition();
	BlockBoozeDefinition getBoozeBlockDefinition();

}
