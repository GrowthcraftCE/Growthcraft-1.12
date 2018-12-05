package growthcraft.milk.client.render;

import javax.annotation.Nonnull;

import growthcraft.core.shared.client.render.TileFluidTanksSpecialRenderer;
import growthcraft.core.shared.utils.BBox;
import growthcraft.milk.common.tileentity.TileEntityCheeseVat;

public class RenderCheeseVat extends TileFluidTanksSpecialRenderer<TileEntityCheeseVat> {
	private static final BBox fluidBBox = BBox.newCube(2, 1, 2, 12, 12, 12).scale(1.0 / 16.0);
	
	public RenderCheeseVat() {
		super(fluidBBox);
	}
	
	@Override
	public void render(@Nonnull TileEntityCheeseVat te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		renderStackedFluid(te, x, y, z, 6000);
	}
}
