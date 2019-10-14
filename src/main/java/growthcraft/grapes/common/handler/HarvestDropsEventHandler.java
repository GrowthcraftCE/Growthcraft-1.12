package growthcraft.grapes.common.handler;

import growthcraft.grapes.shared.config.GrowthcraftGrapesConfig;
import growthcraft.grapes.shared.init.GrowthcraftGrapesItems;
import growthcraft.grapes.shared.init.GrowthcraftGrapesItems.GrapeTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class HarvestDropsEventHandler {

    @SubscribeEvent
    public void onHarvestDrops(HarvestDropsEvent event) {
        Block harvestedBlock = event.getState().getBlock();
        if (harvestedBlock instanceof BlockVine && !event.isSilkTouching() && event.getHarvester() != null) {
            ItemStack heldItemStack = event.getHarvester().getHeldItemMainhand();

            // We do not want to add additional drops if the player is using shears on the vines.
            boolean isShears = heldItemStack != null ? heldItemStack.getItem() instanceof ItemShears : true;
            if (!isShears) {
                doAdditionalDrop(event);
            }
        }
    }

    private void doAdditionalDrop(HarvestDropsEvent event) {
        int chanceVineDropGrapeSeeds = GrowthcraftGrapesConfig.chanceVineDropGrapeSeeds;
        Random random = new Random();

        if (random.nextInt(100) <= chanceVineDropGrapeSeeds) {
            // Randomly get a grape seed.
            int randomGrapeSeed = random.nextInt(GrapeTypes.values().length - 1);
            GrapeTypes grapeTypes = GrapeTypes.values()[randomGrapeSeed];
            int metaGrapeSeed = grapeTypes.getVariantID();
            ItemStack itemStack = new ItemStack(GrowthcraftGrapesItems.grape_seed.getItem(), 1, metaGrapeSeed);
            event.getDrops().add(itemStack);
        }
    }
}
