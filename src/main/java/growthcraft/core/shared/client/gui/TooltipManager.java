package growthcraft.core.shared.client.gui;

import growthcraft.core.shared.utils.Rectangle;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class TooltipManager<C extends Container, T extends TileEntity> {
    public static class TooltipIndex {
        // use this to identify the tooltip index
        public final String handle;
        // The rectangle where this tooltip will be displayed
        public final Rectangle rect;

        public TooltipIndex(String h, Rectangle r) {
            this.handle = h;
            this.rect = r;
        }
    }

    public final GrowthcraftGuiContainer<C, T> gui;
    protected final List<TooltipIndex> tooltipIndices = new ArrayList<TooltipIndex>();
    protected final List<String> tooltipCache = new ArrayList<String>();

    public TooltipManager(GrowthcraftGuiContainer<C, T> g) {
        this.gui = g;
    }

    public void addTooltipIndex(String handle, Rectangle r) {
        tooltipIndices.add(new TooltipIndex(handle, r));
    }

    public void addTooltipIndex(String handle, int x, int y, int w, int h) {
        addTooltipIndex(handle, new Rectangle(x, y, w, h));
    }

    // Overwrite this method to draw tooltips, use the ti to identify which
    // rectangle you're working with.
    protected void drawTooltipIndex(TooltipIndex ti, int x, int y) {
        tooltipCache.clear();
        gui.addTooltips(ti.handle, tooltipCache);
        if (tooltipCache.size() > 0) {
            gui.drawHoveringText(tooltipCache, x, y);
        }
    }

    protected void drawTooltip(int mx, int my) {
        final int gx = mx - ((gui.width - gui.getXSize()) / 2);
        final int gy = my - ((gui.height - gui.getYSize()) / 2);

        for (TooltipIndex ti : tooltipIndices) {
            if (ti.rect.contains(gx, gy)) {
                drawTooltipIndex(ti, mx, my);
                break;
            }
        }
    }

    public void draw(int mx, int my) {
        drawTooltip(mx, my);
    }
}
