package growthcraft.core.shared.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetMetadata;

public class LootUtils {
    private LootUtils() {
    }

    public static LootPool getOrCreateLootPool(LootTable lootTable, String poolId) {
        LootPool pool = lootTable.getPool(poolId);
        if (pool != null)
            return pool;
        pool = new LootPool(new LootEntry[]{}, new LootCondition[]{}, new RandomValueRange(1), new RandomValueRange(0), poolId);
        lootTable.addPool(pool);
        return pool;
    }

    public static void addLootEntry(LootPool pool, Item item, int minCount, int maxCount, int weight) {
        LootFunction quantityFct = new SetCount(new LootCondition[]{}, new RandomValueRange(minCount, maxCount));

        pool.addEntry(new LootEntryItem(item, weight, 0, new LootFunction[]{quantityFct}, new LootCondition[]{}, "growthcraft." + item.getTranslationKey()));
    }

    public static void addLootEntry(LootPool pool, ItemStack itemStack, int minCount, int maxCount, int weight) {
        LootFunction metaFct = new SetMetadata(new LootCondition[]{}, new RandomValueRange(itemStack.getMetadata()));
        LootFunction quantityFct = new SetCount(new LootCondition[]{}, new RandomValueRange(minCount, maxCount));

        Item item = itemStack.getItem();
        String name = "growthcraft." + item.getTranslationKey() + "." + itemStack.getMetadata();

        pool.addEntry(new LootEntryItem(item, weight, 0, new LootFunction[]{metaFct, quantityFct}, new LootCondition[]{}, name));
    }
}
