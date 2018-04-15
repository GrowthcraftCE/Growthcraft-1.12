package growthcraft.cellar.common.lib.definition;

import growthcraft.cellar.common.definition.BlockBoozeDefinition;
import growthcraft.cellar.common.definition.BoozeDefinition;
import growthcraft.cellar.common.lib.booze.Booze;

public interface IObjectBooze {

	default Booze getFluid() {
		return getFluidDefinition().getFluid();
	}
	
	BoozeDefinition getFluidDefinition();
	BlockBoozeDefinition getBoozeBlockDefinition();

}
