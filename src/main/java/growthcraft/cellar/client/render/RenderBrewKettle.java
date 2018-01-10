package growthcraft.cellar.client.render;

import javax.annotation.Nonnull;

import growthcraft.cellar.common.tileentity.PREVTileEntityBrewKettle;
import growthcraft.core.client.render.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;

public class RenderBrewKettle extends TileEntitySpecialRenderer<PREVTileEntityBrewKettle> {
	private static final float FLUID_HEIGHT = 0.6875F;

	@Override
	public void renderTileEntityAt(@Nonnull PREVTileEntityBrewKettle te, double x, double y, double z, float partialTicks, int destroyStage) {
		int slotToRender = -1;
		double fluidHeight = -1.0;
		for( int i = 0; i < 2; i ++ ) {
			if( !te.isFluidTankFilled(i) )
				continue;
			float f = te.getFluidAmount(i) * FLUID_HEIGHT / te.getFluidTank(i).getCapacity();
			if( fluidHeight < f ) {
				slotToRender = i;
				fluidHeight = f;
			}
		}
		
		if( slotToRender == -1 )
			return;
		
//		final PREVBlockBrewKettle brewKettle = (PREVBlockBrewKettle)te.getBlockType();
//		World world = te.getWorld();
//		IBlockState state = world.getBlockState(te.getPos());
		
		FluidStack fluidStack = te.getFluidStack(slotToRender);
		
		final double d = 0.0625D;
		RenderUtil.renderFluidCuboid(fluidStack, te.getPos(), x, y, z, 1*d, 4*d, 1*d, 15*d, 2*d+fluidHeight, 15*d);
		
		
/*		int color = Minecraft.getMinecraft().getBlockColors().colorMultiplier(state, world, te.getPos(), 0);
		float r = (float)(color >> 16 & 255) / 255.0F;
		float g = (float)(color >> 8 & 255) / 255.0F;
		float b = (float)(color & 255) / 255.0F;
		float f4;
		
//		final Tessellator tes = Tessellator.getInstance();
//		tes.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));

		if (EntityRenderer.anaglyphEnable)
		{
			final float f5 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
			f4 = (r * 30.0F + g * 70.0F) / 100.0F;
			final float f6 = (r * 30.0F + b * 70.0F) / 100.0F;
			r = f5;
			g = f4;
			b = f6;
		}

		// Render Liquid
		for (int i = 0; i < 2; ++i)
		{
			if (te.isFluidTankFilled(i))
			{
				final Fluid fluid = te.getFluid(i);
				color = fluid.getColor();
				r = (float)(color >> 16 & 255) / 255.0F;
				g = (float)(color >> 8 & 255) / 255.0F;
				b = (float)(color & 255) / 255.0F;
				f = 1.0F;
				tes.setColorOpaque_F(f * r, f * g, f * b);
				f = te.getFluidAmount(i) * FLUID_HEIGHT / te.getFluidTank(i).getCapacity();
				renderer.setRenderBounds(2 * d, 0.0D, 2 * d, 14 * d, (double)(0.25F + f), 14 * d);
				final IIcon icon = fluid.getIcon();
				if (icon != null)
				{
					renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, icon);
				}
			}
		}
*/
	}

}
