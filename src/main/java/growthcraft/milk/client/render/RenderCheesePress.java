package growthcraft.milk.client.render;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import growthcraft.core.api.utils.Easing;
import growthcraft.milk.common.block.BlockCheesePress;
import growthcraft.milk.common.block.BlockCheesePress.AnimationStage;
import growthcraft.milk.common.tileentity.TileEntityCheesePress;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;

public class RenderCheesePress extends TileEntitySpecialRenderer<TileEntityCheesePress> {
	double SCALE = 1.0 / 16.0;
	
	@Override
	public void renderTileEntityAt(@Nonnull TileEntityCheesePress te, double x, double y, double z, float partialTicks, int destroyStage) {
//		if( !te.isAnimating() )
//			return; // No running animation, no rendering.
		BlockPos pos = te.getPos();
		IBlockAccess world = MinecraftForgeClient.getRegionRenderCache(te.getWorld(), pos);
		IBlockState state = world.getBlockState(pos).withProperty(BlockCheesePress.SUBMODEL_CAP, true); //.withProperty(BlockCheesePress.STAGE_PRESS, AnimationStage.PRESSING);
		
		BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel capModel = blockrendererdispatcher.getModelForState(state);
        if( capModel == null )
        	return; // No model.

		final float eased = (float)((te.animDir < 0) ? Easing.d.cubicIn : Easing.d.cubicOut).call(te.animProgress);

        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer renderer = tessellator.getBuffer();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableBlend();
        GlStateManager.disableCull();

        if (Minecraft.isAmbientOcclusionEnabled())
        {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        }
        else
        {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        renderTileEntityFast(te, x, y, z, partialTicks, destroyStage, renderer);
        blockrendererdispatcher.getBlockModelRenderer().renderModel(world, capModel, state, pos, renderer, false);
//        renderer.setTranslation(0, 0, 0);

		GL11.glPushMatrix();
		GL11.glTranslated(x+0.5, y +0.5 + eased * SCALE * 2, z+0.5);
		GL11.glRotatef(eased * 360.0f, 0.0f, 1.0f, 0.0f);
		GL11.glTranslated(-0.5- pos.getX(), -0.5- pos.getY(), -0.5- pos.getZ());
		tessellator.draw();
        GL11.glPopMatrix();

        RenderHelper.enableStandardItemLighting();
	}

}
