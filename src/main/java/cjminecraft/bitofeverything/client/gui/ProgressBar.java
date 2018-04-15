package cjminecraft.bitofeverything.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * An easy way to draw a {@link ProgressBar}
 *
 * @author CJMinecraft
 */
public class ProgressBar extends Gui {
	// REMOVEME
	
    private ResourceLocation texture;
    private ProgressBarDirection direction;
    private int positionX, positionY;
    private int width, height;
    private int textureX, textureY;

    private float min, max = 0;

    /**
     * Create a {@link ProgressBar} with all the details set
     *
     * @param texture   The texture of the {@link ProgressBar}
     * @param direction The way in which the {@link ProgressBar} will move
     * @param width     The width of the {@link ProgressBar}
     * @param height    The height of the {@link ProgressBar}
     * @param positonX  The x position
     * @param positionY The y position
     * @param textureX  The x position of the texture
     * @param textureY  The y position of the texture
     */
    public ProgressBar(ResourceLocation texture, ProgressBarDirection direction, int width, int height, int positonX,
                       int positionY, int textureX, int textureY) {
        this.texture = texture;
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.positionX = positonX;
        this.positionY = positionY;
        this.textureX = textureX;
        this.textureY = textureY;
    }

    /**
     * Set the minimum value (aka the <strong>this</strong> out of something)
     *
     * @param min The <strong>this</strong> out of something
     * @return The {@link ProgressBar}
     */
    public ProgressBar setMin(int min) {
        this.min = min;
        return this;
    }

    /**
     * Set the maximum value (aka the cap or the something out of
     * <strong>this</strong>)
     *
     * @param max The <strong>this</strong> out of something
     * @return The {@link ProgressBar}
     */
    public ProgressBar setMax(int max) {
        this.max = max;
        return this;
    }

    /**
     * Get the width based on the min and max values
     *
     * @return The width
     */
    private int getAdjustedWidth() {
        return (int) (min != 0 && max != 0 ? min / max * width : 0);
    }

    /**
     * Get the height based on the min and max values
     *
     * @return The height
     */
    private int getAdjustedHeight() {
        return (int) (min != 0 && max != 0 ? min / max * height : 0);
    }

    /**
     * Draw the {@link ProgressBar}
     *
     * @param mc So that the code can bind the texture
     */
    public void draw(Minecraft mc) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(texture);
        switch (direction) {
            case DIAGONAL_UP_LEFT:
                this.drawTexturedModalRect(positionX, positionY, textureX, textureY, width, height);
                this.drawTexturedModalRect(positionX, positionY, positionX, positionY, width - getAdjustedWidth(),
                        height - getAdjustedHeight());
                break;
            case DIAGONAL_UP_RIGHT:
                this.drawTexturedModalRect(positionX, positionY, textureX, textureY, width, height);
                this.drawTexturedModalRect(positionX + getAdjustedWidth(), positionY, positionX + getAdjustedWidth(),
                        positionY, width - getAdjustedWidth(), height - getAdjustedHeight());
                break;
            case DIAGONAL_DOWN_LEFT:
                this.drawTexturedModalRect(positionX, positionY, textureX, textureY, width, height);
                this.drawTexturedModalRect(positionX, positionY + getAdjustedHeight(), positionX,
                        positionY + getAdjustedHeight(), width - getAdjustedWidth(), height - getAdjustedHeight());
                break;
            case DIAGONAL_DOWN_RIGHT:
                this.drawTexturedModalRect(positionX, positionY, textureX, textureY, getAdjustedWidth(),
                        getAdjustedHeight());
                break;
            case DOWN_TO_UP:
                this.drawTexturedModalRect(positionX, positionY, textureX, textureY, width, height);
                this.drawTexturedModalRect(positionX, positionY, positionX, positionY, width, height - getAdjustedHeight());
                break;
            case LEFT_TO_RIGHT:
                this.drawTexturedModalRect(positionX, positionY, textureX, textureY, getAdjustedWidth(), height);
                break;
            case RIGHT_TO_LEFT:
                this.drawTexturedModalRect(positionX, positionY, textureX, textureY, width, height);
                this.drawTexturedModalRect(positionX, positionY, positionX, positionY, width - getAdjustedWidth(), height);
                break;
            case UP_TO_DOWN:
                this.drawTexturedModalRect(positionX, positionY, textureX, textureY, width, getAdjustedHeight());
                break;
            default:
                this.drawTexturedModalRect(positionX, positionY, textureX, textureY, width, height);
                break;
        }
    }

    /**
     * Which way the {@link ProgressBar} will move
     *
     * @author CJMinecraft
     */
    public enum ProgressBarDirection {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT, UP_TO_DOWN, DOWN_TO_UP, DIAGONAL_UP_RIGHT, DIAGONAL_UP_LEFT, DIAGONAL_DOWN_RIGHT, DIAGONAL_DOWN_LEFT
    }

}