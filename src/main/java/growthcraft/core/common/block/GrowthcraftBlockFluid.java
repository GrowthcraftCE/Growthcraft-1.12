package growthcraft.core.common.block;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class GrowthcraftBlockFluid extends BlockFluidClassic {
	// OPEN
	
	private int color = 0xFFFFFF;

	public GrowthcraftBlockFluid(Fluid fluid, Material material) {
		super(fluid, material);
		this.setUnlocalizedName(fluid.getUnlocalizedName());
	}

	@Nonnull
	@Override
	public String getUnlocalizedName() {
		Fluid fluid = FluidRegistry.getFluid(fluidName);
		if (fluid != null) {
			return fluid.getUnlocalizedName();
		}
		return super.getUnlocalizedName();
	}
	
	public GrowthcraftBlockFluid refreshSettings()
	{
		setDensity(definedFluid.getDensity());
		return this;
	}

	public GrowthcraftBlockFluid refreshLight()
	{
		// http://stackoverflow.com/questions/596216/formula-to-determine-brightness-of-rgb-color
		final float lum = 0.2126f * (((color >> 16) & 0xFF) / 255.0f) +
			0.7152f * (((color >> 8) & 0xFF) / 255.0f) +
			0.0722f * ((color & 0xFF) / 255.0f);
		setLightOpacity((int)((1f - lum) * 15));
		return this;
	}

	public GrowthcraftBlockFluid setColor(int kolor)
	{
		this.color = kolor;
		return this;
	}

	public int getColor()
	{
		return this.color;
	}
}
