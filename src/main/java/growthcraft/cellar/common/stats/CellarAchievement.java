package growthcraft.cellar.common.stats;

import growthcraft.core.shared.IAchievement;
import net.minecraft.entity.player.EntityPlayer;

//Todo: Replace with advancements. Achievements are no more on 1.12.
public enum CellarAchievement implements IAchievement {
    // OPEN_ADHOC
    // REVISE_ME 0

    GET_DRUNK;

    @Override
    public void unlock(EntityPlayer player) {
//TODO:		GrowthcraftCellarAchievements.instance().unlock(this, player);
    }

    @Override
    public void addStat(EntityPlayer player, int n) {
//TODO:		GrowthcraftCellarAchievements.instance().addStat(this, player, n);
    }
}
