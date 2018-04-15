package growthcraft.cellar.client.render;

import javax.annotation.Nonnull;

import growthcraft.cellar.common.tileentity.TileEntityCultureJar;
import growthcraft.core.client.render.TileFluidTanksSpecialRenderer;
import growthcraft.core.utils.BBox;

public class RenderCultureJar extends TileFluidTanksSpecialRenderer<TileEntityCultureJar> {
	private static final BBox fluidBBox = BBox.newCube(6, 1, 6, 4, 4, 4).scale(1.0/16.0);

	public RenderCultureJar() {
		super(fluidBBox /*, Mode.RENDER_MAXFLUID */);
	}
	
	@Override
	public void renderTileEntityAt(@Nonnull TileEntityCultureJar te, double x, double y, double z, float partialTicks, int destroyStage) {
		renderMaxFluid(te, x, y, z);
	}

	
}
