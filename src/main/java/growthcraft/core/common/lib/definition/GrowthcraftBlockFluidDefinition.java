package growthcraft.core.common.lib.definition;

import javax.annotation.Nonnull;

import growthcraft.core.common.lib.block.GrowthcraftBlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class GrowthcraftBlockFluidDefinition extends BlockTypeDefinition<GrowthcraftBlockFluid>
{
	public GrowthcraftBlockFluidDefinition(@Nonnull GrowthcraftBlockFluid fluid)
	{
		super(fluid);
	}

	public void register(ResourceLocation name)
	{
		super.register(name, null /*ItemGrcBlockFluid.class*/);
	}

	public static GrowthcraftBlockFluidDefinition create(Fluid fluid, Material mat)
	{
		return new GrowthcraftBlockFluidDefinition(new GrowthcraftBlockFluid(fluid, mat));
	}

	public static GrowthcraftBlockFluidDefinition create(Fluid fluid)
	{
		return create(fluid, Material.WATER);
	}

	@SuppressWarnings({"rawtypes"})
	public static GrowthcraftBlockFluidDefinition create(FluidTypeDefinition def)
	{
		return create(def.getFluid());
	}
}
