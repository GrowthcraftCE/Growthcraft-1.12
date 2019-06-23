package growthcraft.hops.common.handler;

import growthcraft.hops.shared.config.GrowthcraftHopsConfig;
import growthcraft.hops.shared.init.GrowthcraftHopsItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class HarvestDropsEventHandler {

    @SubscribeEvent
    public void onHarvestBlocks(HarvestDropsEvent event) {
        Block harvestedBlock = event.getState().getBlock();
        if ( harvestedBlock instanceof BlockVine && !event.isSilkTouching() && event.getHarvester() != null ) {
            ItemStack heldItemStack = event.getHarvester().getHeldItemMainhand();

            // We do not want to add additional drops if the player is using shears on the vines.
            boolean isShears = heldItemStack != null ? heldItemStack.getItem() instanceof ItemShears : true;
            if ( ! isShears ) {
                doAdditionalDrop(event);
            }
        }
    }

    private void doAdditionalDrop(HarvestDropsEvent event) {
        int chanceVineDropHopSeeds = GrowthcraftHopsConfig.changeVineDropHopSeeds;
        if (chanceVineDropHopSeeds == 0) { return; }

        Random random = new Random();

        if ( random.nextInt(100) <= chanceVineDropHopSeeds ) {
            ItemStack itemStack = new ItemStack(GrowthcraftHopsItems.hop_seeds.getItem(), 1);
            event.getDrops().add(itemStack);
        }
    }
}
