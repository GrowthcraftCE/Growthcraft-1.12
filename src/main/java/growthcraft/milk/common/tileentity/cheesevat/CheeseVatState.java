package growthcraft.milk.common.tileentity.cheesevat;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Locale;

public enum CheeseVatState {
    IDLE,
    PREPARING_RICOTTA,
    PREPARING_CHEESE,
    PREPARING_CURDS;

    public static final BiMap<String, CheeseVatState> stateMap = HashBiMap.create();
    private static final CheeseVatState[] VALUES = {IDLE, PREPARING_RICOTTA, PREPARING_CHEESE, PREPARING_CURDS};
    public final String name;

    static {
        for (CheeseVatState state : VALUES) {
            stateMap.put(state.name, state);
        }
    }

    private CheeseVatState() {
        this.name = name().toLowerCase(Locale.ENGLISH);
    }

    public static CheeseVatState getStateSafe(String name) {
        final CheeseVatState state = stateMap.get(name);
        if (state != null) return state;
        return IDLE;
    }
}
