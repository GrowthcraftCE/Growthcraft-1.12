package growthcraft.core.shared.tileentity.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TileEventHandler {
    public static enum EventType {
        NULL,
        GUI_NETWORK_WRITE,
        GUI_NETWORK_READ,
        NETWORK_WRITE,
        NETWORK_READ,
        NBT_WRITE,
        NBT_READ;
    }

    EventType event();
}
