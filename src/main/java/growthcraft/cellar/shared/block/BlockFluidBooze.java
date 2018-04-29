package growthcraft.cellar.shared.block;

import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.core.shared.block.GrowthcraftBlockFluid;
import growthcraft.core.shared.block.Materials;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidBooze extends GrowthcraftBlockFluid {
	public BlockFluidBooze(Fluid fluid) {
		super(fluid, GrowthcraftCellarConfig.boozeIsWater ? Material.WATER : Materials.booze);
		refreshColor();
	}

}
