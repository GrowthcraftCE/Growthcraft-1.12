package growthcraft.core.shared.client.gui;

import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import org.lwjgl.opengl.GL11;

import growthcraft.core.shared.client.gui.widget.WidgetManager;
import growthcraft.core.shared.fluids.UnitFormatter;
import growthcraft.core.shared.utils.ColorUtils;
import growthcraft.core.shared.utils.Rectangle;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public abstract class GrowthcraftGuiContainer<C extends Container, T extends TileEntity> extends GuiContainer {

	protected T tileEntity;
	protected ResourceLocation guiResource;
	@SuppressWarnings({"rawtypes"})
	protected TooltipManager tooltipManager;
	@SuppressWarnings({"rawtypes"})
	protected WidgetManager widgets;

	public GrowthcraftGuiContainer(ResourceLocation res, C container, T te) {
		super(container);
		this.guiResource = res;
		this.tileEntity = te;
	}
	
	public T getTileEntity()
	{
		return tileEntity;
	}

	public FontRenderer getFontRenderer()
	{
		return fontRenderer;
	}

	public int getGuiX()
	{
		return (width - xSize) / 2;
	}

	public int getGuiY()
	{
		return (height - ySize) / 2;
	}

	public int getXSize()
	{
		return xSize;
	}

	public int getYSize()
	{
		return ySize;
	}

	public void setZLevel(float z)
	{
		this.zLevel = z;
	}

	public void getItemRendererZLevel(float z)
	{
		itemRender.zLevel = z;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.tooltipManager = new TooltipManager<C, T>(this);
		this.widgets = new WidgetManager<C, T>(this);
	}

	@Override
	@SuppressWarnings({"rawtypes"})
	public void drawHoveringText(List<String> l, int x, int y, FontRenderer renderer)
	{
		super.drawHoveringText(l, x, y, renderer);
	}
	
	public void drawHoveringText(List<String> l, int x, int y)
	{
		drawHoveringText(l, x, y, fontRenderer);
	}
	
	@Override
	public void drawGradientRect(int x, int y, int w, int h, int color1, int color2)
	{
		super.drawGradientRect(x, y, w, h, color1, color2);
	}
	
	public void bindTexture(ResourceLocation res)
	{
		mc.getTextureManager().bindTexture(res);
	}

	public void bindGuiTexture()
	{
		bindTexture(guiResource);
	}
	
	public void drawFluidStack(int x, int y, int wp, int hp, int width, int height, int amount, FluidStack fluidstack)
	{
		if (fluidstack == null) return;

		final Fluid fluid = fluidstack.getFluid();
		final int color = fluid.getColor();

		if (fluid != null)
		{
			TextureAtlasSprite fluidTexture = mc.getTextureMapBlocks().getTextureExtry(fluid.getStill().toString());
			if( fluidTexture != null ) {
				try {
		            mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		            final float argb[] = ColorUtils.getARGB(color);
//		            final float r = (float)(color >> 16 & 255) / 255.0F;
//					final float g = (float)(color >> 8 & 255) / 255.0F;
//					final float b = (float)(color & 255) / 255.0F;
					GL11.glColor4f(argb[1], argb[2], argb[3], 1.0f);
		            drawTexturedModalRect(x + guiLeft, y + guiTop + (height - amount), fluidTexture, width, amount);
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				}
				catch(NullPointerException exc) {
					/* Simply catch the NPE if the getFluid from the TE is NULL */
				}
			}
/*			final IIcon icon = fluid.getStillIcon();

			if (icon != null)
			{
				bindTexture(TextureMap.locationBlocksTexture);

				final float r = (float)(color >> 16 & 255) / 255.0F;
				final float g = (float)(color >> 8 & 255) / 255.0F;
				final float b = (float)(color & 255) / 255.0F;
				GL11.glColor4f(r, g, b, 1.0f);
				drawTexturedModelRectFromIcon(x + wp, y + hp + height - amount, icon, width, amount);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			}
*/
		}
	}

	public void drawTank(int w, int h, int wp, int hp, int width, int height, int amount, FluidTank tank)
	{
		FluidStack fluidstack = tank.getFluid();
        int fluidHeight = (tank.getFluidAmount() * /*52*/height) / tank.getCapacity();
		drawFluidStack(w, h, wp, hp, width, height, fluidHeight, fluidstack);
	}
	
/*	public void drawTank(int w, int h, int wp, int hp, int width, int height, int amount, FluidStack fluidstack, FluidTank _tank)
	{
		drawFluidStack(w, h, wp, hp, width, height, amount, fluidstack);
	} */

	protected void addFluidTooltips(FluidStack fluid, List<String> tooltip)
	{
		if (fluid == null) return;
		if (fluid.amount <= 0) return;

		tooltip.add(fluid.getLocalizedName());

		final String s = UnitFormatter.fluidModifier(fluid.getFluid());
		if (s != null) tooltip.add(s);
	}

	public void addTooltipIndex(String handle, Rectangle r)
	{
		tooltipManager.addTooltipIndex(handle, r);
	}

	public void addTooltipIndex(String handle, int x, int y, int w, int h)
	{
		tooltipManager.addTooltipIndex(handle, x, y, w, h);
	}

	protected void drawInventoryName(int mx, int my)
	{
		if (tileEntity instanceof IInventory)
		{
			final IInventory inv = (IInventory)tileEntity;
			final String invName = inv.getName();
			final String s = inv.hasCustomName() ? invName : I18n.format(invName);
			fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
			fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
		}
	}

	protected void drawWidgets(int mx, int my)
	{
		widgets.draw(mx, my);
	}

	protected void drawWidgetsForeground(int mx, int my)
	{
		widgets.drawForeground(mx, my);
	}

	protected void drawGuiPanel(int mx, int my)
	{
		drawTexturedModalRect(getGuiX(), getGuiY(), 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		bindGuiTexture();
		drawGuiPanel(mx, my);
		drawWidgets(mx, my);
		bindGuiTexture();
	}

	protected void drawTooltips(int mx, int my)
	{
		tooltipManager.draw(mx, my);
		this.renderHoveredToolTip(mx, my);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mx, int my)
	{
		super.drawGuiContainerForegroundLayer(mx, my);
		drawInventoryName(mx, my);
		drawWidgetsForeground(mx, my);
	}

	@Override
	public void drawScreen(int mx, int my, float par)
	{
		super.drawScreen(mx, my, par);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableGUIStandardItemLighting();

		GL11.glPushMatrix();
		{
			drawTooltips(mx, my);
		}
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	}

	
	// Overwrite this method to add tooltips based on the handle
	public void addTooltips(String handle, List<String> tips) {}


}
