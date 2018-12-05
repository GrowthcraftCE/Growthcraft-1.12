package growthcraft.milk.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;

public class RenderModel<T extends TileEntity> extends TileEntitySpecialRenderer<T> {
	
	private final IProperty<Boolean> modelProp;
	
	public RenderModel(IProperty<Boolean> modelProp) {
		super();
		this.modelProp = modelProp;
	}
	
	protected boolean prepare(T te, Tessellator tessellator, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		BlockPos pos = te.getPos();
		IBlockAccess world = MinecraftForgeClient.getRegionRenderCache(te.getWorld(), pos);
		IBlockState state = world.getBlockState(pos);
		if( state.getBlock() != te.getBlockType() )
			return false; // Preventing a crash on states.
		state = state.withProperty(modelProp, true);
		
		BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel capModel = blockrendererdispatcher.getModelForState(state);
        if( capModel == null )
        	return false; // No model.
		
        BufferBuilder renderer = tessellator.getBuffer();
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

        renderTileEntityFast(te, x, y, z, partialTicks, destroyStage, alpha, renderer);
        blockrendererdispatcher.getBlockModelRenderer().renderModel(world, capModel, state, pos, renderer, false);
//        renderer.setTranslation(0, 0, 0);
        return true;
	}
	
	protected void finish() {
        GlStateManager.enableCull();
		RenderHelper.enableStandardItemLighting();
	}
}
