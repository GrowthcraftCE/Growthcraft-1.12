package growthcraft.cellar.shared.processing.heatsource;

import java.util.HashMap;

import javax.annotation.Nonnull;

import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.item.ItemKey;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class HeatSourceRegistry
{
	static class HeatMap extends HashMap<Integer, IHeatSourceBlock>
	{
		public static final long serialVersionUID = 1L;
	}

	static class HeatSourceTree extends HashMap<Block, HeatMap>
	{
		public static final long serialVersionUID = 1L;
	}

	public static final float DEFAULT_HEAT = 1.0f;
	public static final float NO_HEAT = 0.0f;

	private HeatSourceTree heatSources = new HeatSourceTree();

	public void addHeatSource(@Nonnull Block block, int meta, IHeatSourceBlock heat)
	{
		if (!heatSources.containsKey(block))
		{
			heatSources.put(block, new HeatMap());
		}
		final HeatMap map = heatSources.get(block);
		map.put(meta, heat);
		GrowthcraftLogger.getLogger(Reference.MODID).debug("Added new HeatSource block=%s", block);
	}

	public void addHeatSource(@Nonnull Block block, int meta, float heat)
	{
		addHeatSource(block, meta, new GenericHeatSourceBlock(block, heat));
	}

	public void addHeatSource(@Nonnull Block block, int meta)
	{
		addHeatSource(block, meta, DEFAULT_HEAT);
	}

	public void addHeatSource(@Nonnull Block block, IHeatSourceBlock heat)
	{
		addHeatSource(block, ItemKey.WILDCARD_VALUE, heat);
	}

	public void addHeatSource(@Nonnull Block block)
	{
		addHeatSource(block, ItemKey.WILDCARD_VALUE);
	}

	public IHeatSourceBlock getHeatSource(IBlockState state) {
		Block block = state.getBlock();
		int meta = block.getMetaFromState(state);
		return getHeatSource(block, meta);
	}
	
	public IHeatSourceBlock getHeatSource(Block block, int meta)
	{
		final HeatMap map = heatSources.get(block);
		if (map == null) return null;

		IHeatSourceBlock f = map.get(meta);
		if (f == null) f = map.get(ItemKey.WILDCARD_VALUE);
		if (f == null) return null;
		return f;
	}

	public IHeatSourceBlock getHeatSource(Block block)
	{
		return getHeatSource(block, ItemKey.WILDCARD_VALUE);
	}

	public boolean isBlockHeatSource(Block block, int meta)
	{
		final HeatMap map = heatSources.get(block);
		if (map == null) return false;
		return map.get(meta) != null || map.get(ItemKey.WILDCARD_VALUE) != null;
	}

	public boolean isBlockHeatSource(Block block)
	{
		return isBlockHeatSource(block, ItemKey.WILDCARD_VALUE);
	}
}
