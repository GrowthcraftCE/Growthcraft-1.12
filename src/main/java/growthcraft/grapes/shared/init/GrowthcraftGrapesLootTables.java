package growthcraft.grapes.shared.init;

import growthcraft.grapes.shared.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.HashSet;
import java.util.Set;

/**
 * Handles the definition and registration of custom loot tables for GrowthcraftGrapes.
 */
public class GrowthcraftGrapesLootTables {

    /**
     * Define the custom loot table(s). The String passed to RegistrationHandler.create will determine the name of
     * loot json file located in the assets directory.
     * Example:
     *  String = "loot_table_simple_dungeon"
     *  ResourceLocation = "growthcraft_grapes:loot_tables/loot_table_simple_dungeon.json"
     */
    public static final ResourceLocation LOOT_TABLE_SIMPLE_DUNGEON = RegistrationHandler.create("loot_table_simple_dungeon");

    public static void registerLootTables() {
        RegistrationHandler.LOOT_TABLES.forEach(LootTableList::register);
    }

    public static class RegistrationHandler {

        /**
         * HashSet containing all of the LootTable ID's for our mod.
         */
        public static final Set<ResourceLocation> LOOT_TABLES = new HashSet<>();

        /**
         * Creates a LootTable ID
         * @param id The ID of the LootTable without the modid reference.
         * @return The ID of the LootTable as a ResourceLocation.
         */
        protected static ResourceLocation create(final String id) {
            final ResourceLocation lootTable = new ResourceLocation(Reference.MODID, id);
            RegistrationHandler.LOOT_TABLES.add(lootTable);
            return lootTable;
        }

    }
}
