package growthcraft.core.common.definition;

import java.lang.reflect.Array;

import javax.annotation.Nonnull;

import net.minecraftforge.fluids.Fluid;

public class FluidDefinition extends FluidTypeDefinition<Fluid>
{
	public FluidDefinition(@Nonnull Fluid item)
	{
		super(item);
	}

	public static FluidDefinition[] convertArray(Fluid[] fluids)
	{
		final FluidDefinition[] defs = new FluidDefinition[fluids.length];
		for (int i = 0; i < fluids.length; ++i)
		{
			defs[i] = new FluidDefinition(fluids[i]);
		}
		return defs;
	}

	public static <T extends Fluid> T[] convertArray(FluidTypeDefinition<T>[] fluidDefs, Class<T> clazz) {
		final T[] fluids = (T[]) Array.newInstance(clazz, fluidDefs.length); //(T[]) new Fluid[fluidDefs.length];
		for (int i = 0; i < fluidDefs.length; ++i)
		{
			fluids[i] = fluidDefs[i].getFluid(); 
		}

		return fluids;
	}
}
