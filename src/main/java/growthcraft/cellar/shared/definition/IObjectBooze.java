package growthcraft.cellar.shared.definition;

import growthcraft.cellar.shared.fluids.Booze;

public interface IObjectBooze {

	default Booze getFluid() {
		return getFluidDefinition().getFluid();
	}
	
	BoozeDefinition getFluidDefinition();
	BlockBoozeDefinition getBoozeBlockDefinition();

}
