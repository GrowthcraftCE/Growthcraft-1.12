package growthcraft.milk.common.handlers;

import growthcraft.milk.GrowthcraftMilk;
import growthcraft.milk.shared.init.GrowthcraftMilkItems;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class EntityDropsEventHandler {
    Random random = new Random();

    @SubscribeEvent
    public void onEntityDrops(LivingDropsEvent event) {
        if (event.getEntityLiving() instanceof EntityCow) {
            EntityAgeable eventEntity = (EntityAgeable) event.getEntity();
            int randomDrop = random.nextInt(100);

            if (GrowthcraftMilk.config.getBabyCowsOnlyDropStomach() && eventEntity.getGrowingAge() > 0) {
                return;
            }

            if (randomDrop <= GrowthcraftMilk.config.getStomachDropChance()) {
                ItemStack itemStackStomach = GrowthcraftMilkItems.stomach.asStack();
                EntityItem entityItem = new EntityItem(eventEntity.world, eventEntity.posX, eventEntity.posY, eventEntity.posZ, itemStackStomach);
                event.getDrops().add(entityItem);
            }
        }
    }

}
