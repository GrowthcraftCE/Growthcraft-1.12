package growthcraft.core.shared.client;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GrowthcraftCoreState {
    public static final String detailedKey = "SHIFT";

    private GrowthcraftCoreState() {
    }

    @SideOnly(Side.CLIENT)
    public static boolean isAltKeyDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU);
    }

    @SideOnly(Side.CLIENT)
    public static boolean isCtrlKeyDown() {
        if (Minecraft.IS_RUNNING_ON_MAC)
            return Keyboard.isKeyDown(Keyboard.KEY_LMETA) || Keyboard.isKeyDown(Keyboard.KEY_RMETA);
        return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
    }

    @SideOnly(Side.CLIENT)
    public static boolean isShiftKeyDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    /**
     * Should we show detailed information on items?
     * This is usually toggled via Shift
     *
     * @return true show details, false otherwise
     */
    public static boolean showDetailedInformation() {
        return isShiftKeyDown();
    }
}
