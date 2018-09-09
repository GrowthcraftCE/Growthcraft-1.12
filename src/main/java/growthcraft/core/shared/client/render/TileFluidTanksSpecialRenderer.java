package growthcraft.core.shared.client.render;

import javax.annotation.Nonnull;

import growthcraft.core.shared.client.render.utils.RenderUtil;
import growthcraft.core.shared.fluids.IFluidTanks;
import growthcraft.core.shared.utils.BBox;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;

public class TileFluidTanksSpecialRenderer<T extends TileEntity & IFluidTanks> extends TileEntitySpecialRenderer<T> {
	private final BBox fluidBBox;

	public TileFluidTanksSpecialRenderer(BBox fluidBBox) {
		this.fluidBBox = fluidBBox;
	}
	
	private double roundToPixel(double fluidHeight) {
		return MathHelper.floor( fluidHeight*16.0 + 0.5 ) / 16.0;
	}
	
	public void renderMaxFluid(@Nonnull T te, double x, double y, double z) {
		// NOTE: Synch with FluidRenderUtils.getActualFluidBBoxForMax()
		// TODO: Refactorize me later!
		
		int slotToRender = -1;
		double fluidHeight = -1.0;
		int numTanks = te.getTankCount();
		for( int i = 0; i < numTanks; i ++ ) {
			if( !te.isFluidTankFilled(i) )
				continue;
			float f = (float)te.getFluidAmount(i) / (float)te.getFluidTank(i).getCapacity();
			if( fluidHeight < f ) {
				slotToRender = i;
				fluidHeight = f;
			}
		}
		
		fluidHeight *= fluidBBox.h();
		fluidHeight = roundToPixel(fluidHeight);
		
		if( slotToRender == -1 || fluidHeight <= 0 )
			return;
		
		FluidStack fluidStack = te.getFluidStack(slotToRender);
		
		RenderUtil.renderFluidCuboid(fluidStack, te.getPos(), x, y, z,
									 fluidBBox.x0(), fluidBBox.y0(), fluidBBox.z0(),
									 fluidBBox.x1(), fluidBBox.y0()+fluidHeight, fluidBBox.z1());
	}
	
	public int getDefaultTotalCapacity(@Nonnull T te) {
		int totalCap = 0;
		for( int i = 0; i < te.getTankCount(); i ++ ) {
			totalCap += te.getFluidTank(i).getCapacity();
		}
		
		return totalCap;
	}
	
	public void renderStackedFluid(@Nonnull T te, double x, double y, double z, int totalCap) {
		int numTanks = te.getTankCount();
		
		double y0 = fluidBBox.y0();
		double s = 1.0 / (double)totalCap;
		for( int i = 0; i < numTanks; i ++ ) {
			if( !te.isFluidTankFilled(i) )
				continue;
			FluidStack fluidStack = te.getFluidStack(i);

			double h = roundToPixel( fluidBBox.h() * s * (double)fluidStack.amount );
			if( h <= 0 )
				continue;
			
			RenderUtil.renderFluidCuboid(fluidStack, te.getPos(), x, y, z,
					 fluidBBox.x0(), y0, fluidBBox.z0(),
					 fluidBBox.x1(), y0+h, fluidBBox.z1());
			y0 += h;
		}
	}
}
