package growthcraft.cellar.api.definition;

import growthcraft.cellar.api.booze.Booze;
import growthcraft.cellar.common.definition.BlockBoozeDefinition;
import growthcraft.cellar.common.definition.BoozeDefinition;

public interface IObjectBooze {

	default Booze getFluid() {
		return getFluidDefinition().getFluid();
	}
	
	BoozeDefinition getFluidDefinition();
	BlockBoozeDefinition getBoozeBlockDefinition();

}
