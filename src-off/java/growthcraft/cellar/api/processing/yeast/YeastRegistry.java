package growthcraft.cellar.api.processing.yeast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.core.api.item.ItemKey;
import growthcraft.core.api.item.WeightedItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class YeastRegistry
{
	private Set<ItemKey> yeastList = new HashSet<ItemKey>();
	private Map<BiomeDictionary.Type, Set<WeightedItemStack>> biomeTypeToYeast = new HashMap<BiomeDictionary.Type, Set<WeightedItemStack>>();
	private Map<String, Set<WeightedItemStack>> biomeNameToYeast = new HashMap<String, Set<WeightedItemStack>>();
	private Map<ItemKey, Set<BiomeDictionary.Type>> yeastToBiomeType = new HashMap<ItemKey, Set<BiomeDictionary.Type>>();
	private Map<ItemKey, Set<String>> yeastToBiomeName = new HashMap<ItemKey, Set<String>>();

	private ItemKey stackToKey(@Nonnull ItemStack stack)
	{
		return new ItemKey(stack);
	}

	public void addYeast(@Nonnull ItemStack yeast)
	{
		yeastList.add(stackToKey(yeast));
	}

	public boolean isYeast(@Nullable ItemStack yeast)
	{
		if (yeast == null) return false;
		if (yeast.getItem() == null) return false;
		return yeastList.contains(stackToKey(yeast));
	}

	public void addYeastToBiomeType(@Nonnull ItemStack yeast, int weight, @Nonnull BiomeDictionary.Type type)
	{
		addYeast(yeast);
		if (!biomeTypeToYeast.containsKey(type))
		{
			GrowthcraftCellar.logger.debug("Initializing biome type to yeast set for %s", type);
			biomeTypeToYeast.put(type, new HashSet<WeightedItemStack>());
		}
		final ItemKey yeastKey = stackToKey(yeast);
		if (!yeastToBiomeType.containsKey(yeastKey))
		{
			GrowthcraftCellar.logger.debug("Initializing yeast to biome type set for %s", yeast);
			yeastToBiomeType.put(yeastKey, new HashSet<BiomeDictionary.Type>());
		}
		biomeTypeToYeast.get(type).add(new WeightedItemStack(weight, yeast));
		yeastToBiomeType.get(yeastKey).add(type);
	}

	public void addYeastToBiomeByName(@Nonnull ItemStack yeast, int weight, @Nonnull String name)
	{
		addYeast(yeast);
		final ItemKey yeastKey = stackToKey(yeast);
		if (!yeastToBiomeName.containsKey(yeastKey))
		{
			GrowthcraftCellar.logger.debug("Initializing yeast to biome name set for %s", yeast);
			yeastToBiomeName.put(yeastKey, new HashSet<String>());
		}
		yeastToBiomeName.get(yeastKey).add(name);
		if (!biomeNameToYeast.containsKey(name))
		{
			GrowthcraftCellar.logger.debug("Initializing biome name to yeast set for %s", name);
			biomeNameToYeast.put(name, new HashSet<WeightedItemStack>());
		}
		biomeNameToYeast.get(name).add(new WeightedItemStack(weight, yeast));
	}

	public Set<WeightedItemStack> getYeastListForBiomeType(@Nonnull BiomeDictionary.Type type)
	{
		return biomeTypeToYeast.get(type);
	}

	public Set<WeightedItemStack> getYeastListForBiomeName(@Nonnull String type)
	{
		return biomeNameToYeast.get(type);
	}

	public Set<String> getBiomeNamesForYeast(@Nullable ItemStack yeast)
	{
		if (yeast == null) return null;
		return yeastToBiomeName.get(stackToKey(yeast));
	}

	public Set<BiomeDictionary.Type> getBiomeTypesForYeast(@Nullable ItemStack yeast)
	{
		if (yeast == null) return null;
		return yeastToBiomeType.get(stackToKey(yeast));
	}

	public boolean canYeastFormInBiome(@Nullable ItemStack yeast, @Nullable Biome biome)
	{
		if (yeast == null || biome == null) return false;

		final Set<String> biomeNames = getBiomeNamesForYeast(yeast);
		if (biomeNames != null)
		{
			if (biomeNames.contains(biome.getBiomeName())) return true;
		}

		final Set<BiomeDictionary.Type> yeastBiomeList = getBiomeTypesForYeast(yeast);
		if (yeastBiomeList != null)
		{
			for (BiomeDictionary.Type t : BiomeDictionary.getTypes(biome))
			{
				if (yeastBiomeList.contains(t)) return true;
			}
		}

		return false;
	}
}
