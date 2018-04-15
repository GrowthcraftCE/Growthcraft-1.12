package growthcraft.core.client.lib.gui.widget;

import growthcraft.core.common.lib.tileentity.device.IFluidTanks;
import growthcraft.core.common.lib.utils.Rectangle;
import net.minecraft.tileentity.TileEntity;

public class WidgetFluidTank extends Widget
{
	public final int tankIndex;
	public int numberColor = 0xFFFFFF;
	public Rectangle ruleRect = new Rectangle();

	@SuppressWarnings({"rawtypes"})
	public WidgetFluidTank(WidgetManager man, int index, int x, int y, int w, int h)
	{
		super(man, x, y, w, h);
		this.tankIndex = index;
	}

	public WidgetFluidTank setRuleOverlay(int x, int y, int w, int h)
	{
		ruleRect.set(x, y, w, h);
		return this;
	}

	@Override
	protected void doDraw(int mx, int my, int x, int y)
	{
		final TileEntity tileEntity = manager.gui.getTileEntity();

		if (tileEntity instanceof IFluidTanks)
		{
			final IFluidTanks tanks = (IFluidTanks)tileEntity;
			final int w = getWidth();
			final int h = getHeight();
			if (tanks.getFluidAmountScaled(h, tankIndex) > 0)
			{
				manager.gui.drawFluidStack(getX(), getY(), manager.gui.getGuiX(), manager.gui.getGuiY(), w, h,
					tanks.getFluidAmountScaled(h, tankIndex),
					tanks.getFluidStack(tankIndex)/*, tanks.getFluidTank(tankIndex)*/);
			}
			if (!ruleRect.isEmpty())
			{
				manager.gui.bindGuiTexture();
				manager.gui.drawTexturedModalRect(x, y, ruleRect.x, ruleRect.y, ruleRect.w, ruleRect.h);
			}
		}
	}

	@Override
	protected void doDrawForeground(int mx, int my, int x, int y)
	{
		final TileEntity tileEntity = manager.gui.getTileEntity();

		if (tileEntity instanceof IFluidTanks)
		{
			final IFluidTanks tanks = (IFluidTanks)tileEntity;
			if (!tanks.isFluidTankEmpty(tankIndex))
			{
				final String s = String.valueOf(tanks.getFluidAmount(tankIndex));
				manager.gui.getFontRenderer().drawStringWithShadow(s,
					rect.x2() - manager.gui.getFontRenderer().getStringWidth(s),
					rect.y2() - 8,
					numberColor);
			}
		}
	}
}
