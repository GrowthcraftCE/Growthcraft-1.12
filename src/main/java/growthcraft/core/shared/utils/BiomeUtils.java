package growthcraft.core.shared.utils;

import growthcraft.core.shared.config.TagParser;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeUtils
{
	public static BiomeDictionary.Type types[] =
	{
        /*Temperature-based tags. Specifying neither implies a biome is temperate*/
		BiomeDictionary.Type.HOT,
		BiomeDictionary.Type.COLD,
		
        /*Tags specifying the amount of vegetation a biome has. Specifying neither implies a biome to have moderate amounts*/
		BiomeDictionary.Type.SPARSE,
		BiomeDictionary.Type.DENSE,

		/*Tags specifying how moist a biome is. Specifying neither implies the biome as having moderate humidity*/
		BiomeDictionary.Type.WET,
		BiomeDictionary.Type.DRY,

        /*Tree-based tags, SAVANNA refers to dry, desert-like trees (Such as Acacia), CONIFEROUS refers to snowy trees (Such as Spruce) and JUNGLE refers to jungle trees.
         * Specifying no tag implies a biome has temperate trees (Such as Oak)*/
		BiomeDictionary.Type.SAVANNA,
		BiomeDictionary.Type.CONIFEROUS,
		BiomeDictionary.Type.JUNGLE,

		/*Tags specifying the nature of a biome*/
		BiomeDictionary.Type.SPOOKY,
		BiomeDictionary.Type.DEAD,
		BiomeDictionary.Type.LUSH,
		BiomeDictionary.Type.NETHER,
		BiomeDictionary.Type.END,
		BiomeDictionary.Type.MUSHROOM,
		BiomeDictionary.Type.MAGICAL,
		BiomeDictionary.Type.RARE,

		BiomeDictionary.Type.OCEAN,
		BiomeDictionary.Type.RIVER,
        /*
         * A general tag for all water-based biomes. Shown as present if OCEAN or RIVER are.
         */
		BiomeDictionary.Type.WATER,

		/*Generic types which a biome can be*/
		BiomeDictionary.Type.MESA,
		BiomeDictionary.Type.FOREST,
		BiomeDictionary.Type.PLAINS,
		BiomeDictionary.Type.MOUNTAIN,
		BiomeDictionary.Type.HILLS,
		BiomeDictionary.Type.SWAMP,
		BiomeDictionary.Type.SANDY,
		BiomeDictionary.Type.SNOWY,
		BiomeDictionary.Type.WASTELAND,
		BiomeDictionary.Type.BEACH,
		BiomeDictionary.Type.VOID,

	};
	
	public static class BiomeTypeNotFound extends Exception
	{
		public static final long serialVersionUID = 1L;

		public BiomeTypeNotFound(String msg)
		{
			super(msg);
		}

		public BiomeTypeNotFound() {}
	}

	private BiomeUtils() {}

	/**
	 * The default implementation of the BiomeDictionary.Type doesn't have
	 * a non-mutative version of the getType method.
	 * Growthcraft will NOT attempt to add any new biomes, if you f*ck up,
	 * this method will slap you in the face for it.
	 *
	 * @param name - name of the biome to look for, this will be upcased
	 * @return type - found biome type
	 */
	public static BiomeDictionary.Type fetchBiomeType(String name) throws BiomeTypeNotFound
	{
		final String upcasedName = name.toUpperCase();

		// I really shouldn't be doing this, but what choice do you have :(
		for (BiomeDictionary.Type type : types)
		{
			if (type.getName().equals(upcasedName)) return type;
		}
		throw new BiomeTypeNotFound("Biome type '" + name + "' not found.");
	}

	public static boolean testBiomeTypeTags(Biome biome, TagParser.Tag[] tags)
	{
		if (tags.length == 0)
		{
			return false;
		}
		boolean hasMatching = false;
		for (TagParser.Tag tag : tags)
		{
			try
			{
				final boolean res = BiomeDictionary.hasType(biome, fetchBiomeType(tag.value));
				if (tag.exclude && res) return false;
				if (tag.must && !res) return false;
				if (res) hasMatching = true;
			}
			catch (BiomeTypeNotFound ex)
			{
				ex.printStackTrace();
				return false;
			}
		}
		return hasMatching;
	}

	public static boolean testBiomeTypeTagsTable(Biome biome, TagParser.Tag[][] tagTable)
	{
		for (TagParser.Tag[] row : tagTable)
		{
			if (testBiomeTypeTags(biome, row)) return true;
		}
		return false;
	}

	public static boolean testBiomeIdTags(String biomeId, TagParser.Tag[] tags)
	{
		for (TagParser.Tag tag : tags)
		{
			if (tag.value.equals(biomeId))
			{
				return true;
			}
		}
		return false;
	}
}
