package growthcraft.cellar.common.block;

import growthcraft.cellar.GrowthcraftCellarConfig;
import growthcraft.core.common.block.GrowthcraftBlockFluid;
import growthcraft.core.common.block.Materials;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidBooze extends GrowthcraftBlockFluid {
	// INITIALIZE registry; creative tabs; ?texture name?
	
	public BlockFluidBooze(Fluid fluid) {
		super(fluid, GrowthcraftCellarConfig.boozeIsWater ? Material.WATER : Materials.booze);
		refreshColor();
	}

}
