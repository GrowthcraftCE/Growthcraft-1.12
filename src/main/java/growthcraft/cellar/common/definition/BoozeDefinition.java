package growthcraft.cellar.common.definition;

import javax.annotation.Nonnull;

import growthcraft.cellar.api.booze.Booze;
import growthcraft.core.common.definition.FluidTypeDefinition;

public class BoozeDefinition extends FluidTypeDefinition<Booze> {

	public BoozeDefinition(@Nonnull Booze boozeFluid) {
		super(boozeFluid);
	}

}
