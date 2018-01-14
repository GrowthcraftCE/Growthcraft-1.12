package growthcraft.core.client.render;

import javax.annotation.Nonnull;

import growthcraft.cellar.common.tileentity.PREVTileEntityBrewKettle;
import growthcraft.core.client.utils.RenderUtil;
import growthcraft.core.common.tileentity.device.IFluidTanks;
import growthcraft.core.utils.BBox;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;

public class TileFluidTanksSpecialRenderer<T extends TileEntity & IFluidTanks> extends TileEntitySpecialRenderer<T> {
	private final BBox fluidBBox;
//	private final Mode mode;

	public TileFluidTanksSpecialRenderer(BBox fluidBBox /*, Mode mode */) {
		this.fluidBBox = fluidBBox;
//		this.mode = mode;
	}
	
	private double roundToPixel(double fluidHeight) {
		return MathHelper.floor( fluidHeight*16.0 + 0.5 ) / 16.0;
	}
	
	public void renderMaxFluid(@Nonnull T te, double x, double y, double z) {
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

//			int cap = te.getFluidTank(i).getCapacity();
//			double scap = (double)cap * s;
//			double h = roundToPixel( fluidBBox.h() * scap * (double)fluidStack.amount / (double)cap );
			double h = roundToPixel( fluidBBox.h() * s * (double)fluidStack.amount );
			if( h <= 0 )
				continue;
			
			RenderUtil.renderFluidCuboid(fluidStack, te.getPos(), x, y, z,
					 fluidBBox.x0(), y0, fluidBBox.z0(),
					 fluidBBox.x1(), y0+h, fluidBBox.z1());
			y0 += h;
		}
	}
	
/*	@Override
	public void renderTileEntityAt(@Nonnull T te, double x, double y, double z, float partialTicks, int destroyStage) {
		if( mode == Mode.RENDER_MAXFLUID )
			renderMaxFluid(te, x, y, z, partialTicks, destroyStage);
		else
			renderStackedFluid(te, x, y, z, partialTicks, destroyStage);
	}
	
	public static enum Mode {
		RENDER_MAXFLUID,
		RENDER_STACKED
	} */
}
