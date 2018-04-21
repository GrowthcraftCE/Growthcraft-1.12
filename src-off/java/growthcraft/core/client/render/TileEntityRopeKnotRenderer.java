package growthcraft.core.client.render;

import growthcraft.core.common.tileentity.TileEntityRopeKnot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityRopeKnotRenderer extends TileEntitySpecialRenderer<TileEntityRopeKnot> {

    @Override
    public void renderTileEntityAt(TileEntityRopeKnot te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);

        GlStateManager.pushMatrix();
        {
            //GlStateManager.translate(x, y, z);
            //GlStateManager.rotate(90F, 1, 0, 0);

            // Get the contents of the RopeKnot
            IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            ItemStack stack = handler.getStackInSlot(0);

            EntityItem ITEM = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0, stack);
            ITEM.hoverStart = 0F;

            /**
             * TODO: Get the texture of the given fence and apply it the the fencePost layer in our RopeKnot block model.
             * IBlockState state = worldIn.getBlockState(pos);
             * int meta = block.getMetaFromState(state);
             * String model = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state).getIconName();
             */
            //Block block = Block.getBlockFromItem(stack.getItem());
            //String model = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(block.getDefaultState()).getIconName();

            Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM, x, y, z, 0F, 0F, false);
        }
        GlStateManager.popMatrix();


    }

    @Override
    protected void bindTexture(ResourceLocation location) {
        super.bindTexture(location);
    }
}
