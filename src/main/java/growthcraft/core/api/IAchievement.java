package growthcraft.core.api;

import net.minecraft.entity.player.EntityPlayer;

public interface IAchievement
{
	// REVISE_ME 0
	
	void unlock(EntityPlayer player);
	void addStat(EntityPlayer player, int n);
}
