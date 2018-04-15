package growthcraft.cellar.stats;

import growthcraft.core.shared.IAchievement;
import net.minecraft.entity.player.EntityPlayer;

public enum CellarAchievement implements IAchievement
{
	// OPEN_ADHOC
	// REVISE_ME 0
	
	GET_DRUNK;

	@Override
	public void unlock(EntityPlayer player)
	{
		GrowthcraftCellarAchievements.instance().unlock(this, player);
	}

	@Override
	public void addStat(EntityPlayer player, int n)
	{
		GrowthcraftCellarAchievements.instance().addStat(this, player, n);
	}
}
