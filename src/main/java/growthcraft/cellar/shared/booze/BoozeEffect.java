package growthcraft.cellar.shared.booze;

import growthcraft.cellar.shared.booze.effect.EffectTipsy;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.effect.AbstractEffect;
import growthcraft.core.shared.effect.EffectAddPotionEffect;
import growthcraft.core.shared.effect.EffectList;
import growthcraft.core.shared.effect.IEffect;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BoozeEffect extends AbstractEffect {
    // REVISE_ME 0

    public static class BoozeEffectList extends EffectList {
        /**
         * Adds the description of all the internal effects
         *
         * @param list - list to add description lines to
         */
        @Override
        public void getDescription(List<String> list) {
            for (IEffect effect : effects) {
                effect.getDescription(list);
            }
        }
    }

    private EffectTipsy tipsyEffect;
    private EffectList effects = new BoozeEffectList();
    private Fluid booze;

    public BoozeEffect(@Nonnull Fluid flu) {
        this.booze = flu;
    }

    public BoozeEffect() {
    }

    public BoozeEffect clearEffects() {
        effects.clear();
        return this;
    }

    public BoozeEffect addEffect(IEffect effect) {
        effects.add(effect);
        return this;
    }

    public EffectAddPotionEffect createPotionEntry(@Nonnull Potion p, int time, int level) {
        final BoozePotionEffectFactory factory = new BoozePotionEffectFactory(booze, p, time, level);
        final EffectAddPotionEffect effect = new EffectAddPotionEffect(factory);
        addEffect(effect);
        return effect;
    }

    public BoozeEffect addPotionEntry(@Nonnull Potion p, int time, int level) {
        createPotionEntry(p, time, level);
        return this;
    }

    public EffectTipsy getTipsyEffect() {
        return tipsyEffect;
    }

    public BoozeEffect setTipsyEffect(EffectTipsy tipsy) {
        this.tipsyEffect = tipsy;
        return this;
    }

    public BoozeEffect setTipsy(float chance, int time) {
        setTipsyEffect(new EffectTipsy().setTipsy(chance, time));
        return this;
    }

    public BoozeEffect clearTipsy() {
        this.tipsyEffect = null;
        return this;
    }

    public EffectList getEffects() {
        return effects;
    }

    public boolean canCauseTipsy() {
        return tipsyEffect != null && tipsyEffect.canCauseTipsy();
    }

    public boolean hasEffects() {
        return effects.size() > 0;
    }

    public boolean isValid() {
        return canCauseTipsy() || hasEffects();
    }

    @Override
    public void apply(World world, Entity entity, Random random, Object data) {
        if (tipsyEffect != null) tipsyEffect.apply(world, entity, random, data);
        effects.apply(world, entity, random, data);
    }

    @Override
    protected void getActualDescription(List<String> list) {
        if (tipsyEffect != null) tipsyEffect.getDescription(list);
        effects.getDescription(list);
    }

    @Override
    protected void readFromNBT(NBTTagCompound data) {
        this.booze = null;
        this.tipsyEffect = null;
        if (data.hasKey("tipsy_effect")) {
            this.tipsyEffect = (EffectTipsy) CoreRegistry.instance().getEffectsRegistry().loadEffectFromNBT(data, "tipsy_effect");
        }
        this.effects = (BoozeEffectList) CoreRegistry.instance().getEffectsRegistry().loadEffectFromNBT(data, "effects");
        if (data.hasKey("fluid.name")) {
            this.booze = FluidRegistry.getFluid(data.getString("fluid.name"));
        }
    }

    @Override
    protected void writeToNBT(NBTTagCompound data) {
        if (tipsyEffect != null) {
            tipsyEffect.writeToNBT(data, "tipsy_effect");
        }
        effects.writeToNBT(data, "effects");
        if (booze != null) {
            data.setString("fluid.name", booze.getName());
        }
    }
}
