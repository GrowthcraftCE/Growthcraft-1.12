package growthcraft.cellar.events;

import growthcraft.cellar.init.GrowthcraftCellarPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerLivingUpdateEventCellar
{
	// REVISE_ME 0
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		final EntityLivingBase ent = event.getEntityLiving();

		if (ent.isPotionActive(GrowthcraftCellarPotions.potionTipsy))
		{
			if (ent.getActivePotionEffect(GrowthcraftCellarPotions.potionTipsy).getDuration() == 0)
			{
				ent.removePotionEffect(GrowthcraftCellarPotions.potionTipsy);
				return;
			}

			final int lvl = ent.getActivePotionEffect(GrowthcraftCellarPotions.potionTipsy).getAmplifier();

			if (lvl >= 3)
			{
				ent.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));

				if (lvl >= 4)
				{
					ent.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 0));
				}
			}
		}
	}
}