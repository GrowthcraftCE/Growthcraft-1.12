package growthcraft.bees.api.user;

import java.io.BufferedReader;

import growthcraft.bees.GrowthcraftBees;
import growthcraft.bees.api.BeesRegistry;
import growthcraft.core.api.item.ItemKey;
import growthcraft.core.api.user.AbstractUserJSONConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UserBeesConfig extends AbstractUserJSONConfig
{
	private final UserBeesEntries defaultEntries = new UserBeesEntries();
	private UserBeesEntries entries;

	public UserBeeEntry addDefault(ItemStack bee)
	{
		final UserBeeEntry entry = new UserBeeEntry(bee);
		defaultEntries.data.add(entry);
		return entry;
	}

	public UserBeeEntry addDefault(Item bee)
	{
		return addDefault(new ItemStack(bee, ItemKey.WILDCARD_VALUE));
	}

	@Override
	protected String getDefault()
	{
		return gson.toJson(defaultEntries);
	}

	@Override
	protected void loadFromBuffer(BufferedReader buff) throws IllegalStateException
	{
		this.entries = gson.fromJson(buff, UserBeesEntries.class);
	}

	private void addBeeEntry(UserBeeEntry entry)
	{
		if (entry == null)
		{
			GrowthcraftBees.logger.error("Invalid Entry");
			return;
		}

		if (entry.item == null || entry.item.isInvalid())
		{
			GrowthcraftBees.logger.error("Invalid item for entry {%s}", entry);
			return;
		}

		for (ItemStack stack : entry.item.getItemStacks())
		{
			BeesRegistry.instance().addBee(stack);
		}
	}

	@Override
	public void postInit()
	{
		if (entries != null)
		{
			if (entries.data != null)
			{
				GrowthcraftBees.logger.debug("Adding %d user bee entries.", entries.data.size());
				for (UserBeeEntry entry : entries.data) addBeeEntry(entry);
			}
			else
			{
				GrowthcraftBees.logger.error("Config contains invalid data.");
			}
		}
	}
}
