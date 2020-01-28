package growthcraft.grapes.common.handler;

import growthcraft.grapes.shared.Reference;
import growthcraft.grapes.shared.init.GrowthcraftGrapesLootTables;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class GrowthcraftGrapesLootTableHandler {

    @SubscribeEvent
    public static void lootTableLoad(final LootTableLoadEvent event) {

        boolean isSimpleDungeon = event.getName().toString().equals("minecraft:chests/simple_dungeon");
        boolean isMineshaft = event.getName().toString().equals("minecraft:chests/abandoned_mineshaft");
        boolean isSpawnBonus = event.getName().toString().equals("minecraft:chests/spawn_bonus_chest");

        if (isMineshaft || isSimpleDungeon) {
            final String name = GrowthcraftGrapesLootTables.LOOT_TABLE_SIMPLE_DUNGEON.toString();

            final LootEntry entry = new LootEntryTable(
                    GrowthcraftGrapesLootTables.LOOT_TABLE_SIMPLE_DUNGEON,
                    50, 0,
                    new LootCondition[0], name);

            final RandomValueRange rolls = new RandomValueRange(0, 1);
            final LootPool pool = new LootPool(new LootEntry[]{entry}, new LootCondition[0], rolls, rolls, name);
            event.getTable().addPool(pool);
        }
    }
}
