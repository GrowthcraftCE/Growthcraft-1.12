package growthcraft.cellar.events;

import growthcraft.cellar.init.CellarPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerLivingUpdateEventCellar
{
	// REVISE_ME 0
	// INITIALIZE
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		final EntityLivingBase ent = event.getEntityLiving();

		if (ent.isPotionActive(CellarPotions.potionTipsy))
		{
			if (ent.getActivePotionEffect(CellarPotions.potionTipsy).getDuration() == 0)
			{
				ent.removePotionEffect(CellarPotions.potionTipsy);
				return;
			}

			final int lvl = ent.getActivePotionEffect(CellarPotions.potionTipsy).getAmplifier();

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