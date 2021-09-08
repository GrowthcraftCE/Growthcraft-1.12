package growthcraft.tweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;

public interface DummyAction extends IAction {
    @Override
    public default String describe() {
        return "";
    }

    public static void apply(DummyAction action) {
        CraftTweakerAPI.apply(action);
    }
}
