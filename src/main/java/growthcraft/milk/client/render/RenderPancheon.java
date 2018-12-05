package growthcraft.milk.client.render;

import javax.annotation.Nonnull;

import growthcraft.core.shared.client.render.TileFluidTanksSpecialRenderer;
import growthcraft.core.shared.utils.BBox;
import growthcraft.milk.common.tileentity.TileEntityPancheon;

public class RenderPancheon extends TileFluidTanksSpecialRenderer<TileEntityPancheon>{
	private static final BBox fluidBBox = BBox.newCube(1, 1, 1, 14, 4, 14).scale(1.0/16.0);
			
	public RenderPancheon() {
		super(fluidBBox);
	}
	
	@Override
	public void render(@Nonnull TileEntityPancheon te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		renderStackedFluid(te, x, y, z, 1000);
	}
}
