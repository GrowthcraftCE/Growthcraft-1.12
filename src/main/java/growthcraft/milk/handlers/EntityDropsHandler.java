package growthcraft.milk.handlers;

import growthcraft.milk.init.GrowthcraftMilkItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityDropsHandler {

    @SubscribeEvent
    public void onEnityDrops(LivingDropsEvent event) {
        if ( event.getEntityLiving() instanceof EntityCow ) {
            Entity eventEntity = event.getEntity();
            ItemStack itemStackStomach = GrowthcraftMilkItems.stomach.asStack();
            EntityItem entityItem = new EntityItem(eventEntity.world, eventEntity.posX, eventEntity.posY, eventEntity.posZ, itemStackStomach);
            event.getDrops().add(entityItem);
        }
    }
}
