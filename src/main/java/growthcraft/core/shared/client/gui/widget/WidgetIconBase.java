package growthcraft.core.shared.client.gui.widget;

import growthcraft.core.shared.utils.Rectangle;

public class WidgetIconBase extends Widget {
    public final Rectangle textureRect = new Rectangle();

    @SuppressWarnings({"rawtypes"})
    public WidgetIconBase(WidgetManager man, int x, int y, int w, int h) {
        super(man, x, y, w, h);
    }

    public WidgetIconBase setTextureRect(int x, int y, int w, int h) {
        textureRect.set(x, y, w, h);
        return this;
    }
}
