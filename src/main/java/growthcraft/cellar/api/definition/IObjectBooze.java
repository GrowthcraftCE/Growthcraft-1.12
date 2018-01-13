package growthcraft.cellar.api.definition;

import growthcraft.cellar.common.definition.BlockBoozeDefinition;
import growthcraft.cellar.common.definition.BoozeDefinition;

public interface IObjectBooze {

	BoozeDefinition getFluidDefinition();
	BlockBoozeDefinition getBoozeBlockDefinition();

}
