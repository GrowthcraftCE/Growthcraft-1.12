package growthcraft.grapes.common.utils;

import growthcraft.grapes.shared.init.GrowthcraftGrapesBlocks;
import net.minecraft.block.Block;

public class GrapeBlockCheck
{
	private GrapeBlockCheck() {}

	/**
	 * Determines if block is a grape vine.
	 *
	 * @param block - block to check
	 * @return true if block is a grape vine, false otherwise
	 */
	public static boolean isGrapeVine(Block block)
	{
		return GrowthcraftGrapesBlocks.grapeVine0.getBlock() == block ||
				GrowthcraftGrapesBlocks.grapeVine1.getBlock() == block;
	}

	/**
	 * Determines if block is a grape vine trunk.
	 *
	 * @param block - block to check
	 * @return true if block is a grape vine trunk, false otherwise
	 */
	public static boolean isGrapeVineTrunk(Block block)
	{
		return GrowthcraftGrapesBlocks.grapeVine1.getBlock() == block;
	}
}