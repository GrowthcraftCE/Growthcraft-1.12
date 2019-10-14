package growthcraft.bees.common.lib.config.user;

import java.io.BufferedReader;

import growthcraft.bees.GrowthcraftBees;
import growthcraft.bees.common.lib.config.BeesRegistry;
import growthcraft.bees.common.lib.config.ForcedFlowerBlockEntry;
import growthcraft.bees.shared.Reference;
import growthcraft.core.shared.item.ItemKey;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.config.AbstractUserJSONConfig;
import net.minecraft.block.Block;

public class UserFlowersConfig extends AbstractUserJSONConfig {
    private final UserFlowersEntries defaultEntries = new UserFlowersEntries();
    private UserFlowersEntries entries;

    public UserFlowerEntry addDefault(UserFlowerEntry entry) {
        defaultEntries.data.add(entry);
        return entry;
    }

    public UserFlowerEntry addDefault(Block flower, int meta) {
        return addDefault(new UserFlowerEntry(flower, meta));
    }

    public UserFlowerEntry addDefault(Block flower) {
        return addDefault(flower, ItemKey.WILDCARD_VALUE);
    }

    @Override
    protected String getDefault() {
        return gson.toJson(defaultEntries);
    }

    @Override
    protected void loadFromBuffer(BufferedReader buff) throws IllegalStateException {
        this.entries = gson.fromJson(buff, UserFlowersEntries.class);
    }

    private void addFlowerEntry(UserFlowerEntry entry) {
        if (entry == null) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("Invalid Entry");
            return;
        }

        if (entry.block == null || entry.block.isInvalid()) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("Invalid block for entry {%s}", entry);
            return;
        }

        switch (entry.entry_type) {
            case "generic":
                BeesRegistry.instance().addFlower(entry.block.getBlock(), entry.block.meta);
                break;
            case "forced":
                BeesRegistry.instance().addFlower(new ForcedFlowerBlockEntry(entry.block.getBlock(), entry.block.meta));
                break;
            default:
                GrowthcraftLogger.getLogger(Reference.MODID).error("Invalid entry_type '%s' for entry {%s}", entry.entry_type, entry);
        }

    }

    @Override
    public void postInit() {
        if (entries != null) {
            if (entries.data != null) {
                GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding %d user flower entries.", entries.data.size());
                for (UserFlowerEntry entry : entries.data) addFlowerEntry(entry);
            } else {
                GrowthcraftLogger.getLogger(Reference.MODID).error("Config contains invalid data.");
            }
        }
    }
}