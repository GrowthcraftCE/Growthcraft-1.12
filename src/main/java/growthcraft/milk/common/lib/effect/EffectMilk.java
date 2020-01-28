package growthcraft.milk.common.lib.effect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import growthcraft.core.shared.effect.IEffect;
import growthcraft.core.shared.io.nbt.NBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

/**
 * This is an effect similar to drinking milk, however it can blacklist certain
 * potions effects.
 */
public class EffectMilk implements IEffect {
    // A list of effects that should not be removed by this effect
    private Set<Integer> blacklist = new HashSet<Integer>();

    public EffectMilk clearBlacklist() {
        blacklist.clear();
        return this;
    }

    public EffectMilk blacklistPotions(Potion... potions) {
        for (Potion potion : potions) {
            blacklist.add(Potion.getIdFromPotion(potion));
        }
        return this;
    }

    public void apply(World _w, Entity entity, Random _r, Object _d) {
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase elb = (EntityLivingBase) entity;
            final List<Integer> effectsToRemove = new ArrayList<Integer>();
            for (Object e : elb.getActivePotionEffects()) {
                if (e instanceof PotionEffect) {
                    final PotionEffect eff = (PotionEffect) e;
                    final int id = Potion.getIdFromPotion(eff.getPotion());
                    final boolean isCurable = eff.getCurativeItems().size() > 0 && !blacklist.contains(id);
                    if (isCurable) {
                        // to prevent concurrent modifications, cache the
                        // effect ids
                        effectsToRemove.add(id);
                    }
                }
            }
            for (int id : effectsToRemove) {
                elb.removePotionEffect(Potion.getPotionById(id));
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void getDescription(List<String> list) {
        list.add(I18n.translateToLocal("grc.effect.milk"));
    }

    /**
     * Creates a new EffectMilk, and initializes the blacklist with the
     * given potions
     *
     * @param potions - list of potions to add to the blacklist
     * @return effect
     */
    public static EffectMilk create(Potion... potions) {
        final EffectMilk eff = new EffectMilk();
        return eff.blacklistPotions(potions);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt, String name) {
        blacklist.clear();
        if (nbt.hasKey(name)) {
            final NBTTagCompound tag = nbt.getCompoundTag(name);
            final NBTTagCompound blacklistTag = tag.getCompoundTag("blacklist");
            NBTHelper.readIntegerCollection(blacklist, blacklistTag);
        } else {
            // log error
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt, String name) {
        final NBTTagCompound tag = new NBTTagCompound();
        final NBTTagCompound blacklistTag = new NBTTagCompound();
        NBTHelper.writeIntegerCollection(blacklistTag, blacklist);
        tag.setTag("blacklist", blacklistTag);
        nbt.setTag(name, tag);
    }
}
