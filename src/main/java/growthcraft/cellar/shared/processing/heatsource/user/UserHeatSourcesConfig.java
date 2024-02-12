package growthcraft.cellar.shared.processing.heatsource.user;

import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.config.AbstractUserJSONConfig;
import growthcraft.core.shared.item.ItemKey;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedReader;
import java.util.Map;

/**
 * Provides users with the ability to set blocks as heat sources for Growthcraft
 * blocks
 */
public class UserHeatSourcesConfig extends AbstractUserJSONConfig {
    private final UserHeatSourceEntries defaultEntries = new UserHeatSourceEntries();
    private UserHeatSourceEntries entries;

    public UserHeatSourceEntry addDefault(String m, String b, Map<Integer, Float> s) {
        final UserHeatSourceEntry entry = new UserHeatSourceEntry(m, b, s);
        defaultEntries.data.add(entry);
        return entry;
    }

    public UserHeatSourceEntry addDefault(ResourceLocation registryName, Map<Integer, Float> s) {
        return addDefault(registryName.getNamespace(), registryName.getPath(), s);
    }

    @Override
    protected String getDefault() {
        return gson.toJson(defaultEntries);
    }

    @Override
    protected void loadFromBuffer(BufferedReader buff) throws IllegalStateException {
        this.entries = gson.fromJson(buff, UserHeatSourceEntries.class);
    }

    private void addHeatSource(UserHeatSourceEntry entry) {
        final Block block = Block.getBlockFromName(entry.mod_id + ":" + entry.block_name); // GameRegistry.findBlock(entry.mod_id, entry.block_name);
        if (block != null) {
            if (entry.states == null || entry.states.size() == 0) {
                GrowthcraftLogger.getLogger(Reference.MODID).warn("Block contains invalid states, we will assume a wildcard, but you should probably set this. mod_id='%s' block='%s'", entry.mod_id, entry.block_name);
                CellarRegistry.instance().heatSource().addHeatSource(block, ItemKey.WILDCARD_VALUE);
            } else {
                for (Map.Entry<Integer, Float> pair : entry.states.entrySet()) {
                    int key = pair.getKey();
                    if (key < 0) key = ItemKey.WILDCARD_VALUE;
                    CellarRegistry.instance().heatSource().addHeatSource(block, key, pair.getValue());
                }
            }
        } else {
            GrowthcraftLogger.getLogger(Reference.MODID).error("Block could not be found, and will not be added as heat source. mod_id='%s' block='%s'", entry.mod_id, entry.block_name);
        }
    }

    @Override
    public void postInit() {
        if (entries != null) {
            if (entries.data != null) {
                GrowthcraftLogger.getLogger(Reference.MODID).debug("Registering %d heat sources.", entries.data.size());
                for (UserHeatSourceEntry entry : entries.data) addHeatSource(entry);
            } else {
                GrowthcraftLogger.getLogger(Reference.MODID).error("Config data is invalid");
            }
        }
    }
}
