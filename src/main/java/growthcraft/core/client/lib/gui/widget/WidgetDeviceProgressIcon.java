package growthcraft.core.client.lib.gui.widget;

import growthcraft.core.common.lib.tileentity.feature.ITileProgressiveDevice;
import net.minecraft.tileentity.TileEntity;

public class WidgetDeviceProgressIcon extends WidgetIconBase
{
	public static enum ProgressDirection
	{
		LEFT_TO_RIGHT,
		RIGHT_TO_LEFT,
		TOP_TO_BOTTOM,
		BOTTOM_TO_TOP;
	}

	public ProgressDirection progressDirection = ProgressDirection.LEFT_TO_RIGHT;

	@SuppressWarnings({"rawtypes"})
	public WidgetDeviceProgressIcon(WidgetManager man, int x, int y, int w, int h)
	{
		super(man, x, y, w, h);
	}

	public WidgetDeviceProgressIcon setProgressDirection(ProgressDirection dir)
	{
		this.progressDirection = dir;
		return this;
	}

	@Override
	protected void doDraw(int mx, int my, int x, int y)
	{
		final TileEntity tileEntity = manager.gui.getTileEntity();
		if (tileEntity instanceof ITileProgressiveDevice)
		{
			final ITileProgressiveDevice progDevice = (ITileProgressiveDevice)tileEntity;
			int dx = x;
			int dy = y;
			int tx = textureRect.x;
			int ty = textureRect.y;
			int tw = textureRect.w;
			int th = textureRect.h;

			switch (progressDirection)
			{
				case LEFT_TO_RIGHT:
				{
					tw = progDevice.getDeviceProgressScaled(tw);
				} break;
				case RIGHT_TO_LEFT:
				{
					tw = progDevice.getDeviceProgressScaled(tw);
					final int offX = textureRect.w - tw;
					dx += offX;
					tx += offX;
				} break;
				case TOP_TO_BOTTOM:
				{
					th = progDevice.getDeviceProgressScaled(th);
				} break;
				case BOTTOM_TO_TOP:
				{
					th = progDevice.getDeviceProgressScaled(th);
					final int offY = textureRect.h - th;
					dy += offY;
					ty += offY;
				} break;
				default:
			}

			if (tw > 0 && th > 0)
			{
				manager.gui.drawTexturedModalRect(dx, dy, tx, ty, tw, th);
			}
		}
	}
}
