package growthcraft.milk.client.render;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import growthcraft.core.api.utils.Easing;
import growthcraft.milk.common.block.BlockButterChurnPlunger;
import growthcraft.milk.common.tileentity.TileEntityButterChurnPlunger;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.BlockPos;

public class RenderButterChurnPlunger extends RenderModel<TileEntityButterChurnPlunger> {
	double SCALE = 1.0 / 16.0;
	
	public RenderButterChurnPlunger() {
		super(BlockButterChurnPlunger.SUBMODEL_PLUNGER);
	}
	
	@Override
	public void renderTileEntityAt(@Nonnull TileEntityButterChurnPlunger te, double x, double y, double z, float partialTicks, int destroyStage) {
//		if( !te.isAnimating() )
//			return; // No running animation, no rendering.
        Tessellator tessellator = Tessellator.getInstance();
        if( !prepare(te, tessellator, x, y, z, partialTicks, destroyStage) )
        	return;

		BlockPos pos = te.getPos();
		final float eased = (float)((te.animDir < 0) ? Easing.d.cubicIn : Easing.d.cubicOut).call(te.animProgress);

        GL11.glPushMatrix();
		GL11.glTranslated(x+0.5, y +0.5 + eased * SCALE * 4, z+0.5);
		GL11.glTranslated(-0.5- pos.getX(), -0.5- pos.getY(), -0.5- pos.getZ());
		tessellator.draw();
        GL11.glPopMatrix();

        finish();
	}

}
