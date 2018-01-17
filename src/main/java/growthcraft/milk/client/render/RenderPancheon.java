package growthcraft.milk.client.render;

import javax.annotation.Nonnull;

import growthcraft.core.client.render.TileFluidTanksSpecialRenderer;
import growthcraft.core.utils.BBox;
import growthcraft.milk.common.tileentity.TileEntityPancheon;

public class RenderPancheon extends TileFluidTanksSpecialRenderer<TileEntityPancheon>{
	private static final BBox fluidBBox = BBox.newCube(1, 1, 1, 14, 4, 14).scale(1.0/16.0);
			
	public RenderPancheon() {
		super(fluidBBox);
	}
	
	@Override
	public void renderTileEntityAt(@Nonnull TileEntityPancheon te, double x, double y, double z, float partialTicks, int destroyStage) {
		renderStackedFluid(te, x, y, z, 1000);
	}
}
