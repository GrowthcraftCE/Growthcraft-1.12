package growthcraft.core.shared.client.gui.widget;

import growthcraft.core.shared.client.gui.GrowthcraftGuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class WidgetManager<C extends Container, T extends TileEntity> {
    public final GrowthcraftGuiContainer<C, T> gui;
    protected List<Widget> widgets = new ArrayList<Widget>();

    public WidgetManager(GrowthcraftGuiContainer<C, T> g) {
        this.gui = g;
    }

    @SuppressWarnings({"rawtypes"})
    public WidgetManager add(Widget widget) {
        widgets.add(widget);
        return this;
    }

    public void draw(int mx, int my) {
        for (Widget widget : widgets) {
            widget.draw(mx, my);
        }
    }

    public void drawForeground(int mx, int my) {
        for (Widget widget : widgets) {
            widget.drawForeground(mx, my);
        }
    }
}
