package growthcraft.cellar.common.lib.schema;

import javax.annotation.Nonnull;

import growthcraft.cellar.common.lib.processing.common.Residue;
import growthcraft.core.shared.config.schema.ItemKeySchema;
import net.minecraft.item.ItemStack;

public class ResidueSchema extends ItemKeySchema
{
	public float pomace;

	public ResidueSchema(@Nonnull Residue res)
	{
		super(res.residueItem);
		this.pomace = res.pomaceRate;
	}

	public ResidueSchema()
	{
		super();
		this.pomace = 1.0f;
	}

	public Residue asResidue()
	{
		final ItemStack itemStack = asStack();
		if (itemStack == null) return null;
		return new Residue(itemStack, pomace);
	}

	@Override
	public String toString()
	{
		return String.format("%s~(pomace: %.4f)", super.toString(), pomace);
	}
}