package growthcraft.core.shared.effect;

import javax.annotation.Nonnull;

import net.minecraft.potion.Potion;

public class EffectUtils {
    private EffectUtils() {
    }

    public static EffectAddPotionEffect createAddPotionEffect(@Nonnull Potion p, int time, int level) {
        final IPotionEffectFactory factory = new SimplePotionEffectFactory(p, time, level);
        return new EffectAddPotionEffect(factory);
    }
}
