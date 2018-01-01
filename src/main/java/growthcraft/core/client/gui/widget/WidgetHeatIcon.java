package growthcraft.core.client.gui.widget;

import growthcraft.core.common.tileentity.feature.ITileHeatedDevice;
import net.minecraft.tileentity.TileEntity;

public class WidgetHeatIcon extends WidgetIconBase
{
	@SuppressWarnings({"rawtypes"})
	public WidgetHeatIcon(WidgetManager man, int x, int y, int w, int h)
	{
		super(man, x, y, w, h);
	}

	@Override
	protected void doDraw(int mx, int my, int x, int y)
	{
		final TileEntity tileEntity = manager.gui.getTileEntity();
		if (tileEntity instanceof ITileHeatedDevice)
		{
			final ITileHeatedDevice heatedDevice = (ITileHeatedDevice)tileEntity;
			if (heatedDevice.isHeated())
			{
				final int iconHeight = heatedDevice.getHeatScaled(textureRect.h);
				final int offY = textureRect.h - iconHeight;
				manager.gui.drawTexturedModalRect(x, y + offY, textureRect.x, textureRect.y + offY, textureRect.w, iconHeight);
			}
		}
	}
}
