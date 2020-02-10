package growthcraft.core.shared.effect;

import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.config.description.Describer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Has a random chance of applying its sub effect to the target
 */
public class EffectChance extends AbstractEffect {
    // REVISE_ME 0

    private float chance;
    private IEffect effect;

    /**
     * @param effekt - the effect to apply when the conditions are met
     */
    public EffectChance(IEffect effekt) {
        this.effect = effekt;
    }

    public EffectChance() {
    }

    public EffectChance setChance(float chan) {
        this.chance = chan;
        return this;
    }

    public float getChance() {
        return chance;
    }

    public IEffect getEffect() {
        return effect;
    }

    public EffectChance setEffect(IEffect effekt) {
        this.effect = effekt;
        return this;
    }

    @Override
    public void apply(World world, Entity entity, Random random, Object data) {
        if (effect != null) {
            if ((random.nextInt(2000) / 2000.0f) < chance) {
                effect.apply(world, entity, random, data);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void getActualDescription(List<String> list) {
        if (effect != null) {
            final List<String> tempList = new ArrayList<String>();
            effect.getDescription(tempList);
            if (tempList.size() > 0) {
                final String str = I18n.translateToLocalFormatted("effect.chance.format", (int) (chance * 100));
                Describer.compactDescription(str, list, tempList);
            }
        }
    }

    @Override
    protected void readFromNBT(NBTTagCompound data) {
        this.chance = data.getFloat("chance");
        if (data.hasKey("effect")) {
            this.effect = CoreRegistry.instance().getEffectsRegistry().loadEffectFromNBT(data, "effect");
        }
    }

    @Override
    protected void writeToNBT(NBTTagCompound data) {
        data.setFloat("chance", chance);
        if (effect != null) {
            effect.writeToNBT(data, "effect");
        }
    }
}
