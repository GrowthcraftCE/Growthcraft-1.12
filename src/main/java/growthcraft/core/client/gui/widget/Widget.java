package growthcraft.core.client.gui.widget;

import growthcraft.core.api.utils.Rectangle;

public class Widget
{
	public boolean visible = true;
	public Rectangle rect;
	@SuppressWarnings({"rawtypes"})
	protected WidgetManager manager;

	@SuppressWarnings({"rawtypes"})
	public Widget(WidgetManager man, Rectangle r)
	{
		this.manager = man;
		this.rect = r;
	}

	@SuppressWarnings({"rawtypes"})
	public Widget(WidgetManager man, int x, int y, int w, int h)
	{
		this(man, new Rectangle(x, y, w, h));
	}

	public int getX()
	{
		return rect.x;
	}

	public int getY()
	{
		return rect.y;
	}

	public int getWidth()
	{
		return rect.w;
	}

	public int getHeight()
	{
		return rect.h;
	}

	public int getGuiX()
	{
		return manager.gui.getGuiX();
	}

	public int getGuiY()
	{
		return manager.gui.getGuiY();
	}

	public int getGuiXSize()
	{
		return manager.gui.getXSize();
	}

	public int getGuiYSize()
	{
		return manager.gui.getYSize();
	}

	// Get Screen X
	public int getSX()
	{
		return getGuiX() + getX();
	}

	// Get Screen X
	public int getSY()
	{
		return getGuiY() + getY();
	}

	public boolean isVisible()
	{
		return visible;
	}

	public Widget show()
	{
		this.visible = true;
		return this;
	}

	public Widget hide()
	{
		this.visible = false;
		return this;
	}

	/**
	 * @param mx - mouse x coordinate
	 * @param my - mouse y coordinate
	 * @param x - screen x coordinate to draw to
	 * @param y - screen y coordinate to draw to
	 */
	protected void doDraw(int mx, int my, int x, int y)
	{
	}

	/**
	 * @param mx - mouse x coordinate
	 * @param my - mouse y coordinate
	 * @param x - screen x coordinate to draw to
	 * @param y - screen y coordinate to draw to
	 */
	protected void doDrawForeground(int mx, int my, int x, int y)
	{
	}

	/**
	 * @param mx - mouse x coordinate
	 * @param my - mouse y coordinate
	 */
	public final void draw(int mx, int my)
	{
		if (isVisible())
		{
			manager.gui.bindGuiTexture();
			doDraw(mx, my, getSX(), getSY());
			manager.gui.bindGuiTexture();
		}
	}

	/**
	 * @param mx - mouse x coordinate
	 * @param my - mouse y coordinate
	 */
	public final void drawForeground(int mx, int my)
	{
		if (isVisible())
		{
			manager.gui.bindGuiTexture();
			doDrawForeground(mx, my, getSX(), getSY());
			manager.gui.bindGuiTexture();
		}
	}
}
