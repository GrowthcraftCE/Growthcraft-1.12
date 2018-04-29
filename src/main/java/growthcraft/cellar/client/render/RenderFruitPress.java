package growthcraft.cellar.client.render;

import javax.annotation.Nonnull;

import growthcraft.cellar.common.tileentity.TileEntityFruitPress;
import growthcraft.core.shared.client.render.TileFluidTanksSpecialRenderer;
import growthcraft.core.shared.utils.BBox;

public class RenderFruitPress extends TileFluidTanksSpecialRenderer<TileEntityFruitPress> {
	private static final BBox fluidBBox = BBox.newCube(3, 4, 3, 10, 10, 10).scale(1.0/16.0);
	
	public RenderFruitPress() {
		super(fluidBBox);
	}

	@Override
	public void render(@Nonnull TileEntityFruitPress te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		renderMaxFluid(te, x, y, z);
	}
}
