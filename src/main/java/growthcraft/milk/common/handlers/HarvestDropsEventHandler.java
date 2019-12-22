package growthcraft.milk.common.handlers;

import growthcraft.milk.shared.init.GrowthcraftMilkItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class HarvestDropsEventHandler {

    @SubscribeEvent
    public void onHarvestDrops(HarvestDropsEvent event) {
        Block harvestedBlock = event.getState().getBlock();
        if ( harvestedBlock instanceof BlockTallGrass && event.getHarvester() != null ) {
            doAdditionalDrop(event);
        }
    }

    private void doAdditionalDrop(HarvestDropsEvent event ) {
        Random random = new Random();

        if ( random.nextInt(100) <= 2 ) {
            ItemStack thistleStack = new ItemStack(GrowthcraftMilkItems.thistleSeed.getItem());
            event.getDrops().add(thistleStack);
        }
    }
}
