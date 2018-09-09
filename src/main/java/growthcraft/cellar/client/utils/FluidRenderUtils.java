package growthcraft.cellar.client.utils;

import growthcraft.core.shared.fluids.IFluidTanks;
import growthcraft.core.shared.utils.BBox;
import net.minecraft.util.math.MathHelper;

public class FluidRenderUtils {
	
	public static BBox getActualFluidBBoxForMax( BBox maxFluidBBox, IFluidTanks tanks ) {
		// NOTE: Synch with TileFluidTanksSpecialRenderer.renderMaxFluid()
		// TODO: Refactorize me later!
		
		int slotToRender = -1;
		double fluidHeight = -1.0;
		int numTanks = tanks.getTankCount();
		for( int i = 0; i < numTanks; i ++ ) {
			if( !tanks.isFluidTankFilled(i) )
				continue;
			float f = (float)tanks.getFluidAmount(i) / (float)tanks.getFluidTank(i).getCapacity();
			if( fluidHeight < f ) {
				slotToRender = i;
				fluidHeight = f;
			}
		}
		
		fluidHeight *= maxFluidBBox.h();
		fluidHeight = roundToPixel(fluidHeight);
		
		if( slotToRender == -1 || fluidHeight <= 0 )
			fluidHeight = 0;
		
		return new BBox(maxFluidBBox.x0(), maxFluidBBox.y0(), maxFluidBBox.z0(),
						maxFluidBBox.x1(), maxFluidBBox.y0()+fluidHeight, maxFluidBBox.z1());
	}
	
	private static double roundToPixel(double fluidHeight) {
		return MathHelper.floor( fluidHeight*16.0 + 0.5 ) / 16.0;
	}
}
