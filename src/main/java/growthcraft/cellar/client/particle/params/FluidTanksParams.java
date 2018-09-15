package growthcraft.cellar.client.particle.params;

import growthcraft.core.shared.fluids.IFluidTanks;
import growthcraft.core.shared.utils.BBox;

public class FluidTanksParams {
	private final BBox fluidBBox;
	private final IFluidTanks tanks;
	
	public FluidTanksParams(BBox fluidBBox, IFluidTanks tanks) {
		this.fluidBBox = fluidBBox;
		this.tanks = tanks;
	}
	
	public BBox getFluidBBox() {
		return fluidBBox;
	}
	
	public IFluidTanks getTanks() {
		return this.tanks;
	}
}
