package growthcraft.milk.client.render;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import growthcraft.core.shared.utils.Easing;
import growthcraft.milk.common.block.BlockCheesePress;
import growthcraft.milk.common.tileentity.TileEntityCheesePress;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.BlockPos;

public class RenderCheesePress extends RenderModel<TileEntityCheesePress> {
    double SCALE = 1.0 / 16.0;

    public RenderCheesePress() {
        super(BlockCheesePress.SUBMODEL_CAP);
    }

    @Override
    public void render(@Nonnull TileEntityCheesePress te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
//		if( !te.isAnimating() )
//			return; // No running animation, no rendering.
        Tessellator tessellator = Tessellator.getInstance();
        if (!prepare(te, tessellator, x, y, z, partialTicks, destroyStage, alpha))
            return;

        BlockPos pos = te.getPos();
        final float eased = (float) ((te.animDir < 0) ? Easing.d.cubicIn : Easing.d.cubicOut).call(te.animProgress);

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5 + eased * SCALE * 2, z + 0.5);
        GL11.glRotatef(eased * 360.0f, 0.0f, 1.0f, 0.0f);
        GL11.glTranslated(-0.5 - pos.getX(), -0.5 - pos.getY(), -0.5 - pos.getZ());
        tessellator.draw();
        GL11.glPopMatrix();

        finish();
    }

}
