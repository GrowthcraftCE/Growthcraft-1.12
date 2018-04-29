package growthcraft.cellar.client.render;

import javax.annotation.Nonnull;

import growthcraft.cellar.common.tileentity.TileEntityBrewKettle;
import growthcraft.core.shared.client.render.TileFluidTanksSpecialRenderer;
import growthcraft.core.shared.utils.BBox;

public class RenderBrewKettle extends TileFluidTanksSpecialRenderer<TileEntityBrewKettle> {
	private static final BBox fluidBBox = BBox.newCube(1, 4, 1, 14, 10, 14).scale(1.0/16.0);

	public RenderBrewKettle() {
		super(fluidBBox);
	}
	
	@Override
	public void render(@Nonnull TileEntityBrewKettle te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		renderMaxFluid(te, x, y, z);
	}

}
