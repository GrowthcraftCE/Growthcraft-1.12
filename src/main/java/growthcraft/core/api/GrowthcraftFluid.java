package growthcraft.core.api;

import java.util.List;

import javax.annotation.Nonnull;

import growthcraft.core.description.IDescribable;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

/**
 * An extension of the default Forge Fluid class, adding descriptions and color
 * settings
 */
public class GrowthcraftFluid extends Fluid implements IDescribable
{
	// REVISE_ME 0
	
	protected int color;

	public GrowthcraftFluid(@Nonnull String fluidName, ResourceLocation still, ResourceLocation flowing)
	{
		super(fluidName, still, flowing);
		this.color = 0xFFFFFF;
	}

	/**
	 * Returns the color of the fluid
	 *
	 * @return color - a RGB24 value
	 */
	@Override
	public int getColor()
	{
		return color | 0xFF000000;
	}

	/**
	 * Sets the color of the fluid
	 *
	 * @param col - a RGB24 color
	 */
	public GrowthcraftFluid setColor(int col)
	{
		this.color = col;
		return this;
	}

	/**
	 * Adds the fluids's description to the list
	 *
	 * @param list - list to add description to
	 */
	@Override
	public void getDescription(@Nonnull List<String> list)
	{
		final String unloc = getUnlocalizedName() + ".desc";
		final String result = I18n.format(unloc);
		if (!unloc.equals(result))
		{
			list.add(result);
		}
	}

	/**
	 * Fluids don't produce any useful information when converted to a String.
	 * This changes that.
	 *
	 * @return string
	 */
	@Override
	public String toString()
	{
		return String.format("GrowthcraftFluid(name=%s, color=%x)", getName(), getColor());
	}
}