package growthcraft.core.client.lib.gui.widget;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;


@SideOnly(Side.CLIENT)
public class GuiButtonDiscard extends GuiButton
{
	private final ResourceLocation res;
	private final int iconX;
	private final int iconY;

	public GuiButtonDiscard(ResourceLocation resl, int id, int xpos, int ypos)
	{
		super(id, xpos, ypos, 16, 16, "");
		this.res = resl;
		this.iconX = 0;
		this.iconY = 166;
	}

	@Override
	public void drawButton(Minecraft mc, int w, int h)
	{
		if (this.visible)
		{
			mc.getTextureManager().bindTexture(this.res);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = w >= this.x && h >= this.y && w < this.x + this.width && h < this.y + this.height;
			final short y = 182;
			int x = 0;

			if (!this.enabled)
			{
				x += this.width * 1;
			}
			else if (this.hovered)
			{
				x += this.width * 2;
			}

			this.drawTexturedModalRect(this.x, this.y, x, y, this.width, this.height);
			this.drawTexturedModalRect(this.x, this.y, this.iconX, this.iconY, 16, 16);
		}
	}
}
