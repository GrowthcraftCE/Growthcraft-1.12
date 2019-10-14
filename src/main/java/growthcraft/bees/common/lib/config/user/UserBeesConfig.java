package growthcraft.bees.common.lib.config.user;

import java.io.BufferedReader;

import growthcraft.bees.GrowthcraftBees;
import growthcraft.bees.common.lib.config.BeesRegistry;
import growthcraft.bees.shared.Reference;
import growthcraft.core.shared.item.ItemKey;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.config.AbstractUserJSONConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UserBeesConfig extends AbstractUserJSONConfig {
    private final UserBeesEntries defaultEntries = new UserBeesEntries();
    private UserBeesEntries entries;

    public UserBeeEntry addDefault(ItemStack bee) {
        final UserBeeEntry entry = new UserBeeEntry(bee);
        defaultEntries.data.add(entry);
        return entry;
    }

    public UserBeeEntry addDefault(Item bee) {
        return addDefault(new ItemStack(bee, ItemKey.WILDCARD_VALUE));
    }

    @Override
    protected String getDefault() {
        return gson.toJson(defaultEntries);
    }

    @Override
    protected void loadFromBuffer(BufferedReader buff) throws IllegalStateException {
        this.entries = gson.fromJson(buff, UserBeesEntries.class);
    }

    private void addBeeEntry(UserBeeEntry entry) {
        if (entry == null) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("Invalid Entry");
            return;
        }

        if (entry.item == null || entry.item.isInvalid()) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("Invalid item for entry {%s}", entry);
            return;
        }

        for (ItemStack stack : entry.item.getItemStacks()) {
            BeesRegistry.instance().addBee(stack);
        }
    }

    @Override
    public void postInit() {
        if (entries != null) {
            if (entries.data != null) {
                GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding %d user bee entries.", entries.data.size());
                for (UserBeeEntry entry : entries.data) addBeeEntry(entry);
            } else {
                GrowthcraftLogger.getLogger(Reference.MODID).error("Config contains invalid data.");
            }
        }
    }
}
