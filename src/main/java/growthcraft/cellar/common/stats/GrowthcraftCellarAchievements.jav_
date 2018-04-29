package growthcraft.cellar.common.stats;

import java.util.EnumMap;
import java.util.Map;

import growthcraft.cellar.shared.init.GrowthcraftCellarItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public class GrowthcraftCellarAchievements {
	// OPEN_ADHOC
	// REVISE_ME 0

	private static GrowthcraftCellarAchievements INSTANCE = new GrowthcraftCellarAchievements();
	
	private static final Achievement NO_ACHIEVEMENT = (Achievement)null;
	private final Map<CellarAchievement, Achievement> achievements = new EnumMap<CellarAchievement, Achievement>(CellarAchievement.class);
	
	private GrowthcraftCellarAchievements() {
	}
	
	public static GrowthcraftCellarAchievements instance() {
		return INSTANCE;
	}
	
	public void preInit() {
		// OPEN_ADHOC
		
		achievements.put(CellarAchievement.GET_DRUNK,
				(new Achievement("achievement.getDrunk", "getDrunk",
					0, -4,
					GrowthcraftCellarItems.chievItemDummy.asStack(),
					NO_ACHIEVEMENT /*achievements.get(CellarAchievement.FERMENT_BOOZE) */)
				).setSpecial().registerStat()
			);
	}
	
	public void unlock(CellarAchievement a, EntityPlayer player)
	{
		final Achievement achievement = achievements.get(a);
		if (achievement != null)
		{
//			player.triggerAchievement(achievement);
			player.addStat(achievement);	// As implemented in MC v1.7.10 at EntityPlayer.triggerAchievement()
		}
	}

	public void addStat(CellarAchievement a, EntityPlayer player, int n)
	{
		final Achievement achievement = achievements.get(a);
		if (achievement != null)
		{
			player.addStat(achievement, n);
		}
	}
}
