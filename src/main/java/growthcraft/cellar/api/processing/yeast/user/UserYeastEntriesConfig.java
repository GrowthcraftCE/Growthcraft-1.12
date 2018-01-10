package growthcraft.cellar.api.processing.yeast.user;

import java.io.BufferedReader;
import java.util.ArrayList;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.api.CellarRegistry;
import growthcraft.core.api.schema.ItemKeySchema;
import growthcraft.core.api.user.AbstractUserJSONConfig;
import growthcraft.core.api.utils.BiomeUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BiomeDictionary;

/**
 * This allows users to define new yeast entries and map them to a biome
 * for generation in the Ferment Jar.
 */
public class UserYeastEntriesConfig extends AbstractUserJSONConfig
{
	private final UserYeastEntries defaultEntries = new UserYeastEntries();
	private UserYeastEntries entries;

	@Override
	protected String getDefault()
	{
		final ItemKeySchema brewersYeast = new ItemKeySchema("Growthcraft|Cellar", "grc.yeast", 1, 0);
		brewersYeast.setComment("Brewers Yeast");

		final ItemKeySchema lagerYeast = new ItemKeySchema("Growthcraft|Cellar", "grc.yeast", 1, 1);
		lagerYeast.setComment("Lager Yeast");

		final ItemKeySchema etherealYeast = new ItemKeySchema("Growthcraft|Cellar", "grc.yeast", 1, 3);
		etherealYeast.setComment("Ethereal Yeast");

		final ItemKeySchema originYeast = new ItemKeySchema("Growthcraft|Cellar", "grc.yeast", 1, 4);
		etherealYeast.setComment("Origin Yeast");

		final UserYeastEntry brewers = new UserYeastEntry(brewersYeast, 1, new ArrayList<String>());
		brewers.setComment("Brewers yeast is the default yeast, which appears in all other biomes that are filled by the Lager or Ethereal");

		final UserYeastEntry lager = new UserYeastEntry(lagerYeast, 10, new ArrayList<String>());
		lager.setComment("Lager yeast is found in COLD biomes, think snow places!");

		final UserYeastEntry ethereal = new UserYeastEntry(etherealYeast, 10, new ArrayList<String>());
		ethereal.setComment("Ethereal yeast is found in MAGICAL biomes, because its special");

		final UserYeastEntry origin = new UserYeastEntry(originYeast, 10, new ArrayList<String>());
		origin.setComment("Origin yeast is found in MUSHROOM biomes.");

		for (BiomeDictionary.Type biomeType : BiomeUtils.types)
		{
			final String biomeTypeName = biomeType.getName();
			{
				if( biomeType.equals(BiomeDictionary.Type.COLD) )
				{
					lager.biome_types.add(biomeTypeName);
				}
				else if( biomeType.equals(BiomeDictionary.Type.MAGICAL) ) {
					ethereal.biome_types.add(biomeTypeName);
				}
				else if( biomeType.equals(BiomeDictionary.Type.MUSHROOM) ) {
					origin.biome_types.add(biomeTypeName);
				}
				else {
					brewers.biome_types.add(biomeTypeName);
				}
			}
		}
		defaultEntries.data.add(brewers);
		defaultEntries.data.add(ethereal);
		defaultEntries.data.add(lager);
		defaultEntries.data.add(origin);
		defaultEntries.setComment("Default Yeast Config v1.1.0");
		return gson.toJson(defaultEntries);
	}

	@Override
	protected void loadFromBuffer(BufferedReader reader) throws IllegalStateException
	{
		this.entries = gson.fromJson(reader, UserYeastEntries.class);
	}

	private void addYeastEntry(UserYeastEntry entry)
	{
		if (entry == null)
		{
			GrowthcraftCellar.logger.error("Yeast entry was invalid.");
			return;
		}

		if (entry.item == null || entry.item.isInvalid())
		{
			GrowthcraftCellar.logger.error("Yeast item was invalid {%s}", entry);
			return;
		}

		for (ItemStack itemstack : entry.item.getItemStacks())
		{
			if (entry.biome_types != null)
			{
				for (String biome : entry.biome_types)
				{
					try
					{
						final BiomeDictionary.Type biomeType = BiomeUtils.fetchBiomeType(biome);
						CellarRegistry.instance().yeast().addYeastToBiomeType(itemstack, entry.weight, biomeType);
						GrowthcraftCellar.logger.debug("Added user yeast {%s} to biome type '%s'", itemstack, biome);
					}
					catch (BiomeUtils.BiomeTypeNotFound ex)
					{
						GrowthcraftCellar.logger.error("A biome type '%s' for entry {%s} could not be found.", biome, entry);
					}
				}
			}

			if (entry.biome_names != null)
			{
				for (String biomeName : entry.biome_names)
				{
					CellarRegistry.instance().yeast().addYeastToBiomeByName(itemstack, entry.weight, biomeName);
					GrowthcraftCellar.logger.debug("Added user yeast {%s} to biome '%s'", itemstack, biomeName);
				}
			}
		}
	}

	@Override
	public void postInit()
	{
		if (entries != null)
		{
			if (entries.data != null)
			{
				GrowthcraftCellar.logger.debug("Adding %d yeast entries.", entries.data.size());
				for (UserYeastEntry entry : entries.data) addYeastEntry(entry);
			}
			else
			{
				GrowthcraftCellar.logger.error("Invalid yeast entries data");
			}
		}
	}
}
