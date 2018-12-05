package growthcraft.core.shared.definition;

import javax.annotation.Nonnull;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.IForgeRegistry;

public class GrowthcraftBlockFluidDefinition extends BlockTypeDefinition<GrowthcraftBlockFluid>
{
	public GrowthcraftBlockFluidDefinition(@Nonnull GrowthcraftBlockFluid fluid)
	{
		super(fluid);
	}

	public void register(IForgeRegistry<Block> registry, ResourceLocation name)
	{
		super.registerBlock(registry, name);
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
