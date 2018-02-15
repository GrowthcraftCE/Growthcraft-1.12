package growthcraft.grapes.tileentity.render;

import growthcraft.grapes.tileentity.TileEntityGrapeVineFruit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class RendererGrapeVineFruit extends TileEntitySpecialRenderer<TileEntityGrapeVineFruit> {


    @Override
    public void renderTileEntityAt(TileEntityGrapeVineFruit te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);

        GlStateManager.pushMatrix();
        {
            IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            ItemStack stack = handler.getStackInSlot(0);

            EntityItem GRAPEVINE_ITEM = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0, stack);
            GRAPEVINE_ITEM.hoverStart = 0F;

            GlStateManager.translate( x, y, z);
            GlStateManager.translate(0.5F, -0.3F, 0.5F);
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            GlStateManager.rotate(45F, 0, 1,0);
            Minecraft.getMinecraft().getRenderManager().doRenderEntity(GRAPEVINE_ITEM, 0, 0, 0, 0F, 0F, false);
        }
        GlStateManager.popMatrix();
    }

    @Override
    protected void bindTexture(ResourceLocation location) {
        super.bindTexture(location);
    }
}
